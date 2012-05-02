package com.jboss.trading.ws.client.test;

import com.jboss.trading.test.TestConfig;
import com.jboss.trading.ws.client.TradingServicesClient;
import com.jboss.trading.ws.client.model.*;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradingServicesClientIT {

    private static final Logger LOGGER = 
            LoggerFactory.getLogger(TradingServicesClientIT.class);
    
    private TestConfig testConfig;
    
    private ClientTestConfig clientTestConfig;
    
    private TradingServicesClient tradingServicesClient;

    public TradingServicesClientIT() {

        testConfig = TestConfig.getInstance();

        clientTestConfig = ClientTestConfig.getInstance();

        String wsdlUrl = clientTestConfig.getWsdlUrl();

        try {

            tradingServicesClient = 
                    TradingServicesClient.getInstance(wsdlUrl);
        } 
        catch (MalformedURLException ex) {

            LOGGER.error(ex.getMessage(), ex);
        }
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPlaceBuyLimitOrder() throws Exception {

        try {

            tradingServicesClient.placeLimitOrder(
                    testConfig.getStockHolderId(),
                    TransactionType.BUY, testConfig.getQuantity(),
                    testConfig.getStockSymbol(), testConfig.getPrice());
        } 
        catch (PlaceOrderException_Exception ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testPlaceBuyMarketOrder() throws Exception {

        tradingServicesClient.placeMarketOrder(
                testConfig.getStockHolderId(), TransactionType.BUY, 
                testConfig.getQuantity(), testConfig.getStockSymbol());
    }

    @Test
    public void testPlaceSellLimitOrder() throws Exception {

        try {

            tradingServicesClient.placeLimitOrder(
                    testConfig.getStockHolderId(), TransactionType.SELL, 
                    testConfig.getQuantity(), testConfig.getStockSymbol(), 
                    testConfig.getPrice());
        } 
        catch (PlaceOrderException_Exception ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testPlaceSellMarketOrder() throws Exception {

        tradingServicesClient.placeMarketOrder(
                testConfig.getStockHolderId(), TransactionType.SELL, 
                testConfig.getQuantity(), testConfig.getStockSymbol());
    }

    @Test
    public void testViewLimitOrder() throws Exception {

        AtomicInteger limitOrderIdCounter = 
                testConfig.getLimitOrderIdCounter();

        LimitOrder limitOrder = 
                tradingServicesClient.viewLimitOrder(
                        limitOrderIdCounter.getAndIncrement());

        Assert.assertNotNull(limitOrder);
    }

    @Test
    public void testViewMarketOrder() throws Exception {

        AtomicInteger marketOrderIdCounter = 
                testConfig.getMarketOrderIdCounter();

        MarketOrder marketOrder = 
                tradingServicesClient.viewMarketOrder(
                        marketOrderIdCounter.getAndIncrement());

        Assert.assertNotNull(marketOrder);
    }

    @Test
    public void testViewStockHolderLimitOrders() throws Exception {

        List<LimitOrder> limitOrders =
                tradingServicesClient.viewStockHolderLimitOrders(
                        testConfig.getStockHolderId(), 
                        testConfig.getMaxLimitOrderResults());

        Assert.assertNotNull(limitOrders);
        Assert.assertFalse(limitOrders.isEmpty());
    }

    @Test
    public void testViewStockHolderMarketOrders() throws Exception {

        List<MarketOrder> marketOrders =
                tradingServicesClient.viewStockHolderMarketOrders(
                        testConfig.getStockHolderId(), 
                        testConfig.getMaxMarketOrderResults());

        Assert.assertNotNull(marketOrders);
        Assert.assertFalse(marketOrders.isEmpty());
    }

    @Test
    public void testCancelLimitOrder() throws Exception {

        AtomicInteger limitOrderIdCounter = 
                testConfig.getLimitOrderIdCounter();

        try {

            tradingServicesClient.cancelLimitOrder(
                    limitOrderIdCounter.decrementAndGet());
        } 
        catch (LimitOrderNotFoundException_Exception ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testCancelMarketOrder() throws Exception {

        AtomicInteger marketOrderIdCounter = 
                testConfig.getMarketOrderIdCounter();

        try {

            tradingServicesClient.cancelMarketOrder(
                    marketOrderIdCounter.decrementAndGet());
        } 
        catch (MarketOrderNotFoundException_Exception ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testcancelLimitOrderFailure() throws Exception {

        AtomicInteger limitOrderIdCounter = 
                testConfig.getLimitOrderIdCounter();

        int limitOrderId = limitOrderIdCounter.getAndIncrement();

        try {

            tradingServicesClient.cancelLimitOrder(limitOrderId);

            Assert.fail();
        } 
        catch (LimitOrderNotFoundException_Exception ex) {

            Assert.assertEquals(
                    String.format(
                            testConfig.getCancelLimitOrderErrorMsgPrefix(), 
                            limitOrderId), ex.getMessage());
        }
    }

    @Test
    public void testcancelMarketOrderFailure() throws Exception {

        AtomicInteger marketOrderCounter = 
                testConfig.getMarketOrderIdCounter();

        int marketOrderId = marketOrderCounter.getAndIncrement();

        try {

            tradingServicesClient.cancelMarketOrder(marketOrderId);

            Assert.fail();
        } 
        catch (MarketOrderNotFoundException_Exception ex) {

            Assert.assertEquals(
                    String.format(
                            testConfig.getCancelMarketOrderErrorMsgPrefix(), 
                            marketOrderId), ex.getMessage());
        }
    }
}

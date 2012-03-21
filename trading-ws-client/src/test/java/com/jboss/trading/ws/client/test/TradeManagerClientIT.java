package com.jboss.trading.ws.client.test;

import com.jboss.trading.test.TestConfig;
import com.jboss.trading.ws.client.TradeManagerClient;
import com.jboss.trading.ws.client.model.*;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeManagerClientIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeManagerClientIT.class);
    
    private TestConfig testConfig;
    
    private ClientTestConfig clientTestConfig;
    
    private TradeManagerClient tradeManagerClient;

    public TradeManagerClientIT() {

        testConfig = TestConfig.getInstance();

        clientTestConfig = ClientTestConfig.getInstance();

        String wsdlUrl = clientTestConfig.getWsdlUrl();

        try {

            tradeManagerClient = TradeManagerClient.getInstance(wsdlUrl);
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

            tradeManagerClient.placeLimitOrder(testConfig.getStockHolderId(),
                    TransactionType.BUY, testConfig.getQuantity(),
                    testConfig.getStockSymbol(), testConfig.getPrice());
        } 
        catch (PlaceOrderException_Exception ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testPlaceBuyMarketOrder() throws Exception {

        tradeManagerClient.placeMarketOrder(testConfig.getStockHolderId(),
                TransactionType.BUY, testConfig.getQuantity(),
                testConfig.getStockSymbol());
    }

    @Test
    public void testPlaceSellLimitOrder() throws Exception {

        try {

            tradeManagerClient.placeLimitOrder(testConfig.getStockHolderId(),
                    TransactionType.SELL, testConfig.getQuantity(),
                    testConfig.getStockSymbol(), testConfig.getPrice());
        } 
        catch (PlaceOrderException_Exception ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testPlaceSellMarketOrder() throws Exception {

        tradeManagerClient.placeMarketOrder(testConfig.getStockHolderId(),
                TransactionType.SELL, testConfig.getQuantity(),
                testConfig.getStockSymbol());
    }

    @Test
    public void testViewLimitOrder() throws Exception {

        AtomicInteger limitOrderIdCounter = testConfig.getLimitOrderIdCounter();

        LimitOrder limitOrder = tradeManagerClient.viewLimitOrder(limitOrderIdCounter.getAndIncrement());

        Assert.assertNotNull(limitOrder);
    }

    @Test
    public void testViewMarketOrder() throws Exception {

        AtomicInteger marketOrderIdCounter = testConfig.getMarketOrderIdCounter();

        MarketOrder marketOrder = tradeManagerClient.viewMarketOrder(marketOrderIdCounter.getAndIncrement());

        Assert.assertNotNull(marketOrder);
    }

    @Test
    public void testViewStockHolderLimitOrders() throws Exception {

        List<LimitOrder> limitOrders =
                tradeManagerClient.viewStockHolderLimitOrders(testConfig.getStockHolderId(), testConfig.getMaxLimitOrderResults());

        Assert.assertNotNull(limitOrders);
        Assert.assertFalse(limitOrders.isEmpty());
    }

    @Test
    public void testViewStockHolderMarketOrders() throws Exception {

        List<MarketOrder> marketOrders =
                tradeManagerClient.viewStockHolderMarketOrders(testConfig.getStockHolderId(), testConfig.getMaxMarketOrderResults());

        Assert.assertNotNull(marketOrders);
        Assert.assertFalse(marketOrders.isEmpty());
    }

    @Test
    public void testCancelLimitOrder() throws Exception {

        AtomicInteger limitOrderIdCounter = testConfig.getLimitOrderIdCounter();

        try {

            tradeManagerClient.cancelLimitOrder(limitOrderIdCounter.decrementAndGet());
        } 
        catch (LimitOrderNotFoundException_Exception ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testCancelMarketOrder() throws Exception {

        AtomicInteger marketOrderIdCounter = testConfig.getMarketOrderIdCounter();

        try {

            tradeManagerClient.cancelMarketOrder(marketOrderIdCounter.decrementAndGet());
        } 
        catch (MarketOrderNotFoundException_Exception ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testcancelLimitOrderFailure() throws Exception {

        AtomicInteger limitOrderIdCounter = testConfig.getLimitOrderIdCounter();

        int limitOrderId = limitOrderIdCounter.getAndIncrement();

        try {

            tradeManagerClient.cancelLimitOrder(limitOrderId);

            Assert.fail();
        } 
        catch (LimitOrderNotFoundException_Exception ex) {

            Assert.assertEquals(String.format(testConfig.getCancelLimitOrderErrorMsgPrefix(), limitOrderId), ex.getMessage());
        }
    }

    @Test
    public void testcancelMarketOrderFailure() throws Exception {

        AtomicInteger marketOrderCounter = testConfig.getMarketOrderIdCounter();

        int marketOrderId = marketOrderCounter.getAndIncrement();

        try {

            tradeManagerClient.cancelMarketOrder(marketOrderId);

            Assert.fail();
        } 
        catch (MarketOrderNotFoundException_Exception ex) {

            Assert.assertEquals(String.format(testConfig.getCancelMarketOrderErrorMsgPrefix(), marketOrderId), ex.getMessage());
        }
    }
}

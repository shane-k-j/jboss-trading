package com.jboss.trading.services.test;

import com.jboss.trading.api.TradingManager;
import com.jboss.trading.api.exception.LimitOrderNotFoundException;
import com.jboss.trading.api.exception.MarketOrderNotFoundException;
import com.jboss.trading.api.exception.PlaceOrderException;
import com.jboss.trading.api.model.LimitOrder;
import com.jboss.trading.api.model.MarketOrder;
import com.jboss.trading.api.model.TransactionType;
import com.jboss.trading.test.TestConfig;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class TradingManagerLocalIT {

    private TestConfig testConfig;
    
    @Inject 
    @Named("TradingManagerBean")
    TradingManager tradingManager;

    public TradingManagerLocalIT() {

        testConfig = TestConfig.getInstance();
    }

    @Deployment
    public static JavaArchive createTestArchive() {

        return ShrinkWrap.create(
                JavaArchive.class, "trading-services.jar")
                .addPackage("com.jboss.trading.api")
                .addPackage("com.jboss.trading.api.exception")
                .addPackage("com.jboss.trading.api.model")
                .addPackage("com.jboss.trading.services")
                .addPackage("com.jboss.trading.services.persistence")
                .addPackage("com.jboss.trading.services.persistence.factory")
                .addPackage("com.jboss.trading.services.util")
                .addPackage("com.jboss.trading.test")
                .addPackage("org.slf4j.impl")
                .addAsResource("META-INF/beans.xml")
                .addAsResource("META-INF/jboss-deployment-structure.xml")
                .addAsResource("META-INF/persistence.xml")
                .addAsResource("META-INF/trading-jms.xml")
                .addAsResource("import.sql")
                .addAsResource("test.properties");
    }

    @Test
    public void testPlaceBuyLimitOrder() throws Exception {

        try {

        	tradingManager.placeLimitOrder(testConfig.getStockHolderId(),
                    TransactionType.BUY, testConfig.getQuantity(),
                    testConfig.getStockSymbol(), testConfig.getPrice());
        } 
        catch (PlaceOrderException ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testPlaceBuyMarketOrder() throws Exception {

    	tradingManager.placeMarketOrder(testConfig.getStockHolderId(),
                TransactionType.BUY, testConfig.getQuantity(),
                testConfig.getStockSymbol());
    }

    @Test
    public void testPlaceSellLimitOrder() throws Exception {

        try {

        	tradingManager.placeLimitOrder(testConfig.getStockHolderId(),
                    TransactionType.SELL, testConfig.getQuantity(),
                    testConfig.getStockSymbol(), testConfig.getPrice());
        } 
        catch (PlaceOrderException ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testPlaceSellMarketOrder() throws Exception {

    	tradingManager.placeMarketOrder(testConfig.getStockHolderId(),
                TransactionType.SELL, testConfig.getQuantity(),
                testConfig.getStockSymbol());
    }

    @Test
    public void testViewLimitOrder() throws Exception {

        AtomicInteger limitOrderIdCounter = testConfig.getLimitOrderIdCounter();

        LimitOrder limitOrder = tradingManager.viewLimitOrder(limitOrderIdCounter.getAndIncrement());

        Assert.assertNotNull(limitOrder);
    }

    @Test
    public void testViewMarketOrder() throws Exception {

        AtomicInteger marketOrderIdCounter = testConfig.getMarketOrderIdCounter();

        MarketOrder marketOrder = tradingManager.viewMarketOrder(marketOrderIdCounter.getAndIncrement());

        Assert.assertNotNull(marketOrder);
    }

    @Test
    public void testViewStockHolderLimitOrders() throws Exception {

        List<LimitOrder> limitOrders =
        		tradingManager.viewStockHolderLimitOrders(testConfig.getStockHolderId(), testConfig.getMaxLimitOrderResults());

        Assert.assertNotNull(limitOrders);
        Assert.assertFalse(limitOrders.isEmpty());
    }

    @Test
    public void testViewStockHolderMarketOrders() throws Exception {

        List<MarketOrder> marketOrders =
        		tradingManager.viewStockHolderMarketOrders(testConfig.getStockHolderId(), testConfig.getMaxMarketOrderResults());

        Assert.assertNotNull(marketOrders);
        Assert.assertFalse(marketOrders.isEmpty());
    }

    @Test
    public void testCancelLimitOrder() throws Exception {

        AtomicInteger limitOrderIdCounter = testConfig.getLimitOrderIdCounter();

        try {

        	tradingManager.cancelLimitOrder(limitOrderIdCounter.decrementAndGet());
        } 
        catch (LimitOrderNotFoundException ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testCancelMarketOrder() throws Exception {

        AtomicInteger marketOrderIdCounter = testConfig.getMarketOrderIdCounter();

        try {

        	tradingManager.cancelMarketOrder(marketOrderIdCounter.decrementAndGet());
        } 
        catch (MarketOrderNotFoundException ex) {

            Assert.fail(ex.getMessage());
        }
    }

    @Test
    public void testcancelLimitOrderFailure() throws Exception {

        AtomicInteger limitOrderIdCounter = testConfig.getLimitOrderIdCounter();

        int limitOrderId = limitOrderIdCounter.getAndIncrement();

        try {

        	tradingManager.cancelLimitOrder(limitOrderId);

            Assert.fail();
        } 
        catch (LimitOrderNotFoundException ex) {

            Assert.assertEquals(String.format(testConfig.getCancelLimitOrderErrorMsgPrefix(), limitOrderId), ex.getMessage());
        }
    }

    @Test
    public void testcancelMarketOrderFailure() throws Exception {

        AtomicInteger marketOrderCounter = testConfig.getMarketOrderIdCounter();

        int marketOrderId = marketOrderCounter.getAndIncrement();

        try {

        	tradingManager.cancelMarketOrder(marketOrderId);

            Assert.fail();
        } 
        catch (MarketOrderNotFoundException ex) {

            Assert.assertEquals(String.format(testConfig.getCancelMarketOrderErrorMsgPrefix(), marketOrderId), ex.getMessage());
        }
    }
}

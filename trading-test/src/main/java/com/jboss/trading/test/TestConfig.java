package com.jboss.trading.test;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestConfig.class);
    
    private static final String PROPS_FILE_NAME = "test.properties";
    
    private static final String PROPS_CANCEL_LIMIT_ORDER_ERROR_MSG_PREFIX =
            "cancel.limit.order.error.msg.prefix";
    
    private static final String PROPS_CANCEL_MARKET_ORDER_ERROR_MSG_PREFIX =
            "cancel.market.order.error.msg.prefix";
    
    private static final String PROPS_INITIAL_LIMIT_ORDER_ID = "initial.limit.order.id";
    private static final String PROPS_INITIAL_MARKET_ORDER_ID = "initial.market.order.id";
    private static final String PROPS_MAX_LIMIT_ORDER_RESULTS = "max.limit.order.results";
    private static final String PROPS_MAX_MARKET_ORDER_RESULTS = "max.market.order.results";
    private static final String PROPS_PRICE = "price";
    private static final String PROPS_QUANTITY = "quantity";
    private static final String PROPS_STOCK_HOLDER_ID = "stock.holder.id";
    private static final String PROPS_STOCK_SYMBOL = "stock.symbol";
    
    private Integer stockHolderId;
    
    private Integer quantity;
    
    private String stockSymbol;
    
    private Float price;
    
    private Integer maxLimitOrderResults;
    
    private Integer maxMarketOrderResults;
    
    private String cancelLimitOrderErrorMsgPrefix;
    
    private String cancelMarketOrderErrorMsgPrefix;
    
    private AtomicInteger limitOrderIdCounter;
    
    private AtomicInteger marketOrderIdCounter;

    private TestConfig() {

        Properties props = new Properties();

        try {

            props.load(TestConfig.class.getClassLoader().getResourceAsStream(PROPS_FILE_NAME));

            stockHolderId = Integer.parseInt(props.getProperty(PROPS_STOCK_HOLDER_ID));

            quantity = Integer.parseInt(props.getProperty(PROPS_QUANTITY));

            stockSymbol = props.getProperty(PROPS_STOCK_SYMBOL);

            price = Float.parseFloat(props.getProperty(PROPS_PRICE));

            maxLimitOrderResults = Integer.parseInt(props.getProperty(PROPS_MAX_LIMIT_ORDER_RESULTS));
            maxMarketOrderResults = Integer.parseInt(props.getProperty(PROPS_MAX_MARKET_ORDER_RESULTS));

            cancelLimitOrderErrorMsgPrefix = props.getProperty(PROPS_CANCEL_LIMIT_ORDER_ERROR_MSG_PREFIX);
            cancelMarketOrderErrorMsgPrefix = props.getProperty(PROPS_CANCEL_MARKET_ORDER_ERROR_MSG_PREFIX);

            Integer initialLimitOrderId = Integer.parseInt(props.getProperty(PROPS_INITIAL_LIMIT_ORDER_ID));
            Integer initialMarketOrderId = Integer.parseInt(props.getProperty(PROPS_INITIAL_MARKET_ORDER_ID));

            limitOrderIdCounter = new AtomicInteger(initialLimitOrderId);
            marketOrderIdCounter = new AtomicInteger(initialMarketOrderId);
        } 
        catch (IOException ex) {

            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public static TestConfig getInstance() {

        return TestConfigHolder.INSTANCE;
    }

    public String getCancelLimitOrderErrorMsgPrefix() {
        
        return cancelLimitOrderErrorMsgPrefix;
    }

    public String getCancelMarketOrderErrorMsgPrefix() {
        
        return cancelMarketOrderErrorMsgPrefix;
    }

    public AtomicInteger getLimitOrderIdCounter() {
        
        return limitOrderIdCounter;
    }

    public AtomicInteger getMarketOrderIdCounter() {
        
        return marketOrderIdCounter;
    }

    public Integer getMaxLimitOrderResults() {
        
        return maxLimitOrderResults;
    }

    public Integer getMaxMarketOrderResults() {
        
        return maxMarketOrderResults;
    }

    public Float getPrice() {
        
        return price;
    }

    public Integer getQuantity() {
        
        return quantity;
    }

    public Integer getStockHolderId() {
        
        return stockHolderId;
    }

    public String getStockSymbol() {
        
        return stockSymbol;
    }

    private static class TestConfigHolder {

        public static final TestConfig INSTANCE = new TestConfig();
    }
}

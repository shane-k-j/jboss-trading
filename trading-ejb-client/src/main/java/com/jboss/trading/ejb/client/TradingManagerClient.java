package com.jboss.trading.ejb.client;

import com.jboss.trading.api.TradingManager;
import com.jboss.trading.api.exception.LimitOrderNotFoundException;
import com.jboss.trading.api.exception.MarketOrderNotFoundException;
import com.jboss.trading.api.exception.PlaceOrderException;
import com.jboss.trading.api.model.LimitOrder;
import com.jboss.trading.api.model.MarketOrder;
import com.jboss.trading.api.model.TransactionType;

import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TradingManagerClient implements TradingManager {

    private static final String PROP_PKGS_VALUE = 
            "org.jboss.ejb.client.naming";
    
    private TradingManager tradingManager;

    public TradingManagerClient(TradingManager tradingManager) {

        this.tradingManager = tradingManager;
    }

    public static TradingManagerClient getInstance(String jndiName) 
            throws NamingException {

        Properties properties = new Properties();

        properties.put(Context.URL_PKG_PREFIXES, PROP_PKGS_VALUE);

        InitialContext ctx = new InitialContext(properties);

        TradingManager tradingManager = 
                (TradingManager) ctx.lookup(jndiName);

        TradingManagerClient tradingManagerClient = 
                new TradingManagerClient(tradingManager);

        return tradingManagerClient;
    }

    @Override
    public void cancelLimitOrder(Integer limitOrderId) 
            throws LimitOrderNotFoundException {

    	tradingManager.cancelLimitOrder(limitOrderId);
    }

    @Override
    public void cancelMarketOrder(Integer marketOrderId) 
            throws MarketOrderNotFoundException {

    	tradingManager.cancelMarketOrder(marketOrderId);
    }

    @Override
    public void placeLimitOrder(
            Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol, Float price) 
            throws PlaceOrderException {

    	tradingManager.placeLimitOrder(
                stockHolderId, transactionType, quantity, 
                stockSymbol, price);
    }

    @Override
    public void placeMarketOrder(
            Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol) {

    	tradingManager.placeMarketOrder(
                stockHolderId, transactionType, quantity, stockSymbol);
    }

    @Override
    public LimitOrder viewLimitOrder(Integer limitOrderId) 
            throws LimitOrderNotFoundException {

        return tradingManager.viewLimitOrder(limitOrderId);
    }

    @Override
    public MarketOrder viewMarketOrder(Integer marketOrderId) 
            throws MarketOrderNotFoundException {

        return tradingManager.viewMarketOrder(marketOrderId);
    }

    @Override
    public List<LimitOrder> viewStockHolderLimitOrders(
            Integer stockHolderId, Integer numberLimitOrders) {

        return tradingManager.viewStockHolderLimitOrders(
                stockHolderId, numberLimitOrders);
    }

    @Override
    public List<MarketOrder> viewStockHolderMarketOrders(
            Integer stockHolderId, Integer numberMarketOrders) {

        return tradingManager.viewStockHolderMarketOrders(
                stockHolderId, numberMarketOrders);
    }
}

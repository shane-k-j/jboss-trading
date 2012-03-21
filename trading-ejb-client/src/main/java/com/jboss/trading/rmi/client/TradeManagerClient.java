package com.jboss.trading.rmi.client;

import com.jboss.trading.api.TradeManager;
import com.jboss.trading.api.exception.LimitOrderNotFoundException;
import com.jboss.trading.api.exception.MarketOrderNotFoundException;
import com.jboss.trading.api.exception.PlaceOrderException;
import com.jboss.trading.api.model.LimitOrder;
import com.jboss.trading.api.model.MarketOrder;
import com.jboss.trading.api.model.TransactionType;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TradeManagerClient implements TradeManager {

    private static final String PROP_INITIAL = "java.naming.factory.initial";
    private static final String PROP_PKGS = "java.naming.factory.url.pkgs";
    private static final String PROP_URL = "java.naming.provider.url";
    
    private static final String PROP_INITIAL_VALUE = "org.jnp.interfaces.NamingContextFactory";
    private static final String PROP_PKGS_VALUE = "org.jboss.naming:org.jnp.interfaces";
    private static final String PROP_URL_VALUE_PREFIX = "jnp://";
    
    private TradeManager tradeManager;

    public TradeManagerClient(TradeManager tradeManager) {

        this.tradeManager = tradeManager;
    }

    public static TradeManager getInstance(String hostPort, String jndiName) throws NamingException {

        Properties properties = new Properties();

        properties.put(PROP_INITIAL, PROP_INITIAL_VALUE);
        properties.put(PROP_PKGS, PROP_PKGS_VALUE);
        properties.put(PROP_URL, PROP_URL_VALUE_PREFIX + hostPort);

        InitialContext ctx = new InitialContext(properties);

        TradeManager tradeManagerRemote = (TradeManager) ctx.lookup(jndiName);

        TradeManager tradeManager = new TradeManagerClient(tradeManagerRemote);

        return tradeManager;
    }

    @Override
    public void cancelLimitOrder(Integer limitOrderId) throws LimitOrderNotFoundException {

        tradeManager.cancelLimitOrder(limitOrderId);
    }

    @Override
    public void cancelMarketOrder(Integer marketOrderId) throws MarketOrderNotFoundException {

        tradeManager.cancelMarketOrder(marketOrderId);
    }

    @Override
    public void placeLimitOrder(Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol, Float price) throws PlaceOrderException {

        tradeManager.placeLimitOrder(stockHolderId, transactionType, quantity, stockSymbol, price);
    }

    @Override
    public void placeMarketOrder(Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol) {

        tradeManager.placeMarketOrder(stockHolderId, transactionType, quantity, stockSymbol);
    }

    @Override
    public LimitOrder viewLimitOrder(Integer limitOrderId) throws LimitOrderNotFoundException {

        return tradeManager.viewLimitOrder(limitOrderId);
    }

    @Override
    public MarketOrder viewMarketOrder(Integer marketOrderId) throws MarketOrderNotFoundException {

        return tradeManager.viewMarketOrder(marketOrderId);
    }

    @Override
    public List<LimitOrder> viewStockHolderLimitOrders(Integer stockHolderId, Integer numberLimitOrders) {

        return tradeManager.viewStockHolderLimitOrders(stockHolderId, numberLimitOrders);
    }

    @Override
    public List<MarketOrder> viewStockHolderMarketOrders(Integer stockHolderId, Integer numberMarketOrders) {

        return tradeManager.viewStockHolderMarketOrders(stockHolderId, numberMarketOrders);
    }
}

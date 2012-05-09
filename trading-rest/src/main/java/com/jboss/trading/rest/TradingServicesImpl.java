package com.jboss.trading.rest;

import com.jboss.trading.api.TradingManager;
import com.jboss.trading.api.exception.LimitOrderNotFoundException;
import com.jboss.trading.api.exception.MarketOrderNotFoundException;
import com.jboss.trading.api.exception.PlaceOrderException;
import com.jboss.trading.api.model.LimitOrder;
import com.jboss.trading.api.model.MarketOrder;
import com.jboss.trading.api.model.TransactionType;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

public class TradingServicesImpl implements TradingServices {
    
    @Inject 
    @Named("TradingManagerBean")
    TradingManager tradingManager;
    
    public void cancelLimitOrder(Integer limitOrderId)
            throws LimitOrderNotFoundException {

    	tradingManager.cancelLimitOrder(limitOrderId);
    }

    public void cancelMarketOrder(Integer marketOrderId)
            throws MarketOrderNotFoundException {

    	tradingManager.cancelMarketOrder(marketOrderId);
    }

    public void placeLimitOrder(
            Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol, Float price) 
            throws PlaceOrderException {

    	tradingManager.placeLimitOrder(
                stockHolderId, transactionType, quantity, 
                stockSymbol, price);
    }

    public void placeMarketOrder(
            Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol) {

    	tradingManager.placeMarketOrder(
                stockHolderId, transactionType, quantity,
                stockSymbol);
    }

    public LimitOrder viewLimitOrder(Integer limitOrderId)
            throws LimitOrderNotFoundException {

        return tradingManager.viewLimitOrder(limitOrderId);
    }

    public MarketOrder viewMarketOrder(Integer marketOrderId)
            throws MarketOrderNotFoundException {

        return tradingManager.viewMarketOrder(marketOrderId);
    }

    public List<LimitOrder> viewStockHolderLimitOrders(
            Integer stockHolderId, Integer numberLimitOrders) {
        
        return tradingManager.viewStockHolderLimitOrders(
                stockHolderId, numberLimitOrders);
    }

    public List<MarketOrder> viewStockHolderMarketOrders(
            Integer stockHolderId, Integer numberMarketOrders) {

        return tradingManager.viewStockHolderMarketOrders(
                stockHolderId, numberMarketOrders);
    }
}

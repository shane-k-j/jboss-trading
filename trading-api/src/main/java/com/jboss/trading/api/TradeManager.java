package com.jboss.trading.api;

import com.jboss.trading.api.exception.LimitOrderNotFoundException;
import com.jboss.trading.api.exception.MarketOrderNotFoundException;
import com.jboss.trading.api.exception.PlaceOrderException;
import com.jboss.trading.api.model.LimitOrder;
import com.jboss.trading.api.model.MarketOrder;
import com.jboss.trading.api.model.TransactionType;
import java.util.List;

public interface TradeManager {

    void placeLimitOrder(Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol, Float price) 
            throws PlaceOrderException;

    void placeMarketOrder(Integer stockHolderId, 
            TransactionType transactionType, Integer quantity, 
            String stockSymbol);

    void cancelLimitOrder(Integer limitOrderId) 
            throws LimitOrderNotFoundException;

    void cancelMarketOrder(Integer marketOrderId)
            throws MarketOrderNotFoundException;

    LimitOrder viewLimitOrder(Integer limitOrderId)
            throws LimitOrderNotFoundException;

    MarketOrder viewMarketOrder(Integer marketOrderId)
            throws MarketOrderNotFoundException;

    List<LimitOrder> viewStockHolderLimitOrders(Integer stockHolderId, 
            Integer numberLimitOrders);

    List<MarketOrder> viewStockHolderMarketOrders(Integer stockHolderId, 
            Integer numberMarketOrders);
}

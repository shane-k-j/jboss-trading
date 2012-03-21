package com.jboss.trading.ws.client;

import com.jboss.trading.ws.client.model.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TradeManagerClient {

    TradeManagerBean tradeManager;

    public TradeManagerClient(TradeManagerBean tradeManager) {

        this.tradeManager = tradeManager;
    }

    public static TradeManagerClient getInstance(String urlStr) throws MalformedURLException {

        URL url = new URL(urlStr);

        TradeManagerBeanService service = new TradeManagerBeanService(url);

        TradeManagerBean tradeManager = service.getTradeManagerBeanPort();

        TradeManagerClient client = new TradeManagerClient(tradeManager);

        return client;
    }

    public void cancelLimitOrder(Integer limitOrderId) throws LimitOrderNotFoundException_Exception {

        tradeManager.cancelLimitOrder(limitOrderId);
    }

    public void cancelMarketOrder(Integer marketOrderId) throws MarketOrderNotFoundException_Exception {

        tradeManager.cancelMarketOrder(marketOrderId);
    }

    public void placeLimitOrder(Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol, Float price) throws PlaceOrderException_Exception {

        tradeManager.placeLimitOrder(stockHolderId, transactionType, quantity, stockSymbol, price);
    }

    public void placeMarketOrder(Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol) {

        tradeManager.placeMarketOrder(stockHolderId, transactionType, quantity, stockSymbol);
    }

    public LimitOrder viewLimitOrder(Integer limitOrderId) throws LimitOrderNotFoundException_Exception {

        return tradeManager.viewLimitOrder(limitOrderId);
    }

    public MarketOrder viewMarketOrder(Integer marketOrderId) throws MarketOrderNotFoundException_Exception {

        return tradeManager.viewMarketOrder(marketOrderId);
    }

    public List<LimitOrder> viewStockHolderLimitOrders(Integer stockHolderId, Integer numberLimitOrders) {

        return tradeManager.viewStockHolderLimitOrders(stockHolderId, numberLimitOrders);
    }

    public List<MarketOrder> viewStockHolderMarketOrders(Integer stockHolderId, Integer numberMarketOrders) {

        return tradeManager.viewStockHolderMarketOrders(stockHolderId, numberMarketOrders);
    }
}

package com.jboss.trading.ws.client;

import com.jboss.trading.ws.client.model.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TradingServicesClient {

    TradingServices tradingServices;

    public TradingServicesClient(TradingServices tradingServices) {

        this.tradingServices = tradingServices;
    }

    public static TradingServicesClient getInstance(String urlStr) 
            throws MalformedURLException {

        URL url = new URL(urlStr);

        TradingServicesService service = 
                new TradingServicesService(url);

        TradingServices tradingServices = 
                service.getTradingServicesPort();

        TradingServicesClient client = 
                new TradingServicesClient(tradingServices);

        return client;
    }

    public void cancelLimitOrder(Integer limitOrderId) 
            throws LimitOrderNotFoundException_Exception {

        tradingServices.cancelLimitOrder(limitOrderId);
    }

    public void cancelMarketOrder(Integer marketOrderId) 
            throws MarketOrderNotFoundException_Exception {

        tradingServices.cancelMarketOrder(marketOrderId);
    }

    public void placeLimitOrder(
            Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol, Float price) 
            throws PlaceOrderException_Exception {

        tradingServices.placeLimitOrder(
                stockHolderId, transactionType, 
                quantity, stockSymbol, price);
    }

    public void placeMarketOrder(
            Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol) {

        tradingServices.placeMarketOrder(
                stockHolderId, transactionType, 
                quantity, stockSymbol);
    }

    public LimitOrder viewLimitOrder(Integer limitOrderId) 
            throws LimitOrderNotFoundException_Exception {

        return tradingServices.viewLimitOrder(limitOrderId);
    }

    public MarketOrder viewMarketOrder(Integer marketOrderId) 
            throws MarketOrderNotFoundException_Exception {

        return tradingServices.viewMarketOrder(marketOrderId);
    }

    public List<LimitOrder> viewStockHolderLimitOrders(
            Integer stockHolderId, Integer numberLimitOrders) {

        return tradingServices.viewStockHolderLimitOrders(
                stockHolderId, numberLimitOrders);
    }

    public List<MarketOrder> viewStockHolderMarketOrders(
            Integer stockHolderId, Integer numberMarketOrders) {

        return tradingServices.viewStockHolderMarketOrders(
                stockHolderId, numberMarketOrders);
    }
}

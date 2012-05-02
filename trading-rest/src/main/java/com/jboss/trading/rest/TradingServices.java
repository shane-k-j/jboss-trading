package com.jboss.trading.rest;

import com.jboss.trading.api.TradeManager;
import com.jboss.trading.api.exception.LimitOrderNotFoundException;
import com.jboss.trading.api.exception.MarketOrderNotFoundException;
import com.jboss.trading.api.exception.PlaceOrderException;
import com.jboss.trading.api.model.LimitOrder;
import com.jboss.trading.api.model.MarketOrder;
import com.jboss.trading.api.model.TransactionType;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/services")
public class TradingServices {
    
    @Inject 
    @Named("TradeManagerBean")
    TradeManager tradeManager;
    
    @DELETE
    @Path("/orders/limit/{limitOrderId}")
    @Produces("application/json")
    public void cancelLimitOrder(
            @PathParam("limitOrderId") Integer limitOrderId)
            throws LimitOrderNotFoundException {

        tradeManager.cancelLimitOrder(limitOrderId);
    }

    @DELETE
    @Path("/orders/market/{marketOrderId}")
    @Produces("application/json")
    public void cancelMarketOrder(
            @PathParam("marketOrderId") Integer marketOrderId)
            throws MarketOrderNotFoundException {

        tradeManager.cancelMarketOrder(marketOrderId);
    }

    @POST
    @Produces("application/json")
    @Path("/orders/limit")
    public void placeLimitOrder(
            @FormParam("stockHolderId") Integer stockHolderId,
            @FormParam("transactionType") TransactionType transactionType,
            @FormParam("quantity") Integer quantity,
            @FormParam("stockSymbol") String stockSymbol,
            @FormParam("price") Float price) 
            throws PlaceOrderException {

        tradeManager.placeLimitOrder(
                stockHolderId, transactionType, quantity, 
                stockSymbol, price);
    }

    @POST
    @Produces("application/json")
    @Path("/orders/market")
    public void placeMarketOrder(
            @FormParam("stockHolderId") Integer stockHolderId,
            @FormParam("transactionType") TransactionType transactionType,
            @FormParam("quantity") Integer quantity,
            @FormParam("stockSymbol") String stockSymbol) {

        tradeManager.placeMarketOrder(
                stockHolderId, transactionType, quantity,
                stockSymbol);
    }

    @GET
    @Path("/orders/limit/{limitOrderId}")
    @Produces("application/json")
    public LimitOrder viewLimitOrder(
            @PathParam("limitOrderId") Integer limitOrderId)
            throws LimitOrderNotFoundException {

        return tradeManager.viewLimitOrder(limitOrderId);
    }

    @GET
    @Path("/orders/market/{marketOrderId}")
    @Produces("application/json")
    public MarketOrder viewMarketOrder(
            @PathParam("marketOrderId") Integer marketOrderId)
            throws MarketOrderNotFoundException {

        return tradeManager.viewMarketOrder(marketOrderId);
    }

    @GET
    @Path("/stockholders/{stockHolderId}/orders/limit/last/{numberLimitOrders}")
    @Produces("application/json")
    public List<LimitOrder> viewStockHolderLimitOrders(
            @PathParam("stockHolderId") Integer stockHolderId,
            @PathParam("numberLimitOrders") Integer numberLimitOrders) {
        
        return tradeManager.viewStockHolderLimitOrders(
                stockHolderId, numberLimitOrders);
    }

    @GET
    @Path("/stockholders/{stockHolderId}/orders/market/last/{numberLimitOrders}")
    @Produces("application/json")
    public List<MarketOrder> viewStockHolderMarketOrders(
            @PathParam("stockHolderId") Integer stockHolderId,
            @PathParam("numberLimitOrders") Integer numberMarketOrders) {

        return tradeManager.viewStockHolderMarketOrders(
                stockHolderId, numberMarketOrders);
    }
}

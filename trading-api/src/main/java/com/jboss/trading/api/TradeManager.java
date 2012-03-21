package com.jboss.trading.api;

import com.jboss.trading.api.exception.LimitOrderNotFoundException;
import com.jboss.trading.api.exception.MarketOrderNotFoundException;
import com.jboss.trading.api.exception.PlaceOrderException;
import com.jboss.trading.api.model.LimitOrder;
import com.jboss.trading.api.model.MarketOrder;
import com.jboss.trading.api.model.TransactionType;
import java.util.List;
import javax.ws.rs.*;

@Path("/")
public interface TradeManager {

    @POST
    @Produces("application/json")
    @Path("/orders/limit")
    void placeLimitOrder(
            @FormParam("stockHolderId") Integer stockHolderId,
            @FormParam("transactionType") TransactionType transactionType,
            @FormParam("quantity") Integer quantity,
            @FormParam("stockSymbol") String stockSymbol,
            @FormParam("price") Float price) throws PlaceOrderException;

    @POST
    @Produces("application/json")
    @Path("/orders/market")
    void placeMarketOrder(
            @FormParam("stockHolderId") Integer stockHolderId,
            @FormParam("transactionType") TransactionType transactionType,
            @FormParam("quantity") Integer quantity,
            @FormParam("stockSymbol") String stockSymbol);

    @DELETE
    @Path("/orders/limit/{limitOrderId}")
    @Produces("application/json")
    void cancelLimitOrder(@PathParam("limitOrderId") Integer limitOrderId)
            throws LimitOrderNotFoundException;

    @DELETE
    @Path("/orders/market/{marketOrderId}")
    @Produces("application/json")
    void cancelMarketOrder(@PathParam("marketOrderId") Integer marketOrderId)
            throws MarketOrderNotFoundException;

    @GET
    @Path("/orders/limit/{limitOrderId}")
    @Produces("application/json")
    LimitOrder viewLimitOrder(@PathParam("limitOrderId") Integer limitOrderId)
            throws LimitOrderNotFoundException;

    @GET
    @Path("/orders/market/{marketOrderId}")
    @Produces("application/json")
    MarketOrder viewMarketOrder(@PathParam("marketOrderId") Integer marketOrderId)
            throws MarketOrderNotFoundException;

    @GET
    @Path("/stockholders/{stockHolderId}/orders/limit/last/{numberLimitOrders}")
    @Produces("application/json")
    List<LimitOrder> viewStockHolderLimitOrders(
            @PathParam("stockHolderId") Integer stockHolderId,
            @PathParam("numberLimitOrders") Integer numberLimitOrders);

    @GET
    @Path("/stockholders/{stockHolderId}/orders/market/last/{numberLimitOrders}")
    @Produces("application/json")
    List<MarketOrder> viewStockHolderMarketOrders(
            @PathParam("stockHolderId") Integer stockHolderId,
            @PathParam("numberLimitOrders") Integer numberMarketOrders);
}

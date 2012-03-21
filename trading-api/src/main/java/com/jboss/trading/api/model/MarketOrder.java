package com.jboss.trading.api.model;

import java.io.Serializable;
import java.util.Date;

public class MarketOrder implements Serializable {

    private Integer id;
    
    private Integer stockHolderId;
    
    private String stockSymbol;
    
    private Date opened;
    
    private OrderStatus orderStatus;
    
    private Integer quantity;
    
    private TransactionType transactionType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOpened() {
        return opened;
    }

    public void setOpened(Date opened) {
        this.opened = opened;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStockHolderId() {
        return stockHolderId;
    }

    public void setStockHolderId(Integer stockHolderId) {
        this.stockHolderId = stockHolderId;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}

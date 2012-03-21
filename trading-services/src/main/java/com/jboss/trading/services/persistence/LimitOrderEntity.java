package com.jboss.trading.services.persistence;

import com.jboss.trading.api.model.OrderStatus;
import com.jboss.trading.api.model.TransactionType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "tbl_limit_orders")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class LimitOrderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "fk_stock_holder_id")
    private Integer stockHolderId;
    
    @Column(name = "fk_stock_symbol")
    private String stockSymbol;
    
    @Temporal(TemporalType.TIMESTAMP) //@PrePersist, @PreUpdate
    private Date opened;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    
    private Float price;
    
    private Integer quantity;
    
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    
    @Version
    private Integer version;

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

    public Float getPrice() {
        
        return price;
    }

    public void setPrice(Float price) {
        
        this.price = price;
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

    public Integer getVersion() {
        
        return version;
    }

    public void setVersion(Integer version) {
        
        this.version = version;
    }
}

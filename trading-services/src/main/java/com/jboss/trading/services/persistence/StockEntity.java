package com.jboss.trading.services.persistence;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tbl_stocks")
public class StockEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "stock_symbol", unique = true)
    private String stockSymbol;
    
    @Column(name = "company_name")
    private String companyName;

    public String getCompanyName() {
        
        return companyName;
    }

    public void setCompanyName(String companyName) {
        
        this.companyName = companyName;
    }

    public Integer getId() {
        
        return id;
    }

    public void setId(Integer id) {
        
        this.id = id;
    }

    public String getStockSymbol() {
        
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        
        this.stockSymbol = stockSymbol;
    }
}
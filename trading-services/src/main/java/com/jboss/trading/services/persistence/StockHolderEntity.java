package com.jboss.trading.services.persistence;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "tbl_stock_holders")
public class StockHolderEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;

    public String getFirstName() {
        
        return firstName;
    }

    public void setFirstName(String firstName) {
        
        this.firstName = firstName;
    }

    public Integer getId() {
        
        return id;
    }

    public void setId(Integer id) {
        
        this.id = id;
    }

    public String getLastName() {
        
        return lastName;
    }

    public void setLastName(String lastName) {
        
        this.lastName = lastName;
    }
}

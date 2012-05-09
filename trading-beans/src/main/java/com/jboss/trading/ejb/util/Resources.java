package com.jboss.trading.ejb.util;

import com.jboss.trading.api.TradingManager;
import com.jboss.trading.ejb.TradingManagerLocal;

import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class Resources {
    
    @EJB
    TradingManagerLocal tradingManager;
    
    @Produces
    @Named("TradingManagerBean")
    public TradingManager getTradingManager() {
        
        return tradingManager;
    }
}

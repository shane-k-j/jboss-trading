package com.jboss.trading.services.util;

import com.jboss.trading.api.TradeManager;
import com.jboss.trading.services.TradeManagerLocal;
import javax.ejb.EJB;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

public class Resources {
    
    @EJB
    TradeManagerLocal tradeManager;
    
    @Produces
    @Named("TradeManagerBean")
    public TradeManager getTradeManager() {
        
        return tradeManager;
    }
}

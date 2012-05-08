package com.jboss.trading.rest.client;

import com.jboss.trading.api.TradeManager;
import com.jboss.trading.api.exception.LimitOrderNotFoundException;
import com.jboss.trading.api.exception.MarketOrderNotFoundException;
import com.jboss.trading.api.exception.PlaceOrderException;
import com.jboss.trading.api.model.LimitOrder;
import com.jboss.trading.api.model.MarketOrder;
import com.jboss.trading.api.model.TransactionType;
import com.jboss.trading.rest.TradingServices;
import java.util.List;
import org.jboss.resteasy.client.ClientResponseFailure;
import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJacksonProvider;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeManagerClient implements TradeManager {

    private static final Logger LOGGER = 
    		LoggerFactory.getLogger(TradeManagerClient.class);
    
    private TradingServices tradingServices;

    static {

        ResteasyProviderFactory providerFactory = 
        		ResteasyProviderFactory.getInstance();

        providerFactory.addMessageBodyReader(ResteasyJacksonProvider.class);

        RegisterBuiltin.register(providerFactory);
    }

    public TradeManagerClient(TradingServices tradingServices) {

        this.tradingServices = tradingServices;
    }

    public static TradeManagerClient getInstance(String baseUri) {

    	TradingServices tradingServices = 
    			ProxyFactory.create(TradingServices.class, baseUri);

        TradeManagerClient tradeManagerClient = 
        		new TradeManagerClient(tradingServices);

        return tradeManagerClient;
    }

    @Override
    public void cancelLimitOrder(Integer limitOrderId) 
    		throws LimitOrderNotFoundException {

        try {

        	tradingServices.cancelLimitOrder(limitOrderId);
        } 
        catch (ClientResponseFailure ex) {

            if (LOGGER.isDebugEnabled()) {
            	
                LOGGER.debug(ex.getMessage(), ex);
            }

            throw LimitOrderNotFoundException.getInstance(limitOrderId);
        }
    }

    @Override
    public void cancelMarketOrder(Integer marketOrderId) 
    		throws MarketOrderNotFoundException {

        try {

        	tradingServices.cancelMarketOrder(marketOrderId);
        } 
        catch (ClientResponseFailure ex) {

            if (LOGGER.isDebugEnabled()) {
            	
                LOGGER.debug(ex.getMessage(), ex);
            }

            throw MarketOrderNotFoundException.getInstance(marketOrderId);
        }
    }

    @Override
    public void placeLimitOrder(
    		Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol, Float price) 
            throws PlaceOrderException {

    	tradingServices.placeLimitOrder(
    			stockHolderId, transactionType, quantity, 
    			stockSymbol, price);
    }

    @Override
    public void placeMarketOrder(
    		Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol) {

    	tradingServices.placeMarketOrder(
    			stockHolderId, transactionType, 
    			quantity, stockSymbol);
    }

    @Override
    public LimitOrder viewLimitOrder(Integer limitOrderId) 
    		throws LimitOrderNotFoundException {

        try {

            return tradingServices.viewLimitOrder(limitOrderId);
        } 
        catch (ClientResponseFailure ex) {

            if (LOGGER.isDebugEnabled()) {
            	
                LOGGER.debug(ex.getMessage(), ex);
            }

            throw LimitOrderNotFoundException.getInstance(limitOrderId);
        }
    }

    @Override
    public MarketOrder viewMarketOrder(Integer marketOrderId) 
    		throws MarketOrderNotFoundException {

        try {

            return tradingServices.viewMarketOrder(marketOrderId);
        } 
        catch (ClientResponseFailure ex) {

            if (LOGGER.isDebugEnabled()) {
            	
                LOGGER.debug(ex.getMessage(), ex);
            }

            throw MarketOrderNotFoundException.getInstance(marketOrderId);
        }
    }

    @Override
    public List<LimitOrder> viewStockHolderLimitOrders(
    		Integer stockHolderId, Integer numberLimitOrders) {

        return tradingServices.viewStockHolderLimitOrders(
        		stockHolderId, numberLimitOrders);
    }

    @Override
    public List<MarketOrder> viewStockHolderMarketOrders(
    		Integer stockHolderId, Integer numberMarketOrders) {

        return tradingServices.viewStockHolderMarketOrders(
        		stockHolderId, numberMarketOrders);
    }
}

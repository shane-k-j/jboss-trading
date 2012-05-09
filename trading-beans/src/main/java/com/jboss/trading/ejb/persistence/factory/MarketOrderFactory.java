package com.jboss.trading.ejb.persistence.factory;

import com.jboss.trading.api.model.MarketOrder;
import com.jboss.trading.ejb.persistence.MarketOrderEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarketOrderFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MarketOrderFactory.class);

    public static MarketOrder toMarketOrder(MarketOrderEntity marketOrderEntity) {

        MarketOrder marketOrder = new MarketOrder();

        try {

            BeanUtils.copyProperties(marketOrder, marketOrderEntity);
        } 
        catch (IllegalAccessException ex) {

            LOGGER.error(ex.getMessage(), ex);
        } 
        catch (InvocationTargetException ex) {

            LOGGER.error(ex.getMessage(), ex);
        }

        return marketOrder;
    }

    public static List<MarketOrder> toMarketOrders(List<MarketOrderEntity> marketOrderEntities) {

        List<MarketOrder> marketOrders = new ArrayList<MarketOrder>();

        for (MarketOrderEntity marketOrderEntity : marketOrderEntities) {

            MarketOrder marketOrder = MarketOrderFactory.toMarketOrder(marketOrderEntity);

            marketOrders.add(marketOrder);
        }

        return marketOrders;
    }
}

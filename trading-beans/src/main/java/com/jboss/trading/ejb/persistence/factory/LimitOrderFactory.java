package com.jboss.trading.ejb.persistence.factory;

import com.jboss.trading.api.model.LimitOrder;
import com.jboss.trading.ejb.persistence.LimitOrderEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LimitOrderFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(LimitOrderFactory.class);

    public static LimitOrder toLimitOrder(LimitOrderEntity limitOrderEntity) {

        LimitOrder limitOrder = new LimitOrder();

        try {

            BeanUtils.copyProperties(limitOrder, limitOrderEntity);
        } 
        catch (IllegalAccessException ex) {

            LOGGER.error(ex.getMessage(), ex);
        } 
        catch (InvocationTargetException ex) {

            LOGGER.error(ex.getMessage(), ex);
        }

        return limitOrder;
    }

    public static List<LimitOrder> toLimitOrders(List<LimitOrderEntity> limitOrderEntities) {

        List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

        for (LimitOrderEntity limitOrderEntity : limitOrderEntities) {

            LimitOrder limitOrder = LimitOrderFactory.toLimitOrder(limitOrderEntity);

            limitOrders.add(limitOrder);
        }

        return limitOrders;
    }
}

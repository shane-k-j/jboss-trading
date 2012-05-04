package com.jboss.trading.services;

import com.jboss.trading.api.exception.LimitOrderNotFoundException;
import com.jboss.trading.api.exception.MarketOrderNotFoundException;
import com.jboss.trading.api.exception.PlaceOrderException;
import com.jboss.trading.api.model.LimitOrder;
import com.jboss.trading.api.model.MarketOrder;
import com.jboss.trading.api.model.OrderStatus;
import com.jboss.trading.api.model.TransactionType;
import com.jboss.trading.services.persistence.LimitOrderEntity;
import com.jboss.trading.services.persistence.MarketOrderEntity;
import com.jboss.trading.services.persistence.factory.LimitOrderFactory;
import com.jboss.trading.services.persistence.factory.MarketOrderFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class TradeManagerBean implements TradeManagerLocal, TradeManagerRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradeManagerBean.class);
    
    private static final String STOCK_HOLDER_LIMIT_ORDERS_QUERY_STR =
            "from LimitOrderEntity where stockHolderId = :stockHolderId order by opened desc";
    
    private static final String STOCK_HOLDER_MARKET_ORDERS_QUERY_STR =
            "from MarketOrderEntity where stockHolderId = :stockHolderId order by opened desc";
    
    private static final String STOCK_HOLDER_ID_PARAM_STR = "stockHolderId";
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Resource(mappedName = "java:/queue/jbossTradingLimitOrders")
    Queue limitOrderDest;
    
    @Resource(mappedName = "java:/ConnectionFactory")
    QueueConnectionFactory connectionFactory;
    
    Connection connection;
    
    @Override
    public void cancelLimitOrder(Integer limitOrderId) throws LimitOrderNotFoundException {

        LimitOrderEntity limitOrderEntity = entityManager.find(LimitOrderEntity.class, limitOrderId);

        if (limitOrderEntity != null) {

            entityManager.remove(limitOrderEntity);
        } 
        else {

            LimitOrderNotFoundException lonf_ex = LimitOrderNotFoundException.getInstance(limitOrderId);

            LOGGER.error(lonf_ex.getMessage(), lonf_ex);

            throw lonf_ex;
        }
    }

    @Override
    public void cancelMarketOrder(Integer marketOrderId) throws MarketOrderNotFoundException {

        MarketOrderEntity marketOrderEntity = entityManager.find(MarketOrderEntity.class, marketOrderId);

        if (marketOrderEntity != null) {

            entityManager.remove(marketOrderEntity);
        } 
        else {

            MarketOrderNotFoundException monf_ex = MarketOrderNotFoundException.getInstance(marketOrderId);

            LOGGER.error(monf_ex.getMessage(), monf_ex);

            throw monf_ex;
        }
    }

    @Override
    public void placeLimitOrder(Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol, Float price) throws PlaceOrderException {

        Date opened = Calendar.getInstance().getTime();

        LimitOrderEntity limitOrderEntity = new LimitOrderEntity();

        limitOrderEntity.setStockHolderId(stockHolderId);
        limitOrderEntity.setStockSymbol(stockSymbol);

        limitOrderEntity.setOpened(opened);
        limitOrderEntity.setOrderStatus(OrderStatus.Open);
        limitOrderEntity.setPrice(price);
        limitOrderEntity.setQuantity(quantity);
        limitOrderEntity.setTransactionType(transactionType);

        try {

            submitLimitOrder(limitOrderEntity);
        } 
        catch (JMSException ex) {

            PlaceOrderException pe_ex = new PlaceOrderException(ex);

            LOGGER.error(pe_ex.getMessage(), pe_ex);

            throw pe_ex;
        }
    }

    @Override
    public void placeMarketOrder(Integer stockHolderId, TransactionType transactionType,
            Integer quantity, String stockSymbol) {

        Date opened = Calendar.getInstance().getTime();

        MarketOrderEntity marketOrderEntity = new MarketOrderEntity();

        marketOrderEntity.setStockHolderId(stockHolderId);
        marketOrderEntity.setStockSymbol(stockSymbol);

        marketOrderEntity.setOpened(opened);
        marketOrderEntity.setOrderStatus(OrderStatus.Open);
        marketOrderEntity.setQuantity(quantity);
        marketOrderEntity.setTransactionType(transactionType);

        entityManager.persist(marketOrderEntity);
    }

    @Override
    public LimitOrder viewLimitOrder(Integer limitOrderId) throws LimitOrderNotFoundException {

        LimitOrderEntity limitOrderEntity = entityManager.find(LimitOrderEntity.class, limitOrderId);

        if (limitOrderEntity != null) {

            LimitOrder limitOrder = LimitOrderFactory.toLimitOrder(limitOrderEntity);

            return limitOrder;
        }

        LimitOrderNotFoundException lonf_ex = LimitOrderNotFoundException.getInstance(limitOrderId);

        LOGGER.error(lonf_ex.getMessage(), lonf_ex);

        throw lonf_ex;
    }

    @Override
    public MarketOrder viewMarketOrder(Integer marketOrderId) throws MarketOrderNotFoundException {

        MarketOrderEntity marketOrderEntity = entityManager.find(MarketOrderEntity.class, marketOrderId);

        if (marketOrderEntity != null) {

            MarketOrder marketOrder = MarketOrderFactory.toMarketOrder(marketOrderEntity);

            return marketOrder;
        }

        MarketOrderNotFoundException monf_ex = MarketOrderNotFoundException.getInstance(marketOrderId);

        LOGGER.error(monf_ex.getMessage(), monf_ex);

        throw monf_ex;
    }

    @Override
    public List<LimitOrder> viewStockHolderLimitOrders(Integer stockHolderId, Integer numberLimitOrders) {

        Query query = entityManager.createQuery(STOCK_HOLDER_LIMIT_ORDERS_QUERY_STR);

        query.setParameter(STOCK_HOLDER_ID_PARAM_STR, stockHolderId);

        query.setMaxResults(numberLimitOrders);

        List<LimitOrderEntity> limitOrderEntities = (List<LimitOrderEntity>) query.getResultList();

        List<LimitOrder> limitOrders = LimitOrderFactory.toLimitOrders(limitOrderEntities);

        return limitOrders;
    }

    @Override
    public List<MarketOrder> viewStockHolderMarketOrders(Integer stockHolderId, Integer numberMarketOrders) {

        Query query = entityManager.createQuery(STOCK_HOLDER_MARKET_ORDERS_QUERY_STR);

        query.setParameter(STOCK_HOLDER_ID_PARAM_STR, stockHolderId);

        query.setMaxResults(numberMarketOrders);

        List<MarketOrderEntity> marketOrderEntities = (List<MarketOrderEntity>) query.getResultList();

        List<MarketOrder> marketOrders = MarketOrderFactory.toMarketOrders(marketOrderEntities);

        return marketOrders;
    }

    @PostConstruct
    public void postConstruct() {

        try {

            connection = connectionFactory.createConnection();
        } 
        catch (JMSException ex) {

            LOGGER.error(ex.getMessage(), ex);
        }
    }

    @PreDestroy
    public void preDestroy() {

        try {

            connection.close();
        } 
        catch (JMSException ex) {

            LOGGER.error(ex.getMessage(), ex);
        }
    }

    private void submitLimitOrder(LimitOrderEntity limitOrderEntity) throws JMSException {

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        try {

            ObjectMessage message = session.createObjectMessage(limitOrderEntity);

            MessageProducer producer = session.createProducer(limitOrderDest);

            producer.send(message);
        } 
        finally {

            session.close();
        }
    }
}

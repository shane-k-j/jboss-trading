package com.jboss.trading.services;

import com.jboss.trading.services.persistence.LimitOrderEntity;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jboss.ejb3.annotation.Depends;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/jbossTradingLimitOrders")})
@Depends("jboss.messaging.destination:service=Queue,name=jbossTradingLimitOrders")
public class LimitOrderProcessorBean implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(LimitOrderProcessorBean.class);
    
    @PersistenceContext
    private EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Override
    public void onMessage(Message message) {

        ObjectMessage objectMessage = (ObjectMessage) message;

        try {

            LimitOrderEntity limitOrderEntity = (LimitOrderEntity) objectMessage.getObject();

            entityManager.persist(limitOrderEntity);
        } 
        catch (JMSException ex) {

            LOGGER.error(ex.getMessage(), ex);
        }
    }
}

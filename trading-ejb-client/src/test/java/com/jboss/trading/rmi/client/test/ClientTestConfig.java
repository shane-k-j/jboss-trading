package com.jboss.trading.rmi.client.test;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTestConfig {

    private static final Logger LOGGER = 
    		LoggerFactory.getLogger(ClientTestConfig.class);
    
    private static final String PROPS_FILE_NAME = 
    		"ejb-client-test.properties";
    
    private static final String PROPS_JNDI_NAME = 
    		"jndi.name";
    
    private String jndiName;

    private ClientTestConfig() {

        Properties props = new Properties();

        try {

            props.load(ClientTestConfig.class
            		.getClassLoader()
            		.getResourceAsStream(PROPS_FILE_NAME));

            jndiName = props.getProperty(PROPS_JNDI_NAME);
        } 
        catch (IOException ex) {

            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public static ClientTestConfig getInstance() {

        return ClientTestConfig.EJBClientTestConfigHolder.INSTANCE;
    }
    
    public String getJndiName() {
        
        return jndiName;
    }

    private static class EJBClientTestConfigHolder {

        public static final ClientTestConfig INSTANCE = 
        		new ClientTestConfig();
    }
}

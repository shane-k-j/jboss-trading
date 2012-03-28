package com.jboss.trading.rmi.client.test;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTestConfig.class);
    
    private static final String PROPS_FILE_NAME = "ejb-client-test.properties";
    private static final String PROPS_HOST_PORT = "host.port";
    private static final String PROPS_JNDI_NAME = "jndi.name";
    
    private String hostPort;
    
    private String jndiName;

    private ClientTestConfig() {

        Properties props = new Properties();

        try {

            props.load(ClientTestConfig.class.getClassLoader().getResourceAsStream(PROPS_FILE_NAME));

            hostPort = props.getProperty(PROPS_HOST_PORT);
            jndiName = props.getProperty(PROPS_JNDI_NAME);
        } 
        catch (IOException ex) {

            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public static ClientTestConfig getInstance() {

        return ClientTestConfig.RmiClientTestConfigHolder.INSTANCE;
    }

    public String getHostPort() {
        
        return hostPort;
    }

    public String getJndiName() {
        
        return jndiName;
    }

    private static class RmiClientTestConfigHolder {

        public static final ClientTestConfig INSTANCE = new ClientTestConfig();
    }
}

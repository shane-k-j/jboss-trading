package com.jboss.trading.ws.client.test;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTestConfig.class);
    
    private static final String PROPS_FILE_NAME = "ws-client-test.properties";
    
    private static final String PROPS_WSDL_URL = "wsdl.url";
    
    private String wsdlUrl;

    private ClientTestConfig() {

        Properties props = new Properties();

        try {

            props.load(ClientTestConfig.class.getClassLoader().getResourceAsStream(PROPS_FILE_NAME));

            wsdlUrl = props.getProperty(PROPS_WSDL_URL);
        } 
        catch (IOException ex) {

            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public static ClientTestConfig getInstance() {

        return ClientTestConfig.WsClientTestConfigHolder.INSTANCE;
    }

    public String getWsdlUrl() {
        
        return wsdlUrl;
    }

    private static class WsClientTestConfigHolder {

        public static final ClientTestConfig INSTANCE = new ClientTestConfig();
    }
}

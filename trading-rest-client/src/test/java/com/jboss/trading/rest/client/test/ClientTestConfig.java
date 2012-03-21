package com.jboss.trading.rest.client.test;

import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientTestConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientTestConfig.class);
    
    private static final String PROPS_FILE_NAME = "rest-client-test.properties";
    
    private static final String PROPS_BASE_URL = "base.url";
    
    private String baseUrl;

    private ClientTestConfig() {

        Properties props = new Properties();

        try {

            props.load(ClientTestConfig.class.getClassLoader().getResourceAsStream(PROPS_FILE_NAME));

            baseUrl = props.getProperty(PROPS_BASE_URL);
        } 
        catch (IOException ex) {

            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public static ClientTestConfig getInstance() {

        return ClientTestConfig.RestClientTestConfigHolder.INSTANCE;
    }

    public String getBaseUrl() {
        
        return baseUrl;
    }

    private static class RestClientTestConfigHolder {

        public static final ClientTestConfig INSTANCE = new ClientTestConfig();
    }
}

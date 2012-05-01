package com.jboss.trading.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/trading")
@ApplicationScoped
public class TradingApplication extends Application {
}
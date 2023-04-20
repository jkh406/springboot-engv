package com.anbtech.webffice.com.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anbtech.webffice.com.cmm.service.WebfficeProperties;

public class WebfficeWebServletContextListener implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebfficeWebServletContextListener.class);

	public WebfficeWebServletContextListener() {
		setEgovProfileSetting();
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		if (System.getProperty("spring.profiles.active") == null) {
			setEgovProfileSetting();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		if (System.getProperty("spring.profiles.active") != null) {
			System.clearProperty("spring.profiles.active");
		}
	}

	public void setEgovProfileSetting() {
		try {
			LOGGER.debug("===========================Start EgovServletContextLoad START ===========");
			System.setProperty("spring.profiles.active",
					WebfficeProperties.getProperty("Globals.DbType") + "," + WebfficeProperties.getProperty("Globals.Auth"));
			LOGGER.debug("Setting spring.profiles.active>" + System.getProperty("spring.profiles.active"));
			LOGGER.debug("===========================END   EgovServletContextLoad END ===========");
		} catch (IllegalArgumentException e) {
			LOGGER.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : " + e.getMessage());
		}
	}
}

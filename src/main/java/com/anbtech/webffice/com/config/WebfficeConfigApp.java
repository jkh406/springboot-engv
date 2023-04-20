package com.anbtech.webffice.com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@Import({
	WebfficeConfigAppAspect.class,
	WebfficeConfigAppCommon.class,
	WebfficeConfigAppDatasource.class,
	WebfficeConfigAppIdGen.class,
	WebfficeConfigAppProperties.class,
	WebfficeConfigAppMapper.class,
	WebfficeConfigAppTransaction.class,
	WebfficeConfigAppValidator.class,
	WebfficeConfigAppWhitelist.class
})
@PropertySources({
	@PropertySource("classpath:/application.properties")
}) //CAUTION: min JDK 8
public class WebfficeConfigApp {

}

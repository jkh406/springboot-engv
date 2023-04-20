package com.anbtech.webffice.com.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.anbtech.webffice.com.cmm.WebfficeComTraceHandler;
import com.anbtech.webffice.com.cmm.WebfficeMessageSource;
import com.anbtech.webffice.com.cmm.ImagePaginationRenderer;
import com.anbtech.webffice.com.cmm.web.WebfficeMultipartResolver;

import org.egovframe.rte.fdl.cmmn.trace.LeaveaTrace;
import org.egovframe.rte.fdl.cmmn.trace.handler.TraceHandler;
import org.egovframe.rte.fdl.cmmn.trace.manager.DefaultTraceHandleManager;
import org.egovframe.rte.fdl.cmmn.trace.manager.TraceHandlerService;
import org.egovframe.rte.fdl.cryptography.EgovPasswordEncoder;
import org.egovframe.rte.fdl.cryptography.impl.EgovARIACryptoServiceImpl;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.DefaultPaginationManager;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationRenderer;

/**
 * @ClassName : WebfficeConfigAppCommon.java
 * @Description : 공통 Bean 설정
 *
 * @since  : 2023. 04. 20
 * @version : 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일              수정자               수정내용
 *  -------------  ------------   ---------------------
 * </pre>
 *
 */
@Configuration
@ComponentScan(basePackages = "com.anbtech.webffice", includeFilters = {
	@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Service.class),
	@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Repository.class)
}, excludeFilters = {
	@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class),
	@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)
})
public class WebfficeConfigAppCommon {

	/**
	 * @return AntPathMatcher 등록.  Ant 경로 패턴 경로와 일치하는지 여부를 확인
	 */
	@Bean
	public AntPathMatcher antPathMatcher() {
		return new AntPathMatcher();
	}

	/**
	 * @return [Resource 설정] 메세지 Properties 경로 설정
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
		
		reloadableResourceBundleMessageSource.setBasenames(
			"classpath:/com.anbtech.webffice/message/com/message-common",
			"classpath:/org/egovframe/rte/fdl/idgnr/messages/idgnr",
			"classpath:/org/egovframe/rte/fdl/property/messages/properties");
		reloadableResourceBundleMessageSource.setCacheSeconds(60);
		return reloadableResourceBundleMessageSource;
	}

	/**
	 * @return [Resource 설정] 메세지 소스 등록
	 */
	@Bean
	public WebfficeMessageSource egovMessageSource() {
		WebfficeMessageSource egovMessageSource = new WebfficeMessageSource();
		egovMessageSource.setReloadableResourceBundleMessageSource(messageSource());
		return egovMessageSource;
	}

	/**
	 * @return [LeaveaTrace 설정] defaultTraceHandler 등록
	 */
	@Bean
	public WebfficeComTraceHandler defaultTraceHandler() {
		return new WebfficeComTraceHandler();
	}

	/**
	 * @return [LeaveaTrace 설정] traceHandlerService 등록. TraceHandler 설정
	 */
	@Bean
	public DefaultTraceHandleManager traceHandlerService() {
		DefaultTraceHandleManager defaultTraceHandleManager = new DefaultTraceHandleManager();
		defaultTraceHandleManager.setReqExpMatcher(antPathMatcher());
		defaultTraceHandleManager.setPatterns(new String[] {"*"});
		defaultTraceHandleManager.setHandlers(new TraceHandler[] {defaultTraceHandler()});
		return defaultTraceHandleManager;
	}

	/**
	 * @return [LeaveaTrace 설정] LeaveaTrace 등록
	 */
	@Bean
	public LeaveaTrace leaveaTrace() {
		LeaveaTrace leaveaTrace = new LeaveaTrace();
		leaveaTrace.setTraceHandlerServices(new TraceHandlerService[] {traceHandlerService()});
		return leaveaTrace;
	}

	/**
	 * @return [ImagePaginationRenderer 설정] ImagePaginationRenderer 등록
	 */
	@Bean
	public ImagePaginationRenderer imageRenderer() {
		return new ImagePaginationRenderer();
	}

	/**
	 * @return [ImagePaginationRenderer 설정] defaultPaginationManager 설정.
	 */
	@Bean
	public DefaultPaginationManager paginationManager() {
		DefaultPaginationManager defaultPaginationManager = new DefaultPaginationManager();

		Map<String, PaginationRenderer> rendererType = new HashMap<>();
		rendererType.put("image", imageRenderer());
		defaultPaginationManager.setRendererType(rendererType);

		return defaultPaginationManager;
	}

	/**
	 * @return [MultipartResolver 설정] CommonsMultipartResolver 등록
	 */
	@Bean
	public CommonsMultipartResolver springRegularCommonsMultipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setMaxUploadSize(100000000);
		commonsMultipartResolver.setMaxInMemorySize(100000000);
		return commonsMultipartResolver;
	}

	/**
	 * 확장자 제한 : globals.properties > Globals.fileUpload.Extensions로 설정
	 * @return [MultipartResolver 설정] EgovMultipartResolver 등록
	 */
	@Bean
	public WebfficeMultipartResolver localMultiCommonsMultipartResolver() {
		WebfficeMultipartResolver egovMultipartResolver = new WebfficeMultipartResolver();
		egovMultipartResolver.setMaxUploadSize(100000000);
		egovMultipartResolver.setMaxInMemorySize(100000000);
		return egovMultipartResolver;
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		return localMultiCommonsMultipartResolver();
	}
	
	/**
	 * 암복호화
	 * @return [EgovPasswordEncoder 설정] EgovPasswordEncoder 등록
	 */
	@Bean
	public EgovPasswordEncoder egovPasswordEncoder() {
		EgovPasswordEncoder egovPasswordEncoder = new EgovPasswordEncoder();
		egovPasswordEncoder.setAlgorithm("SHA-256");
		egovPasswordEncoder.setHashedPassword("gdyYs/IZqY86VcWhT8emCYfqY1ahw2vtLG+/FzNqtrQ=");
		return egovPasswordEncoder;
	}
	
	/**
	 * 암복호화
	 * @return [EgovARIACryptoServiceImpl 설정] EgovARIACryptoServiceImpl 등록
	 */
	@Bean
	public EgovARIACryptoServiceImpl egovARIACryptoService() {
		EgovARIACryptoServiceImpl egovARIACryptoServiceImpl = new EgovARIACryptoServiceImpl();
		egovARIACryptoServiceImpl.setPasswordEncoder(egovPasswordEncoder());
		egovARIACryptoServiceImpl.setBlockSize(1024);
		return egovARIACryptoServiceImpl;
	}
}

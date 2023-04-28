package com.anbtech.webffice.com.cmm;

import org.egovframe.rte.fdl.cmmn.trace.handler.TraceHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class Name : WebfficeComTraceHandler.java
 * @Description : 공통서비스의 trace 처리 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *
 * @since 2023. 04. 20.
 *
 */
public class WebfficeComTraceHandler implements TraceHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebfficeComTraceHandler.class);

    /**
     * 발생된 메시지를 출력한다.
     */
    public void todo(Class<?> clazz, String message) {
    	LOGGER.debug("[TRACE]CLASS::: {}", clazz.getName());
    	LOGGER.debug("[TRACE]MESSAGE::: {}", message);
    }
}

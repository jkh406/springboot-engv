package com.anbtech.webffice.com.cmm;

import org.egovframe.rte.fdl.cmmn.exception.handler.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Class Name : WebfficeComExcepHndlr.java
 * @Description : 공통서비스의 exception 처리 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *
 * @since 2023. 04. 20.
 * @version
 * @see
 *
 */
public class WebfficeComExcepHndlr implements ExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebfficeComExcepHndlr.class);

    /**
     * 발생된 Exception을 처리한다.
     */
    public void occur(Exception ex, String packageName) {
		LOGGER.debug("[HANDLER][PACKAGE]::: {}", packageName);
		LOGGER.debug("[HANDLER][Exception]:::", ex);
    }
}

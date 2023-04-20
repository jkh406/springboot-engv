package com.anbtech.webffice.com.cmm.service.impl;

import java.util.List;

import com.anbtech.webffice.com.cmm.service.WebfficeUserDetailsService;
import com.anbtech.webffice.com.cmm.util.WebfficeUserDetailsHelper;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 *
 * @since 2023. 04. 20.
 * @version 1.0
 * @see
 *
 * <pre>
 * 개정이력(Modification Information)
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *
 *  </pre>
 */

public class WebfficeUserDetailsSessionServiceImpl extends EgovAbstractServiceImpl implements
	WebfficeUserDetailsService {

	@Override
	public Object getAuthenticatedUser() {
		if (WebfficeUserDetailsHelper.isAuthenticated()) {
			return WebfficeUserDetailsHelper.getAuthenticatedUser();
		}
		return null;
	}

	@Override
	public List<String> getAuthorities() {
		//		return listAuth;
		return WebfficeUserDetailsHelper.getAuthorities();
	}

	@Override
	public Boolean isAuthenticated() {
		// 인증된 유저인지 확인한다.
		return WebfficeUserDetailsHelper.isAuthenticated();

	}

}

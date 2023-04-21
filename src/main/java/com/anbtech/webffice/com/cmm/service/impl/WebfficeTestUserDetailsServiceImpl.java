package com.anbtech.webffice.com.cmm.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.anbtech.webffice.com.cmm.LoginVO;
import com.anbtech.webffice.com.cmm.service.WebfficeUserDetailsService;

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

public class WebfficeTestUserDetailsServiceImpl extends EgovAbstractServiceImpl implements
		WebfficeUserDetailsService {

	@Override
	public Object getAuthenticatedUser() {

		LoginVO loginVO = new LoginVO();
		loginVO.setId(loginVO.getId());
		loginVO.setPassword(loginVO.getPassword());
		loginVO.setUserSe(loginVO.getUserSe());
		loginVO.setEmail(loginVO.getEmail());
		loginVO.setName(loginVO.getName());
		loginVO.setDept_no(loginVO.getDept_no());
		return loginVO;

		// return
		// RequestContextHolder.getRequestAttributes().getAttribute("loginVO",
		// RequestAttributes.SCOPE_SESSION);

	}

	@Override
	public List<String> getAuthorities() {

		// 권한 설정을 리턴한다.

		List<String> listAuth = new ArrayList<String>();
		listAuth.add("IS_AUTHENTICATED_ANONYMOUSLY");
		listAuth.add("IS_AUTHENTICATED_FULLY");
		listAuth.add("IS_AUTHENTICATED_REMEMBERED");
		listAuth.add("ROLE_ADMIN");
		listAuth.add("ROLE_ANONYMOUS");
		listAuth.add("ROLE_RESTRICTED");
		listAuth.add("ROLE_USER");

		return listAuth;
	}

	@Override
	public Boolean isAuthenticated() {
		// 인증된 유저인지 확인한다.

		/*if (RequestContextHolder.getRequestAttributes() == null) {
			return false;
		} else {

			if (RequestContextHolder.getRequestAttributes().getAttribute(
					"loginVO", RequestAttributes.SCOPE_SESSION) == null) {
				return false;
			} else {
				return true;
			}
		}*/

		return true;
	}

}

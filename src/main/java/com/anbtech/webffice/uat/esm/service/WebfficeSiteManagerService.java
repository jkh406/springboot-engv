package com.anbtech.webffice.uat.esm.service;

import java.util.Map;

/**
 * 사이트관리자의 로그인 비밀번호를 변경 처리하는 비즈니스 구현 클래스
 * @since 2023.04.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자          수정내용
 *  -------    --------    ---------------------------
 *
 *  </pre>
 */
public interface WebfficeSiteManagerService {
	/**
	 * 기존 비번과 비교하여 변경된 비밀번호를 저장한다.
	 * @param map데이터 String: login_id, old_password, new_password
	 * @return 성공시 1
	 * @throws Exception
	 */
	Integer updateAdminPassword(Map<?, ?> map) throws Exception;
}

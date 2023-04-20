package com.anbtech.webffice.uat.esm.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anbtech.webffice.com.cmm.LoginVO;
import com.anbtech.webffice.com.cmm.ResponseCode;
import com.anbtech.webffice.com.cmm.service.ResultVO;
import com.anbtech.webffice.com.cmm.util.WebfficeUserDetailsHelper;
import com.anbtech.webffice.com.jwt.config.JwtVerification;
import com.anbtech.webffice.uat.esm.service.WebfficeSiteManagerService;
import com.anbtech.webffice.utl.sim.service.WebfficeFileScrty;

/**
 * 사이트관리자의 로그인 비밀번호를 변경 처리하는 컨트롤러 클래스
 * @since 2023.04.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *  수정일        수정자      수정내용
 *  -------    --------   ---------------------------
 *
 *  </pre>
 */
@RestController
public class WebfficeSiteManagerApiController {
	/** EgovSiteManagerService */
	@Resource(name = "siteManagerService")
	private WebfficeSiteManagerService siteManagerService;
	
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	private ResultVO handleAuthError(ResultVO resultVO) {
		resultVO.setResultCode(ResponseCode.AUTH_ERROR.getCode());
		resultVO.setResultMessage(ResponseCode.AUTH_ERROR.getMessage());
		return resultVO;
	}
	/**
	 * 사이트관리자의 기존 비번과 비교하여 변경된 비밀번호를 저장한다.
	 * @param map데이터: String old_password, new_password
	 * @param request - 토큰값으로 인증된 사용자를 확인하기 위한 HttpServletRequest
	 * @return result - 수정결과
	 * @exception Exception
	 */
	@PostMapping(value = "/uat/esm/updateAdminPasswordAPI.do")
	public ResultVO updateAdminPassword(@RequestBody Map<String,String> param, HttpServletRequest request) throws Exception {
		ResultVO resultVO = new ResultVO();
		// Headers에서 Authorization 속성값에 발급한 토큰값이 정상인지 확인
		if (!jwtVerification.isVerification(request)) {
			return handleAuthError(resultVO); // 토큰 확인
		}
		LoginVO user = (LoginVO)WebfficeUserDetailsHelper.getAuthenticatedUser();
		String old_password = param.get("old_password");
		String new_password = param.get("new_password");
		String login_id = user.getId();
		Map<String,Object> resultMap = new HashMap<String,Object>();
		resultMap.put("old_password", WebfficeFileScrty.encryptPassword(old_password, login_id));
		resultMap.put("new_password", WebfficeFileScrty.encryptPassword(new_password, login_id));
		resultMap.put("login_id", login_id);
		System.out.println("===>>> loginVO.getId() = "+login_id);
		Integer result = siteManagerService.updateAdminPassword(resultMap); //저장성공 시 1, 실패 시 0 반환
		System.out.println("===>>> result = "+result);
		if(result > 0) {
			resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
			resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		}else{
			resultVO.setResultCode(ResponseCode.SAVE_ERROR.getCode());
			resultVO.setResultMessage(ResponseCode.SAVE_ERROR.getMessage());
		}

		return resultVO;
	}
}

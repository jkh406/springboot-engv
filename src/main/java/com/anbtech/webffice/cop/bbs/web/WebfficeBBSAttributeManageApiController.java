package com.anbtech.webffice.cop.bbs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cmmn.exception.EgovBizException;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.anbtech.webffice.com.cmm.ComDefaultCodeVO;
import com.anbtech.webffice.com.cmm.WebfficeMessageSource;
import com.anbtech.webffice.com.cmm.LoginVO;
import com.anbtech.webffice.com.cmm.ResponseCode;
import com.anbtech.webffice.com.cmm.service.WebfficeCmmUseService;
import com.anbtech.webffice.com.cmm.service.ResultVO;
import com.anbtech.webffice.com.cmm.util.WebfficeUserDetailsHelper;
import com.anbtech.webffice.com.jwt.config.JwtVerification;
import com.anbtech.webffice.cop.bbs.service.BoardMasterVO;
import com.anbtech.webffice.cop.bbs.service.WebfficeBBSAttributeManageService;

/**
 * 게시판 속성관리를 위한 컨트롤러  클래스
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
@RestController
public class WebfficeBBSAttributeManageApiController {
	
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;

	/** EgovBBSAttributeManageService */
	@Resource(name = "EgovBBSAttributeManageService")
	private WebfficeBBSAttributeManageService bbsAttrbService;

	/** EgovCmmUseService */
	@Resource(name = "EgovCmmUseService")
	private WebfficeCmmUseService cmmUseService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	/** DefaultBeanValidator */
	@Autowired
	private DefaultBeanValidator beanValidator;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	WebfficeMessageSource egovMessageSource;

	/**
	 * 게시판 마스터 목록을 조회한다.
	 *
	 * @param request
	 * @param boardMasterVO
	 * @return resultVO
	 * @throws Exception
	 */
	@PostMapping(value ="/cop/bbs/selectBBSMasterInfsAPI.do")
	public ResultVO selectBBSMasterInfs(HttpServletRequest request,
		@RequestBody BoardMasterVO boardMasterVO)
		throws Exception {

		ResultVO resultVO = new ResultVO();

		// 기존 세션 체크 인증에서 토큰 방식으로 변경
		if (!jwtVerification.isVerification(request)) {
			return handleAuthError(resultVO); // 토큰 확인
		}

		boardMasterVO.setPageUnit(propertyService.getInt("pageUnit"));
		boardMasterVO.setPageSize(propertyService.getInt("pageSize"));

		PaginationInfo paginationInfo = new PaginationInfo();

		paginationInfo.setCurrentPageNo(boardMasterVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(boardMasterVO.getPageUnit());
		paginationInfo.setPageSize(boardMasterVO.getPageSize());

		boardMasterVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardMasterVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardMasterVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> resultMap = bbsAttrbService.selectBBSMasterInfs(boardMasterVO);
		int totCnt = Integer.parseInt((String)resultMap.get("resultCnt"));

		paginationInfo.setTotalRecordCount(totCnt);

		resultMap.put("paginationInfo", paginationInfo);

		resultVO.setResult(resultMap);
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

		return resultVO;
	}

	/**
	 * 게시판 마스터 상세내용을 조회한다.
	 *
	 * @param request
	 * @param searchVO
	 * @return resultVO
	 * @throws Exception
	 */
	@PostMapping(value ="/cop/bbs/selectBBSMasterInfAPI.do")
	public ResultVO selectBBSMasterInf(HttpServletRequest request,
		@RequestBody BoardMasterVO searchVO)
		throws Exception {
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 기존 세션 체크 인증에서 토큰 방식으로 변경
		if (!jwtVerification.isVerification(request)) {
			return handleAuthError(resultVO); // 토큰 확인
		}

		BoardMasterVO vo = bbsAttrbService.selectBBSMasterInf(searchVO);
		resultMap.put("boardMasterVO", vo);

		resultVO.setResult(resultMap);
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

		// return "cop/bbs/EgovBoardMstrUpdt";
		return resultVO;
	}

	/**
	 * 신규 게시판 마스터 정보를 등록한다.
	 *
	 * @param request
	 * @param boardMasterVO
	 * @param bindingResult
	 * @return resultVO
	 * @throws Exception
	 */
	@PostMapping(value ="/cop/bbs/insertBBSMasterInfAPI.do")
	public ResultVO insertBBSMasterInf(HttpServletRequest request,
		BoardMasterVO boardMasterVO,
		BindingResult bindingResult)
		throws Exception {
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 기존 세션 체크 인증에서 토큰 방식으로 변경
		if (!jwtVerification.isVerification(request)) {
			return handleAuthError(resultVO); // 토큰 확인
		}

		LoginVO user = (LoginVO)WebfficeUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = WebfficeUserDetailsHelper.isAuthenticated();

		beanValidator.validate(boardMasterVO, bindingResult);
		if (bindingResult.hasErrors()) {

			ComDefaultCodeVO vo = new ComDefaultCodeVO();

			vo.setCodeId("COM004");

			List<?> codeResult = cmmUseService.selectCmmCodeDetail(vo);

			resultMap.put("typeList", codeResult);

			vo.setCodeId("COM009");

			codeResult = cmmUseService.selectCmmCodeDetail(vo);

			resultMap.put("attrbList", codeResult);

			resultVO.setResult(resultMap);
			resultVO.setResultCode(ResponseCode.INPUT_CHECK_ERROR.getCode());
			resultVO.setResultMessage(ResponseCode.INPUT_CHECK_ERROR.getMessage());
			
			return resultVO;
		}

		if (isAuthenticated) {
			boardMasterVO.setFrstRegisterId(user.getId());
			boardMasterVO.setUseAt("Y");
			boardMasterVO.setTrgetId("SYSTEMDEFAULT_REGIST");
			boardMasterVO.setPosblAtchFileSize(propertyService.getString("posblAtchFileSize"));

			bbsAttrbService.insertBBSMastetInf(boardMasterVO);

			resultVO.setResult(resultMap);
			resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
			resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		}

		return resultVO;
	}

	/**
	 * 게시판 마스터 정보를 수정한다.
	 *
	 * @param request
	 * @param bbsId
	 * @param boardMasterVO
	 * @param bindingResult
	 * @return resultVO
	 * @throws Exception
	 */
	@PutMapping(value ="/cop/bbs/updateBBSMasterInfAPI/{bbsId}.do")
	public ResultVO updateBBSMasterInf(HttpServletRequest request,
		@PathVariable("bbsId") String bbsId,
		@RequestBody BoardMasterVO boardMasterVO,
		BindingResult bindingResult) throws Exception {
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 기존 세션 체크 인증에서 토큰 방식으로 변경
		if (!jwtVerification.isVerification(request)) {
			return handleAuthError(resultVO); // 토큰 확인
		}

		LoginVO user = (LoginVO)WebfficeUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = WebfficeUserDetailsHelper.isAuthenticated();

		beanValidator.validate(boardMasterVO, bindingResult);

		if (bindingResult.hasErrors()) {
			BoardMasterVO vo = bbsAttrbService.selectBBSMasterInf(boardMasterVO);

			resultMap.put("BoardMasterVO", vo);

			resultVO.setResult(resultMap);
			resultVO.setResultCode(ResponseCode.INPUT_CHECK_ERROR.getCode());
			resultVO.setResultMessage(ResponseCode.INPUT_CHECK_ERROR.getMessage());
			return resultVO;
		}

		if (isAuthenticated) {
			boardMasterVO.setLastUpdusrId(user.getId());
			boardMasterVO.setPosblAtchFileSize(propertyService.getString("posblAtchFileSize"));
			bbsAttrbService.updateBBSMasterInf(boardMasterVO);

			resultVO.setResult(resultMap);
			resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
			resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		}

		return resultVO;
	}

	/**
	 * 게시판 마스터 정보를 삭제한다.
	 *
	 * @param request
	 * @param bbsId
	 * @param boardMasterVO
	 * @return resultVO
	 * @throws Exception
	 */
	@PutMapping(value ="/cop/bbs/deleteBBSMasterInfAPI/{bbsId}.do")
	public ResultVO deleteBBSMasterInf(HttpServletRequest request,
		@PathVariable("bbsId") String bbsId,
		@RequestBody BoardMasterVO boardMasterVO) throws Exception {
		ResultVO resultVO = new ResultVO();

		// 기존 세션 체크 인증에서 토큰 방식으로 변경
		if (!jwtVerification.isVerification(request)) {
			return handleAuthError(resultVO); // 토큰 확인
		}

		LoginVO user = (LoginVO)WebfficeUserDetailsHelper.getAuthenticatedUser();
		Boolean isAuthenticated = WebfficeUserDetailsHelper.isAuthenticated();

		if (isAuthenticated) {
			boardMasterVO.setLastUpdusrId(user.getId());
			bbsAttrbService.deleteBBSMasterInf(boardMasterVO);

			resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
			resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		}

		return resultVO;
	}

	private ResultVO handleAuthError(ResultVO resultVO) {
		resultVO.setResultCode(ResponseCode.AUTH_ERROR.getCode());
		resultVO.setResultMessage(ResponseCode.AUTH_ERROR.getMessage());
		return resultVO;
	}

	/**
	 * 운영자 권한을 확인한다.(로그인 여부를 확인한다.)
	 *
	 * @throws EgovBizException
	 */
	protected boolean checkAuthority() throws Exception {
		// 사용자권한 처리
		if (!WebfficeUserDetailsHelper.isAuthenticated()) {
			return false;
		} else {
			return true;
		}
	}

}

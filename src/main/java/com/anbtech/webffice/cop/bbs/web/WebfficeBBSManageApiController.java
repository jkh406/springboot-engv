package com.anbtech.webffice.cop.bbs.web;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cryptography.EgovCryptoService;
import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.anbtech.webffice.com.cmm.WebfficeMessageSource;
import com.anbtech.webffice.com.cmm.LoginVO;
import com.anbtech.webffice.com.cmm.ResponseCode;
import com.anbtech.webffice.com.cmm.service.WebfficeFileMngService;
import com.anbtech.webffice.com.cmm.service.WebfficeFileMngUtil;
import com.anbtech.webffice.com.cmm.service.FileVO;
import com.anbtech.webffice.com.cmm.service.ResultVO;
import com.anbtech.webffice.com.cmm.util.WebfficeUserDetailsHelper;
import com.anbtech.webffice.com.cmm.web.WebfficeFileDownloadController;
import com.anbtech.webffice.com.jwt.config.JwtVerification;
import com.anbtech.webffice.cop.bbs.service.BoardMasterVO;
import com.anbtech.webffice.cop.bbs.service.BoardVO;
import com.anbtech.webffice.cop.bbs.service.WebfficeBBSAttributeManageService;
import com.anbtech.webffice.cop.bbs.service.WebfficeBBSManageService;
import com.anbtech.webffice.utl.sim.service.WebfficeFileScrty;

/**
 * 게시물 관리를 위한 컨트롤러 클래스
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
public class WebfficeBBSManageApiController {
	
	/** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;

	@Resource(name = "EgovBBSManageService")
	private WebfficeBBSManageService bbsMngService;

	@Resource(name = "EgovBBSAttributeManageService")
	private WebfficeBBSAttributeManageService bbsAttrbService;

	@Resource(name = "EgovFileMngService")
	private WebfficeFileMngService fileMngService;

	@Resource(name = "EgovFileMngUtil")
	private WebfficeFileMngUtil fileUtil;

	@Resource(name = "propertiesService")
	protected EgovPropertyService propertyService;

	@Resource(name = "egovMessageSource")
	WebfficeMessageSource egovMessageSource;

	@Resource(name = "EgovFileMngService")
	private WebfficeFileMngService fileService;
	
	/** 암호화서비스 */
    @Resource(name="egovARIACryptoService")
    EgovCryptoService cryptoService;

	//---------------------------------
	// 2009.06.29 : 2단계 기능 추가
	//---------------------------------
	//SHT-CUSTOMIZING//@Resource(name = "EgovBBSCommentService")
	//SHT-CUSTOMIZING//private EgovBBSCommentService bbsCommentService;

	//SHT-CUSTOMIZING//@Resource(name = "EgovBBSSatisfactionService")
	//SHT-CUSTOMIZING//private EgovBBSSatisfactionService bbsSatisfactionService;

	//SHT-CUSTOMIZING//@Resource(name = "EgovBBSScrapService")
	//SHT-CUSTOMIZING//private EgovBBSScrapService bbsScrapService;
	////-------------------------------

	@Autowired
	private DefaultBeanValidator beanValidator;
	
	/**
	 * 게시판 마스터 상세내용을 조회한다.
	 * 파일 첨부 가능 여부 조회용
	 *
	 * @param request
	 * @param searchVO
	 * @return resultVO
	 * @throws Exception
	 */
	@PostMapping(value = "/cop/bbs/selectUserBBSMasterInfAPI.do", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResultVO selectUserBBSMasterInf(@RequestBody BoardMasterVO searchVO)
		throws Exception {
		ResultVO resultVO = new ResultVO();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		BoardMasterVO master = bbsAttrbService.selectBBSMasterInf(searchVO);
		resultMap.put("brdMstrVO", master);

		resultVO.setResult(resultMap);
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		
		return resultVO;
	}

	/**
	 * 게시물에 대한 목록을 조회한다.
	 *
	 * @param boardVO
	 * @return resultVO
	 * @throws Exception
	 */
	@PostMapping(value = "/cop/bbs/selectBoardListAPI.do", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResultVO selectBoardArticles(@RequestBody BoardVO boardVO)
		throws Exception {
		ResultVO resultVO = new ResultVO();

		LoginVO user = (LoginVO)WebfficeUserDetailsHelper.getAuthenticatedUser();

		BoardMasterVO vo = new BoardMasterVO();
		vo.setBbsId(boardVO.getBbsId());
		vo.setUniqId(user.getId());

		BoardMasterVO master = bbsAttrbService.selectBBSMasterInf(vo);

		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(boardVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(propertyService.getInt("pageUnit"));
		paginationInfo.setPageSize(propertyService.getInt("pageSize"));

		boardVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		boardVO.setLastIndex(paginationInfo.getLastRecordIndex());
		boardVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		Map<String, Object> resultMap = bbsMngService.selectBoardArticles(boardVO, vo.getBbsAttrbCode());

		int totCnt = Integer.parseInt((String)resultMap.get("resultCnt"));
		paginationInfo.setTotalRecordCount(totCnt);

		resultMap.put("boardVO", boardVO);
		resultMap.put("brdMstrVO", master);
		resultMap.put("paginationInfo", paginationInfo);
		resultMap.put("user", user);

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		resultVO.setResult(resultMap);

		return resultVO;
	}

	/**
	 * 게시물에 대한 상세 정보를 조회한다.
	 *
	 * @param boardVO
	 * @return resultVO
	 * @throws Exception
	 */
	@PostMapping(value = "/cop/bbs/selectBoardArticleAPI.do")
	public ResultVO selectBoardArticle(@RequestBody BoardVO boardVO)
		throws Exception {

		ResultVO resultVO = new ResultVO();

		LoginVO user = new LoginVO();
		if (WebfficeUserDetailsHelper.isAuthenticated()) {
			user = (LoginVO)WebfficeUserDetailsHelper.getAuthenticatedUser();
		}

		// 조회수 증가 여부 지정
		boardVO.setPlusCount(true);

		//---------------------------------
		// 2009.06.29 : 2단계 기능 추가
		//---------------------------------
		if (!boardVO.getSubPageIndex().equals("")) {
			boardVO.setPlusCount(false);
		}
		////-------------------------------

		boardVO.setLastUpdusrId(user.getId());
		BoardVO vo = bbsMngService.selectBoardArticle(boardVO);

		//----------------------------
		// template 처리 (기본 BBS template 지정  포함)
		//----------------------------
		BoardMasterVO master = new BoardMasterVO();

		master.setBbsId(boardVO.getBbsId());
		master.setUniqId(user.getId());

		BoardMasterVO masterVo = bbsAttrbService.selectBBSMasterInf(master);
		
		//model.addAttribute("brdMstrVO", masterVo);

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("boardVO", vo);
		resultMap.put("sessionUniqId", user.getId());
		resultMap.put("brdMstrVO", masterVo);
		resultMap.put("user", user);

		// 2021-06-01 신용호 추가
		// 첨부파일 확인
		if (vo != null && vo.getAtchFileId() != null && !vo.getAtchFileId().isEmpty()) {
			FileVO fileVO = new FileVO();
			fileVO.setAtchFileId(vo.getAtchFileId());
			List<FileVO> resultFiles = fileService.selectFileInfs(fileVO);
			
			// FileId를 유추하지 못하도록 암호화하여 표시한다. (2022.12.06 추가) - 파일아이디가 유추 불가능하도록 조치
			for (FileVO file : resultFiles) {
				String toEncrypt = file.atchFileId;
				file.setAtchFileId(Base64.getEncoder().encodeToString(cryptoService.encrypt(toEncrypt.getBytes(),WebfficeFileDownloadController.ALGORITM_KEY)));
			}
						
			resultMap.put("resultFiles", resultFiles);
		}

		resultVO.setResult(resultMap);
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		return resultVO;
	}

	/**
	 * 게시물에 대한 내용을 수정한다.
	 *
	 * @param boardVO
	 * @param multiRequest
	 * @param bindingResult
	 * @param request
	 * @return resultVO
	 * @throws Exception
	 */
	@PostMapping(value ="/cop/bbs/updateBoardArticleAPI.do")
	public ResultVO updateBoardArticle(final MultipartHttpServletRequest multiRequest,
		BoardVO boardVO,
		BindingResult bindingResult,
		HttpServletRequest request)
		throws Exception {
		ResultVO resultVO = new ResultVO();

		// 사용자권한 처리
		LoginVO user = new LoginVO();

		String atchFileId = boardVO.getAtchFileId().replaceAll("\\s", "");

		beanValidator.validate(boardVO, bindingResult);
		if (bindingResult.hasErrors()) {

			resultVO.setResultCode(ResponseCode.INPUT_CHECK_ERROR.getCode());
			resultVO.setResultMessage(ResponseCode.INPUT_CHECK_ERROR.getMessage());

			return resultVO;
		}
		
		// 기존 세션 체크 인증에서 토큰 방식으로 변경
		if (!jwtVerification.isVerification(request)) {
			return handleAuthError(resultVO); // 토큰 확인
		} else if (jwtVerification.isVerification(request)) {
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			if (!files.isEmpty()) {
				if ("".equals(atchFileId)) {
					List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, atchFileId, "");
					atchFileId = fileMngService.insertFileInfs(result);
					boardVO.setAtchFileId(atchFileId);
				} else {
					FileVO fvo = new FileVO();
					fvo.setAtchFileId(atchFileId);
					int cnt = fileMngService.getMaxFileSN(fvo);
					List<FileVO> _result = fileUtil.parseFileInf(files, "BBS_", cnt, atchFileId, "");
					fileMngService.updateFileInfs(_result);
				}
			}

			boardVO.setLastUpdusrId(user.getId());
			boardVO.setNtcrNm(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨) 
			boardVO.setPassword(WebfficeFileScrty.encryptPassword("", user.getId())); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
			boardVO.setNttCn(unscript(boardVO.getNttCn())); // XSS 방지

			bbsMngService.updateBoardArticle(boardVO);
		}

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

		return resultVO;
	}

	/**
	 * 게시물을 등록한다.
	 *
	 * @param multiRequest
	 * @param boardVO
	 * @param bindingResult
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value ="/cop/bbs/insertBoardArticleAPI.do")
	public ResultVO insertBoardArticle(final MultipartHttpServletRequest multiRequest,
		BoardVO boardVO,
		BindingResult bindingResult,
		HttpServletRequest request)
		throws Exception {
		ResultVO resultVO = new ResultVO();

		LoginVO user = new LoginVO();

		beanValidator.validate(boardVO, bindingResult);
		if (bindingResult.hasErrors()) {
			resultVO.setResultCode(ResponseCode.INPUT_CHECK_ERROR.getCode());
			resultVO.setResultMessage(ResponseCode.INPUT_CHECK_ERROR.getMessage());

			return resultVO;
		}
		
		// 기존 세션 체크 인증에서 토큰 방식으로 변경
		if (!jwtVerification.isVerification(request)) {
			return handleAuthError(resultVO); // 토큰 확인
		} else if (jwtVerification.isVerification(request)) {
			List<FileVO> result = null;
			String atchFileId = "";

			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			if (!files.isEmpty()) {
				result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
			}
			boardVO.setAtchFileId(atchFileId);
			boardVO.setFrstRegisterId(user.getId());
			boardVO.setBbsId(boardVO.getBbsId());

			boardVO.setNtcrNm(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
			boardVO.setPassword(WebfficeFileScrty.encryptPassword("", user.getId())); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
			// board.setNttCn(unscript(board.getNttCn())); // XSS 방지

			bbsMngService.insertBoardArticle(boardVO);
		}

		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		return resultVO;
	}

	/**
	 * 게시물에 대한 답변을 등록한다.
	 *
	 * @param multiRequest
	 * @param boardVO
	 * @param bindingResult
	 * @param request 
	 * @return resultVO
	 * @throws Exception
	 */
	@PostMapping(value ="/cop/bbs/replyBoardArticleAPI.do")
	public ResultVO replyBoardArticle(final MultipartHttpServletRequest multiRequest,
		BoardVO boardVO,
		BindingResult bindingResult,
		HttpServletRequest request)
		throws Exception {
		ResultVO resultVO = new ResultVO();

		LoginVO user = new LoginVO();

		beanValidator.validate(boardVO, bindingResult);
		if (bindingResult.hasErrors()) {
			resultVO.setResultCode(ResponseCode.INPUT_CHECK_ERROR.getCode());
			resultVO.setResultMessage(ResponseCode.INPUT_CHECK_ERROR.getMessage());

			return resultVO;
		}
		
		// 기존 세션 체크 인증에서 토큰 방식으로 변경
		if (!jwtVerification.isVerification(request)) {
			return handleAuthError(resultVO); // 토큰 확인
		} else if (jwtVerification.isVerification(request)) {
			final Map<String, MultipartFile> files = multiRequest.getFileMap();
			String atchFileId = "";

			if (!files.isEmpty()) {
				List<FileVO> result = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
				atchFileId = fileMngService.insertFileInfs(result);
			}

			boardVO.setAtchFileId(atchFileId);
			boardVO.setReplyAt("Y");
			boardVO.setFrstRegisterId(user.getId());
			boardVO.setBbsId(boardVO.getBbsId());
			boardVO.setParnts(Long.toString(boardVO.getNttId()));
			boardVO.setSortOrdr(boardVO.getSortOrdr());
			boardVO.setReplyLc(Integer.toString(Integer.parseInt(boardVO.getReplyLc()) + 1));

			boardVO.setNtcrNm(""); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)
			boardVO.setPassword(WebfficeFileScrty.encryptPassword("", user.getId())); // dummy 오류 수정 (익명이 아닌 경우 validator 처리를 위해 dummy로 지정됨)

			boardVO.setNttCn(unscript(boardVO.getNttCn())); // XSS 방지

			bbsMngService.insertBoardArticle(boardVO);
		}

		//return "forward:/cop/bbs/selectBoardList.do";
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());
		return resultVO;
	}

	/**
	 * 게시물에 대한 내용을 삭제한다.
	 *
	 * @param boardVO
	 * @param nttId
	 * @param request
	 * @return resultVO
	 * @throws Exception
	 */
	@PutMapping(value = "/cop/bbs/deleteBoardArticleAPI/{nttId}.do")
	public ResultVO deleteBoardArticle(@RequestBody BoardVO boardVO, 
		@PathVariable("nttId") String nttId,
		HttpServletRequest request)

		throws Exception {
		ResultVO resultVO = new ResultVO();
		
		// 기존 세션 체크 인증에서 토큰 방식으로 변경
		if (!jwtVerification.isVerification(request)) {
			return handleAuthError(resultVO); // 토큰 확인
		}

		LoginVO user = (LoginVO)WebfficeUserDetailsHelper.getAuthenticatedUser();
		
		boardVO.setNttId(Long.parseLong(nttId));
		boardVO.setLastUpdusrId(user.getId());

		bbsMngService.deleteBoardArticle(boardVO);
		
		resultVO.setResultCode(ResponseCode.SUCCESS.getCode());
		resultVO.setResultMessage(ResponseCode.SUCCESS.getMessage());

		return resultVO;
	}

	/**
	 * XSS 방지 처리.
	 *
	 * @param data
	 * @return
	 */
	protected String unscript(String data) {
		if (data == null || data.trim().equals("")) {
			return "";
		}

		String ret = data;

		ret = ret.replaceAll("<(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;script");
		ret = ret.replaceAll("</(S|s)(C|c)(R|r)(I|i)(P|p)(T|t)", "&lt;/script");

		ret = ret.replaceAll("<(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;object");
		ret = ret.replaceAll("</(O|o)(B|b)(J|j)(E|e)(C|c)(T|t)", "&lt;/object");

		ret = ret.replaceAll("<(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;applet");
		ret = ret.replaceAll("</(A|a)(P|p)(P|p)(L|l)(E|e)(T|t)", "&lt;/applet");

		ret = ret.replaceAll("<(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");
		ret = ret.replaceAll("</(E|e)(M|m)(B|b)(E|e)(D|d)", "&lt;embed");

		ret = ret.replaceAll("<(F|f)(O|o)(R|r)(M|m)", "&lt;form");
		ret = ret.replaceAll("</(F|f)(O|o)(R|r)(M|m)", "&lt;form");

		return ret;
	}
	
	private ResultVO handleAuthError(ResultVO resultVO) {
		resultVO.setResultCode(ResponseCode.AUTH_ERROR.getCode());
		resultVO.setResultMessage(ResponseCode.AUTH_ERROR.getMessage());
		return resultVO;
	}

}
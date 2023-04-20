package com.anbtech.webffice.com.cmm.web;

import java.util.Base64;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cryptography.EgovCryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.anbtech.webffice.com.cmm.ResponseCode;
import com.anbtech.webffice.com.cmm.service.WebfficeFileMngService;
import com.anbtech.webffice.com.cmm.service.FileVO;
import com.anbtech.webffice.com.cmm.service.ResultVO;
import com.anbtech.webffice.com.jwt.config.JwtVerification;

/**
 * 파일 조회, 삭제, 다운로드 처리를 위한 컨트롤러 클래스
 * @since 2023.04.20
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *
 * </pre>
 */
@RestController
public class WebfficeFileMngApiController {

    @Resource(name = "EgovFileMngService")
    private WebfficeFileMngService fileService;
    
    /** JwtVerification */
	@Autowired
	private JwtVerification jwtVerification;
	
	/** 암호화서비스 */
    @Resource(name="egovARIACryptoService")
    EgovCryptoService cryptoService;

    /**
     * 첨부파일에 대한 삭제를 처리한다.
     *
     * @param atchFileId
     * @param fileSn
     * @return resultVO
     * @throws Exception
     */
    @DeleteMapping(value ="/cmm/fms/deleteFileInfsAPI/{atchFileId}/{fileSn}.do")
    public ResultVO deleteFileInf(HttpServletRequest request, @PathVariable("atchFileId") String atchFileId,
    	@PathVariable("fileSn") String fileSn) throws Exception {
    	ResultVO resultVO = new ResultVO();
    	
    	// 암호화된 atchFileId 를 복호화 (2022.12.06 추가) - 파일아이디가 유추 불가능하도록 조치
    	atchFileId = atchFileId.replaceAll(" ", "+");
    	byte[] decodedBytes = Base64.getDecoder().decode(atchFileId);
    	String decodedFileId = new String(cryptoService.decrypt(decodedBytes,WebfficeFileDownloadController.ALGORITM_KEY));
    			
    	FileVO fileVO = new FileVO();
    	
    	fileVO.setAtchFileId(decodedFileId);
    	fileVO.setFileSn(fileSn);

		//Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();

		if (jwtVerification.isVerification(request)) {
		    fileService.deleteFileInf(fileVO);
		    
		    resultVO.setResultCode(200);
			resultVO.setResultMessage("삭제 성공");
		} else {
			resultVO.setResultCode(ResponseCode.AUTH_ERROR.getCode());
			resultVO.setResultMessage(ResponseCode.AUTH_ERROR.getMessage());
		}

		//--------------------------------------------
		// contextRoot가 있는 경우 제외 시켜야 함
		//--------------------------------------------
		////return "forward:/cmm/fms/selectFileInfs.do";
		//return "forward:" + returnUrl;

		return resultVO;
    }
}

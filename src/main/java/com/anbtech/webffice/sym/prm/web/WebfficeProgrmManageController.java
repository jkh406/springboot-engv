package com.anbtech.webffice.sym.prm.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springmodules.validation.commons.DefaultBeanValidator;

import com.anbtech.webffice.com.cmm.ComDefaultVO;
import com.anbtech.webffice.sym.prm.service.ProgrmManageVO;
import com.anbtech.webffice.sym.prm.service.WebfficeProgrmManageService;
/**
 * 프로그램목록 관리및 변경을 처리하는 비즈니스 구현 클래스
 * @since 2023.04.26
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

@Controller
public class WebfficeProgrmManageController {

	/** Validator */
	@Autowired
	private DefaultBeanValidator beanValidator;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/** EgovProgrmManageService */
	@Resource(name = "progrmManageService")
	private WebfficeProgrmManageService progrmManageService;

	/**
	 * 프로그램목록을 상세화면 호출 및 상세조회한다.
	 * @param tmp_progrmNm  String
	 * @return 출력페이지정보 "sym/prm/WebfficeProgramListDetailSelectUpdt"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/prm/WebfficeProgramListDetailSelect.do", consumes = {MediaType.APPLICATION_JSON_VALUE , MediaType.TEXT_HTML_VALUE})
	public String selectProgrm(@RequestParam("tmp_progrmNm") String tmp_progrmNm, @ModelAttribute("searchVO") ComDefaultVO searchVO, ModelMap model) throws Exception {
		searchVO.setSearchKeyword(tmp_progrmNm);
		ProgrmManageVO progrmManageVO = progrmManageService.selectProgrm(searchVO);
		model.addAttribute("progrmManageVO", progrmManageVO);
		return "sym/prm/EgovProgramListDetailSelectUpdt";
	}

	/**
	 * 프로그램목록 리스트조회한다.
	 * @param searchVO ComDefaultVO
	 * @return 출력페이지정보 "sym/prm/WebfficeProgramListManage"
	 * @exception Exception
	 */
	@PostMapping(value = "/sym/prm/ProgramListManageSelect.do")
	public HashMap<String, Object> selectProgrmList() throws Exception {

		ProgrmManageVO vo = new ProgrmManageVO();
		HashMap<String,Object> resultMap = new HashMap<String,Object>();
		
		List<?> resultVO = progrmManageService.selectProgrmList(vo);

		resultMap.put("resultVO", resultVO);
		resultMap.put("resultCode", "300");

		System.out.print("resultVO =====" + resultMap);
		return resultMap;
	}

	/**
	 * 프로그램목록 멀티 삭제한다.
	 * @param checkedProgrmFileNmForDel String
	 * @return 출력페이지정보 "forward:/sym/prm/EgovProgramListManageSelect.do"
	 * @exception Exception
	 */
	@RequestMapping("/sym/prm/EgovProgrmManageListDelete.do")
	public String deleteProgrmManageList(@RequestParam("checkedProgrmFileNmForDel") String checkedProgrmFileNmForDel,
			@ModelAttribute("progrmManageVO") ProgrmManageVO progrmManageVO, ModelMap model) throws Exception {
		String sLocationUrl = null;
		String resultMsg = "";
		String[] delProgrmFileNm = checkedProgrmFileNmForDel.split(",");
		model.addAttribute("resultMsg", resultMsg);
		return sLocationUrl;
	}

	/**
	 * 프로그램목록을 등록화면으로 이동 및 등록 한다.
	 * @param progrmManageVO ProgrmManageVO
	 * @param commandMap     Map
	 * @return 출력페이지정보 등록화면 호출시 "sym/prm/EgovProgramListRegist",
	 *         출력페이지정보 등록처리시 "forward:/sym/prm/EgovProgramListManageSelect.do"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/prm/EgovProgramListRegist.do")
	public String insertProgrmList(@RequestParam Map<String, Object> commandMap, @ModelAttribute("progrmManageVO") ProgrmManageVO progrmManageVO, BindingResult bindingResult,
			ModelMap model) throws Exception {
		String resultMsg = "";
		String sLocationUrl = null;

		String sCmd = commandMap.get("cmd") == null ? "" : (String) commandMap.get("cmd");
		if (sCmd.equals("insert")) {
			beanValidator.validate(progrmManageVO, bindingResult);
			if (bindingResult.hasErrors()) {
				sLocationUrl = "sym/prm/EgovProgramListRegist";
				return sLocationUrl;
			}
			if (progrmManageVO.getProgrmDc() == null || progrmManageVO.getProgrmDc().equals("")) {
				progrmManageVO.setProgrmDc(" ");
			}
			progrmManageService.insertProgrm(progrmManageVO);
			sLocationUrl = "forward:/sym/prm/EgovProgramListManageSelect.do";
		} else {
			sLocationUrl = "sym/prm/EgovProgramListRegist";
		}
		return sLocationUrl;
	}

	/**
	 * 프로그램목록을 수정 한다.
	 * @param progrmManageVO ProgrmManageVO
	 * @return 출력페이지정보 "forward:/sym/prm/EgovProgramListManageSelect.do"
	 * @exception Exception
	 */
	/*프로그램목록수정*/
	@RequestMapping(value = "/sym/prm/EgovProgramListDetailSelectUpdt.do")
	public String updateProgrmList(@ModelAttribute("progrmManageVO") ProgrmManageVO progrmManageVO, BindingResult bindingResult, ModelMap model) throws Exception {
		String resultMsg = "";
		String sLocationUrl = null;

		beanValidator.validate(progrmManageVO, bindingResult);
		if (bindingResult.hasErrors()) {
			sLocationUrl = "forward:/sym/prm/EgovProgramListDetailSelect.do";
			return sLocationUrl;
		}
		if (progrmManageVO.getProgrmDc() == null || progrmManageVO.getProgrmDc().equals("")) {
			progrmManageVO.setProgrmDc(" ");
		}
		progrmManageService.updateProgrm(progrmManageVO);
		sLocationUrl = "forward:/sym/prm/EgovProgramListManageSelect.do";
		return sLocationUrl;
	}

	/**
	 * 프로그램목록을 삭제 한다.
	 * @param progrmManageVO ProgrmManageVO
	 * @return 출력페이지정보 "forward:/sym/prm/EgovProgramListManageSelect.do"
	 * @exception Exception
	 */
	@RequestMapping(value = "/sym/prm/EgovProgramListManageDelete.do")
	public String deleteProgrmList(@ModelAttribute("progrmManageVO") ProgrmManageVO progrmManageVO, ModelMap model) throws Exception {
		String resultMsg = "";
		progrmManageService.deleteProgrm(progrmManageVO);
		return "forward:/sym/prm/EgovProgramListManageSelect.do";
	}


}
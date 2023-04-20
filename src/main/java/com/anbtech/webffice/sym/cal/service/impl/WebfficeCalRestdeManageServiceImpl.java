package com.anbtech.webffice.sym.cal.service.impl;

import java.util.List;

import com.anbtech.webffice.sym.cal.service.WebfficeCalRestdeManageService;
import com.anbtech.webffice.sym.cal.service.Restde;
import com.anbtech.webffice.sym.cal.service.RestdeVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;



/**
 *
 * 휴일에 대한 서비스 구현클래스를 정의한다
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
@Service("RestdeManageService")
public class WebfficeCalRestdeManageServiceImpl extends EgovAbstractServiceImpl implements WebfficeCalRestdeManageService {

    @Resource(name="RestdeManageDAO")
    private RestdeManageDAO restdeManageDAO;

	/**
	 * 일반달력 팝업 정보를 조회한다.
	 */
	@Override
	public List<?> selectNormalRestdePopup(Restde restde) throws Exception {
		return restdeManageDAO.selectNormalRestdePopup(restde);
	}

	/**
	 * 행정달력 팝업 정보를 조회한다.
	 */
	@Override
	public List<?> selectAdministRestdePopup(Restde restde) throws Exception {
		return restdeManageDAO.selectAdministRestdePopup(restde);
	}

	/**
	 * 일반달력 일간 정보를 조회한다.
	 */
	@Override
	public List<?> selectNormalDayCal(Restde restde) throws Exception {
		return restdeManageDAO.selectNormalDayCal(restde);
	}

	/**
	 * 일반달력 일간 휴일을 조회한다.
	 */
	@Override
	public List<?> selectNormalDayRestde(Restde restde) throws Exception {
		return restdeManageDAO.selectNormalDayRestde(restde);
	}

	/**
	 * 일반달력 월간 휴일을 조회한다.
	 */
	@Override
	public List<?> selectNormalMonthRestde(Restde restde) throws Exception {
		return restdeManageDAO.selectNormalMonthRestde(restde);
	}

	/**
	 * 행정달력 일간 정보를 조회한다.
	 */
	@Override
	public List<?> selectAdministDayCal(Restde restde) throws Exception {
		return restdeManageDAO.selectAdministDayCal(restde);
	}

	/**
	 * 행정달력 일간 휴일을 조회한다.
	 */
	@Override
	public List<?> selectAdministDayRestde(Restde restde) throws Exception {
		return restdeManageDAO.selectAdministDayRestde(restde);
	}

    /**
	 * 행정달력 월간 휴일을 조회한다.
	 */
	@Override
	public List<?> selectAdministMonthRestde(Restde restde) throws Exception {
		return restdeManageDAO.selectAdministMonthRestde(restde);
	}

    /**
	 * 휴일을 삭제한다.
	 */
	@Override
	public void deleteRestde(Restde restde) throws Exception {
		restdeManageDAO.deleteRestde(restde);
	}

	/**
	 * 휴일을 등록한다.
	 */
	@Override
	public void insertRestde(Restde restde) throws Exception {
    	restdeManageDAO.insertRestde(restde);
	}

	/**
	 * 휴일 상세항목을 조회한다.
	 */
	@Override
	public Restde selectRestdeDetail(Restde restde) throws Exception {
    	Restde ret = restdeManageDAO.selectRestdeDetail(restde);
    	return ret;
	}

	/**
	 * 휴일 목록을 조회한다.
	 */
	@Override
	public List<?> selectRestdeList(RestdeVO searchVO) throws Exception {
        return restdeManageDAO.selectRestdeList(searchVO);
	}

	/**
	 * 휴일 총 갯수를 조회한다.
	 */
	@Override
	public int selectRestdeListTotCnt(RestdeVO searchVO) throws Exception {
        return restdeManageDAO.selectRestdeListTotCnt(searchVO);
	}

	/**
	 * 휴일을 수정한다.
	 */
	@Override
	public void updateRestde(Restde restde) throws Exception {
		restdeManageDAO.updateRestde(restde);
	}

}

package com.anbtech.webffice.sym.prm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import com.anbtech.webffice.com.cmm.ComDefaultVO;
import com.anbtech.webffice.sym.prm.service.ProgrmManageDtlVO;
import com.anbtech.webffice.sym.prm.service.ProgrmManageVO;
import com.anbtech.webffice.sym.prm.service.WebfficeProgrmManageService;

/**
 * 프로그램목록관리 및 프로그램변경관리에 관한 비즈니스 구현 클래스를 정의한다.
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
@Service("progrmManageService")
public class WebfficeProgrmManageServiceImpl extends EgovAbstractServiceImpl implements WebfficeProgrmManageService {

	@Resource(name="progrmManageDAO")
    private ProgrmManageDAO progrmManageDAO;


	/**
	 * 프로그램 상세정보를 조회
	 * @param vo ComDefaultVO
	 * @return ProgrmManageVO
	 * @exception Exception
	 */
	@Override
	public ProgrmManageVO selectProgrm(ComDefaultVO vo) throws Exception{
         	return progrmManageDAO.selectProgrm(vo);
	}
	/**
	 * 프로그램 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	@Override
	public List<?> selectProgrmList(ProgrmManageVO vo) throws Exception {
   		return progrmManageDAO.selectProgrmList(vo);
	}
	/**
	 * 프로그램목록 총건수를 조회한다.
	 * @param vo  ComDefaultVO
	 * @return Integer
	 * @exception Exception
	 */
    @Override
	public int selectProgrmListTotCnt(ComDefaultVO vo) throws Exception {
        return progrmManageDAO.selectProgrmListTotCnt(vo);
	}
	/**
	 * 프로그램 정보를 등록
	 * @param vo ProgrmManageVO
	 * @exception Exception
	 */
	@Override
	public void insertProgrm(ProgrmManageVO vo) throws Exception {
    	progrmManageDAO.insertProgrm(vo);
	}

	/**
	 * 프로그램 정보를 수정
	 * @param vo ProgrmManageVO
	 * @exception Exception
	 */
	@Override
	public void updateProgrm(ProgrmManageVO vo) throws Exception {
    	progrmManageDAO.updateProgrm(vo);
	}

	/**
	 * 프로그램 정보를 삭제
	 * @param vo ProgrmManageVO
	 * @exception Exception
	 */
	@Override
	public void deleteProgrm(ProgrmManageVO vo) throws Exception {
    	progrmManageDAO.deleteProgrm(vo);
	}


}
package com.anbtech.webffice.sym.prm.service;

import java.util.List;

import com.anbtech.webffice.com.cmm.ComDefaultVO;

/**
 * 프로그램관리에 관한 서비스 인터페이스 클래스를 정의한다.
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

public interface WebfficeProgrmManageService {
	/**
	 * 프로그램 상세정보를 조회
	 * @param vo ComDefaultVO
	 * @return ProgrmManageVO
	 * @exception Exception
	 */
	ProgrmManageVO selectProgrm(ComDefaultVO vo) throws Exception;
	/**
	 * 프로그램 목록을 조회
	 * @param vo ComDefaultVO
	 * @return List
	 * @exception Exception
	 */
	List<?> selectProgrmList(ProgrmManageVO vo) throws Exception;
	/**
	 * 프로그램목록 총건수를 조회한다.
	 * @param vo ComDefaultVO
	 * @return int
	 * @exception Exception
	 */
	int selectProgrmListTotCnt(ComDefaultVO vo) throws Exception;
	/**
	 * 프로그램 정보를 등록
	 * @param vo ProgrmManageVO
	 * @exception Exception
	 */
	void insertProgrm(ProgrmManageVO vo) throws Exception;

	/**
	 * 프로그램 정보를 수정
	 * @param vo ProgrmManageVO
	 * @exception Exception
	 */
	void updateProgrm(ProgrmManageVO vo) throws Exception;

	/**
	 * 프로그램 정보를 삭제
	 * @param vo ProgrmManageVO
	 * @exception Exception
	 */
	void deleteProgrm(ProgrmManageVO vo) throws Exception;


}
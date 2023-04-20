package com.anbtech.webffice.cop.bbs.service.impl;
import java.util.List;

import com.anbtech.webffice.cop.bbs.service.BoardMaster;
import com.anbtech.webffice.cop.bbs.service.BoardMasterVO;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;

import org.springframework.stereotype.Repository;

/**
 * 게시판 속성정보 관리를 위한 데이터 접근 클래스
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
@Repository("BBSLoneMasterDAO")
public class BBSLoneMasterDAO extends EgovAbstractMapper {

    /**
     * 등록된 게시판 속성정보를 삭제한다.
     *
     * @param BoardMaster
     */
    public void deleteMaster(BoardMaster boardMaster) throws Exception {
	update("BBSLoneMasterDAO.deleteMaster", boardMaster);
    }

    /**
     * 신규 게시판 속성정보를 등록한다.
     *
     * @param BoardMaster
     */
    public int insertMaster(BoardMaster boardMaster) throws Exception {
	return (int)insert("BBSLoneMasterDAO.insertMaster", boardMaster);
    }

    /**
     * 게시판 속성정보 한 건을 상세조회 한다.
     *
     * @param BoardMasterVO
     */
    public BoardMasterVO selectMaster(BoardMaster vo) throws Exception {
	return (BoardMasterVO)selectOne("BBSLoneMasterDAO.selectMaster", vo);
    }

    /**
     * 게시판 속성정보 목록을 조회한다.
     *
     * @param BoardMasterVO
     */
    @SuppressWarnings("unchecked")
    public List<BoardMasterVO> selectMasterList(BoardMasterVO vo) throws Exception {
	return (List<BoardMasterVO>) list("BBSLoneMasterDAO.selectMasterList", vo);
    }

    /**
     * 게시판 속성정보 목록 숫자를 조회한다
     *
     * @param vo
     * @return
     * @throws Exception
     */
    public int selectMasterListCnt(BoardMasterVO vo) throws Exception {
	return (Integer)selectOne("BBSLoneMasterDAO.selectMasterListCnt", vo);
    }

    /**
     * 게시판 속성정보를 수정한다.
     *
     * @param BoardMaster
     */
    public void updateMaster(BoardMaster boardMaster) throws Exception {
	update("BBSLoneMasterDAO.updateMaster", boardMaster);
    }
}

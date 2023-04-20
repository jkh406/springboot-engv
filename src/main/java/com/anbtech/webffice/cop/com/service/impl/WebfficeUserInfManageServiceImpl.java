package com.anbtech.webffice.cop.com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anbtech.webffice.cop.com.service.WebfficeUserInfManageService;
import com.anbtech.webffice.cop.com.service.UserInfVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


/**
 * 협업에서 사용할 사용자 조회 서비스 기능 구현 클래스
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
@Service("EgovUserInfManageService")
public class WebfficeUserInfManageServiceImpl extends EgovAbstractServiceImpl implements WebfficeUserInfManageService {

    @Resource(name = "EgovUserInfManageDAO")
    private WebfficeUserInfManageDAO userInfDAO;

    /**
     * 동호회 운영자 목록을 조회한다.
     * 
     * @see com.anbtech.webffice.cop.com.service.WebfficeUserInfManageService#selectClubOprtrList(com.anbtech.webffice.cop.com.service.UserInfVO)
     */
    public Map<String, Object> selectClubOprtrList(UserInfVO userVO) throws Exception {
	List<UserInfVO> result = userInfDAO.selectClubOprtrList(userVO);
	int cnt = userInfDAO.selectClubOprtrListCnt(userVO);
	
	Map<String, Object> map = new HashMap<String, Object>();
	
	map.put("resultList", result);
	map.put("resultCnt", Integer.toString(cnt));

	return map;
    }

    /**
     * 동호회 사용자 목록을 조회한다.
     * 
     * @see com.anbtech.webffice.cop.com.service.WebfficeUserInfManageService#selectClubUserList(com.anbtech.webffice.cop.com.service.UserInfVO)
     */
    public Map<String, Object> selectClubUserList(UserInfVO userVO) throws Exception {
	List<UserInfVO> result = userInfDAO.selectClubUserList(userVO);
	int cnt = userInfDAO.selectClubUserListCnt(userVO);
	
	Map<String, Object> map = new HashMap<String, Object>();
	
	map.put("resultList", result);
	map.put("resultCnt", Integer.toString(cnt));

	return map;
    }

    /**
     * 커뮤니티 관리자 목록을 조회한다.
     * 
     * @see com.anbtech.webffice.cop.com.service.WebfficeUserInfManageService#selectCmmntyMngrList(com.anbtech.webffice.cop.com.service.UserInfVO)
     */
    public Map<String, Object> selectCmmntyMngrList(UserInfVO userVO) throws Exception {
	List<UserInfVO> result = userInfDAO.selectCmmntyMngrList(userVO);
	int cnt = userInfDAO.selectCmmntyMngrListCnt(userVO);
	
	Map<String, Object> map = new HashMap<String, Object>();
	
	map.put("resultList", result);
	map.put("resultCnt", Integer.toString(cnt));

	return map;
    }

    /**
     * 커뮤니티 사용자 목록을 조회한다.
     * 
     * @see com.anbtech.webffice.cop.com.service.WebfficeUserInfManageService#selectCmmntyUserList(com.anbtech.webffice.cop.com.service.UserInfVO)
     */
    public Map<String, Object> selectCmmntyUserList(UserInfVO userVO) throws Exception {
	List<UserInfVO> result = userInfDAO.selectCmmntyUserList(userVO);
	int cnt = userInfDAO.selectCmmntyUserListCnt(userVO);
	
	Map<String, Object> map = new HashMap<String, Object>();
	
	map.put("resultList", result);
	map.put("resultCnt", Integer.toString(cnt));

	return map;
    }

    /**
     * 사용자 정보에 대한 목록을 조회한다.
     * 
     * @see com.anbtech.webffice.cop.com.service.WebfficeUserInfManageService#selectUserList(com.anbtech.webffice.cop.com.service.UserInfVO)
     */
    public Map<String, Object> selectUserList(UserInfVO userVO) throws Exception {
	List<UserInfVO> result = userInfDAO.selectUserList(userVO);
	int cnt = userInfDAO.selectUserListCnt(userVO);
	
	Map<String, Object> map = new HashMap<String, Object>();
	
	map.put("resultList", result);
	map.put("resultCnt", Integer.toString(cnt));

	return map;
    }

    /**
     * 동호회에 대한 모든 사용자 목록을 조회한다.
     * 
     * @see com.anbtech.webffice.cop.com.service.WebfficeUserInfManageService#selectAllClubUser(com.anbtech.webffice.cop.com.service.UserInfVO)
     */
    public List<UserInfVO> selectAllClubUser(UserInfVO userVO) throws Exception {
	return userInfDAO.selectAllClubUser(userVO);
    }

    /**
     * 커뮤니티에 대한 모든 사용자 목록을 조회한다.
     * 
     * @see com.anbtech.webffice.cop.com.service.WebfficeUserInfManageService#selectAllCmmntyUser(com.anbtech.webffice.cop.com.service.UserInfVO)
     */
    public List<UserInfVO> selectAllCmmntyUser(UserInfVO userVO) throws Exception {
	return userInfDAO.selectAllCmmntyUser(userVO);
    }
}

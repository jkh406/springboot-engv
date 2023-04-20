package com.anbtech.webffice.com.cmm.service.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * WebfficeComAbstractDAO.java 클래스
 *
 * @since 2023. 04. 20.
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    -------------    ----------------------
 * </pre>
 */
public abstract class WebfficeComAbstractDAO extends EgovAbstractMapper {

	@Override
	@Resource(name = "egov.sqlSession")
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
		super.setSqlSessionFactory(sqlSession);
	}

}

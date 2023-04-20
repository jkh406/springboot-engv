package com.anbtech.webffice.com.cmm.util;

import javax.sql.DataSource;

import org.egovframe.rte.fdl.idgnr.impl.EgovTableIdGnrServiceImpl;
import org.egovframe.rte.fdl.idgnr.impl.strategy.EgovIdGnrStrategyImpl;

/**
 * @ClassName : WebfficeIdGnrBuilder.java
 * @Description : IdGen 정보 builder
 *
 * @since  : 2023. 04. 20
 * @version : 1.0
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일              수정자               수정내용
 *  -------------  ------------   ---------------------
 * </pre>
 *
 */
public class WebfficeIdGnrBuilder {

	// TODO : 기본값 설정, 예외처리 필요

	private DataSource dataSource;
	private EgovIdGnrStrategyImpl egovIdGnrStrategyImpl;

	private String preFix;
	private int cipers;
	private char fillChar;

	private int blockSize;
	private String table;
	private String tableName;

	public WebfficeIdGnrBuilder setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		return this;
	}

	public WebfficeIdGnrBuilder setEgovIdGnrStrategyImpl(EgovIdGnrStrategyImpl egovIdGnrStrategyImpl) {
		this.egovIdGnrStrategyImpl = egovIdGnrStrategyImpl;
		return this;
	}

	public WebfficeIdGnrBuilder setPreFix(String preFix) {
		this.preFix = preFix;
		return this;
	}
	public WebfficeIdGnrBuilder setCipers(int cipers) {
		this.cipers = cipers;
		return this;
	}
	public WebfficeIdGnrBuilder setFillChar(char fillChar) {
		this.fillChar = fillChar;
		return this;
	}
	public WebfficeIdGnrBuilder setBlockSize(int blockSize) {
		this.blockSize = blockSize;
		return this;
	}
	public WebfficeIdGnrBuilder setTable(String table) {
		this.table = table;
		return this;
	}
	public WebfficeIdGnrBuilder setTableName(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public EgovTableIdGnrServiceImpl build() {

		EgovTableIdGnrServiceImpl egovTableIdGnrServiceImpl = new EgovTableIdGnrServiceImpl();
		egovTableIdGnrServiceImpl.setDataSource(dataSource);
		if(egovIdGnrStrategyImpl != null) {
			egovIdGnrStrategyImpl = new EgovIdGnrStrategyImpl();
			egovIdGnrStrategyImpl.setPrefix(preFix);
			egovIdGnrStrategyImpl.setCipers(cipers);
			egovIdGnrStrategyImpl.setFillChar(fillChar);

			egovTableIdGnrServiceImpl.setStrategy(egovIdGnrStrategyImpl);
		}
		egovTableIdGnrServiceImpl.setBlockSize(blockSize);
		egovTableIdGnrServiceImpl.setTable(table);
		egovTableIdGnrServiceImpl.setTableName(tableName);

		return egovTableIdGnrServiceImpl;
	}



}

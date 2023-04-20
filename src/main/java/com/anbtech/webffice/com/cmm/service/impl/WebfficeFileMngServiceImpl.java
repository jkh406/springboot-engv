package com.anbtech.webffice.com.cmm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anbtech.webffice.com.cmm.service.WebfficeFileMngService;
import com.anbtech.webffice.com.cmm.service.FileVO;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * @Class Name : WebfficeFileMngServiceImpl.java
 * @Description : 파일정보의 관리를 위한 구현 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *
 * @since 2023. 04. 20.
 * @version
 * @see
 *
 */
@Service("EgovFileMngService")
public class WebfficeFileMngServiceImpl extends EgovAbstractServiceImpl implements WebfficeFileMngService {

    @Resource(name = "FileManageDAO")
    private FileManageDAO fileMngDAO;

    /**
     * 여러 개의 파일을 삭제한다.
     *
     * @see com.anbtech.webffice.com.cmm.service.WebfficeFileMngService#deleteFileInfs(java.util.List)
     */
    @Override
    public void deleteFileInfs(List<?> fvoList) throws Exception {
	fileMngDAO.deleteFileInfs(fvoList);
    }

    /**
     * 하나의 파일에 대한 정보(속성 및 상세)를 등록한다.
     *
     * @see com.anbtech.webffice.com.cmm.service.WebfficeFileMngService#insertFileInf(com.anbtech.webffice.com.cmm.service.FileVO)
     */
    @Override
	public String insertFileInf(FileVO fvo) throws Exception {
	String atchFileId = fvo.getAtchFileId();

	fileMngDAO.insertFileInf(fvo);

	return atchFileId;
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 등록한다.
     *
     * @see com.anbtech.webffice.com.cmm.service.WebfficeFileMngService#insertFileInfs(java.util.List)
     */
    @Override
    public String insertFileInfs(List<?> fvoList) throws Exception {
	String atchFileId = "";

	if (fvoList.size() != 0) {
	    atchFileId = fileMngDAO.insertFileInfs(fvoList);
	}
	if("".equals(atchFileId)){
		atchFileId = null;
	}
	return atchFileId;
    }

    /**
     * 파일에 대한 목록을 조회한다.
     *
     * @see com.anbtech.webffice.com.cmm.service.WebfficeFileMngService#selectFileInfs(com.anbtech.webffice.com.cmm.service.FileVO)
     */
    @Override
	public List<FileVO> selectFileInfs(FileVO fvo) throws Exception {
	return fileMngDAO.selectFileInfs(fvo);
    }

    /**
     * 여러 개의 파일에 대한 정보(속성 및 상세)를 수정한다.
     *
     * @see com.anbtech.webffice.com.cmm.service.WebfficeFileMngService#updateFileInfs(java.util.List)
     */
    @Override
	public void updateFileInfs(List<?> fvoList) throws Exception {
	//Delete & Insert
	fileMngDAO.updateFileInfs(fvoList);
    }

    /**
     * 하나의 파일을 삭제한다.
     *
     * @see com.anbtech.webffice.com.cmm.service.WebfficeFileMngService#deleteFileInf(com.anbtech.webffice.com.cmm.service.FileVO)
     */
    @Override
	public void deleteFileInf(FileVO fvo) throws Exception {
	fileMngDAO.deleteFileInf(fvo);
    }

    /**
     * 파일에 대한 상세정보를 조회한다.
     *
     * @see com.anbtech.webffice.com.cmm.service.WebfficeFileMngService#selectFileInf(com.anbtech.webffice.com.cmm.service.FileVO)
     */
    @Override
	public FileVO selectFileInf(FileVO fvo) throws Exception {
	return fileMngDAO.selectFileInf(fvo);
    }

    /**
     * 파일 구분자에 대한 최대값을 구한다.
     *
     * @see com.anbtech.webffice.com.cmm.service.WebfficeFileMngService#getMaxFileSN(com.anbtech.webffice.com.cmm.service.FileVO)
     */
    @Override
	public int getMaxFileSN(FileVO fvo) throws Exception {
	return fileMngDAO.getMaxFileSN(fvo);
    }

    /**
     * 전체 파일을 삭제한다.
     *
     * @see com.anbtech.webffice.com.cmm.service.WebfficeFileMngService#deleteAllFileInf(com.anbtech.webffice.com.cmm.service.FileVO)
     */
    @Override
	public void deleteAllFileInf(FileVO fvo) throws Exception {
	fileMngDAO.deleteAllFileInf(fvo);
    }

    /**
     * 파일명 검색에 대한 목록을 조회한다.
     *
     * @see com.anbtech.webffice.com.cmm.service.WebfficeFileMngService#selectFileListByFileNm(com.anbtech.webffice.com.cmm.service.FileVO)
     */
    @Override
	public Map<String, Object> selectFileListByFileNm(FileVO fvo) throws Exception {
	List<FileVO>  result = fileMngDAO.selectFileListByFileNm(fvo);
	int cnt = fileMngDAO.selectFileListCntByFileNm(fvo);

	Map<String, Object> map = new HashMap<String, Object>();

	map.put("resultList", result);
	map.put("resultCnt", Integer.toString(cnt));

	return map;
    }

    /**
     * 이미지 파일에 대한 목록을 조회한다.
     *
     * @see com.anbtech.webffice.com.cmm.service.WebfficeFileMngService#selectImageFileList(com.anbtech.webffice.com.cmm.service.FileVO)
     */
    @Override
	public List<FileVO> selectImageFileList(FileVO vo) throws Exception {
	return fileMngDAO.selectImageFileList(vo);
    }
}

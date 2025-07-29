package com.dtvs.dtvsonline.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtvs.dtvsonline.dao.MmrMasterDAO;
import com.dtvs.dtvsonline.model.MmrMaster;

@Service
@Transactional
public class MmrMasterServiceImpl implements MmrMasterService {

	@Autowired
	private MmrMasterDAO mmrmasterdao;
	
	public List<MmrMaster> fetchMaterialList() {
		return mmrmasterdao.getAllMaterialList();
	}

}

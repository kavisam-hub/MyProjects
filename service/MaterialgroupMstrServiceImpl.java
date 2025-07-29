package com.dtvs.dtvsonline.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtvs.dtvsonline.dao.MaterialGroupMstrDAO;
import com.dtvs.dtvsonline.model.MaterialGroupMaster;

@Transactional
@Service
public class MaterialgroupMstrServiceImpl implements MaterialGroupMstrService {

	@Autowired
	private MaterialGroupMstrDAO materialgroupmstrdao;
	public List<MaterialGroupMaster> getAllGroup() {
		// TODO Auto-generated method stub
		return materialgroupmstrdao.fetchAllGroup();
	}

}

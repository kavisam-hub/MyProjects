package com.dtvs.dtvsonline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dtvs.dtvsonline.dao.MaterialCommodityMasterDAO;
import com.dtvs.dtvsonline.model.MaterialCommodityMaster;

@Service
@Transactional
public class MaterialCommodityMasterServiceImpl implements MaterialCommodityMasterService {

	@Autowired
	private MaterialCommodityMasterDAO materialcommoditymasterdao;

	
    @Transactional
	public List<MaterialCommodityMaster> fetchAllCommodity() {
		
		return materialcommoditymasterdao.getAllCommodity();
	}	

}

package com.dtvs.dtvsonline.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtvs.dtvsonline.dao.MaterialTypeMstrDAO;
import com.dtvs.dtvsonline.model.MaterialTypeMaster;

@Service
@Transactional
public class MaterialTypeMstrServiceImpl implements MaterialTypeMstrService {

	@Autowired
	private MaterialTypeMstrDAO materialtypemstrdao;
	public List<MaterialTypeMaster> getMaterialType() {
		// TODO Auto-generated method stub
		return materialtypemstrdao.fetchMaterialType();
	}

}

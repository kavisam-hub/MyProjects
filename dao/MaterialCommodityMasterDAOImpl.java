package com.dtvs.dtvsonline.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dtvs.dtvsonline.model.MaterialCommodityMaster;

@Repository
public class MaterialCommodityMasterDAOImpl implements MaterialCommodityMasterDAO {

	@Autowired
    private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<MaterialCommodityMaster> getAllCommodity() {
		
		return sessionFactory.getCurrentSession().createQuery("from MaterialCommodityMaster").list();
	}	
	
	
}



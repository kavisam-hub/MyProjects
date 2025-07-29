package com.dtvs.dtvsonline.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dtvs.dtvsonline.model.MaterialTypeMaster;

@Repository
public class MaterialTypeMstrDAOImpl implements MaterialTypeMstrDAO {

	@Autowired
    private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<MaterialTypeMaster> fetchMaterialType() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from MaterialTypeMaster").list();
	}

}

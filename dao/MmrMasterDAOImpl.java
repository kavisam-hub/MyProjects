package com.dtvs.dtvsonline.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dtvs.dtvsonline.model.MmrMaster;

@Repository
public class MmrMasterDAOImpl implements MmrMasterDAO {

	@Autowired
	private  SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<MmrMaster> getAllMaterialList() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from MmrMaster m where m.mmr_sloc='DS01'").list();
	}

}

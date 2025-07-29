package com.dtvs.dtvsonline.service;
 

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dtvs.dtvsonline.dao.ReportsDAO;
import com.dtvs.dtvsonline.model.Authority;

 
@Service
public class ReportsServiceImpl implements ReportsService {	
	@Autowired
	 private ReportsDAO reportsDAO;
	 
	/*    public void setReportsDAO(ReportsDAO reportsDAO) {
	        this.reportsDAO = reportsDAO;
	    }*/
	 //get the report period list
	 @Override
	    @Transactional
	    public List<Authority> listAuthority(String loginname){
	        return reportsDAO.listAuthority(loginname);
	    }
}
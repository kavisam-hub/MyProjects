package com.dtvs.dtvsonline.dao;

import java.util.List;

import com.dtvs.dtvsonline.model.Authority; 

 
public interface ReportsDAO {
	 
	 
	 //get the authorities to the user
	 public List<Authority> listAuthority(String loginname);
	 
	 //get the username
	 public String GetUserName();
}
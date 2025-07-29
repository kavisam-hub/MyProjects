package com.dtvs.dtvsonline.service;
 
import java.util.List;

import com.dtvs.dtvsonline.model.Authority;

 
public interface ReportsService {   
	
	 
	 //list authority
	 public List<Authority> listAuthority(String loginname);
}
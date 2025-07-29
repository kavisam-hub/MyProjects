package com.dtvs.dtvsonline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtvs.dtvsonline.dao.PersonDao;
import com.dtvs.dtvsonline.model.AdUser;

@Service	
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonDao persondao;

	
	public List<AdUser> getPerson() {
		// TODO Auto-generated method stub
		return persondao.getPersonDetails();
	}

}

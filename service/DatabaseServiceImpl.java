package com.dtvs.dtvsonline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dtvs.dtvsonline.dao.DatabaseDAO;
import com.dtvs.dtvsonline.model.User;

@Service
@Transactional
public class DatabaseServiceImpl implements DatabaseService {
	@Autowired
	private DatabaseDAO databasedao;

	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		return databasedao.getUserById(id);
	}

}

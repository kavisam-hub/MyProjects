package com.dtvs.dtvsonline.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dtvs.dtvsonline.dao.UserDAO;
import com.dtvs.dtvsonline.model.User;

 
@Service
public class UserServiceImpl implements UserService {
    @Autowired 
    private UserDAO userDAO;
 
  /*  public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
        } */
    
   
    @Override
    @Transactional
    public void addUser(User u) {
        userDAO.addUser(u);
    }
 
    @Override
    @Transactional
    public void updateUser(User u) {
        userDAO.updateUser(u);
    }
 
    @Override
    @Transactional
    public List<User> listUser() {
        return this.userDAO.listUser();
    }
 
    @Override
    @Transactional
    public User getUserById(String id) {
        return userDAO.getUserById(id);
    }
 
    @Override
    @Transactional
    public void removeUser(String id) {
        userDAO.removeUser(id);
    }     

	@Override
	@Transactional
	public List<User> ADlistUser() {
		// TODO Auto-generated method stub
		return userDAO.ADlistUser();
	}
	@Override
	@Transactional
	public void saveOrUpdateUser(User u) {
		// TODO Auto-generated method stub
		userDAO.saveOrUpdateUser(u);
	} 	
	
}
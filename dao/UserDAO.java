package com.dtvs.dtvsonline.dao;
 
import java.util.List;

import com.dtvs.dtvsonline.model.User;

 
public interface UserDAO {
    public void addUser(User u);
    public void updateUser(User u);
    public List<User> listUser();
    public User getUserById(String id);
    public void removeUser(String id);
    
    //get the AD list users
    public List<User> ADlistUser();
    public  void saveOrUpdateUser(User u);  	
	
	
}
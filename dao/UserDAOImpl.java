package com.dtvs.dtvsonline.dao;
 
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dtvs.dtvsonline.model.User;
 
@Repository
public class UserDAOImpl implements UserDAO {
     
    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    @Autowired
    private SessionFactory sessionFactory;
     
 /*   public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }*/
 
    @Override
    public void addUser(User u) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(u);
        logger.info("User saved successfully, Person Details="+u);
    }
 
    @Override
    public void updateUser(User u) {
        Session session = sessionFactory.getCurrentSession();
        session.update(u);
        logger.info("User updated successfully, Person Details="+u);
    }
     
    @Override
    public void saveOrUpdateUser(User u) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(u);
        logger.info("User updated successfully, Person Details="+u);
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<User> listUser() {
        Session session = sessionFactory.getCurrentSession();
        List<User> userList = session.createQuery("from User where status ='A' order by name").list();
        //for(User u : userList){
            logger.info("User List viewed");
        //}
        return userList;
    }
 
    @Override
    public User getUserById(String id) {
        Session session = sessionFactory.getCurrentSession();      
        User u = (User) session.get(User.class, new String(id));
        logger.info("User deatil viewed, Person details="+u);
        return u;
    }
 
    @Override
    public void removeUser(String id) {
        Session session = sessionFactory.getCurrentSession();
        User u = (User) session.load(User.class, new String(id));
        if(null != u){
            session.delete(u);
        }
        logger.info("User deleted successfully, user details="+u);
    }
 
    
    @SuppressWarnings("unchecked")
    @Override
    public List<User> ADlistUser() {
        Session session = sessionFactory.getCurrentSession();
        List<User> userList = session.createQuery("from User where  adStatus='A' ").list();
        //for(User u : userList){
            logger.info("User List viewed");
        //}
        return userList;
    }	
    
   
}
package com.dtvs.dtvsonline.dao;
 
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.dtvs.dtvsonline.model.Authority;

 
@Repository
public class ReportsDAOImpl implements ReportsDAO {
     
	//get the login name	
	private static final Logger logger = LoggerFactory.getLogger(ReportsDAOImpl.class);
	@Autowired
    private SessionFactory sessionFactory;
     
  /*  public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }*/
  
      
    //authority list
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Authority> listAuthority(String loginname) {
        Session session = sessionFactory.getCurrentSession();
        List<Authority> AuthorityList = session.createQuery("from Authority a where a.id.username in :loginname ")
        		                        .setParameter("loginname", loginname)
        								.list();
        logger.info("Login Name:"+loginname+" Action : Logged in Successful and authorization Verified...");
        
        return AuthorityList;
    }
    
    @Override
    public String GetUserName()
    {
    	//get the login name
    	String login_name; 
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	login_name = auth.getName().toUpperCase(); //get logged in username
       
    	if (login_name == null)
    	{
    		login_name = "Anonymous User";
    	}
    	
    	return login_name;
    }
}
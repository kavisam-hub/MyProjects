package com.dtvs.dtvsonline.security;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.NamingException;

import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import com.dtvs.dtvsonline.model.Authority;
import com.dtvs.dtvsonline.model.AuthorityPK;
import com.dtvs.dtvsonline.model.User;
import com.dtvs.dtvsonline.service.ReportsService;
import com.dtvs.dtvsonline.service.UserService;



public class DTVSDbAuthPopulator implements UserDetailsContextMapper {
	 @Autowired
	private ReportsService reportService;
    
   
    
	 @Autowired  
  private UserService userService;
    
    
	
   // populating roles assigned to the user from AUTHORITIES table in DB
   private List<SimpleGrantedAuthority> loadRolesFromDatabase(String username) {
   
	 
	   List<Authority>  Authoritylist = reportService.listAuthority(username.toUpperCase());
	   
	   List<SimpleGrantedAuthority> allAuthorities = new ArrayList<SimpleGrantedAuthority>();
	   
	   
	// iterate via "New way to loop"
			
			for (Authority Authorityobj : Authoritylist) {
				AuthorityPK authpk = Authorityobj.getId();				
				 allAuthorities.add(new SimpleGrantedAuthority(authpk.getRole()));
			}
	   //Authoritylist.
	  
	   //return test;
	  // List <SimpleGrantedAuthority> = 
      //"SELECT ROLE FROM AUTHORITIES WHERE LCASE(USERNAME) LIKE ?"
      //...
	   
	   return allAuthorities;
   }

   @Override
   public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
      List<SimpleGrantedAuthority> allAuthorities = new ArrayList<SimpleGrantedAuthority>();
      for (GrantedAuthority auth : authorities) {
        if (auth != null && !auth.getAuthority().isEmpty()) {
           allAuthorities.add((SimpleGrantedAuthority) auth);
       }
      }
      // add additional roles from the database table
      allAuthorities.addAll(loadRolesFromDatabase(username));
      
      	User userdetails = this.userService.getUserById(username.toUpperCase());
      
      if (userdetails == null)
      {
    	  Attributes ldap = ctx.getAttributes();
          String mailid;
          String company;
          String ccno;
          String department;
          String manager="";
          String name;
          String displayname;
          
          
          try {
     		 mailid=(String) ldap.get("Mail").get();
     		 company = (String) ldap.get("company").get();
     		 ccno = (String) ldap.get("description").get();
     		 department= (String) ldap.get("department").get();
     		 manager= (String) ldap.get("manager").get();
     		 System.out.println("Manager value is  ---"+manager);
     		 name= (String) ldap.get("cn").get();
     		 displayname = (String) ldap.get("displayname").get();
     		 
     		 User user = new User();
     		user.setId(username.toUpperCase());
     		user.setDept(department);
     		user.setMailid(mailid);
     		user.setName(displayname);
     		user.setPlant(company);
     		user.setCcno(ccno);
     	//	user.setManager(name);     		
     		user.setAdStatus("A");
     		user.setStatus("A");	
     	//	user.setManager(manager);
             
     		this.userService.addUser(user);
     	
     		//	DTVSADUserdetails usersearch = new DTVSADUserdetails();
     		// usersearch.searchuser();
     		
     	} catch (NamingException e) {
     		// TODO Auto-generated catch block
     		e.printStackTrace();
     	}
          
          
      }
      
      //DTVSADUserdetails userdetail = new DTVSADUserdetails();
      //userdetail.getUserBasicAttributes();
      
      
      return new org.springframework.security.core.userdetails.User(username, "", true, true, true, true, allAuthorities);
   }

   @Override
   public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
   }
   

 

}
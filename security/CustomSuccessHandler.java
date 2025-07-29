package com.dtvs.dtvsonline.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.dtvs.dtvsonline.model.User;
import com.dtvs.dtvsonline.service.UserService;
import com.dtvs.dtvsonline.utility.Utils;

public class CustomSuccessHandler extends
SavedRequestAwareAuthenticationSuccessHandler {
		@Autowired
		private UserService userService;    
	   
	    
	
@Override
public void onAuthenticationSuccess(final HttpServletRequest request,
    final HttpServletResponse response, final Authentication authentication)
    		throws IOException, ServletException  {
	super.onAuthenticationSuccess(request, response, authentication);
	
	
	   	String userid=Utils.GetUserName();
	//   	System.out.println("Logged User " +userid);
	    User userdetails = userService.getUserById(userid);
        HttpSession session = request.getSession(true);
        session.setAttribute("UserId", userid);              
        
        session.setAttribute("UserName", userdetails.getName());
        session.setAttribute("UserDept", userdetails.getDept());               

      } 
}

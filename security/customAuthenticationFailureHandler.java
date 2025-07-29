package com.dtvs.dtvsonline.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.dtvs.dtvsonline.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

public class customAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	private UserService userService;  
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, 
    		HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, 
    		ServletException 
    	{
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
     //   httpServletResponse.getOutputStream().println(String.format(jsonPayload, e.getMessage(), Calendar.getInstance().getTime()));
        
        String login_name; 
        String x = e.toString();
        try
        {
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        	login_name = auth.getName().toUpperCase(); //get logged in username	
         	if (login_name == null)
        	{
        		login_name = "Anonymous User";
        	}
        }
        catch(Exception ex)
        {
        	//response.sendRedirect("/login.html?error=fail");   
        	httpServletResponse.sendRedirect("/dtvsonline/WEB-INF/views/jsp/MMR/materiallistPCLApproval.jsp");
        	//String t = ex.toString();
        }

    }
}
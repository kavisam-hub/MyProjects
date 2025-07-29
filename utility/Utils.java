package com.dtvs.dtvsonline.utility;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
	
	public static String GetUserName()
    {
    	//get the login name
    	String login_name; 
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	login_name = auth.getName().toUpperCase(); //get logged in username	
       
//    	if (login_name == null)
//    	{
//    		login_name = "Anonymous User";
//    	}
    	//Added by Akila on 23/12/2019
    	if (login_name.equals("ANONYMOUSUSER"))
    	{
    		//login_name = "Anonymous User";
    		login_name="rln.pur";
    	}
    	
    	return login_name;
    }

}

package com.dtvs.dtvsonline.security;

import org.springframework.ldap.core.AttributesMapper;

import com.dtvs.dtvsonline.model.AdUser;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
 
/**
 * Created by ravi on 10.10.2015.
 */
@SuppressWarnings("rawtypes")
public class UserAttributesMapper implements AttributesMapper{	
	
    @Override
    public Object mapFromAttributes(Attributes attributes) throws NamingException {
        AdUser user = new AdUser();
 
       Attribute id = attributes.get("sAMAccountName");
        if (id != null){
        	user.setId((String) id.get());
        }
 
        Attribute displayname = attributes.get("displayname");
        if (displayname != null){
           // person.setDisplayName((String) displayname.get());
           user.setName((String) displayname.get());
        }
 
        Attribute company = attributes.get("company");
        if (company != null){
        	user.setPlant((String) company.get());
        }
 
        Attribute department = attributes.get("department");
        if (department != null){
        	user.setDept((String) department.get());
        }
 
        Attribute mail = attributes.get("mail");
        if (mail != null){
            user.setMailid((String) mail.get());
        }
 
        Attribute telephonenumber = attributes.get("telephonenumber");
        if (telephonenumber != null){
            user.setPhno((String) telephonenumber.get());
        }
        
        Attribute description = attributes.get("description");
        if (description != null){
        	
            user.setDescription((String) description.get());
        }
        
        Attribute msExchAssistantName = attributes.get("msExchAssistantName");
        if (msExchAssistantName != null){
        	
            user.setMsExchAssistantName((String) msExchAssistantName.get());
        }
        
        Attribute title = attributes.get("title");
        if (title != null){
            user.setTitle((String) title.get());
        }
        
       // System.out.println(user.toString());
 
        return user;
    }
}
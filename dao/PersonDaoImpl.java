package com.dtvs.dtvsonline.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Repository;

import com.dtvs.dtvsonline.model.AdUser;
import com.dtvs.dtvsonline.security.UserAttributesMapper;




@Repository
public class PersonDaoImpl implements PersonDao {
	
	 private LdapTemplate ldapTemplate;
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdUser> getPersonDetails() {
		// TODO Auto-generated method stub
		List<AdUser> persons = new ArrayList<AdUser>();
        try {
        	
            List search = ldapTemplate.search("", "(objectClass=person)", new UserAttributesMapper());
            persons.addAll(search);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return persons;
	}

}

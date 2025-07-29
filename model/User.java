package com.dtvs.dtvsonline.model;

import java.io.Serializable;


import javax.persistence.*;

import org.hibernate.validator.constraints.NotEmpty;





/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="ad_status")
	private String adStatus;
   
	
	//@NotEmpty
	private String ccno;
    
	
	//@NotEmpty
	private String dept;

	private String flag;
  
	//@NotEmpty
	private String mailid;
   
	@NotEmpty
	private String name;

	private String phno;

	//@NotEmpty
	private String plant;

	private String status;

	private String assistantName;		
	private String lastpwdchange;	
	private String Manager;
	private String hodmailid;
	private String pclheadmailid;
/*	@Column(name="iscrnhbuser")
	private String iscrnhbuser;
	
	@Column(name="iscrnhbhod")
	private String iscrnhbhod; */

	public String getPclheadmailid() {
		return pclheadmailid;
	}

	public void setPclheadmailid(String pclheadmailid) {
		this.pclheadmailid = pclheadmailid;
	}

	public String getHodmailid() {
		return hodmailid;
	}

	public void setHodmailid(String hodmailid) {
		this.hodmailid = hodmailid;
	}

	public String getAssistantName() {
		return assistantName;
	}

	public void setAssistantName(String assistantName) {
		this.assistantName = assistantName;
	}

	public User() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAdStatus() {
		return this.adStatus;
	}

	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
	}

	public String getCcno() {
		return this.ccno;
	}

	public void setCcno(String ccno) {
		this.ccno = ccno;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getMailid() {
		return this.mailid;
	}

	public void setMailid(String mailid) {
		this.mailid = mailid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhno() {
		return this.phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public String getPlant() {
		return this.plant;
	}

	public void setPlant(String plant) {
		this.plant = plant;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getLastpwdchange() {
		return lastpwdchange;
	}
	public void setLastpwdchange(String lastpwdchange) {
		this.lastpwdchange = lastpwdchange;
	}

	public String getManager() {
		return Manager;
	}
	public void setManager(String manager) {
		this.Manager = manager;
	}	
	
}
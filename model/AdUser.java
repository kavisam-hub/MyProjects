package com.dtvs.dtvsonline.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user database table.
 * 
 */
public class AdUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String dept;


	private String mailid;

	private String name;

	private String phno;

	private String plant;
	
	private String description;
	
	
	private String msExchAssistantName;
	
	private String title;

	private String manager;
	

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsExchAssistantName() {
		return msExchAssistantName;
	}

	public void setMsExchAssistantName(String msExchAssistantName) {
		this.msExchAssistantName = msExchAssistantName;
	}

	public AdUser() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
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

	
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}



}
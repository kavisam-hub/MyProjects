package com.dtvs.dtvsonline.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MaterialGroupMaster")
public class MaterialGroupMaster implements Serializable{
	
	private static final long serialVersionUID = -3465813074586302847L;
	
	@Id
	@Column(name="mgm_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer mgmid;
	
	@Column(name="mgm_code")
	private String mgmcode;
	
	@Column(name="mgm_description")
	private String mgmdescription;

	public Integer getMgmid() {
		return mgmid;
	}

	public void setMgmid(Integer mgmid) {
		this.mgmid = mgmid;
	}

	public String getMgmcode() {
		return mgmcode;
	}

	public void setMgmcode(String mgmcode) {
		this.mgmcode = mgmcode;
	}

	public String getMgmdescription() {
		return mgmdescription;
	}

	public void setMgmdescription(String mgmdescription) {
		this.mgmdescription = mgmdescription;
	}

}

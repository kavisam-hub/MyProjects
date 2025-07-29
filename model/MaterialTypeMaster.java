package com.dtvs.dtvsonline.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="materialtypemaster")
public class MaterialTypeMaster implements Serializable{
	private static final long serialVersionUID = -3465813074586302847L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="mtm_id")
	private Integer mtmid;
	
	@Column(name="mtm_type")
	private String mtmtype;
	
	@Column(name="mtm_description")
	private String mtmdescription;

	public Integer getMtmid() {
		return mtmid;
	}

	public void setMtmid(Integer mtmid) {
		this.mtmid = mtmid;
	}

	public String getMtmtype() {
		return mtmtype;
	}

	public void setMtmtype(String mtmtype) {
		this.mtmtype = mtmtype;
	}

	public String getMtmdescription() {
		return mtmdescription;
	}

	public void setMtmdescription(String mtmdescription) {
		this.mtmdescription = mtmdescription;
	}	

}

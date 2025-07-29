package com.dtvs.dtvsonline.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "MATERIALMASTERREQUEST")
@DynamicUpdate(true)
public class MaterialMaster implements Serializable {

	private static final long serialVersionUID = -3465813074586302847L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MMR_KEYID")
	private Integer mmr_keyid;

	@Column(name = "MMR_TYPE")
	private String mmr_type;

	@Column(name = "MMR_PLANT")
	private String mmr_plant;

	@Column(name = "MMR_UNIT")
	private String mmr_unit;	

	@Column(name = "MMR_GROUP")
	private String mmr_group;
	
	@Column(name="MMR_DESCRIPTION")
	private String mmr_description;	
	
	@Column(name = "MMR_PARTNO")
	private String mmr_partno;

	@Column(name = "MMR_COMMODITY")
	private int mmr_commodity;

	@Column(name = "MMR_PROFITCENTER")
	private String mmr_profitcenter;

	@Column(name = "MMR_HSNCODE")
	/*
	 * @NotNull
	 * 
	 * @Positive(message="Positive Val required")
	 */
	private Integer mmr_hsncode;

	@Column(name = "MMR_MODEL")
	private String mmr_model;

	@Column(name = "MMR_MAKE")	
	private String mmr_make;

	@Column(name = "MMR_SPECIFICATION")
	private String mmr_specification;

	@Column(name = "MMR_CATEGORY")
	private String mmr_category;

	public String getMmr_category() {
		return mmr_category;
	}

	public void setMmr_category(String mmr_category) {
		this.mmr_category = mmr_category;
	}

	@Column(name = "MMR_STORAGETYPE")
	private String mmr_storagetype;

	@Column(name = "MMR_ITEMTYPE")
	private String mmr_itemtype;
	
	@Column(name="MMR_OTHERDESCRIPTION")
	private String mmr_otherdescription;
	
	@Column (name="MMR_MACHINE")
	private String mmr_machine;

	@Column(name = "MMR_PURPOSE")
	private String mmr_purpose;

	@Column(name = "MMR_CREATEDBY")
	private String mmr_createdby;

	@Column(name = "MMR_DEPTHOD")
	private String mmr_depthod;	

	@Column(name = "MMR_pricecontrol")
	private String mmr_pricecontrol;

	@Column(name = "MMR_PCLHOD")
	private String mmr_pclhod;

	@Column(name="mmr_REJECTIONREASON")
	private String mmr_rejectionreason;	
	
	@Column(name = "MMR_VALUATIONCLASS")
	private int mmr_valclass;	

	@Column(name = "MMR_SAPVERIFIEDBY")
	private String mmr_sapverifiedby;

	@Column(name = "MMR_SAPAPPROVEDBY")
	private String mmr_sapapprovedby;

	@Column(name = "MMR_STATUS")
	private String mmr_status;
	
	@Column(name="MMR_DEPT")
	private String mmr_dept;
	
	@Column(name="MMR_ACTION")
	private String mmr_action;
	
	@Column(name = "MMR_MATERIALCODE")
	private String mmr_materialcode;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MMR_CREATEDTIMESTAMP")
	private Date mmr_createdtimestamp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MMR_HODAPPROVEDTIMESTAMP")
	private Date mmr_hodapprovedtimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MMR_HODREJECTEDTIMESTAMP")
	private Date mmr_hodrejectedtimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MMR_PCLAPPROVEDTIMESTAMP")
	private Date mmr_pclapprovedtimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MMR_PCLREJECTEDTIMESTAMP")
	private Date mmr_pclrejectedtimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MMR_SAPPROCESSTIMESTAMP")
	private Date mmr_sapprocesstimestamp;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MMR_SAPREJECTEDTIMESTAMP")
	private Date mmr_saprejectedtimestamp;	
	
/*	@Column(name="MMR_ISCRNHB")
	private String mmr_iscrnhb; */

	public Date getMmr_hodrejectedtimestamp() {
		return mmr_hodrejectedtimestamp;
	}

	public void setMmr_hodrejectedtimestamp(Date mmr_hodrejectedtimestamp) {
		this.mmr_hodrejectedtimestamp = mmr_hodrejectedtimestamp;
	}

	public Date getMmr_saprejectedtimestamp() {
		return mmr_saprejectedtimestamp;
	}

	public void setMmr_saprejectedtimestamp(Date mmr_saprejectedtimestamp) {
		this.mmr_saprejectedtimestamp = mmr_saprejectedtimestamp;
	}

	public Date getMmr_pclapprovedtimestamp() {
		return mmr_pclapprovedtimestamp;
	}

	public void setMmr_pclapprovedtimestamp(Date mmr_pclapprovedtimestamp) {
		this.mmr_pclapprovedtimestamp = mmr_pclapprovedtimestamp;
	}

	public String getMmr_dept() {
		return mmr_dept;
	}

	public void setMmr_dept(String mmr_dept) {
		this.mmr_dept = mmr_dept;
	}

	public Date getMmr_sapprocesstimestamp() {
		return mmr_sapprocesstimestamp;
	}

	public void setMmr_sapprocesstimestamp(Date mmr_sapprocesstimestamp) {
		this.mmr_sapprocesstimestamp = mmr_sapprocesstimestamp;
	}

	public Date getMmr_hodapprovedtimestamp() {
		return mmr_hodapprovedtimestamp;
	}

	public void setMmr_hodapprovedtimestamp(Date mmr_hodapprovedtimestamp) {
		this.mmr_hodapprovedtimestamp = mmr_hodapprovedtimestamp;
	}

	public Integer getMmr_keyid() {
		return mmr_keyid;
	}
	public int getMmr_valclass() {
		return mmr_valclass;
	}

	public void setMmr_valclass(int mmr_valclass) {
		this.mmr_valclass = mmr_valclass;
	}
	public String getMmr_storagetype() {
		return mmr_storagetype;
	}

	public void setMmr_storagetype(String mmr_storagetype) {
		this.mmr_storagetype = mmr_storagetype;
	}

	public String getMmr_action() {
		return mmr_action;
	}

	public void setMmr_action(String mmr_action) {
		this.mmr_action = mmr_action;
	}

	
	public String getMmr_description() {
		return mmr_description;
	}

	public void setMmr_description(String mmr_description) {
		this.mmr_description = mmr_description;
	}	

	public void setMmr_keyid(Integer mmr_keyid) {
		this.mmr_keyid = mmr_keyid;
	}

	public String getMmr_type() {
		return mmr_type;
	}

	public void setMmr_type(String mmr_type) {
		this.mmr_type = mmr_type;
	}

	public String getMmr_unit() {
		return mmr_unit;
	}

	public void setMmr_unit(String mmr_unit) {
		this.mmr_unit = mmr_unit;
	}

	public String getMmr_group() {
		return mmr_group;
	}

	public void setMmr_group(String mmr_group) {
		this.mmr_group = mmr_group;
	}

	public int getMmr_commodity() {
		return mmr_commodity;
	}

	public void setMmr_commodity(int mmr_commodity) {
		this.mmr_commodity = mmr_commodity;
	}

	public String getMmr_profitcenter() {
		return mmr_profitcenter;
	}

	public void setMmr_profitcenter(String mmr_profitcenter) {
		this.mmr_profitcenter = mmr_profitcenter;
	}

	public Integer getMmr_hsncode() {
		return mmr_hsncode;
	}

	public void setMmr_hsncode(Integer mmr_hsncode) {
		this.mmr_hsncode = mmr_hsncode;
	}	

	public String getMmr_materialcode() {
		return mmr_materialcode;
	}

	public void setMmr_materialcode(String mmr_materialcode) {
		this.mmr_materialcode = mmr_materialcode;
	}	

	public String getMmr_createdby() {
		return mmr_createdby;
	}

	public void setMmr_createdby(String mmr_createdby) {
		this.mmr_createdby = mmr_createdby;
	}

	public String getMmr_depthod() {
		return mmr_depthod;
	}

	public void setMmr_depthod(String mmr_depthod) {
		this.mmr_depthod = mmr_depthod;
	}

	public String getMmr_pclhod() {
		return mmr_pclhod;
	}

	public void setMmr_pclhod(String mmr_pclhod) {
		this.mmr_pclhod = mmr_pclhod;
	}

	public String getMmr_sapverifiedby() {
		return mmr_sapverifiedby;
	}

	public void setMmr_sapverifiedby(String mmr_sapverifiedby) {
		this.mmr_sapverifiedby = mmr_sapverifiedby;
	}

	public String getMmr_sapapprovedby() {
		return mmr_sapapprovedby;
	}

	public void setMmr_sapapprovedby(String mmr_sapapprovedby) {
		this.mmr_sapapprovedby = mmr_sapapprovedby;
	}

	public String getMmr_status() {
		return mmr_status;
	}

	public void setMmr_status(String mmr_status) {
		this.mmr_status = mmr_status;
	}

	public Date getMmr_createdtimestamp() {
		return mmr_createdtimestamp;
	}

	public void setMmr_createdtimestamp(Date mmr_createdtimestamp) {
		this.mmr_createdtimestamp = mmr_createdtimestamp;
	}

	public String getMmr_plant() {
		return mmr_plant;
	}
	
	public String getMmr_partno() {
		return mmr_partno;
	}

	public void setMmr_partno(String mmr_partno) {
		this.mmr_partno = mmr_partno;
	}

	public String getMmr_model() {
		return mmr_model;
	}

	public void setMmr_model(String mmr_model) {
		this.mmr_model = mmr_model;
	}

	public String getMmr_make() {
		return mmr_make;
	}

	public void setMmr_make(String mmr_make) {
		this.mmr_make = mmr_make;
	}

	public String getMmr_specification() {
		return mmr_specification;
	}

	public void setMmr_specification(String mmr_specification) {
		this.mmr_specification = mmr_specification;
	}

	public String getMmr_itemtype() {
		return mmr_itemtype;
	}

	public void setMmr_itemtype(String mmr_itemtype) {
		this.mmr_itemtype = mmr_itemtype;
	}

	public String getMmr_otherdescription() {
		return mmr_otherdescription;
	}

	public void setMmr_otherdescription(String mmr_otherdescription) {
		this.mmr_otherdescription = mmr_otherdescription;
	}

	public String getMmr_machine() {
		return mmr_machine;
	}

	public void setMmr_machine(String mmr_machine) {
		this.mmr_machine = mmr_machine;
	}

	public String getMmr_purpose() {
		return mmr_purpose;
	}

	public void setMmr_purpose(String mmr_purpose) {
		this.mmr_purpose = mmr_purpose;
	}

	public void setMmr_plant(String mmr_plant) {
		this.mmr_plant = mmr_plant;
	}	

	public String getMmr_pricecontrol() {
		return mmr_pricecontrol;
	}

	public void setMmr_pricecontrol(String mmr_pricecontrol) {
		this.mmr_pricecontrol = mmr_pricecontrol;
	}
	
	public String getMmr_rejectionreason() {
		return mmr_rejectionreason;
	}

	public void setMmr_rejectionreason(String mmr_rejectionreason) {
		this.mmr_rejectionreason = mmr_rejectionreason;
	}

	public Date getMmr_pclrejectedtimestamp() {
		return mmr_pclrejectedtimestamp;
	}

	public void setMmr_pclrejectedtimestamp(Date mmr_pclrejectedtimestamp) {
		this.mmr_pclrejectedtimestamp = mmr_pclrejectedtimestamp;
	}
}

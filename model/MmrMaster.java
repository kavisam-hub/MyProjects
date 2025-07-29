package com.dtvs.dtvsonline.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MMR_MASTER")
public class MmrMaster implements Serializable{
	
	private static final long serialVersionUID = -3465813074586302847L;
	
	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 @Column(name="id")
	 private Integer mmr_keyid;	 
	
	 @Column(name = "MTYP")
	 private String mmr_mtyp;
	 
	 @Column(name = "MATL_GROUP")
	 private String mmr_group;
	 
	 @Column(name="import_indigenous")
	 private String mmr_indigenous;
	 
	 @Column(name="materialdescription")
	 private String mmr_description;
	 
	 @Column(name = "BUN")
	 private String mmr_bun;
	 
	 @Column(name = "SLOC")
	 private String mmr_sloc;
	 
	 @Column(name="controlcode")
	 private String mmr_controlcode;
	 
	 @Column(name="materialcommodity")
	 private String mmr_commodity;
	 
	 @Column(name = "PRC")
	 private String mmr_prc;
	 
	 @Column(name = "VALCL")
	 private String mmr_valcl;	 
	 
	 @Column(name = "PROFIT_CTR")
	 private String mmr_profitctr;
	 
	 @Column(name = "MATERIAL")
	 private String mmr_material;

/*	public Integer getMmr_keyid() {
		return mmr_keyid;
	} 

	public void setMmr_keyid(Integer mmr_keyid) {
		this.mmr_keyid = mmr_keyid;
	}  */

	public String getMmr_mtyp() {
		return mmr_mtyp;
	}

	public void setMmr_mtyp(String mmr_mtyp) {
		this.mmr_mtyp = mmr_mtyp;
	}

	public String getMmr_group() {
		return mmr_group;
	}

	public void setMmr_group(String mmr_group) {
		this.mmr_group = mmr_group;
	}

	public String getMmr_indigenous() {
		return mmr_indigenous;
	}

	public void setMmr_indigenous(String mmr_indigenous) {
		this.mmr_indigenous = mmr_indigenous;
	}

	public String getMmr_description() {
		return mmr_description;
	}

	public void setMmr_description(String mmr_description) {
		this.mmr_description = mmr_description;
	}

	public String getMmr_bun() {
		return mmr_bun;
	}

	public void setMmr_bun(String mmr_bun) {
		this.mmr_bun = mmr_bun;
	}

	public String getMmr_sloc() {
		return mmr_sloc;
	}

	public void setMmr_sloc(String mmr_sloc) {
		this.mmr_sloc = mmr_sloc;
	}

	public String getMmr_controlcode() {
		return mmr_controlcode;
	}

	public void setMmr_controlcode(String mmr_controlcode) {
		this.mmr_controlcode = mmr_controlcode;
	}

	public String getMmr_commodity() {
		return mmr_commodity;
	}

	public void setMmr_commodity(String mmr_commodity) {
		this.mmr_commodity = mmr_commodity;
	}

	public String getMmr_prc() {
		return mmr_prc;
	}

	public void setMmr_prc(String mmr_prc) {
		this.mmr_prc = mmr_prc;
	}

	public String getMmr_valcl() {
		return mmr_valcl;
	}

	public void setMmr_valcl(String mmr_valcl) {
		this.mmr_valcl = mmr_valcl;
	}

	public String getMmr_profitctr() {
		return mmr_profitctr;
	}

	public void setMmr_profitctr(String mmr_profitctr) {
		this.mmr_profitctr = mmr_profitctr;
	}

	public String getMmr_material() {
		return mmr_material;
	}

	public void setMmr_material(String mmr_material) {
		this.mmr_material = mmr_material;
	} 	 
	
	}

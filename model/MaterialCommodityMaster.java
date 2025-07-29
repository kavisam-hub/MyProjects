package com.dtvs.dtvsonline.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="MaterialCommodityMaster")
public class MaterialCommodityMaster implements Serializable {
	
	private static final long serialVersionUID = -3465813074586302847L;
	 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="mcm_id")
    private int mcmid;
    
    @Column(name="mcm_code")
    private String mcmcode;
    
    @Column(name="mcm_description")
    private String mcmdescription;
    
    @Column(name="mcm_flag")
    private String mcmflag;

	public int getMcmid() {
		return mcmid;
	}

	public String getMcmcode() {
		return mcmcode;
	}

	public String getMcmdescription() {
		return mcmdescription;
	}

	public String getMcmflag() {
		return mcmflag;
	}
        
    
 
}

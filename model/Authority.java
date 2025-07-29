package com.dtvs.dtvsonline.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the authorities database table.
 * 
 */
@Entity
@Table(name="authorities")
@NamedQuery(name="Authority.findAll", query="SELECT a FROM Authority a")
public class Authority implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AuthorityPK id;

	public Authority() {
	}

	public AuthorityPK getId() {
		return this.id;
	}

	public void setId(AuthorityPK id) {
		this.id = id;
	}

}
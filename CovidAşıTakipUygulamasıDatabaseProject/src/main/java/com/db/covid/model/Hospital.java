package com.db.covid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "HOSPITAL")
public class Hospital extends AuditModelId {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String API = "/hospital/";
	
	@Column(name="NAME")
    private String name;
    
    @Column(name="ADDRESS")
    private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}

package com.db.covid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "PERSONNEL")
public class Personnel extends Person {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String API = "/personnel/";
	
    @Column(name="HOSPITAL_ID")
    private Long hospitalId;

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}
    
}

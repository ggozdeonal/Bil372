package com.db.covid.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PATIENT")
public class Patient extends Person {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String API = "/patient/";

}

package com.db.covid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "VACCINE_TYPE")
public class VaccineType extends AuditModelId {
		
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Column(name="VACCINE_TYPE_NAME")
    private String vaccineTypeName;

	public String getVaccineTypeName() {
		return vaccineTypeName;
	}

	public void setVaccineTypeName(String vaccineTypeName) {
		this.vaccineTypeName = vaccineTypeName;
	}

}

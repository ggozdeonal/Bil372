package com.db.covid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "VACCINE")
public class Vaccine extends AuditModelId {
		
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name="VACCINE_TYPE_ID")
    private Long vaccineTypeId;
	
    @Column(name="VACCINE_NAME")
    private String vaccineName;

	public Long getVaccineTypeId() {
		return vaccineTypeId;
	}

	public void setVaccineTypeId(Long vaccineTypeId) {
		this.vaccineTypeId = vaccineTypeId;
	}

	public String getVaccineName() {
		return vaccineName;
	}

	public void setVaccineName(String vaccineName) {
		this.vaccineName = vaccineName;
	}

}

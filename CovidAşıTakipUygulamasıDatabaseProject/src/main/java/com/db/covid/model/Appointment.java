package com.db.covid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "APPOINTMENT")
public class Appointment extends AuditModelId {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String API = "/appointment/";
	
    @Column(name="HOSPITAL_ID")
    private Long hospitalId;
    
    @Column(name="VACCINE_ROOM_ID")
    private Long vaccineRoomId;
      
    @Column(name="VACCINE_ID")
    private Long vaccineId;
    
    @Column(name="DATE")
    private LocalDate date;
    
    @Column(name="TIME")
    private LocalTime time;
    
    @Column(name="PATIENT_IDENTIFICATION_NUMBER")
    private Long patientIdentificationNumber;

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	public Long getVaccineRoomId() {
		return vaccineRoomId;
	}

	public void setVaccineRoomId(Long vaccineRoomId) {
		this.vaccineRoomId = vaccineRoomId;
	}

	public Long getVaccineId() {
		return vaccineId;
	}

	public void setVaccineId(Long vaccineId) {
		this.vaccineId = vaccineId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Long getPatientIdentificationNumber() {
		return patientIdentificationNumber;
	}

	public void setPatientIdentificationNumber(Long patientIdentificationNumber) {
		this.patientIdentificationNumber = patientIdentificationNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

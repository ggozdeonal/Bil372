package com.db.covid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "VACCINE_DOSE")
public class VaccineDose extends AuditModelId {
		
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String API = "/vaccine_dose/";
	
    @Column(name="VACCINE_ID")
    private Long vaccineId;
    
    @Column(name="BARCODE_NO")
    private Long barcodeNo;

    @Column(name="PATIENT_IDENTIFICATION_NUMBER")
    private Long patientIdentificationNumber;
    
    @Column(name="DOSE_NO")
    private int doseNo;
    
    @Column(name="HOSPITAL_ID")
    private Long hospitalId;
    
    @Column(name="vaccine_room_id")
    private Long vaccineRoomId;

	public Long getVaccineId() {
		return vaccineId;
	}

	public void setVaccineId(Long vaccineId) {
		this.vaccineId = vaccineId;
	}

	public Long getBarcodeNo() {
		return barcodeNo;
	}

	public void setBarcodeNo(Long barcodeNo) {
		this.barcodeNo = barcodeNo;
	}

	public Long getPatientIdentificationNumber() {
		return patientIdentificationNumber;
	}

	public void setPatientIdentificationNumber(Long patientIdentificationNumber) {
		this.patientIdentificationNumber = patientIdentificationNumber;
	}

	public int getDoseNo() {
		return doseNo;
	}

	public void setDoseNo(int doseNo) {
		this.doseNo = doseNo;
	}

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

}

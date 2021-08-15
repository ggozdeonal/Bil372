package com.db.covid.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "VACCINE_ROOM")
public class VaccineRoom extends AuditModelId {
		
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
    
    @Column(name="room_number")
    private String roomNumber;
    
    @Column(name="hospital_id")
    private Long hospitalId;
    
    @Column(name="capacity")
    private int capacity;
    
    @Column(name="PERSONNEL_IDENTIFICATION_NUMBER")
    private Long personnelIdentificationNumber;

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Long getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Long getPersonnelIdentificationNumber() {
		return personnelIdentificationNumber;
	}

	public void setPersonnelIdentificationNumber(Long personnelIdentificationNumber) {
		this.personnelIdentificationNumber = personnelIdentificationNumber;
	}

}

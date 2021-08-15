package com.db.covid.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.transaction.TransactionRequiredException;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.db.covid.model.Appointment;
import com.db.covid.model.Patient;
import com.db.covid.model.Personnel;
import com.db.covid.model.VaccineDose;


@Service
public class CovidDemoService {

    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;
    
    private EntityManager entityManager;

    private StringBuilder nativeQuery;     
    private StringBuilder insertQuery;
    private StringBuilder subQuery;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;   
    private String lastUpdater;
    private String status;	
    
    public void initSubQuery() {
    	createdDate = LocalDateTime.now();
    	updatedDate = createdDate;   
    	
    	lastUpdater = "ADMIN";
    	status = "ACTIVE";
    	
    	subQuery = new StringBuilder("\'" + createdDate + "\', \'" + lastUpdater + "\', \'" + status + "\', \'" + updatedDate + "\'");
    }
    
	@Transactional
	public void initDB() throws TransactionRequiredException {	
		entityManager = entityManagerFactory.createEntityManager();    	

		initSubQuery();
						
    	nativeQuery = new StringBuilder(
							"CREATE TABLE public.appointment (\n"
							+ "	id int8 NOT NULL,\n"
							+ "	created_date timestamp NOT NULL,\n"
							+ "	last_updater varchar(255) NULL,\n"
							+ "	status varchar(255) NULL,\n"
							+ "	updated_date timestamp NOT NULL,\n"
							+ "	\"date\" date NULL,\n"
							+ "	hospital_id int8 NULL,\n"
							+ "	patient_identification_number int8 NULL,\n"
							+ "	\"time\" time NULL,\n"
							+ "	vaccine_id int8 NULL,\n"
							+ "	vaccine_room_id int8 NULL,\n"
							+ "	CONSTRAINT appointment_pkey PRIMARY KEY (id)\n"
							+ ");\n"
							+ "\n"
							+ "CREATE TABLE public.hospital (\n"
							+ "	id int8 NOT NULL,\n"
							+ "	created_date timestamp NOT NULL,\n"
							+ "	last_updater varchar(255) NULL,\n"
							+ "	status varchar(255) NULL,\n"
							+ "	updated_date timestamp NOT NULL,\n"
							+ "	address varchar(255) NULL,\n"
							+ "	\"name\" varchar(255) NULL,\n"
							+ "	CONSTRAINT hospital_pkey PRIMARY KEY (id)\n"
							+ ");\n"
							+ "\n"
							+ "CREATE TABLE public.patient (\n"
							+ "	identification_number int8 NOT NULL,\n"
							+ "	created_date timestamp NOT NULL,\n"
							+ "	last_updater varchar(255) NULL,\n"
							+ "	status varchar(255) NULL,\n"
							+ "	updated_date timestamp NOT NULL,\n"
							+ "	age int4 NULL,\n"
							+ "	email varchar(255) NULL,\n"
							+ "	first_name varchar(255) NULL,\n"
							+ "	last_name varchar(255) NULL,\n"
							+ "	phone_number varchar(255) NULL,\n"
							+ "	CONSTRAINT patient_pkey PRIMARY KEY (identification_number)\n"
							+ ");\n"
							+ "\n"
							+ "CREATE TABLE public.personnel (\n"
							+ "	identification_number int8 NOT NULL,\n"
							+ "	created_date timestamp NOT NULL,\n"
							+ "	last_updater varchar(255) NULL,\n"
							+ "	status varchar(255) NULL,\n"
							+ "	updated_date timestamp NOT NULL,\n"
							+ "	age int4 NULL,\n"
							+ "	email varchar(255) NULL,\n"
							+ "	first_name varchar(255) NULL,\n"
							+ "	last_name varchar(255) NULL,\n"
							+ "	phone_number varchar(255) NULL,\n"
							+ "	hospital_id int8 NULL,\n"
							+ "	CONSTRAINT personnel_pkey PRIMARY KEY (identification_number)\n"
							+ ");\n"
							+ "\n"
							+ "CREATE TABLE public.vaccine (\n"
							+ "	id int8 NOT NULL,\n"
							+ "	created_date timestamp NOT NULL,\n"
							+ "	last_updater varchar(255) NULL,\n"
							+ "	status varchar(255) NULL,\n"
							+ "	updated_date timestamp NOT NULL,\n"
							+ "	vaccine_name varchar(255) NULL,\n"
							+ "	vaccine_type_id int8 NULL,\n"
							+ "	CONSTRAINT vaccine_pkey PRIMARY KEY (id)\n"
							+ ");\n"
							+ "\n"
							+ "CREATE TABLE public.vaccine_dose (\n"
							+ "	id int8 NOT NULL,\n"
							+ "	created_date timestamp NOT NULL,\n"
							+ "	last_updater varchar(255) NULL,\n"
							+ "	status varchar(255) NULL,\n"
							+ "	updated_date timestamp NOT NULL,\n"
							+ "	barcode_no int8 NULL,\n"
							+ "	dose_no int4 NULL,\n"
							+ "	hospital_id int8 NULL,\n"
							+ "	patient_identification_number int8 NULL,\n"
							+ "	vaccine_id int8 NULL,\n"
							+ "	vaccine_room_id int8 NULL,\n"
							+ "	CONSTRAINT vaccine_dose_pkey PRIMARY KEY (id)\n"
							+ ");\n"
							+ "\n"
							+ "CREATE TABLE public.vaccine_room (\n"
							+ "	id int8 NOT NULL,\n"
							+ "	created_date timestamp NOT NULL,\n"
							+ "	last_updater varchar(255) NULL,\n"
							+ "	status varchar(255) NULL,\n"
							+ "	updated_date timestamp NOT NULL,\n"
							+ "	capacity int4 NULL,\n"
							+ "	hospital_id int8 NULL,\n"
							+ "	personnel_identification_number int8 NULL,\n"
							+ "	room_number varchar(255) NULL,\n"
							+ "	CONSTRAINT vaccine_room_pkey PRIMARY KEY (id)\n"
							+ ");\n"
							+ "\n"
							+ "CREATE TABLE public.vaccine_type (\n"
							+ "	id int8 NOT NULL,\n"
							+ "	created_date timestamp NOT NULL,\n"
							+ "	last_updater varchar(255) NULL,\n"
							+ "	status varchar(255) NULL,\n"
							+ "	updated_date timestamp NOT NULL,\n"
							+ "	vaccine_type_name varchar(255) NULL,\n"
							+ "	CONSTRAINT vaccine_type_pkey PRIMARY KEY (id)\n"
							+ ");\n"    			
    			);				
		
    	insertQuery = new StringBuilder("insert into vaccine_type values");
    	nativeQuery.append(insertQuery + " ( 1, " + subQuery + ", \'RNA\' );");
    	nativeQuery.append(insertQuery + " ( 2, " + subQuery + ", \'VIRAL VECTOR\' );");
    	nativeQuery.append(insertQuery + " ( 3, " + subQuery + ", \'INACTIVE VIRUS\' );");

    	insertQuery = new StringBuilder("insert into vaccine values");
    	nativeQuery.append(insertQuery).append(" ( 1, ").append(subQuery).append(", \'BIONTECH\', 1 );");
    	nativeQuery.append(insertQuery).append(" ( 2, ").append(subQuery).append(", \'MODERNA\', 1 );");    	
    	nativeQuery.append(insertQuery).append(" ( 3, ").append(subQuery).append(", \'SPUTNIK V\', 2 );");
    	nativeQuery.append(insertQuery).append(" ( 4, ").append(subQuery).append(", \'ASTRAZENECA\', 2 );");
    	nativeQuery.append(insertQuery).append(" ( 5, ").append(subQuery).append(", \'SINOVAC\', 3 );");
    	
    	insertQuery = new StringBuilder("insert into patient values");
    	nativeQuery.append(insertQuery).append(" ( 11111111110, ").append(subQuery).append(", 50, \'mehmet@gmail.com\', \'Mehmet\', \'Ak\', 5301112345 );");
    	
    	insertQuery = new StringBuilder("insert into personnel values");
    	nativeQuery.append(insertQuery).append(" ( 11111111111, ").append(subQuery).append(", 40, \'ahmet@gmail.com\', \'Ahmet\', \'CAN\', 5301112111, 12345 );");
    	
    	insertQuery = new StringBuilder("insert into vaccine_room values");
    	nativeQuery.append(insertQuery).append(" ( 1234567, ").append(subQuery).append(", 60, 12345, 11111111111, \'HAST12345\' );");
    	
    	insertQuery = new StringBuilder("insert into hospital values");
    	nativeQuery.append(insertQuery).append(" ( 12345, ").append(subQuery).append(", \'ANKARA\', \'ANKARA 12345 HASTANESİ\' );");
    	
    	nativeQuery.append("alter table vaccine add constraint vaccine_type_id_fk foreign key (vaccine_type_id) references vaccine_type (id) match full;");

    	nativeQuery.append("alter table vaccine_dose add constraint patient_identification_number_fk foreign key (patient_identification_number) references patient (identification_number) match full;");
    	nativeQuery.append("alter table vaccine_dose add constraint vaccine_id_fk foreign key (vaccine_id) references vaccine (id) match full;");
    	nativeQuery.append("alter table vaccine_dose add constraint vaccine_room_id_fk foreign key (vaccine_room_id) references vaccine_room (id) match full;");
    	nativeQuery.append("alter table vaccine_dose add constraint hospital_id_fk foreign key (hospital_id) references hospital (id) match full;");
    	
    	nativeQuery.append("alter table vaccine_room add constraint personnel_identification_number_fk foreign key (personnel_identification_number) references personnel (identification_number) match full;");    	
    	nativeQuery.append("alter table vaccine_room add constraint hospital_id_fk foreign key (hospital_id) references hospital (id) match full;");    	

    	nativeQuery.append("alter table personnel add constraint hospital_id_fk foreign key (hospital_id) references hospital (id) match full;");

    	nativeQuery.append("alter table appointment add constraint hospital_id_fk foreign key (hospital_id) references hospital (id) match full;");
    	nativeQuery.append("alter table appointment add constraint patient_identification_number_fk foreign key (patient_identification_number) references patient (identification_number) match full;");    	
    	nativeQuery.append("alter table appointment add constraint vaccine_id_fk foreign key (vaccine_id) references vaccine (id) match full;");
    	nativeQuery.append("alter table appointment add constraint vaccine_room_id_fk foreign key (vaccine_room_id) references vaccine_room (id) match full;");

    	nativeQuery.append("alter table vaccine_dose add constraint order_unique unique (barcode_no);");

		Query query = entityManager.createNativeQuery(nativeQuery.toString());
		entityManager.getTransaction().begin();
		query.executeUpdate();		
		entityManager.getTransaction().commit();
	}
	
	@Transactional
	public void generateAppointments(int month, int day) throws TransactionRequiredException {			
		if(((month >= 1) && (month <= 12))) {
			if(((day >= 1) && (day <= 31))) {	
				entityManager = entityManagerFactory.createEntityManager();    	

				initSubQuery();
				
				int year = 2021;
				int capacity1 = 6;
		        int capacity2 = 10;
		        int timeCounter = 11;
		        
		        for(int i = 0; i < capacity1; i++){                         
		             for(int j = 0; j < capacity2; j++){           	            	 
		             	insertQuery = new StringBuilder("insert into appointment values");   
		            	nativeQuery = insertQuery.append(new StringBuilder(" ( :id, " + subQuery + ", :date, 12345, NULL, :time, 1, 1234567 );"));
		            	
		            	Long id = Long.parseLong(new SimpleDateFormat("YYYYMM").format(new Date()).toString() 
		             							+ day
		             							+ timeCounter + "00" + j + i);
		         		LocalDateTime dateTime = LocalDateTime.of(year, month, day, timeCounter, 00, 00);

		        		Query query = entityManager.createNativeQuery(nativeQuery.toString());
		        		entityManager.getTransaction().begin();
		        		query.setParameter("id", id)
		        			.setParameter("date", dateTime.toLocalDate())  
		        			.setParameter("time",  dateTime.toLocalTime())          			
		        			.executeUpdate();
		        		entityManager.getTransaction().commit();
		             }

		             timeCounter++;
		        }
				
			}
		}		
        		
	}

	@SuppressWarnings("unchecked")
	public List<Appointment> getAvailableAppointmentsByDate(String date) {  
		entityManager = entityManagerFactory.createEntityManager();    	

        String nativeQuery = "SELECT p.* FROM appointment p WHERE p.date =:date AND p.patient_identification_number IS NULL AND p.status=\'ACTIVE\'";
        
		List<Appointment> appointments = (List<Appointment>) entityManager.createNativeQuery(nativeQuery, Appointment.class)
                						.setParameter("date", LocalDate.parse(date))
                						.getResultList();
				
        return appointments;
	}
	
	public BigInteger getAppointmentCountByDate(String date) {  
		entityManager = entityManagerFactory.createEntityManager();    	
       
        return (BigInteger) entityManager
        		.createNativeQuery("SELECT count(id) FROM appointment WHERE date =:date AND patient_identification_number IS NOT NULL AND status=\'ACTIVE\'")
                	.setParameter("date", LocalDate.parse(date))
                	.getSingleResult();        		
	}
	
	public BigInteger getAppointmentCountByDateTime(String date, String time) {  
		entityManager = entityManagerFactory.createEntityManager();    	
        
        return (BigInteger) entityManager
        		.createNativeQuery("SELECT count(id) FROM appointment WHERE date =:date AND time =:time AND patient_identification_number IS NOT NULL AND status=\'ACTIVE\'")
        			.setParameter("date", LocalDate.parse(date))
        			.setParameter("time", LocalTime.parse(time))
        			.getSingleResult();                  
	}
	
	public Appointment getAvailableAppointmentByDateTime(LocalDate date, LocalTime time) {  
		entityManager = entityManagerFactory.createEntityManager();    	

        String nativeQuery = "select p.* FROM appointment p WHERE p.date =:date AND p.time =:time AND p.patient_identification_number IS NULL AND p.status=\'ACTIVE\' limit 1";
        
        Appointment appointment = (Appointment) entityManager.createNativeQuery(nativeQuery, Appointment.class)
                								.setParameter("date", date)
                								.setParameter("time", time)
                								.getSingleResult();	
                
        return appointment;
	}

	@Transactional
	public Appointment makeAppointment(Appointment appointment) throws TransactionRequiredException {		
		
		Appointment appointmentTemp = getAvailableAppointmentByDateTime(appointment.getDate(), appointment.getTime());
		
		if(appointmentTemp != null) {
		
			entityManager = entityManagerFactory.createEntityManager();    	
									
        	StringBuilder nativeQuery = new StringBuilder("update appointment set patient_identification_number =:patientIdentificationNumber, updated_date =:updatedDate where id =:id");
         	
    		Query query = entityManager.createNativeQuery(nativeQuery.toString(), Appointment.class);
    		entityManager.getTransaction().begin();
    		query.setParameter("id", appointmentTemp.getId())
    			.setParameter("patientIdentificationNumber", appointment.getPatientIdentificationNumber())
    			.setParameter("updatedDate", LocalDateTime.now())
    			.executeUpdate();
    		entityManager.getTransaction().commit();
    		
    		appointmentTemp = (Appointment) entityManager.find(Appointment.class, appointmentTemp.getId());
    		
		}
		
		return appointmentTemp;
	}

	@SuppressWarnings("unchecked")
	public List<Patient> getPatientListByAppointmentDate(String date) {  
		entityManager = entityManagerFactory.createEntityManager();    	
        
    	nativeQuery = new StringBuilder("select pa.* FROM patient pa ")
    				.append("inner join appointment a on pa.identification_number = a.patient_identification_number ")
    				.append("where a.date =:date AND a.patient_identification_number IS NOT NULL AND a.status=\'ACTIVE\' ");
    	
    	List<Patient> patients = (List<Patient>) entityManager.createNativeQuery(nativeQuery.toString(), Patient.class)
                				 .setParameter("date", LocalDate.parse(date))
                				 .getResultList();
  	    	
        return patients;	
	}
	
	@SuppressWarnings("unchecked")
	public List<Patient> getPatientListByAppointmentDateTime(String date, String time) {  
		entityManager = entityManagerFactory.createEntityManager();    	
        
    	nativeQuery = new StringBuilder("select pa.* FROM patient pa ")
    				.append("inner join appointment a on pa.identification_number = a.patient_identification_number ")
    				.append("where a.date =:date AND a.time =:time AND a.patient_identification_number IS NOT NULL AND a.status=\'ACTIVE\' ");
    	
    	List<Patient> patients = (List<Patient>) entityManager.createNativeQuery(nativeQuery.toString(), Patient.class)
                				 .setParameter("date", LocalDate.parse(date))
                				 .setParameter("time", LocalTime.parse(time))
                				 .getResultList();
  	    	
        return patients;	
	}
	
	@Transactional
	public VaccineDose vaccinate(VaccineDose vaccineDose) {
		//Long barcodeNo, Long patientIdentificationNumber, Long hospitalId, Long vaccineId, Long vaccineRoomId
    	EntityManager entityManager = entityManagerFactory.createEntityManager();
        
    	initSubQuery();
    	
		StringBuilder nativeQuery = new StringBuilder("INSERT INTO vaccine_dose values ");
		nativeQuery.append("( :id, " + subQuery + ", :barcodeNo, :doseNo, :hospitalId, :patientIdentificationNumber, :vaccineId, :vaccineRoomId ");
		nativeQuery.append(" ) ");
		
     	Long id = Long.parseLong(new SimpleDateFormat("YYYYMMddhhmmss").format(new Date()).toString());

		Query query = entityManager.createNativeQuery(nativeQuery.toString(), VaccineDose.class);
		entityManager.getTransaction().begin();
		query.setParameter("id", id)
			.setParameter("barcodeNo", vaccineDose.getBarcodeNo())
	        .setParameter("patientIdentificationNumber", vaccineDose.getPatientIdentificationNumber())
	        .setParameter("hospitalId", vaccineDose.getHospitalId())
	        .setParameter("vaccineId", vaccineDose.getVaccineId())
	        .setParameter("vaccineRoomId", vaccineDose.getVaccineRoomId())
	        .setParameter("doseNo", Long.parseLong(getVaccineDoseCountByPatientId(vaccineDose.getPatientIdentificationNumber()).add(BigInteger.ONE).toString()))	        
			.executeUpdate();

		entityManager.getTransaction().commit();
				
		return (VaccineDose) entityManager.find(VaccineDose.class, id);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Personnel> getAllPersonnel() {  
    	entityManager = entityManagerFactory.createEntityManager(); 
    	
    	List<Personnel> personnelList = entityManager.createQuery("SELECT p FROM Personnel p WHERE p.status=\'ACTIVE\'").getResultList();   	
    	
        return personnelList;
	}

	public Personnel getPersonnelById(Long id) {       
		entityManager = entityManagerFactory.createEntityManager();
		
		Personnel personnel = (Personnel) entityManager
                			  .createNativeQuery("SELECT * FROM personnel WHERE identification_number =:id AND status=\'ACTIVE\'", Personnel.class)
                			  .setParameter("id", id)
                			  .getSingleResult();		

        return personnel;
	}
	
	@Transactional
	public Personnel insertPersonnel(Personnel personnel) throws TransactionRequiredException {		
		entityManager = entityManagerFactory.createEntityManager();    	

		initSubQuery();
		
     	insertQuery = new StringBuilder("insert into personnel values");   
    	nativeQuery = insertQuery.append(new StringBuilder(" ( :identification_number, " + subQuery + ", :age, :email, :first_name, :last_name, :phone_number, :hospital_id );"));
 
		Query query = entityManager.createNativeQuery(nativeQuery.toString(), Personnel.class);
		entityManager.getTransaction().begin();
		query.setParameter("identification_number", personnel.getIdentificationNumber())
	      .setParameter("first_name", personnel.getFirstName())  
	      .setParameter("last_name", personnel.getLastName())	
	      .setParameter("age", personnel.getAge())
	      .setParameter("email", personnel.getEmail())      
	      .setParameter("phone_number", personnel.getPhoneNumber())  
	      .setParameter("hospital_id", personnel.getHospitalId())  
	      .executeUpdate();
		
		entityManager.getTransaction().commit();		
		Personnel personnelTemp = (Personnel) entityManager.find(Personnel.class, personnel.getIdentificationNumber());		
		
		return personnelTemp;
	}
	
	public BigInteger getTotalVaccineDoseCount() {  
		entityManager = entityManagerFactory.createEntityManager();    	
		return (BigInteger) entityManager.createNativeQuery("SELECT count(id) FROM vaccine_dose WHERE status=\'ACTIVE\'").getSingleResult();					
	}
	
	public BigInteger getTotalVaccineDoseCountByHospitalId(Long hospitalId) {  
		entityManager = entityManagerFactory.createEntityManager();    	
        return (BigInteger) entityManager.createNativeQuery("SELECT count(id) FROM vaccine_dose WHERE hospital_id =:hospitalId and status=\'ACTIVE\'")
        			.setParameter("hospitalId", hospitalId)
        			.getSingleResult();	      		
	}
	
	public BigInteger getVaccineDoseCountByPatientId(Long patientIdentificationNumber) {  
		entityManager = entityManagerFactory.createEntityManager();    	
        return (BigInteger) entityManager
        			.createNativeQuery("SELECT count(id) FROM vaccine_dose WHERE patient_identification_number =:patientIdentificationNumber and status=\'ACTIVE\'")
                	.setParameter("patientIdentificationNumber", patientIdentificationNumber)
                	.getSingleResult();        
	}
	
	@SuppressWarnings("unchecked")
	public List<Patient> getVaccinedPatientListByPersonnelId(Long personnelIdenticationNumber){
		entityManager = entityManagerFactory.createEntityManager();    	
        
    	nativeQuery = new StringBuilder("select distinct pa.* from patient pa ")
    				.append("inner join vaccine_dose vd on vd.patient_identification_number = pa.identification_number ")
    				.append("inner join personnel p on vd.hospital_id = p.hospital_id ")
    				.append("where p.identification_number =:personnelIdenticationNumber and p.status=\'ACTIVE\' ");
    	
    	return (List<Patient>) entityManager.createNativeQuery(nativeQuery.toString(), Patient.class)
                				 .setParameter("personnelIdenticationNumber", personnelIdenticationNumber)
                				 .getResultList();  	    	
	}

	@SuppressWarnings("unchecked")
	public List<Personnel> getPersonnelListByHospitalId(Long hospitalId){
		entityManager = entityManagerFactory.createEntityManager();    	
        
    	nativeQuery = new StringBuilder("select p.* from personnel p ")
					.append("inner join hospital h on p.hospital_id = h.id ")
    				.append("inner join vaccine_room vr on h.id = vr.hospital_id ")
    				.append("where p.hospital_id =:hospitalId and p.status=\'ACTIVE\' ");
    	
    	return (List<Personnel>) entityManager.createNativeQuery(nativeQuery.toString(), Personnel.class)
                				 .setParameter("hospitalId", hospitalId)
                				 .getResultList();  	    	
	}
	
	@SuppressWarnings("unchecked")
	public List<Personnel> getPersonnelListByHospitalIdRoomNumber(Long hospitalId, String roomNumber){
		entityManager = entityManagerFactory.createEntityManager();    	
        
    	nativeQuery = new StringBuilder("select p.* from personnel p ")
					.append("inner join hospital h on p.hospital_id = h.id ")
    				.append("inner join vaccine_room vr on h.id = vr.hospital_id ")
    				.append("where p.hospital_id =:hospitalId and vr.room_number =:roomNumber and p.status=\'ACTIVE\' ");
    	
    	return (List<Personnel>) entityManager.createNativeQuery(nativeQuery.toString(), Personnel.class)
                				 .setParameter("hospitalId", hospitalId)
                				 .setParameter("roomNumber", roomNumber)
                				 .getResultList();  	    	
	}
		
	@SuppressWarnings("unchecked")
	public List<VaccineDose> getVaccineInfoByPatientId(Long patientIdenticationNumber){
		entityManager = entityManagerFactory.createEntityManager();    	
        
    	nativeQuery = new StringBuilder("select vd.* from vaccine_dose vd, patient p ")
    			
    				.append("where p.identification_number =:patientIdenticationNumber and vd.status=\'ACTIVE\' ");
    	
    	return (List<VaccineDose>) entityManager.createNativeQuery(nativeQuery.toString(), VaccineDose.class)
                				 .setParameter("patientIdenticationNumber", patientIdenticationNumber)
                				 .getResultList();  	    	
	}
	
	
	public Personnel getPersonnelByVaccineBarcodeNo(Long barcodeNo) {       
		entityManager = entityManagerFactory.createEntityManager();
		
    	nativeQuery = new StringBuilder("select p.* from personnel p ")
    				.append("inner join vaccine_room vr on p.identification_number = vr.personnel_identification_number ")
					.append("inner join vaccine_dose vd on vr.id = vd.vaccine_room_id ")
    				.append("where vd.barcode_no =:barcodeNo and p.status=\'ACTIVE\' ");
		
		Personnel personnel = (Personnel) entityManager
                			  .createNativeQuery(nativeQuery.toString(), Personnel.class)
                			  .setParameter("barcodeNo", barcodeNo)
                			  .getSingleResult();		

        return personnel;
	}

	@SuppressWarnings("unchecked")
	public List<Patient> getVaccinedPatientListByHospitalId(Long hospitalId){
		entityManager = entityManagerFactory.createEntityManager();    	
        
    	nativeQuery = new StringBuilder("select distinct p.* from patient p ")
					.append("inner join vaccine_dose vd on p.identification_number = vd.patient_identification_number ")
    				.append("inner join vaccine_room vr on vd.vaccine_room_id = vr.id ")

    				.append("where vr.hospital_id =:hospitalId and p.status=\'ACTIVE\' ");
    	
    	return (List<Patient>) entityManager.createNativeQuery(nativeQuery.toString(), Patient.class)
                				 .setParameter("hospitalId", hospitalId)
                				 .getResultList();  	    	
	}
	
	@SuppressWarnings("unchecked")
	public List<Patient> getVaccinedPatientListByHospitalIdRoomNumber(Long hospitalId, String roomNumber){
		entityManager = entityManagerFactory.createEntityManager();    	
        
    	nativeQuery = new StringBuilder("select distinct p.* from patient p ")
					.append("inner join vaccine_dose vd on p.identification_number = vd.patient_identification_number ")
    				.append("inner join vaccine_room vr on vd.vaccine_room_id = vr.id ")

    				.append("where vr.hospital_id =:hospitalId and vr.room_number =:roomNumber and p.status=\'ACTIVE\' ");
    	
    	return (List<Patient>) entityManager.createNativeQuery(nativeQuery.toString(), Patient.class)
                				 .setParameter("hospitalId", hospitalId)
                				 .setParameter("roomNumber", roomNumber)
                				 .getResultList();  	    	
	}

	public BigInteger getTotalVaccinedPatientByHospitalId(Long hospitalId){
		entityManager = entityManagerFactory.createEntityManager();    	
        
    	nativeQuery = new StringBuilder("select count(distinct vd.patient_identification_number) from vaccine_dose vd ")
    				.append("inner join vaccine_room vr on vd.vaccine_room_id = vr.id ")
    				.append("where vr.hospital_id =:hospitalId and vd.status=\'ACTIVE\' ");
    	
    	return (BigInteger) entityManager.createNativeQuery(nativeQuery.toString())
                				 .setParameter("hospitalId", hospitalId)
                				 .getSingleResult();  	    	
	}
	
	public BigInteger getTotalVaccinedPatientByHospitalIdRoomNumber(Long hospitalId, String roomNumber){
		entityManager = entityManagerFactory.createEntityManager();    	
        
    	nativeQuery = new StringBuilder("select count(distinct vd.patient_identification_number) from vaccine_dose vd ")
    				.append("inner join vaccine_room vr on vd.vaccine_room_id = vr.id ")
    				.append("where vr.hospital_id =:hospitalId and vr.room_number =:roomNumber and vd.status=\'ACTIVE\' ");
    	
    	return (BigInteger) entityManager.createNativeQuery(nativeQuery.toString())
                				 .setParameter("hospitalId", hospitalId)
                				 .setParameter("roomNumber", roomNumber)
                				 .getSingleResult();  	    	
	}
}

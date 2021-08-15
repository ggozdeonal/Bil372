package com.db.covid.controller;

import java.math.BigInteger;
import java.util.List;

import javax.transaction.TransactionRequiredException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.db.covid.model.Appointment;
import com.db.covid.model.Patient;
import com.db.covid.model.Personnel;
import com.db.covid.model.VaccineDose;
import com.db.covid.service.CovidDemoService;

@RestController
public class CovidDemoController {
    CovidDemoService demoService;
    
    private static String apiHomeMessage1 = "Hello from App Service ";
    private static String apiHomeMessage2 = " Controller (in the staging slot)!";
    
    @Autowired
    public CovidDemoController(CovidDemoService demoService) {
        this.demoService = demoService;
    }

    // api'nin çalışıp çalışmadığını kontrol etmek için
    // get localhost:8081/
    @GetMapping("/")
    public String home() {
        return apiHomeMessage1 + "/main/" + apiHomeMessage2;    
    }
    
    // create table, alter table ve dummy verilerin otomatik olarak girişlerinin yapılmasının sağlandığı yer
    // program çalıştırıldıktan sonra postman'da get seçilerek localhost:8081/init/ gönderilmesi gerekmekte
    @GetMapping("/init/")
    public void init() throws TransactionRequiredException {
    	demoService.initDB();  	
    }
 
    // parametre olarak verilen ay ve gün için 2021 yılı için otomatik olarak randevu oluşturulur
    // localhost:8081/init/8/5
    // aynı ay için aynı gün üzerinden toplamda bir kez çalıştırılabilmekte
    @GetMapping("/init/{month}/{day}")
    public void initAppointment(@PathVariable int month, @PathVariable int day) throws TransactionRequiredException {
    	demoService.generateAppointments(month, day);	
    }

    // api'nin çalışıp çalışmadığını kontrol etmek için
    // get localhost:8081/personnel/
    @GetMapping(Personnel.API)
    public String homePersonnel() {
        return apiHomeMessage1 + Personnel.API + apiHomeMessage2;    
    }

    // personel oluşturma
    // localhost:8081/personnel/insert/
    /*
     * 
     * constraint olduğundan dolayı veritabanında olmayan bir hospitalId ile girilememekte
     * identificationNumber da her seferinde farklı girilmek zorunda
     * 
     * {
    		"identificationNumber": 32423434544363,
    		"firstName": "t",
    		"lastName": "a",
    		"age": 26,
    		"phoneNumber": "5213123",
    		"email": "dasd@sa.com",
    		"hospitalId": 12345
		}
     * 
     */
    @PostMapping(Personnel.API + "insert")
    public Personnel createPersonnel(@RequestBody Personnel personnel) throws TransactionRequiredException {            	   	    	
    	return demoService.insertPersonnel(personnel);	
    }
    
    // belirli bir personeli getir
    // localhost:8081/personnel/3242343454436321
    @GetMapping(Personnel.API + "{id}")
    public Personnel getPersonnel(@PathVariable Long id) {
        return demoService.getPersonnelById(id);
    }

    // tüm personelleri getir
    // localhost:8081/personnel/all
    @GetMapping(Personnel.API + "all")
    public List<Personnel> getAllPersonnel() {
        return demoService.getAllPersonnel();  	
    }

	// belirli bir hastanede çalışan personellerin listesi
    // localhost:8081/personnel/hospital/12345
    @GetMapping(Personnel.API + "hospital/{hospitalId}")
    public List<Personnel> getPersonnelListByHospitalId(@PathVariable Long hospitalId) {
        return demoService.getPersonnelListByHospitalId(hospitalId);
    }
 
	// belirli bir hastanedeki belirli bir aşı odasında çalışan personellerin listesi
    // localhost:8081/personnel/hospital/12345/HAST12345
    @GetMapping(Personnel.API + "hospital/{hospitalId}/{roomNumber}")
    public List<Personnel> getPersonnelListByHospitalIdRoomNumber(@PathVariable Long hospitalId, @PathVariable String roomNumber) {
        return demoService.getPersonnelListByHospitalIdRoomNumber(hospitalId, roomNumber);
    }

    // belirli bir hastanede aşı olan hastaların listesi
    // localhost:8081/patient/hospital/12345
    @GetMapping(Patient.API + "hospital/{hospitalId}")
    public List<Patient> getVaccinedPatientListByHospitalId(@PathVariable Long hospitalId) {
        return demoService.getVaccinedPatientListByHospitalId(hospitalId);
    }
    
    // belirli bir hastanedeki belirli bir aşı odasında aşı olan hastaların listesi
    // localhost:8081/patient/hospital/12345/HAST12345
    @GetMapping(Patient.API + "hospital/{hospitalId}/{roomNumber}")
    public List<Patient> getVaccinedPatientListByHospitalIdRoomNumber(@PathVariable Long hospitalId, @PathVariable String roomNumber) {
        return demoService.getVaccinedPatientListByHospitalIdRoomNumber(hospitalId, roomNumber);
    }
    
    // belirli bir hastanede aşı olan hastaların toplam sayısı
    // localhost:8081/patient/total/12345
    @GetMapping(Patient.API + "total/{hospitalId}")
    public BigInteger getTotalVaccinedPatientByHospitalId(@PathVariable Long hospitalId) {
        return demoService.getTotalVaccinedPatientByHospitalId(hospitalId);
    }
    
    // belirli bir hastanedeki belirli bir aşı odasında aşı olan hastaların toplam sayısı
    // localhost:8081/patient/total/12345/HAST12345
    @GetMapping(Patient.API + "total/{hospitalId}/{roomNumber}")
    public BigInteger getTotalVaccinedPatientByHospitalIdRoomNumber(@PathVariable Long hospitalId, @PathVariable String roomNumber) {
        return demoService.getTotalVaccinedPatientByHospitalIdRoomNumber(hospitalId, roomNumber);
    }
      
    // api'nin çalışıp çalışmadığını kontrol etmek için
    // get localhost:8081/appointment/
    @GetMapping(Appointment.API)
    public String homeAppointment() {
        return apiHomeMessage1 + Appointment.API + apiHomeMessage2;    
    }
       
    // verilen tarih için boş olan tüm randevuları getirir
    // localhost:8081/appointment/2021-08-05
    // üstteki tarih formatı yyyy-mm-dd şeklinde olmak zorunda
    @GetMapping(Appointment.API + "{date}")
    public List<Appointment> getAvailableAppointmentsByDate(@PathVariable String date) {
        return demoService.getAvailableAppointmentsByDate(date);  	
    }
    
    // verilen tarih için toplam randevu sayısını (dolu olan) getirir
    // localhost:8081/appointment/count/2021-08-05
    @GetMapping(Appointment.API + "count/{date}")
    public BigInteger getAppointmentDateCount(@PathVariable String date) {
        return demoService.getAppointmentCountByDate(date);  	
    }
    
    // verilen tarih ve saat için toplam randevu sayısını (dolu olan) getirir
    // localhost:8081/appointment/count/2021-08-05/13:00:00
    @GetMapping(Appointment.API + "count/{date}/{time}")
    public BigInteger getAppointmentDateTimeCount(@PathVariable String date, @PathVariable String time) {
        return demoService.getAppointmentCountByDateTime(date, time);  	
    }
    
    // verilen tarih için randevu almış olan hastaları getirir
    // localhost:8081/appointment/patients/2021-08-05/13:00:00
    @GetMapping(Appointment.API + "patients/{date}")
    public List<Patient> getPatientListByAppointmentDateTime(@PathVariable String date) {
        return demoService.getPatientListByAppointmentDate(date);  	
    }
    
    // verilen tarih ve saat için randevu almış olan hastaları getirir
    // localhost:8081/appointment/patients/2021-08-05/13:00:00
    @GetMapping(Appointment.API + "patients/{date}/{time}")
    public List<Patient> getPatientListByAppointmentDateTime(@PathVariable String date, @PathVariable String time) {
        return demoService.getPatientListByAppointmentDateTime(date, time);  	
    }
    
    // randevu oluşturma mekanizması
    // postman'de put seçilerek localhost:8081/appointment/create
    /*
     * 
     * constraint kaynaklı olarak girilen patientIdentificationNumber veritabanında olmak zorunda
     * tarih ve saat formatları da birebir aynı olmalı
     * 
     * {
    		"date": "2021-08-05",
    		"time": "13:00:00",
    		"patientIdentificationNumber": 11111111110
		}
     * 
     */
    @PutMapping(Appointment.API + "create")
    public Appointment makeAppointment(@RequestBody Appointment appointment) throws TransactionRequiredException {
        return demoService.makeAppointment(appointment);  	
    }
    
    // aşı yapma fonksiyonu
    // barcode_no için unique constraint tanımlandığı için her seferinde farklı girilmesi gerekmekte
    // bir kişi birden fazla aşı olabilir. doz sayımı otomatik olarak oluşturuluyor.
    // postman'de post seçilerek localhost:8081/vaccine_dose/create
    /*
     * 
     * {
    		"barcodeNo": 999888777666,
    		"hospitalId": 12345,
    		"vaccineRoomId": 1234567,
    		"patientIdentificationNumber": 123456789,
    		"vaccineId": 3
		}
     * 
     * 
     */    
    @PostMapping(VaccineDose.API + "create")
    public VaccineDose vaccinate(@RequestBody VaccineDose vaccineDose) throws TransactionRequiredException {            	   	    	
        return demoService.vaccinate(vaccineDose);
    }
    
    // hastaların bireysel aşılanma bilgileri
    // localhost:8081/vaccine_dose/11111111110
    @GetMapping(VaccineDose.API + "{patientId}")
    public List<VaccineDose> getVaccineInfoByPatientId(@PathVariable Long patientId) {
        return demoService.getVaccineInfoByPatientId(patientId);
    }
    
	// belirli bir personelin aşıladığı kişi listesi
    // localhost:8081/vaccine_dose/list/11111111111
    @GetMapping(VaccineDose.API + "list/{personnelIdenticationNumber}")
    public List<Patient> getVaccinedPatientListByPersonnelId(@PathVariable Long personnelIdenticationNumber) {
        return demoService.getVaccinedPatientListByPersonnelId(personnelIdenticationNumber);
    }
    
	// verilen barkodlu aşıyı yapan personelin kim olduğu
    // localhost:8081/vaccine_dose/barcode/222222
    @GetMapping(VaccineDose.API + "barcode/{barcodeNo}")
    public Personnel getPersonnelByVaccineBarcodeNo(@PathVariable Long barcodeNo) {
        return demoService.getPersonnelByVaccineBarcodeNo(barcodeNo);
    }
    
    // şu ana kadar yapılan toplam aşı sayısı
    // localhost:8081/vaccine_dose/total
    @GetMapping(VaccineDose.API + "total")
    public BigInteger getTotalVaccineDoseCount() {
        return demoService.getTotalVaccineDoseCount();
    }
    
    // belirli bir hastanede yapılan toplam aşı sayısı
    // localhost:8081/vaccine_dose/total/12345
    @GetMapping(VaccineDose.API + "total/{hospitalId}")
    public BigInteger getTotalVaccineDoseCountByHospitalId(@PathVariable Long hospitalId) {
        return demoService.getTotalVaccineDoseCountByHospitalId(hospitalId);
    }
    
}


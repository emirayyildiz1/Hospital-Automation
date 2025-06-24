package Clinic_Rezervation_sys_24011606.NormalClasses;

import java.io.Serializable;
import java.util.Date;

public class Rendezvous implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Date dateTime;
	private Hospital hospital;
	private Section section;
	private Doctor doctor;


	public Rendezvous(Date dateTime,Hospital hospital, Section section, Doctor doctor) {
		this.dateTime = dateTime;
		this.doctor = doctor;
		this.hospital = hospital;
		this.section = section;
	}
	
	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Hospital getHospital() {
		return hospital;
	}

	public void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	
}

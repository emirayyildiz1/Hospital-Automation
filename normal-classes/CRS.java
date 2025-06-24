package Clinic_Rezervation_sys_24011606.NormalClasses;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class CRS implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Hashtable<Long,Patient> patients;
	private Vector<Rendezvous> rendezvous;
	private Hashtable<Integer,Hospital> hospitals;
	
	public CRS() {
		patients = new Hashtable<>();
		rendezvous = new Vector<>();
		hospitals = new Hashtable<>();
	}
	
	
	public synchronized boolean makeRendezvous(long patient_id,int hospital_id, int section_id,int diploma_id,Date desired_date) {
		
		Hospital hospital = hospitals.get(hospital_id);
		Section section = hospital.getSection(section_id);
		Doctor doctor = section.getDoctor(diploma_id);

		
		Rendezvous r = new Rendezvous(desired_date,hospital,section,doctor);
		
		boolean isAddedToDoc = doctor.getSchedule().addRendezvous(patients.get(patient_id), desired_date, r);
			
		boolean isAddedToPat = patients.get(patient_id).addRendezvous(r);
		
		if(isAddedToDoc && isAddedToPat) {
			rendezvous.add(r);
			return true;
		}			
		else {
			patients.get(patient_id).deleteRendezvous(r);
			return false;
		}
			
	}
	
	
	public void saveTablesToDisk(String fullPath) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fullPath))) {
	        oos.writeObject(this); 
	        System.out.println("CRS object saved to " + fullPath);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public void loadTablesToDisk(String fullPath) {
		 try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fullPath))) {
		        CRS loadedCRS = (CRS) ois.readObject(); 

		        this.patients = loadedCRS.patients;
		        this.rendezvous = loadedCRS.rendezvous;
		        this.hospitals = loadedCRS.hospitals;

		        System.out.println("CRS object loaded from " + fullPath);
		    } catch (IOException | ClassNotFoundException e) {
		        e.printStackTrace();
		    }
	}


	public Hashtable<Long, Patient> getPatients() {
		return patients;
	}


	public Vector<Rendezvous> getRendezvous() {
		return rendezvous;
	}


	public Hashtable<Integer, Hospital> getHospitals() {
		return hospitals;
	}
	

	public boolean addPatients(long key, Patient patient) {
		for(Patient p : patients.values()) {
			if(p.getID() == patient.getID())
				return false;
		}patients.put(key, patient);
		return true;
	}

	public boolean addHospitals(Integer key, Hospital hospital) {
		for(Hospital p : hospitals.values()) {
			if(p.getName().equals(hospital.getName()) || p.getID() == hospital.getID())
				return false;
		}hospitals.put(key, hospital);
		return true;
		
	}


	
	

	
}

package Clinic_Rezervation_sys_24011606.NormalClasses;

import java.io.Serializable;
import java.util.*;

public class Schedule implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Rendezvous> sessions;
	private ArrayList<Date> availableDays;
	private int maxPatientPerDay;
	
	public Schedule() {
		this.maxPatientPerDay = 8;
		sessions = new LinkedList<>();
		availableDays = new ArrayList<>();
	
	}
	
	public int getMaxPatientPerDay() {
		return maxPatientPerDay;
	}
	
	public Schedule(int maxPatientPerDay) {
		this.maxPatientPerDay = maxPatientPerDay;
		sessions = new LinkedList<>();
		availableDays = new ArrayList<>();
	}
	
	public boolean addRendezvous(Patient p, Date desired,Rendezvous r) {
		
		if(sessions.size() == 0) {
			if(availableDays.contains(desired)) {
				sessions.add(r);
				return true;
			}
		}
		
		int counter = 0;
		for(int i = 0; i < sessions.size(); i++) {
			if(sessions.get(i).getDateTime().equals(desired))
				counter++;
		}
		
		if(counter == maxPatientPerDay) {
			
				System.out.println("Max patient number for date : " + desired + " has already reached.");
				return false;
		}else {
			if(availableDays.contains(desired)) {
				sessions.addLast(r);
				return true;
			}else
				return false;   
		}
	}
	
	public void addAvaliableDays(ArrayList<Date> dates) {
		availableDays = dates;
	}
	

	
	
}

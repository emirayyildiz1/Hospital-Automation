package Clinic_Rezervation_sys_24011606.NormalClasses;

import java.util.Calendar;

import java.util.LinkedList;

public class Patient extends Person{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<Rendezvous> rendezvouses;

	public Patient(String name, long national_id) {
		super(name, national_id);
		rendezvouses = new LinkedList<Rendezvous>();
	}
	
	public LinkedList<Rendezvous> getRendezvous(){
		return rendezvouses;
	}
	
	public void deleteRendezvous(Rendezvous r) {
		rendezvouses.remove(r);
	}
	
	public void addRendezvousWithoutTime(Rendezvous rendezvous) {
		rendezvouses.addLast(rendezvous);
	}
	
	public boolean addRendezvous(Rendezvous rendezvous) {
	      Calendar calendar1 = Calendar.getInstance();
	      calendar1.setTime(rendezvous.getDateTime());
	      
		for(Rendezvous r : rendezvouses) {
			Calendar calendar2 = Calendar.getInstance();
	        calendar2.setTime(r.getDateTime());
			if(r.getDateTime().getYear() == rendezvous.getDateTime().getYear() && 
					r.getDateTime().getDay() == rendezvous.getDateTime().getDay()) {
				System.out.println("You already have a session at that time...");
				return false;
			}
		} rendezvouses.addLast(rendezvous);
		return true;
	}
	


}

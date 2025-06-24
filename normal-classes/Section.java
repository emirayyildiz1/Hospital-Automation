package Clinic_Rezervation_sys_24011606.NormalClasses;

import java.io.Serializable;
import java.util.LinkedList;

public class Section implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int id;
	private String name;
	private LinkedList<Doctor> doctors;
	
	public Section() {
		this.id = 0;
	}
	
	public Section(int id, String name) {
		this.id = id;
		this.name = name;
		doctors = new LinkedList<>();
	}
	


	public LinkedList<Doctor> listDoctors() {
		return doctors;
	}

	
	
	public Doctor getDoctor(int diploma_id) {
		int i = 0;
		while(i < doctors.size()) {
			if(doctors.get(i).getDiplomaID() == diploma_id)
				return doctors.get(i);
			i++;
		}
		return null;
	}
	
	public boolean addDoctor(Doctor doctor) throws DuplicateInfoException{
		for(Doctor doc : doctors) {
			if(doctor.getDiplomaID() == doc.getDiplomaID()) {
				if(Main.decision == 1) {
					DuplicateInfoException d;
					throw d = new DuplicateInfoException("This doctor has already exists.");
				}else if(Main.decision == 2) {
					return false;
				}
				
			}
		}doctors.addLast(doctor); 
		return true;
	}
		
	
	public int getID() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}

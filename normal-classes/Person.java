package Clinic_Rezervation_sys_24011606.NormalClasses;

import java.io.Serializable;

public abstract class Person implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private final long national_id;
	
	public Person(String name, long national_id) {
		this.name = name;
		this.national_id = national_id;
	}
	
	public String toString() {
		return "Name : " + name + "\tID : " + national_id;
	}
	
	public String getName() {
		return name;
	}

	public long getID() {
		return national_id;
	}

}

package Clinic_Rezervation_sys_24011606.NormalClasses;

public class Doctor extends Person{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int diploma_id;
	private Schedule schedule;
	
	
	
	public Doctor(String name,long national_id,int diploma_id) {
		super(name,national_id);
		this.diploma_id = diploma_id;
		schedule = new Schedule();
	}
	
	public Schedule getSchedule() {
		return schedule;
	}
	
	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	public String toString() {
		return super.getName();
	}
	
	public int getDiplomaID() {
		return diploma_id;
	}
	
	
}

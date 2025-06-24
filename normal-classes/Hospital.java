package Clinic_Rezervation_sys_24011606.NormalClasses;

import java.io.Serializable;
import java.util.LinkedList;

public class Hospital implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int id;
	private String name;
	private LinkedList<Section> sections;
	
	public Hospital(int id, String name) {
		this.id = id;
		this.name = name;
		sections = new LinkedList<>();
	}
	
	public Section getSection(int id) {
		int i = 0;
		while(i < sections.size()) {
			if(id == sections.get(i).getID())
				return sections.get(i);
			i++;
		} return null;
	}
	
	private Section getSection(String name) {
		int i = 0;
		while(i < sections.size()) {
			if(name.equals(sections.get(i).getName()))
				return sections.get(i);
		} return null;
	}
	
	public void addSection(Section section){
		this.sections.add(section);
			
	}
	
	public String getName() {
		return name;
	}

	public LinkedList<Section> getSections() {
		return sections;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public String toString() {
		return name;
	}
}

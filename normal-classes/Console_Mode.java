package Clinic_Rezervation_sys_24011606.NormalClasses;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Console_Mode {
	private final String filePath = "infoConsole.ser";
	Scanner input = new Scanner(System.in);
	private CRS crs = new CRS();

	public void startConsoleMode() {
		System.out.println("Welcome to CRS System.\nWhat is your status?"
				+ "\n1-Patient(1)\n2-Admin(2)\n3-Exit(3)");
		
		int a = 0;
		while(a == 0) {
			int status = input.nextInt();
		
			if(status == 1) {
				a++;
				patientScreen1();
			}else if(status == 2) {
				a++;
				adminScreen();
			}else if(status == 3) {
				input.close();
				System.exit(0);
			}
			else {
				continue;
			}
		}
	}
	
	public void patientScreen1()  {
		System.out.println("\n1-Register(1)\n2-Login(2)\n3-Back(3) "
				+ "\n(Choose Register if it is first time you entering.)");
		int decision = input.nextInt();

		
		System.out.print("Enter your name: ");
		String name = input.next();
		
		System.out.print("Enter your national id: ");
		long natID = input.nextLong();
		
		if(decision == 1) {
			try {
			    if (!crs.getPatients().containsKey(natID)) {
			        Patient p = new Patient(name, natID);
			        crs.addPatients(natID, p);
			        System.out.println("Patient successfully registered.");
			        patientScreen2(p);
			    } else {
			        throw new DuplicateInfoException("You have already registered. Please login.");
			    }
			} catch (DuplicateInfoException e) {
			    System.out.println(e.getMessage());
			    patientScreen1();
			}
			
		}else if(decision == 2) {
			try {
			    if (crs.getPatients().containsKey(natID)) {
			    	Patient pat = crs.getPatients().get(natID);
				    System.out.println("Patient successfully logged in.");
				    patientScreen2(pat);
			    } else {
			        throw new IDException("This ID has not registered to system.");
			    }
			} catch (IDException e) {
			    System.out.println(e.getMessage());
			    patientScreen1();
			}
			
		}else if(decision == 3) {
			startConsoleMode();
		}
		
	
	}
	
	public void patientScreen2(Patient patient) {
		System.out.println("\n1-Add Session(1)\n2-My Sessions(2)\n3-Back(3)");
		
		int decision = input.nextInt();
		if(decision == 1) {
			System.out.println("\nWhich hospital? (Type the name of it.)" + "\n" + crs.getHospitals());
			
			Section s = new Section();
			
			for(Hospital h : crs.getHospitals().values()) {
				String hospName = input.next();
				if(h.getName().equalsIgnoreCase(hospName)) {
					System.out.println("\nWhich section? \n" + h.getSections());
					for(Section sec : h.getSections()) {
						System.out.print("("+sec.getID()+")"+"\t");
						
					}
					int sectionName = input.nextInt();
					s = h.getSection(sectionName);
					System.out.println("\nWhich doctor? \n" + s.listDoctors() 
					+ " (Please type the name of the doctor.)");
					String docName = input.next();
						System.out.print("Enter the date (dd-MM-yyyy format): ");
						String dateString = input.next();
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						Date date;
						try {
							date = sdf.parse(dateString);
							for(Doctor d : s.listDoctors()) {
								if(d.getName().equalsIgnoreCase(docName)) {
									boolean isAdded = crs.makeRendezvous(patient.getID(), h.getID(), s.getID(), d.getDiplomaID(), date);
									if(isAdded) {
										System.out.println("Session added successfully.");
									}else
										System.out.println();
								}
						
						}
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
				}
			}
			patientScreen2(patient);
			
		}else if(decision == 2) {
			System.err.println("Hospital\tSection\t\tDoctor");
			for(Rendezvous r : patient.getRendezvous()) {
				System.out.println(r.getHospital() + "\t\t" + r.getSection() 
				+ "\t\t" + r.getDoctor());
			}
			patientScreen2(patient);
			
		}else if(decision == 3) {
			startConsoleMode();
		}
	
	}

	public void adminScreen()  {
		System.out.println("\n1-Add Hospital(1)\n2-Add Doctor(2)\n3-Save Data(3)"
				+ "\n4-Load Data(4)\n5-Back(5)");
		
		int decision = input.nextInt();
		if(decision == 1) {
			System.out.print("Hospital's name: ");
			String hospName = input.next();
			
			System.out.print("Hospital's id: ");
			int hospID = input.nextInt();
			
			Hospital hospital = new Hospital(hospID,hospName);
			
			int sectionID = 1;
			while(sectionID != 0) {
				System.out.print("Section name: ");
				String sectionName = input.next();
				
				System.out.println("Section ID: ");
				sectionID = input.nextInt();
				try {
					for(Section sect : hospital.getSections()) {
						if(sect.getID() == sectionID) {
							throw new DuplicateInfoException("This section ID has already exists.");
						}
					}
				}catch(DuplicateInfoException e) {
					System.out.println(e.getMessage());;
					continue;
				}
				
				Section s = new Section(sectionID,sectionName);
				hospital.addSection(s);
				System.out.print("(Enter \"0\" if you want to exit.): ");
			}hospital.getSections().removeLast();
			
			if(crs.addHospitals(hospID, hospital)) {
				System.out.println("The hospital added successfully.");
			}
			adminScreen();
			
		}
		else if(decision == 2) {
			System.out.println("Which hospital?"+"\n"+crs.getHospitals());
			Section s = new Section();

			for(Hospital h : crs.getHospitals().values()) {
				String hospitalNameString = input.next();
				if(h.getName().equalsIgnoreCase(hospitalNameString)) {
					System.out.println("Which section? " + "\n" + h.getSections());
					for(Section sec : h.getSections()) {
						System.out.print("("+sec.getID()+")"+"\t");
						
					}
					
					int sectionName = input.nextInt();
					s = h.getSection(sectionName);
					break;
				}else
					System.out.println("Enter the name of the hospital again...");
			}
			
			System.out.print("Doctor's name: ");
			String docName = input.next();
			
			System.out.print("Doctor's id: ");
			long docID = input.nextLong();
			
			System.out.print("Doctor's diploma id: ");
			int docDiplomaID = input.nextInt();
			
			try {
				for(Doctor d: s.listDoctors()) {
					if(d.getDiplomaID() == docDiplomaID) {
						throw new DuplicateInfoException("This diplomaID has already exist.");
					}
				}
			}catch(DuplicateInfoException e) {
				System.out.println(e.getMessage());
				adminScreen();
			}
			System.out.print("Doctor's max patients per day: ");
			int docMaxPatPerDay = input.nextInt();
			
			Doctor doc = new Doctor(docName,docID,docDiplomaID);
			Schedule sch = new Schedule(docMaxPatPerDay);
			
			doc.setSchedule(sch);
			
			if(s.addDoctor(doc)) {
				System.out.println("The doctor added successfully.");
			}
			adminScreen();
		}
		
		else if(decision == 3) {
			saveInfoConsole(filePath);
			adminScreen();
		}
		
		else if(decision == 4) {
			loadInfoConsole(filePath);
			adminScreen();
		}
		else if( decision == 5) {
			startConsoleMode();
		}
	}


	public void saveInfoConsole(String filePath) {
		crs.saveTablesToDisk(filePath);
	}
	
	public void loadInfoConsole(String filePath){
		crs.loadTablesToDisk(filePath);
	}

}

package Clinic_Rezervation_sys_24011606.NormalClasses;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.*;

//Emir Ayyıldız
//24011606

public class Main extends Application {

	private static Screen screen = Screen.getPrimary();
	private static final double SCREENHEIGHT = screen.getBounds().getHeight() * 2 / 3;
	private static final double SCREENWIDTH = screen.getBounds().getWidth() * 2 / 3;
	private static Scanner input;
	private Stage stage;
	protected static int decision;
	private CRS crs = new CRS();
	private final String filePath = "info.ser";
	private Label successLabel = null;
	
	

	
	public static void main(String[] args) {
		System.out.print("Should the program run on console(1) or on GUI(2)? input : ");
		while (true) {
			input = new Scanner(System.in);
			int ip = input.nextInt();

			if (ip == 1) {
				decision = 1;
				Console_Mode cm = new Console_Mode();
				cm.startConsoleMode();
				break;
			} else if (ip == 2) {
				decision = 2;
				launch(args);
				break;
			} else {
				System.out.print("\nInvalid input. Try again: ");
				continue;
			}
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		stage.setTitle("Clinic Reservation Information System");
		stage.setScene(createRegisterScene());
		stage.show();
	}

	public Scene createRegisterScene() {
		ArrayList<Node> nodes = new ArrayList<>();
		Label welcome = new Label("WELCOME TO THE CRS SYSTEM\nWhat is your status?");
		welcome.setStyle("-fx-font-size: 30px;");

		Button patButton = createButton("PATIENT", 300, 100, "patientFirstScene"); patButton.setStyle("-fx-font-size: 40px;");
		Button adminButton = createButton("ADMIN", 300, 100, "adminFirstScene"); adminButton.setStyle("-fx-font-size: 40px;");

		VBox entryVbox = new VBox(20, welcome, patButton, adminButton);

		entryVbox.setLayoutY(100);
		entryVbox.setLayoutX(250);

		nodes.add(entryVbox);
		Pane registerScreenPane = createPane(nodes);
		Scene registerScreenScene = new Scene(registerScreenPane, SCREENWIDTH,SCREENHEIGHT);

		return registerScreenScene;

	}
	

	public void patientFirstScene() {
		successLabel = null;
		Scene previousScene = stage.getScene();
		
		TextField patName = createTextField( 200, 50, 20);
		TextField patID = createTextField( 200, 50, 20);
		Button login = new Button("LOGIN"); login.setPrefSize(120, 50);
		Button register = new Button("REGISTER"); register.setPrefSize(120, 50);
		Button back = new Button("BACK");
		
		Label patNameLabel = createLabel("Patient Name:",15,false);
		Label patIDLabel = createLabel("Patient ID:",15,false);

		HBox patNameHBox = new HBox(10, patNameLabel, patName);
		patNameHBox.setAlignment(Pos.CENTER_RIGHT);

		HBox patIDHBox = new HBox(10, patIDLabel, patID);
		patIDHBox.setAlignment(Pos.CENTER_RIGHT);

		VBox buttonsVBox = new VBox(20, register, login, back);
		buttonsVBox.setAlignment(Pos.CENTER_LEFT);

		VBox patientButtons = new VBox(30, patNameHBox, patIDHBox);
		patientButtons.setAlignment(Pos.CENTER);
		
		HBox patientScreenBox = new HBox(20,patientButtons,buttonsVBox);
		patientScreenBox.setAlignment(Pos.CENTER);

		StackPane patientPane = new StackPane();
		patientPane.getChildren().add(patientScreenBox);
		

		
		register.setOnMouseClicked(event3->{
			String name = patName.getText();
			long id = Integer.parseInt(patID.getText());
			Patient patient = new Patient(name,id);
			
			boolean isAdded = crs.addPatients(id,patient);
			
			if(isAdded) {
				 if (successLabel == null) {
		                successLabel = createLabel("The patient successfully added.", 13, false);
		                successLabel.setTextFill(Color.GREEN);
		                patientScreenBox.getChildren().add(successLabel);
		            }
				patName.clear();
				patID.clear();
			}
			else {
				errorScreen("This Patient Has Already Exists.");
			}
			
		});
		
		login.setOnMouseClicked(event4->{
			String name = patName.getText();
			long id = Integer.parseInt(patID.getText());
			
			if(crs.getPatients().containsKey(id)) {
				patientScreen(crs.getPatients().get(id));
				patName.clear();
				patID.clear();
					
			}
			else {
				errorScreen("There is no patient here, name: "+ name +" and id: "+ id 
						+" .\nYou need to register first.");
			}
		});
		
		back.setOnMouseClicked(event5 -> {
			stage.setScene(previousScene);
		});
		
	
		Scene patientFirstScene = new Scene(patientPane, SCREENWIDTH,SCREENHEIGHT);
		stage.setScene(patientFirstScene);
		
		if(crs.getHospitals().isEmpty()) {
			errorScreen("There is no hospitals in the system yet.");
			stage.setScene(previousScene);
		}

	}

	public void patientScreen(Patient patient) {
		Scene previousScene = stage.getScene();
		
		Label welcome = createLabel("Welcome " + patient.getName() + ",",45,false);
		Button myRendez = createButtonWithParameters("MY RENDEZVOUSES",300,100,"showRendezvous",patient);
		Button addRendez = createButtonWithParameters("ADD RENDEZVOUS",300,100,"addRendezvous",patient);
		myRendez.setStyle("-fx-font-size: 25px;");
		addRendez.setStyle("-fx-font-size: 25px;");
		Button back = new Button("BACK");
		
		VBox patientScreenBox = new VBox(10,welcome,addRendez,myRendez,back);
		patientScreenBox.setAlignment(Pos.CENTER);

		back.setOnMouseClicked(event -> {
			stage.setScene(previousScene);
		});
		StackPane patientPane = createStackPane(patientScreenBox);
		patientPane.setAlignment(Pos.CENTER);
		Scene patientScene = new Scene(patientPane,SCREENWIDTH,SCREENHEIGHT);
		stage.setScene(patientScene);
		
	}
		
	public void addRendezvous(Patient patient) {
		successLabel = null;
		Scene previousScene = stage.getScene();

		Label hospitalLabel = createLabel("Select Hospital:", 15,false);
		ComboBox<Hospital> hospitals = createHospitalComboBox(crs.getHospitals());
		if (!crs.getHospitals().isEmpty()) {
		    hospitals.setValue(crs.getHospitals().values().iterator().next());
		}

		Label sectionLabel = createLabel("Select Section:", 15,false);
		ComboBox<Section> sections = createSectionComboBox(hospitals.getValue().getSections());
		if (!(hospitals.getValue() == null)) {
			if(sections.getItems().size() != 0)
		    	sections.setValue(hospitals.getValue().getSections().get(0));
		}

		Label doctorLabel = createLabel("Select Doctor:", 15,false);
		ComboBox<Doctor> doctors = createDoctorComboBox(sections.getValue().listDoctors());
		if(!(sections.getValue() == null)) {
			if(doctors.getItems().size() != 0)
				doctors.setValue(sections.getValue().listDoctors().get(0));
		}
		
		hospitals.setOnAction(e -> {
		    sections.getItems().clear(); 
		    if (hospitals.getValue() != null) {
		        sections.getItems().addAll(hospitals.getValue().getSections()); 
		        sections.setValue(sections.getItems().get(0)); 
		        doctors.getItems().clear(); 
		        if (!sections.getItems().isEmpty()) {
		            doctors.getItems().addAll(sections.getValue().listDoctors()); 
		        }
		    }
		});

		sections.setOnAction(e -> {
		    doctors.getItems().clear();
		    if (sections.getValue() != null) {
		        doctors.getItems().addAll(sections.getValue().listDoctors()); 
		    }
		});

		LocalDate today = LocalDate.now();
		VBox dates = createDateRadioButtons(today);
		Label dateLabel = createLabel("Select Date:", 15,false);

		Button save = new Button("SAVE");
		save.setPrefSize(120, 50);
		Button back = new Button("BACK");

		
		HBox leftPane = new HBox(20,new VBox(13,hospitalLabel,sectionLabel,doctorLabel), 
		    new VBox(10, hospitals,  sections, doctors));
		leftPane.setAlignment(Pos.CENTER);
		
		VBox leftBox = new VBox(20,leftPane,save,back);
		leftBox.setAlignment(Pos.CENTER);

		VBox rightPane = new VBox(10, dateLabel, dates);
		rightPane.setAlignment(Pos.CENTER);

		HBox mainLayout = new HBox(50, leftBox, rightPane);
		mainLayout.setAlignment(Pos.CENTER);

		save.setOnMouseClicked(event1 -> {
			  Task<Boolean> makeRendezvousTask = new Task<>() {
			        @Override
			        protected Boolean call() {
			            return crs.makeRendezvous(
			                patient.getID(),
			                hospitals.getValue().getID(),
			                sections.getValue().getID(),
			                doctors.getValue().getDiplomaID(),
			                getSelectedDateFromVBoxAsDateRB(dates)
			            );
			        }
			    };

			    makeRendezvousTask.setOnSucceeded(workerStateEvent -> {
			        boolean isAdded = makeRendezvousTask.getValue();
			        if (isAdded) {
			            if (successLabel == null) {
			                successLabel = createLabel("The session successfully added.", 13, false);
			                successLabel.setTextFill(Color.GREEN);
			                mainLayout.getChildren().add(successLabel);
			            }
			        } else {
			            errorScreen("Can not add more sessions to that date.");
			        }
			    });

			    makeRendezvousTask.setOnFailed(workerStateEvent -> {
			        errorScreen("An error occurred while making the rendezvous.");
			    });

			    Thread thread = new Thread(makeRendezvousTask);
			    thread.setDaemon(true); 
			    thread.start();
		
		});

		back.setOnMouseClicked(event2 -> {
		    stage.setScene(previousScene);
		});

		StackPane addRendPane = createStackPane(mainLayout);
		addRendPane.setAlignment(Pos.CENTER);
		Scene addRendScene = new Scene(addRendPane, SCREENWIDTH, SCREENHEIGHT);
		stage.setScene(addRendScene);

	}

	public void showRendezvous(Patient patient) {
		Scene previousScene = stage.getScene();


		GridPane grid = new GridPane();
		grid.setVgap(10); 
		grid.setHgap(20); 
		grid.setPadding(new Insets(10, 10, 10, 10)); 

		grid.add(createLabel("Hospital",15,true), 0, 0);
		grid.add(createLabel("Section",15,true), 1, 0);
		grid.add(createLabel("Doctor",15,true), 2, 0);
		grid.add(createLabel("Date",15,true), 3, 0);

		for (int i = 0; i < patient.getRendezvous().size(); i++) {
		    String hospital = patient.getRendezvous().get(i).getHospital().getName();
		    String section = patient.getRendezvous().get(i).getSection().getName();
		    String doctor = patient.getRendezvous().get(i).getDoctor().getName();
		    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		    String formattedDate = formatter.format(patient.getRendezvous().get(i).getDateTime());

		    grid.add(new Label(hospital), 0, i + 1);
		    grid.add(new Label(section), 1, i + 1);
		    grid.add(new Label(doctor), 2, i + 1);
		    grid.add(new Label(formattedDate), 3, i + 1);
		}

		Button back = new Button("BACK");
		grid.add(back, 0, patient.getRendezvous().size() + 1, 4, 1); 

		ScrollPane showRendezvousPane = new ScrollPane();
		showRendezvousPane.setContent(grid);
		showRendezvousPane.setFitToWidth(true);

		back.setOnMouseClicked(event -> {
		    stage.setScene(previousScene);
		});

		Scene showRendezvousScene = new Scene(showRendezvousPane, SCREENWIDTH, SCREENHEIGHT);
		stage.setScene(showRendezvousScene);

	}


	public void adminFirstScene() {
		Scene preivousScene = stage.getScene();

		Button addDoc = createButton("ADD DOCTOR", 200, 80, "addDoctor"); addDoc.setStyle("-fx-font-size: 20px;");
		Button addHospital = createButton("ADD HOSPITAL", 200, 80, "addHospital"); addHospital.setStyle("-fx-font-size: 20px;"); 
		Button saveInfo = new Button("SAVE INFO"); saveInfo.setPrefSize(200, 80); saveInfo.setOnMouseClicked(e-> saveInfo(filePath));
		Button loadInfo = new Button("LOAD INFO"); loadInfo.setPrefSize(200, 80); loadInfo.setOnMouseClicked(e-> loadInfo(filePath));
		saveInfo.setStyle("-fx-font-size: 30px;"); loadInfo.setStyle("-fx-font-size: 30px;");
		Button back = new Button("BACK");
		back.setOnMouseClicked(event-> stage.setScene(preivousScene));
		VBox adminButtons = new VBox(30, addHospital, addDoc, saveInfo, loadInfo, back);
		adminButtons.setAlignment(Pos.CENTER);
		StackPane adminPane = new StackPane();
		adminPane.getChildren().add(adminButtons);
		Scene adminFirstScene = new Scene(adminPane, SCREENWIDTH,SCREENHEIGHT);
		stage.setScene(adminFirstScene);

	}

	public void addDoctor() {
		successLabel = null;
		Scene previousScene = stage.getScene();
		
		if(crs.getHospitals().isEmpty()) {
			errorScreen("There is no hospitals in system yet.");
			stage.setScene(previousScene);
		}

		
		ComboBox<Hospital> cbHospital = createHospitalComboBox(crs.getHospitals());
		if (!crs.getHospitals().isEmpty()) {
			cbHospital.setValue(crs.getHospitals().values().iterator().next());
		}
		ComboBox<Section> cbSection = createSectionComboBox(cbHospital.getValue().getSections());
		if (!crs.getHospitals().isEmpty()) {
			cbSection.setValue(crs.getHospitals().values().iterator().next().getSections().get(0));
		}
		
		  cbHospital.setOnAction(e -> {
		        if (cbHospital.getValue() != null) {
		            cbSection.getItems().clear();
		            cbSection.getItems().addAll(cbHospital.getValue().getSections());
		            if (!cbSection.getItems().isEmpty()) {
		                cbSection.setValue(cbSection.getItems().get(0)); 
		            }
		        }
		    });
 
		LocalDate today = LocalDate.now();
		VBox dates = createDateCheckBox(today,15);
		
		TextField maxPatientNum = createTextField( 200, 50, 20);
		TextField docName = createTextField( 200, 50, 20);
		TextField docNationalID = createTextField( 200, 50, 20);
		TextField docDiplomaID = createTextField( 200, 50, 20);
		
		Button save = new Button("SAVE");
		save.setPrefSize(120, 50);
		
		Button back = new Button("BACK");
		
		
		Label hospitalLabel = createLabel("Select Hospital:",15,false);
		Label sectionLabel = createLabel("Select Section:",15,false);
		Label docNameLabel = createLabel("Doctor Name:",15,false);
		Label docNationalIDLabel = createLabel("Doctor National ID:",15,false);
		Label docDiplomaIDLabel = createLabel("Doctor Diploma ID:",15,false);
		Label maxPatientLabel = createLabel("Max Patient Number:",15,false);

		HBox hospitalHBox = new HBox(10, hospitalLabel, cbHospital);
		hospitalHBox.setAlignment(Pos.CENTER_RIGHT);

		HBox sectionHBox = new HBox(10, sectionLabel, cbSection);
		sectionHBox.setAlignment(Pos.CENTER_RIGHT);

		HBox docNameHBox = new HBox(10, docNameLabel, docName);
		docNameHBox.setAlignment(Pos.CENTER_RIGHT);

		HBox docNationalIDHBox = new HBox(10, docNationalIDLabel, docNationalID);
		docNationalIDHBox.setAlignment(Pos.CENTER_RIGHT);

		HBox docDiplomaIDHBox = new HBox(10, docDiplomaIDLabel, docDiplomaID);
		docDiplomaIDHBox.setAlignment(Pos.CENTER_RIGHT);

		HBox maxPatientHBox = new HBox(10, maxPatientLabel, maxPatientNum);
		maxPatientHBox.setAlignment(Pos.CENTER_RIGHT);

		VBox doctorInfoVBox = new VBox(20, hospitalHBox, sectionHBox, docNameHBox, docNationalIDHBox, docDiplomaIDHBox, maxPatientHBox, save, back);
		doctorInfoVBox.setAlignment(Pos.CENTER);

		VBox checkBoxVBox = new VBox(20, createLabel("Available Dates of Doctor:",20,false), dates);
		checkBoxVBox.setAlignment(Pos.CENTER_LEFT);

		HBox mainHBox = new HBox(50, doctorInfoVBox, checkBoxVBox);
		mainHBox.setAlignment(Pos.CENTER);

		StackPane addDocPane = new StackPane(mainHBox);
		

		save.setOnMouseClicked(event -> {
			ArrayList<Date> docAvailable = getSelectedDateFromVBoxAsDateCB(dates);
			for(Node cb : dates.getChildren()) {
				if(cb instanceof CheckBox) {
					((CheckBox)cb).setSelected(false);
				}
			}
			
			if(docName.getText().isEmpty() || docNationalID.getText().isEmpty() ||
					docDiplomaID.getText().isEmpty() || docAvailable.isEmpty()) {
				errorScreen("Do not leave empty area.");
			}
			
			else {
				String name = docName.getText();
				long nationalID = (long) Integer.parseInt(docNationalID.getText());
				int diplomaID = Integer.parseInt(docDiplomaID.getText());
				
				for(Doctor doc : cbSection.getValue().listDoctors()) {
					if(doc.getDiplomaID() == diplomaID) {
						errorScreen("This diploma ID has already used.");
						return;
					}
					if(doc.getID() == nationalID) {
						errorScreen("This nastional ID has already used.");
						return;
					}
				}
				Schedule s;
				if(maxPatientNum.getText().isEmpty()) {
				s = new Schedule();
				}
				else {
					s = new Schedule(Integer.parseInt(maxPatientNum.getText()));
				}

				Doctor doctor = new Doctor(name, nationalID, diplomaID);
				doctor.setSchedule(s);
				doctor.getSchedule().addAvaliableDays(docAvailable);

				boolean isAdded = cbSection.getValue().addDoctor(doctor);
				
			
				if (!isAdded) {
					errorScreen("This Doctor Has Already Exists.");
				} else {
					
					 if (successLabel == null) {
			                successLabel = createLabel("The doctor successfully added.", 13, false);
			                successLabel.setTextFill(Color.GREEN);
			                mainHBox.getChildren().add(successLabel);
			            }  
						
				
					maxPatientNum.clear();
					docName.clear();
					docNationalID.clear();
					docDiplomaID.clear();
					}
				}
			});
		
		
		back.setOnMouseClicked(event2->{
			stage.setScene(previousScene);
		});

		Scene addDocScene = new Scene(addDocPane, SCREENWIDTH,SCREENHEIGHT);
		stage.setScene(addDocScene);
		
		
	}
		

	public void addHospital() {
		successLabel = null;
		Scene previousScene = stage.getScene();
		TextField hospitalNameField = createTextField(200,50,20); 
		TextField hospitalIDField = createTextField(200,50,20);
		
		
		CheckBox c1 = createCheckBox("Cardiology",10); c1.setPrefSize(200, 30); c1.setStyle("-fx-font-size: 20px;");
		CheckBox c2 = createCheckBox("Neurology",10); c2.setPrefSize(200, 30); c2.setStyle("-fx-font-size: 20px;");
		CheckBox c3 = createCheckBox("Orthopedics",10); c3.setPrefSize(200, 30); c3.setStyle("-fx-font-size: 20px;");
		CheckBox c4 = createCheckBox("Pediatrics",10); c4.setPrefSize(200, 30); c4.setStyle("-fx-font-size: 20px;");
		CheckBox c5 = createCheckBox("Dermatology",10); c5.setPrefSize(200, 30); c5.setStyle("-fx-font-size: 20px;");
		CheckBox c6 = createCheckBox("Ophthalmology",10); c6.setPrefSize(200, 30); c6.setStyle("-fx-font-size: 20px;");
		CheckBox[] sectionChecks = new CheckBox[6];
		sectionChecks[0] = c1;
		sectionChecks[1] = c2;
		sectionChecks[2] = c3;
		sectionChecks[3] = c4;
		sectionChecks[4] = c5;
		sectionChecks[5] = c6;
		ArrayList<CheckBox> checkeds = new ArrayList<>();
		
		Button save = new Button("SAVE");
		save.setPrefSize(120, 50); save.setStyle("-fx-font-size: 30px;");
		
		Button back = new Button("BACK");
		
		Label hospitalNameLabel = createLabel("Hospital Name: ",15,false); 
		Label hospitalIDLabel = createLabel("Hospital ID: ",15,false);

		HBox hospitalNameHBox = new HBox(10, hospitalNameLabel, hospitalNameField);
		hospitalNameHBox.setAlignment(Pos.CENTER_RIGHT);

		HBox hospitalIDHBox = new HBox(10, hospitalIDLabel, hospitalIDField);
		hospitalIDHBox.setAlignment(Pos.CENTER_RIGHT);

		VBox addHospitalVBox = new VBox(20, hospitalNameHBox, hospitalIDHBox, save, back);
		addHospitalVBox.setAlignment(Pos.CENTER);

		Label sectionLabel = createLabel("Sections:", 25,false);
		VBox cbVB = new VBox(10, sectionLabel, c1, c2, c3, c4, c5, c6);
		cbVB.setAlignment(Pos.CENTER);

		HBox addHospitalHBox = new HBox(40, addHospitalVBox, cbVB);
		addHospitalHBox.setAlignment(Pos.CENTER);
		
		
		
		
		StackPane addHospitalPane = new StackPane(); addHospitalPane.getChildren().add(addHospitalHBox);

		save.setOnMouseClicked(event->{
			checkeds.clear();
			
			for(CheckBox c : sectionChecks) {
				if(c.isSelected())
					checkeds.add(c);
			}
			
			if(hospitalNameField.getText().isEmpty() || hospitalIDField.getText().isEmpty() 
					|| checkeds.isEmpty()) {
				errorScreen("Do not leave empty area.");
			}
			else {
				String hospitalName = hospitalNameField.getText();
				int hospitalID = (int)Integer.parseInt(hospitalIDField.getText());
				Hospital hospital = new Hospital(hospitalID,hospitalName);
				
				if(crs.getHospitals().containsKey(hospitalID)) {
					errorScreen("This hospital ID has already used.");
				}
				else {
				boolean isAdded = crs.addHospitals(hospitalID, hospital);
				createSections(checkeds,hospital);
			
				if(!isAdded) {
					errorScreen("This Hospital Has Already Exists.");
				}
				else {
					 if (successLabel == null) {
			                successLabel = createLabel("The hospital successfully added.", 13, false);
			                successLabel.setTextFill(Color.GREEN);
			                addHospitalHBox.getChildren().add(successLabel);
			            } 
					hospitalNameField.clear();
					hospitalIDField.clear();
				} 
				
				for(CheckBox c : sectionChecks) {
					c.setSelected(false);
					}
				}
			}
			
		});
		
		back.setOnMouseClicked(event2-> {
			stage.setScene(previousScene);
		});
		
		
		Scene addHospitalScene = new Scene(addHospitalPane,SCREENWIDTH,SCREENHEIGHT);
		stage.setScene(addHospitalScene);
		
	}
	


	public void createSections(ArrayList<CheckBox> sections, Hospital hospital) {
		for(CheckBox c : sections) {
			String name = c.getText();
			int id = 0;
			switch(name) {
			case "Cardiology": id = 1;
				break;
			case "Neurology": id = 2;
				break;
			case "Orthopedics": id = 3;
				break;
			case "Pediatrics": id = 4;
				break;
			case "Dermatology": id = 5;
				break;
			case "Ophthalmology": id = 6;
				break;
			}
			Section section = new Section(id,name);
			hospital.addSection(section);
		}
	}

//////////////////////////////// BUTTON CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public Button createButton(String title, int width, int height, String methodName) {
		Button button = new Button(title);
		button.setPrefHeight(height);
		button.setPrefWidth(width);

		button.setOnAction(event -> {

			try {
				Method method = Main.class.getMethod(methodName);
				method.invoke(this);
			} catch (Exception e1) {
				
			}

		});
		return button;

	}
	
	public Button createButtonWithParameters(String title, int width, int height, String methodName, Patient patient) {
		Button button = new Button(title);
		button.setPrefHeight(height);
		button.setPrefWidth(width);

		button.setOnAction(event -> {

			try {
				Method method = Main.class.getMethod(methodName,Patient.class);
				method.invoke(this,patient);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		});
		return button;

	}

	public void addButton(String title, int locX, int locY, int width, int height, String methodName) {
		Button button = new Button(title);
		button.setLayoutX(locX);
		button.setLayoutX(locY); 
		button.setPrefHeight(height);
		button.setPrefWidth(width);

		button.setOnAction(event -> {

			try {
				Method method = Main.class.getMethod(methodName);
				method.invoke(this);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		});

	}
////////////////////////////////BUTTON CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

////////////////////////////////TEXTFIELD CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public TextField createTextField( int width, int height, int pxSize) {
		TextField textField = new TextField();
		textField.setPrefWidth(width);
		textField.setPrefHeight(height);
		String css = "-fx-font-size: " + pxSize + "px;";
		textField.setStyle(css);
		
	
		
		return textField;
	}
////////////////////////////////TEXTFIELD CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

////////////////////////////////PANE CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public Pane createPane(ArrayList<Node> nodes) {
		Pane pane = new Pane();
		for (Node node : nodes) {
			pane.getChildren().add(node);
		}
		return pane;
	}

	public Pane createPane(Node node) {
		Pane pane = new Pane();
		pane.getChildren().add(node);
		return pane;
	}
	
	public StackPane createStackPane(Node node) {
		StackPane pane = new StackPane();
		pane.getChildren().add(node);
		return pane;
	}
	
	public StackPane createStackPane(ArrayList<Node> nodes) {
		StackPane pane = new StackPane();
		for (Node node : nodes) {
			pane.getChildren().add(node);
		}
		return pane;
	}
////////////////////////////////PANE CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

////////////////////////////////COMBO BOX CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public ComboBox<String> createPersonComboBox(ArrayList<Person> people) {
		ComboBox<String> cb = new ComboBox<>();
		for (Person person : people) {
			cb.getItems().add(person.getName());
		}
		return cb;
	}

	public ComboBox<Hospital> createHospitalComboBox(Hashtable<Integer, Hospital> hospitals) {
		ComboBox<Hospital> cb = new ComboBox<>();
		for (Hospital hospital : hospitals.values()) {
			cb.getItems().add(hospital);
		}
		return cb;
	}

	public ComboBox<Section> createSectionComboBox(LinkedList<Section> sections) {
		ComboBox<Section> cb = new ComboBox<>();
		for (Section section : sections) {
			cb.getItems().add(section);
		}
		return cb;
	}
	
	public ComboBox<Doctor> createDoctorComboBox(LinkedList<Doctor> doctors){
		ComboBox<Doctor> cb = new ComboBox<>();
		for (Doctor doctor : doctors) {
			cb.getItems().add(doctor);
		}
		return cb;
	}

////////////////////////////////COMBO BOX CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
////////////////////////////////CHECK BOX CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public CheckBox createCheckBox(String title, int pxSize) {
		CheckBox c = new CheckBox(title);
		c.setStyle("-fx-font-size: "+ pxSize + "px;");
		return c;
	}
	
	public VBox createDateCheckBox(LocalDate startDate,int pxSize) {
		VBox vbox = new VBox(20);
		
		for(int i = 0; i < 7; i++) {
			LocalDate date = startDate.plusDays(i);

            CheckBox cb = new CheckBox(date.toString()); 
            cb.setStyle("-fx-font-size: " + pxSize + ";");
            
            vbox.getChildren().add(cb);
		}
		
		return vbox;
	}
	
	
////////////////////////////////CHECK BOX CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	
////////////////////////////////RADIO BUTTON CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public VBox createDateRadioButtons(LocalDate startDate){
		ToggleGroup tg = new ToggleGroup();
		VBox vbox = new VBox(20);
		
		for(int i = 0; i < 7; i++) {
			LocalDate date = startDate.plusDays(i);

            RadioButton rb = new RadioButton(date.toString()); 
            rb.setToggleGroup(tg);

            if (i == 0) {
                rb.setSelected(true);
            }
            vbox.getChildren().add(rb);
		}
		
		return vbox;
	}
	
////////////////////////////////RADIO BUTTON CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
////////////////////////////////LABEL CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public Label createLabel(String text, int pxSize, boolean bold) {
		Label label = new Label(text);
		label.setStyle("-fx-font-size: " + pxSize + ";");
		if(bold == true) {
			label.setStyle("-fx-font-weight: bold;");
		}
		return label;
	}
	
////////////////////////////////LABEL CREATION \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

	public RadioButton getSelectedRadioButton(VBox vbox) {
	    for (Node node : vbox.getChildren()) {
	        if (node instanceof RadioButton) {
	            RadioButton rb = (RadioButton) node;
	            
	            if (rb.isSelected()) {
	                return rb;
	            }
	        }
	    }
	    return null; 
	}
	
	public Date getSelectedDateFromVBoxAsDateRB(VBox vbox) {
	    RadioButton selectedRadioButton = getSelectedRadioButton(vbox);
	    if (selectedRadioButton != null) {
	        LocalDate localDate = LocalDate.parse(selectedRadioButton.getText());
	        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	    }
	    return null;
	}

	public ArrayList<CheckBox> getSelectedCheckBox(VBox vbox) {
		ArrayList<CheckBox> boxes = new ArrayList<>();

		for (Node node : vbox.getChildren()) {
	    	if(node instanceof CheckBox) {
	    		CheckBox cb = (CheckBox)node;
	    		if (cb.isSelected()) {
	                 boxes.add(cb);
	            }
	    	}
	    }return boxes;
	}
	
	public ArrayList<Date> getSelectedDateFromVBoxAsDateCB(VBox vbox) {
		ArrayList<Date> dates = new ArrayList<>();
		ArrayList<CheckBox> selectedCheckBoxes = getSelectedCheckBox(vbox);
		for(CheckBox cb : selectedCheckBoxes) {
			if (cb != null) {
		        LocalDate localDate = LocalDate.parse(cb.getText());
		        dates.add(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		    }
		}return dates;
	    
	}
	
	public void errorScreen(String errorMessage) {
		Stage littleStage = new Stage();
		littleStage.setAlwaysOnTop(true);
		littleStage.setTitle("Error");

		Label littleLabel = new Label(errorMessage);
		littleLabel.setStyle("-fx-font-size: 20px;");
		littleLabel.setTextFill(Color.RED);

		Label crossLabel = new Label("×");
		crossLabel.setStyle("-fx-font-size: 40px;");
		crossLabel.setTextFill(Color.RED);

		Button ok = new Button("OK");
		ok.setPrefSize(120, 50);
		ok.setOnMouseClicked(e->{
			littleStage.close();
		});

		VBox vbox = new VBox(10, crossLabel, littleLabel, ok);
		vbox.setAlignment(Pos.CENTER); 
		vbox.setPrefSize(400, 100); 

		VBox.setVgrow(ok, Priority.ALWAYS);

		Scene littleScene = new Scene(vbox);

		littleStage.setScene(littleScene);
		littleStage.show();

	}

	public void saveInfo(String filePath) {
		crs.saveTablesToDisk(filePath);
	}
	
	public void loadInfo(String filePath) {
		crs.loadTablesToDisk(filePath);
	}
	
	
	
}

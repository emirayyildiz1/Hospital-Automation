package TestClasses;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import Clinic_Rezervation_sys_24011606.NormalClasses.CRS;
import Clinic_Rezervation_sys_24011606.NormalClasses.Doctor;
import Clinic_Rezervation_sys_24011606.NormalClasses.Hospital;
import Clinic_Rezervation_sys_24011606.NormalClasses.Patient;
import Clinic_Rezervation_sys_24011606.NormalClasses.Rendezvous;
import Clinic_Rezervation_sys_24011606.NormalClasses.Schedule;
import Clinic_Rezervation_sys_24011606.NormalClasses.Section;

class CRSTest {

    private CRS crs;
    private Patient patient;
    private Hospital hospital;
    private Section section;
    private Doctor doctor;
    private Schedule schedule;
    private Date testDate;

    @BeforeEach
    void setUp() {
        crs = new CRS();

        patient = new Patient("John Doe", 123456789L);
        hospital = new Hospital(1, "City Hospital");
        section = new Section(1, "Cardiology");
        schedule = new Schedule();
        doctor = new Doctor("Dr. Smith", 1234, 1111);
        testDate = new Date();

        crs.addPatients(123456789L, patient);
        crs.addHospitals(1, hospital);
        doctor.setSchedule(schedule);
        hospital.addSection(section);
        section.addDoctor(doctor);
    }

    @Test
    void testAddPatients() {
        Patient newPatient = new Patient("Jane Doe", 987654321L);
        boolean result = crs.addPatients(987654321L, newPatient);
        assertTrue(result, "New patient should be added successfully");
        assertTrue(crs.getPatients().containsKey(987654321L), "Patient should be in the patients table");
    }

    @Test
    void testAddHospitals() {
        Hospital newHospital = new Hospital(2, "West Hospital");
        boolean result = crs.addHospitals(2, newHospital);
        assertTrue(result, "New hospital should be added successfully");
        assertTrue(crs.getHospitals().containsKey(2), "Hospital should be in the hospitals table");
    }

    @Test
    void testMakeRendezvous() {
    	ArrayList<Date> availableDates = new ArrayList<>();
    	availableDates.add(testDate);
    	schedule.addAvaliableDays(availableDates);
    	
        boolean result = crs.makeRendezvous(123456789L, 1, 1, 1111, testDate);
        assertTrue(result, "Rendezvous should be made successfully");

        assertEquals(1, crs.getRendezvous().size(), "There should be one rendezvous in the system.");
    }

    @Test
    void testSaveTablesToDisk() {
        crs.saveTablesToDisk("crs_test_data.ser");

        File file = new File("crs_test_data.ser");
        assertTrue(file.exists(), "Data should be saved to disk.");
    }

    @Test
    void testLoadTablesFromDisk() {
        crs.saveTablesToDisk("crs_test_data.ser");
        CRS newCRS = new CRS();
        newCRS.loadTablesToDisk("crs_test_data.ser");

        assertEquals(1, newCRS.getPatients().size(), "Patients should be loaded correctly.");
        assertEquals(1, newCRS.getHospitals().size(), "Hospitals should be loaded correctly.");
    }

    @Test
    void testAddPatientsWithDuplicateID() {
        Patient duplicatePatient = new Patient("John Doe", 123456789L);
        boolean result = crs.addPatients(123456789L, duplicatePatient);
        assertFalse(result, "Duplicate patient should not be added.");
    }

    @Test
    void testAddHospitalsWithDuplicateID() {
        Hospital duplicateHospital = new Hospital(1, "City Hospital");
        boolean result = crs.addHospitals(1, duplicateHospital);
        assertFalse(result, "Duplicate hospital should not be added.");
    }
}

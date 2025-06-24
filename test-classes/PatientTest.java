package TestClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Clinic_Rezervation_sys_24011606.NormalClasses.Doctor;
import Clinic_Rezervation_sys_24011606.NormalClasses.Hospital;
import Clinic_Rezervation_sys_24011606.NormalClasses.Patient;
import Clinic_Rezervation_sys_24011606.NormalClasses.Rendezvous;
import Clinic_Rezervation_sys_24011606.NormalClasses.Section;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class PatientTest {

    private Patient patient;
    private Rendezvous rendezvous1;
    private Rendezvous rendezvous2;

    @BeforeEach
    void setUp() {
        patient = new Patient("John Doe", 123456789L);

        Hospital hospital = new Hospital(1, "City Hospital");
        Section section = new Section(1, "Cardiology");
        Doctor doctor = new Doctor("Dr. Smith", 1234, 1111);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2025, Calendar.JANUARY, 15, 10, 0); 
        rendezvous1 = new Rendezvous(calendar.getTime(), hospital, section, doctor);

        calendar.set(2025, Calendar.JANUARY, 16, 14, 0);
        rendezvous2 = new Rendezvous(calendar.getTime(), hospital, section, doctor);
    }

    @Test
    void testAddRendezvous() {
        boolean result1 = patient.addRendezvous(rendezvous1);
        assertTrue(result1, "Rendezvous should be added successfully.");

        boolean result2 = patient.addRendezvous(rendezvous1); 
        assertFalse(result2, "Duplicate rendezvous on the same date should not be added.");

        LinkedList<Rendezvous> rendezvousList = patient.getRendezvous();
        assertEquals(1, rendezvousList.size(), "There should only be one rendezvous in the list.");
    }

    @Test
    void testAddRendezvousOnDifferentDates() {
        boolean result1 = patient.addRendezvous(rendezvous1);
        assertTrue(result1, "First rendezvous should be added successfully.");

        boolean result2 = patient.addRendezvous(rendezvous2); 
        assertTrue(result2, "Rendezvous on a different date should be added successfully.");

        LinkedList<Rendezvous> rendezvousList = patient.getRendezvous();
        assertEquals(2, rendezvousList.size(), "There should be two rendezvous in the list.");
    }

    @Test
    void testDeleteRendezvous() {
        patient.addRendezvous(rendezvous1);
        patient.addRendezvous(rendezvous2);

        patient.deleteRendezvous(rendezvous1);

        LinkedList<Rendezvous> rendezvousList = patient.getRendezvous();
        assertFalse(rendezvousList.contains(rendezvous1), "Deleted rendezvous should not exist in the list.");
        assertEquals(1, rendezvousList.size(), "There should be one rendezvous left in the list.");
    }

}

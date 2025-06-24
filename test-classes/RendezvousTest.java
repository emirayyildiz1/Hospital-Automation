package TestClasses;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Clinic_Rezervation_sys_24011606.NormalClasses.Doctor;
import Clinic_Rezervation_sys_24011606.NormalClasses.Hospital;
import Clinic_Rezervation_sys_24011606.NormalClasses.Rendezvous;
import Clinic_Rezervation_sys_24011606.NormalClasses.Section;

import java.util.Date;

class RendezvousTest {

    private Rendezvous rendezvous;
    private Date dateTime;
    private Hospital hospital;
    private Section section;
    private Doctor doctor;

    @BeforeEach
    void setUp() {
        dateTime = new Date();
        hospital = new Hospital(1, "City Hospital");
        section = new Section(1, "Cardiology");
        doctor = new Doctor("Dr. Smith", 1234, 1111);
        rendezvous = new Rendezvous(dateTime, hospital, section, doctor);
    }

    @Test
    void testGetDateTime() {
        assertEquals(dateTime, rendezvous.getDateTime(), "DateTime should match the initial value.");
    }

    @Test
    void testSetDateTime() {
        Date newDate = new Date(System.currentTimeMillis() + 86400000L); 
        rendezvous.setDateTime(newDate);
        assertEquals(newDate, rendezvous.getDateTime(), "DateTime should be updated to the new value.");
    }

    @Test
    void testGetHospital() {
        assertEquals(hospital, rendezvous.getHospital(), "Hospital should match the initial value.");
    }

    @Test
    void testSetHospital() {
        Hospital newHospital = new Hospital(2, "West Hospital");
        rendezvous.setHospital(newHospital);
        assertEquals(newHospital, rendezvous.getHospital(), "Hospital should be updated to the new value.");
    }

    @Test
    void testGetSection() {
        assertEquals(section, rendezvous.getSection(), "Section should match the initial value.");
    }

    @Test
    void testSetSection() {
        Section newSection = new Section(2, "Neurology");
        rendezvous.setSection(newSection);
        assertEquals(newSection, rendezvous.getSection(), "Section should be updated to the new value.");
    }

    @Test
    void testGetDoctor() {
        assertEquals(doctor, rendezvous.getDoctor(), "Doctor should match the initial value.");
    }

    @Test
    void testSetDoctor() {
        Doctor newDoctor = new Doctor("Dr. Jane", 5678, 2222);
        rendezvous.setDoctor(newDoctor);
        assertEquals(newDoctor, rendezvous.getDoctor(), "Doctor should be updated to the new value.");
    }
}

package TestClasses;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Clinic_Rezervation_sys_24011606.NormalClasses.Doctor;
import Clinic_Rezervation_sys_24011606.NormalClasses.Hospital;
import Clinic_Rezervation_sys_24011606.NormalClasses.Patient;
import Clinic_Rezervation_sys_24011606.NormalClasses.Rendezvous;
import Clinic_Rezervation_sys_24011606.NormalClasses.Schedule;
import Clinic_Rezervation_sys_24011606.NormalClasses.Section;

class DoctorTest {

    private Doctor doctor;
    private Schedule schedule;

    @BeforeEach
    void setUp() {
        doctor = new Doctor("Dr. Smith", 123456789L, 1111);
        schedule = new Schedule();  
    }

    @Test
    void testGetName() {
        assertEquals("Dr. Smith", doctor.toString(), "Doctor's name should match the one provided.");
    }

    @Test
    void testGetDiplomaID() {
        assertEquals(1111, doctor.getDiplomaID(), "Doctor's diploma ID should match the one provided.");
    }

    @Test
    void testGetSchedule() {
        assertNotNull(doctor.getSchedule(), "Doctor's schedule should not be null.");
    }

    @Test
    void testSetSchedule() {
        Schedule newSchedule = new Schedule(10);
        doctor.setSchedule(newSchedule);
        assertEquals(newSchedule, doctor.getSchedule(), "Doctor's schedule should match the one set.");
    }

    @Test
    void testConstructor() {
        assertEquals("Dr. Smith", doctor.toString(), "Doctor's name should match the name passed to the constructor.");
        assertEquals(123456789L, doctor.getID(), "Doctor's national ID should match the one passed.");
        assertEquals(1111, doctor.getDiplomaID(), "Doctor's diploma ID should match the one passed.");
    }
}

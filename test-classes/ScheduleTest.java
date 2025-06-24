package TestClasses;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Clinic_Rezervation_sys_24011606.NormalClasses.*;

import java.util.ArrayList;
import java.util.Date;

class ScheduleTest {

    private Schedule schedule;
    private Patient patient;
    private Rendezvous rendezvous;
    private Date desiredDate;

    @BeforeEach
    void setUp() {
        schedule = new Schedule(3); 
        patient = new Patient("John Doe", 123456789L);
        desiredDate = new Date(); 
        rendezvous = new Rendezvous(desiredDate, 
                new Hospital(1, "City Hospital"), 
                new Section(1, "Cardiology"), 
                new Doctor("Dr. Smith", 1234, 1111));
    }

    @Test
    void testAddRendezvousSuccessfully() {
    	ArrayList<Date> availableDates = new ArrayList<>();
    	availableDates.add(desiredDate);
    	schedule.addAvaliableDays(availableDates);
    	
        boolean result = schedule.addRendezvous(patient, desiredDate, rendezvous);
        assertTrue(result, "Rendezvous should be added successfully.");
    }

    @Test
    void testAddRendezvousMaxPatientsReached() {
        schedule.addRendezvous(patient, desiredDate, rendezvous);
        schedule.addRendezvous(patient, desiredDate, rendezvous);
        schedule.addRendezvous(patient, desiredDate, rendezvous);

        boolean result = schedule.addRendezvous(patient, desiredDate, rendezvous);
        assertFalse(result, "Adding rendezvous should fail when max patient limit is reached.");
    }

    @Test
    void testAddRendezvousUnavailableDate() {
        ArrayList<Date> availableDays = new ArrayList<>();
        schedule.addAvaliableDays(availableDays);

        boolean result = schedule.addRendezvous(patient, desiredDate, rendezvous);
        assertFalse(result, "Rendezvous should not be added for unavailable dates.");
    }

    @Test
    void testAddRendezvousAvailableDate() {
        ArrayList<Date> availableDays = new ArrayList<>();
        availableDays.add(desiredDate);
        schedule.addAvaliableDays(availableDays);

        boolean result = schedule.addRendezvous(patient, desiredDate, rendezvous);
        assertTrue(result, "Rendezvous should be added successfully on an available date.");
    }

    @Test
    void testAddAvailableDays() {
        ArrayList<Date> dates = new ArrayList<>();
        Date date1 = new Date(System.currentTimeMillis() + 86400000); 
        Date date2 = new Date(System.currentTimeMillis() + 2 * 86400000); 

        dates.add(date1);
        dates.add(date2);

        schedule.addAvaliableDays(dates);

        assertTrue(schedule.addRendezvous(patient, date1, rendezvous), "Rendezvous should be added successfully for a newly available date.");
    }
}

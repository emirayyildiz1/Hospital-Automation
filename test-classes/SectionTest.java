package TestClasses;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Clinic_Rezervation_sys_24011606.NormalClasses.Doctor;
import Clinic_Rezervation_sys_24011606.NormalClasses.DuplicateInfoException;
import Clinic_Rezervation_sys_24011606.NormalClasses.Main;
import Clinic_Rezervation_sys_24011606.NormalClasses.Section;

class SectionTest {

    private Section section;

    @BeforeEach
    void setUp() {
        section = new Section(1, "Cardiology");
    }

    @Test
    void testSectionInitialization() {
        assertEquals(1, section.getID(), "Section ID should be initialized correctly.");
        assertEquals("Cardiology", section.getName(), "Section name should be initialized correctly.");
        assertTrue(section.listDoctors().isEmpty(), "Doctors list should be empty upon initialization.");
    }

    @Test
    void testAddDoctorSuccessfully() throws DuplicateInfoException {
        Doctor doctor = new Doctor("Dr. Smith", 1234, 1111);
        boolean result = section.addDoctor(doctor);
        assertTrue(result, "Doctor should be added successfully.");
        assertEquals(1, section.listDoctors().size(), "Doctors list should contain one doctor.");
        assertEquals(doctor, section.getDoctor(1111), "The added doctor should be retrievable by diploma ID.");
    }


    @Test
    void testGetDoctorByDiplomaID() throws DuplicateInfoException {
        Doctor doctor1 = new Doctor("Dr. Smith", 1234, 1111);
        Doctor doctor2 = new Doctor("Dr. Adams", 5678, 2222);
        section.addDoctor(doctor1);
        section.addDoctor(doctor2);

        Doctor retrievedDoctor = section.getDoctor(1111);
        assertNotNull(retrievedDoctor, "Doctor should be found by diploma ID.");
        assertEquals("Dr. Smith", retrievedDoctor.getName(), "Doctor's name should match.");

        retrievedDoctor = section.getDoctor(2222);
        assertNotNull(retrievedDoctor, "Doctor should be found by diploma ID.");
        assertEquals("Dr. Adams", retrievedDoctor.getName(), "Doctor's name should match.");

        retrievedDoctor = section.getDoctor(9999);
        assertNull(retrievedDoctor, "Should return null for a non-existing diploma ID.");
    }

    @Test
    void testToString() {
        assertEquals("Cardiology", section.toString(), "toString should return the section name.");
    }
}

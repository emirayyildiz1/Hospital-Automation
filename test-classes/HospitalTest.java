package TestClasses;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Clinic_Rezervation_sys_24011606.NormalClasses.Doctor;
import Clinic_Rezervation_sys_24011606.NormalClasses.Hospital;
import Clinic_Rezervation_sys_24011606.NormalClasses.Patient;
import Clinic_Rezervation_sys_24011606.NormalClasses.Rendezvous;
import Clinic_Rezervation_sys_24011606.NormalClasses.Section;

import java.util.LinkedList;

class HospitalTest {

    private Hospital hospital;
    private Section section1;
    private Section section2;

    @BeforeEach
    void setUp() {
        hospital = new Hospital(1, "City Hospital");
        section1 = new Section(1, "Cardiology");
        section2 = new Section(2, "Neurology");

        hospital.addSection(section1);
        hospital.addSection(section2);
    }

    @Test
    void testGetName() {
        assertEquals("City Hospital", hospital.getName(), "Hospital name should match the initial value.");
    }

    @Test
    void testGetSections() {
        LinkedList<Section> sections = hospital.getSections();
        assertEquals(2, sections.size(), "Hospital should have two sections.");
        assertTrue(sections.contains(section1), "Sections list should contain Cardiology.");
        assertTrue(sections.contains(section2), "Sections list should contain Neurology.");
    }

    @Test
    void testGetID() {
        assertEquals(1, hospital.getID(), "Hospital ID should match the initial value.");
    }

    @Test
    void testGetSectionByID() {
        Section result = hospital.getSection(1);
        assertNotNull(result, "Should return a section with the given ID.");
        assertEquals(section1, result, "Section should match the one added for ID 1.");
    }

    @Test
    void testGetSectionByInvalidID() {
        Section result = hospital.getSection(999);  
        assertNull(result, "Should return null for a non-existing section ID.");
    }

    @Test
    void testAddSection() {
        Section section3 = new Section(3, "Pediatrics");
        hospital.addSection(section3);

        LinkedList<Section> sections = hospital.getSections();
        assertTrue(sections.contains(section3), "New section should be added to the hospital.");
    }

    @Test
    void testToString() {
        assertEquals("City Hospital", hospital.toString(), "Hospital name should be returned by toString method.");
    }
}

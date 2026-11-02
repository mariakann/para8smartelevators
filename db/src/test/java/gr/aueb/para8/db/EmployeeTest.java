package gr.aueb.para8.db;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EmployeeTest {
    
    @Test 
    void newEmployeeKeepsNameAndEmail() {
        Employee alexandros = new Employee("Alexandros Papadopoulos", "alex@company.com");
        assertEquals("Alexandros Papadopoulos", alexandros.getFullName());
        assertEquals("alex@company.com", alexandros.getEmail());
    }

    @Test
    void rejectsEmailWithoutAt() {
        Employee alexandros = new Employee("Alexandros Papadopoulos", "alex@company.com");
        assertThrows(IllegalArgumentException.class, () -> alexandros.setEmail("lathos"));
    }

    @Test
    void toStringContainsFullName() {
        Employee alexandros = new Employee("Alexandros Papadopoulos", "alex@company.com");
        assertTrue(alexandros.toString().contains("Alexandros Papadopoulos"));
    }

    @Test
    void newEmployeeHasIdZero() {
        Employee alexandros = new Employee("Alexandros Papadopoulos", "alex@company.com");
        assertEquals(0, alexandros.getId());
    }
}

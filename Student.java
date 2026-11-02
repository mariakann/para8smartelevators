package lesson1;

/**
 * Represents a student using three properties (AM, first name, last name). 
 */
public class Student {

    // Private fields: accessible directly only inside the Student class (encapsulation).
    private String am;
    private String firstName;
    private String lastName;

    /**
     * Constructor: called when a new Student object is created.
     *
     * @param am student registration number
     * @param firstName student's first name
     * @param lastName student's last name
     */
    public Student(String am, String firstName, String lastName) {
        this.am = am;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /* Setterd & Getters */
 
    public void setAm(String am) {
        this.am = am;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAm() {
        return am;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "AM: " + am + ", First name: " + firstName + ", Last name: " + lastName;
    }  
    
}

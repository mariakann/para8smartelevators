package gr.aueb.para8.db;

import java.time.OffsetDateTime;

public class Employee {

    private long id;
    private OffsetDateTime createdAt;
    private String fullName;
    private String email;

    public Employee(String fullName, String email) {
        setFullName(fullName);
        setEmail(email);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }
        this.email = email;
    }

    @Override 
    public String toString() {
        return "Employee{id" + id + ", fullName=" + fullName + ", email=" + email + "}";
    }
    
}
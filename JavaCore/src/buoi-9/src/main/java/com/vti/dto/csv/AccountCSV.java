package com.vti.dto.csv;

public class AccountCSV {
    private String email;
    private String fullname;
    private String username;
    private String department;
    private String position;

    public AccountCSV() {}

    public AccountCSV(String email, String fullname, String username, String department, String position) {
        this.email = email;
        this.fullname = fullname;
        this.username = username;
        this.department = department;
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}

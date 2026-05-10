package btvn_buoi5.src.entity;

import java.time.LocalDateTime;

public class Account {
    private int accountID;
    private String email;
    private String username;
    private String fullName;
    private Department department;
    private Position position;
    private LocalDateTime createDate;
    private Group[] groups;
    private static int AUTO_ID = 1;


    public Account() {}

    public Account(String email, String username, String firstName, String lastName, Department department, Position position) {
        this.accountID = AUTO_ID++;
        this.email = email;
        this.username = username;
        this.fullName = firstName + " " + lastName;
        this.department = department;
        this.position = position;
        this.createDate = LocalDateTime.now();
    }


    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + accountID +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", department='" + (department != null ? department.getDepartmentName() : "null" )+ '\'' +
                ", position='" + (position != null ? position.getPositionName() : "null") + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}

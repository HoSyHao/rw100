package TestingSystem_Assignment_4.src.com.vti.entity;

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

//    Question 2:
//    Tạo constructor cho Account:
//    a)	Không có parameters
    public Account() {}
//    b)	Có các parameter là id, Email, Username, FirstName, LastName (với FullName = FirstName + LastName)
    public Account(int accountID, String email, String username, String firstName, String lastName) {
        this.accountID = accountID;
        this.email = email;
        this.username = username;
        StringBuilder stringBuilder = new StringBuilder();
        this.fullName = stringBuilder.append(firstName).append(" ").append(lastName).toString();
    }
//    c)	Có các parameter là id, Email, Username, FirstName, LastName (với FullName = FirstName + LastName) và
//    Position của User, default createDate = now
    public Account(int accountID, String email, String username, String firstName, String lastName, Position position) {
        this.accountID = accountID;
        this.email = email;
        this.username = username;
        StringBuilder stringBuilder = new StringBuilder();
        this.fullName = stringBuilder.append(firstName).append(" ").append(lastName).toString();
        this.position = position;
        this.createDate = LocalDateTime.now();
    }

    //    d)	Có các parameter là id, Email, Username, FirstName, LastName (với FullName = FirstName + LastName) và Position của User, createDate
//    Khởi tạo 1 Object với mỗi constructor ở trên
    public Account(int accountID, String email, String username, String firstName, String lastName, Position position, LocalDateTime createDate) {
        this.accountID = accountID;
        this.email = email;
        this.username = username;
        StringBuilder stringBuilder = new StringBuilder();
        this.fullName = stringBuilder.append(firstName).append(" ").append(lastName).toString();
        this.position = position;
        this.createDate = createDate != null ? createDate : LocalDateTime.now();
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

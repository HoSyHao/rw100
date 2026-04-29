package TestingSystem_Assignment_3;

import java.time.LocalDateTime;

public class Account {
    int accountID;
    String email;
    String username;
    String fullName;
    Department department;
    Position position;
    LocalDateTime createDate;
    Group[] groups;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + accountID +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", department='" + department.departmentName + '\'' +
                ", position='" + position.positionName + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}

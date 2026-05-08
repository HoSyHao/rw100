package TestingSystem_Assignment_4.src.com.vti.backend;

import TestingSystem_Assignment_4.src.com.vti.Enum.PositionName;
import TestingSystem_Assignment_4.src.com.vti.entity.Account;
import TestingSystem_Assignment_4.src.com.vti.entity.Department;
import TestingSystem_Assignment_4.src.com.vti.entity.Group;
import TestingSystem_Assignment_4.src.com.vti.entity.Position;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Exercise1 {
    public void question1(){
        System.out.println("This is the question 1");
        Department department1 = new Department();
        department1.setDepartmentID(1);
        department1.setDepartmentName("Department 1");

        Department department2 = new Department("Department2");

        System.out.println(department1);
        System.out.println(department2);
    }

    public List<Account> question2(){
        List<Account> accounts = new ArrayList<>();
        System.out.println("This is the question 2");
        Account account1 = new Account();
        account1.setAccountID(1);
        account1.setEmail("email1");
        account1.setUsername("username1");
        account1.setFullName("Full Name1");

        Account account2 = new Account(
                2,
                "email2",
                "username2",
                "firstName2",
                "lastName2");

        Position[] positions = createPositons();

        Account account3 = new Account(
                3,
                "email3",
                "username3",
                "firstName3",
                "lastName3",
                positions[0]
                );

        Account account4 = new Account(
                3,
                "email4",
                "username4",
                "firstName4",
                "lastName4",
                positions[3],
                LocalDateTime.of(2026, 5, 6, 0, 0)
                //null
                );

        System.out.println(account1);
        System.out.println(account2);
        System.out.println(account3);
        System.out.println(account4);

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        accounts.add(account4);

        return accounts;
    }

    public void question3(List<Account> accounts){
        System.out.println("This is the question 3");
        Group group1 = new Group();
        group1.setGroupID(1);
        group1.setGroupName("Group 1");
        group1.setCreator(accounts.get(0));
        group1.setCreateDate(LocalDateTime.now());
        group1.setAccounts(new Account[] {
                accounts.get(0),
                accounts.get(1)
        });

        Group group2 = new Group(2,
                "Group 2",
                accounts.get(1),
                null ,
                new Account[] {
                accounts.get(1),
                accounts.get(2)});

        Group group3 = new Group(
                3,
                "Group 3",
                accounts.get(2),
                null,
                new String[]{"username5", "username6"}
        );

        System.out.println(group1);
        System.out.println(group2);
        System.out.println(group3);
    }

    public static Position[] createPositons(){
        PositionName[] positionName = PositionName.values();
        Position[] positions = new Position[positionName.length];

        for(int i = 0; i < positionName.length; i++){
            positions[i] = new Position(i+1, positionName[i]);
        }

        return positions;
    }

}


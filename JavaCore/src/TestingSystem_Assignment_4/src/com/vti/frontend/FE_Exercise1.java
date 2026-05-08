package TestingSystem_Assignment_4.src.com.vti.frontend;

import TestingSystem_Assignment_4.src.com.vti.backend.Exercise1;
import TestingSystem_Assignment_4.src.com.vti.entity.Account;

import java.util.List;

public class FE_Exercise1 {
    public static void main(String[] args) {
        Exercise1 exercise1 = new Exercise1();

        exercise1.question1();
        List<Account> accounts = exercise1.question2();
        exercise1.question3(accounts);
    }
}

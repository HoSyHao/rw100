package TestingSystem_Assignment_2;

import java.time.LocalDateTime;

public class Exercise6 {

    public static void main(String[] args) {
        question1();
        question2();
        question3();
    }

    // Question 1:
    // Tạo method để in ra các số chẵn nguyên dương nhỏ hơn 10
    public static void question1() {
        System.out.println("Các số chẵn nguyên dương nhỏ hơn 10:");

        for (int i = 1; i < 10; i++) {
            if (i % 2 == 0) {
                System.out.print(i + " ");
            }
        }

        System.out.println();
    }

    // Question 2:
    // Tạo method để in thông tin các account
    public static void question2() {
        System.out.println("Thông tin các account:");

        Account[] accounts = createSampleAccounts();

        for (Account account : accounts) {
            System.out.println(account);
        }
    }

    // Question 3:
    // Tạo method để in ra các số nguyên dương nhỏ hơn 10
    public static void question3() {
        System.out.println("Các số nguyên dương nhỏ hơn 10:");

        for (int i = 1; i < 10; i++) {
            System.out.print(i + " ");
        }

        System.out.println();
    }

    // Helper method:
    // Tạo dữ liệu account mẫu để test Question 2
    public static Account[] createSampleAccounts() {
        Account acc1 = new Account();

        acc1.accountID = 1;
        acc1.email = "user1@gmail.com";
        acc1.username = "user1";
        acc1.fullName = "Nguyen Van A";
        acc1.department = new Department();
        acc1.department.departmentName = "Haha";
        acc1.position = new Position();
        acc1.position.positionName = PositionName.DEV;
        acc1.createDate = LocalDateTime.now();

        Account acc2 = new Account();
        acc2.accountID = 2;
        acc2.email = "user2@gmail.com";
        acc2.username = "user2";
        acc2.fullName = "Tran Van B";
        acc2.department = new Department();
        acc2.department.departmentName = "Hihi";
        acc2.position = new Position();
        acc2.position.positionName = PositionName.PM;
        acc2.createDate = LocalDateTime.now();

        Account acc3 = new Account();
        acc3.accountID = 3;
        acc3.email = "user3@gmail.com";
        acc3.username = "user3";
        acc3.fullName = "Le Van C";
        acc3.department = new Department();
        acc3.department.departmentName = "Huhu";
        acc3.position = new Position();
        acc3.position.positionName = PositionName.SCRUM_MASTER;
        acc3.createDate = LocalDateTime.now();

        return new Account[]{acc1, acc2, acc3};
    }
}
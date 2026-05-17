package com.vti;

import com.vti.frontend.AccountFunc;
import com.vti.frontend.DepartmentFunc;
import com.vti.frontend.PositionFunc;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        DepartmentFunc departmentFunc = new DepartmentFunc();
        PositionFunc positionFunc = new PositionFunc();
        AccountFunc accountFunc = new AccountFunc();

        while (true) {
            System.out.println("============= MAIN MENU =============");
            System.out.println("1. Quản lý Department");
            System.out.println("2. Quản lý Position");
            System.out.println("3. Quản lý Account");
            System.out.println("0. Thoát chương trình");

            System.out.print("Chọn chức năng: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    departmentFunc.run();
                    ;
                    break;

                case "2":
                    positionFunc.run();
                    break;

                case "3":
                    accountFunc.run();
                    break;

                case "0":
                    System.out.println("=========== Đã thoát chương trình ===========");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Vui lòng nhập đúng lựa chọn!");
            }

            System.out.println();

        }
    }
}
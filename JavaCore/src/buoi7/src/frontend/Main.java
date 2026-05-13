package buoi7.src.frontend;

import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

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
                    DepartmentFunc.run();
                    break;

                case "2":
                    PositionFunc.run();
                    break;

                case "3":
                    AccountFunc.run();
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
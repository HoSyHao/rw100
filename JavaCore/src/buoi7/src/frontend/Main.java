package buoi7.src.frontend;

import buoi7.src.backend.QLAccount;
import buoi7.src.backend.QLDepartment;
import buoi7.src.backend.QLPosition;

import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("============MENU=============");
            System.out.println("1. Xem ds phòng ban");
            System.out.println("2. Xem ds chức vụ");
            System.out.println("3. Xem ds nhân viên");
            System.out.println("4. Tìm phòng ban theo tên và id");
            System.out.println("5. Tìm phòng ban có nhiều hơn 2 nhân viên");
            System.out.println("6. Tìm chức vụ theo tên");
            System.out.println("7. Tìm nhân viên theo full_name");
            System.out.println("8. Tìm nhân viên theo full_name và username");
            System.out.println("0. Thoát");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    QLDepartment.showDepartment();
                    System.out.println();
                    break;
                case "2":
                    QLPosition.showAllPosition();
                    System.out.println();
                    break;
                case "3":
                    QLAccount.showAllAccount();
                    System.out.println();
                    break;
                case "4":
                    System.out.print("Nhập tên phòng ban: ");
                    String name = sc.nextLine();

                    // Kiem tra id phai la so thi moi di tiep
                    int id;
                    while (true) {
                        try {
                            System.out.print("Nhập id: ");
                            id = Integer.parseInt(sc.nextLine());
                            break;
                        } catch (NumberFormatException e) {
                            System.out.println("Vui lòng nhập số!");
                        }
                    }

                    QLDepartment.findDepartmentByIdAndName(name, id);
                    System.out.println();
                    break;
                case "5":
                    QLDepartment.findeDepartmentHaveLessThan2Acc();
                    System.out.println();
                    break;
                case "6":
                    System.out.print("Nhập tên chức vụ: ");
                    String nameP = sc.nextLine();
                    QLPosition.findByPositionName(nameP);
                    System.out.println();
                    break;
                case "7":
                    System.out.print("Nhập full_name: ");
                    String nameF = sc.nextLine();
                    QLAccount.findByFullname(nameF);
                    System.out.println();
                    break;
                case "8":
                    System.out.print("Nhập full_name: ");
                    String full_name = sc.nextLine();
                    System.out.print("Nhập username: ");
                    String username = sc.nextLine();
                    QLAccount.findByFullnameAndUsername(full_name, username);
                    System.out.println();
                    break;
                case "0":
                    System.out.println("===========Đã thoát chương trình========");
                    System.exit(0);
                default:
                    System.out.println("Vui lòng nhập đúng số để chọn chức năng!!!!");
            }
        }
    }
}
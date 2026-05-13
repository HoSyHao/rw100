package buoi7.src.frontend;

import buoi7.src.backend.QLAccount;
import buoi7.src.backend.QLDepartment;
import buoi7.src.backend.QLPosition;
import buoi7.src.entity.Department;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static buoi7.src.backend.QLDepartment.*;

public class DepartmentFunc {
    private static final Scanner sc = new Scanner(System.in);
    static List<Department> departments = new ArrayList<>();
    public static void run() {
        while (true) {
            System.out.println("============ MỜI BẠN CHỌN CHỨC NĂNG =============");
            System.out.println("1. Xem ds phòng ban");
            System.out.println("2. Thêm phòng ban");
            System.out.println("3. Cập nhật phòng ban");
            System.out.println("4. Xóa phòng ban");
            System.out.println("5. Tìm kiếm phòng theo id và name");
            System.out.println("6. Tìm kiếm phòng ban có nhiều hơn 2 nhân viên");
            System.out.println("0. Thoát");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    departments = findAllDepartment();
                    showDepartment(departments, null, null);
                    System.out.println();
                    break;
                case "2":
                    createDepartment();
                    System.out.println();
                    break;
                case "3":
                    updateDepartment();
                    System.out.println();
                    break;
                case "4":
                    deleteDepartment();
                    System.out.println();
                    break;
                case "5":
                    findDepartmentByIdAndName();
                    System.out.println();
                    break;
                case "6":
                    departments = findeDepartmentHaveMoreThan2Acc();
                    showDepartment(departments, null, null);
                    break;
                case "0":
                    System.out.println("===========Đã thoát chương trình========");
                    System.exit(0);
                default:
                    System.out.println("Vui lòng nhập đúng số để chọn chức năng!!!!");
            }

        }
    }


    public static void createDepartment() {
        System.out.print("Nhập tên phòng ban: ");
        String name = sc.nextLine();
        boolean check = QLDepartment.createDepartment(name);
        if (check) {
            System.out.println("Thêm mới phòng ban " + name + " thành công!!");
        } else {
            System.out.println("Thêm không thành công");
        }
    }

    public static void deleteDepartment() {
        System.out.print("Nhập tên phòng ban: ");
        String name = sc.nextLine();
        boolean check = QLDepartment.deleteDepartment(name);
        if (check) {
            System.out.println("Xóa phòng ban " + name + " thành công!!");
        } else {
            System.out.println("Xóa không thành công");
        }
    }

    public static void updateDepartment() {
        int id;
        while (true) {
            System.out.print("Nhập id: ");
            try {
                id = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!!!");
            }
        }


        System.out.print("Nhập tên muốn cập nhật: ");
        String name = sc.nextLine();
        boolean check = QLDepartment.updateDepartment(id, name);
        if (check) {
            System.out.println("Cập nhật tên phòng ban" + id + " : " + name + " thành công!!");
        } else {
            System.out.println("Cập nhật không thành công");
        }
    }

    public static void findDepartmentByIdAndName() {
        int id;
        while (true) {
            System.out.print("Nhập id: ");
            try {
                id = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số!!!");
            }
        }

        System.out.print("Nhập tên phòng ban cần tìm: ");
        String name = sc.nextLine();
        List<Department> departments = QLDepartment.findDepartmentByIdAndName(name, id);
        showDepartment(departments, name, id);
    }


}
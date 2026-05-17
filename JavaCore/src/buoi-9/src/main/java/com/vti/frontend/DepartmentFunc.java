package com.vti.frontend;

import com.vti.backend.controller.DepartmentController;
import com.vti.entity.Department;

import java.util.List;
import java.util.Scanner;

import static com.vti.utils.InputUtils.inputInt;

public class DepartmentFunc {
    private final Scanner sc = new Scanner(System.in);
    private final DepartmentController departmentController = new DepartmentController();
    List<Department> departments;

    public void run() {
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
                    departments = departmentController.getAllDepartments();
                    this.showDepartment(departments, null, null);
                    System.out.println();
                    break;
                case "2":
                    this.createDepartment();
                    System.out.println();
                    break;
                case "3":
                    this.updateDepartment();
                    System.out.println();
                    break;
                case "4":
                    this.deleteDepartment();
                    System.out.println();
                    break;
                case "5":
                    this.findDepartmentByIdAndName();
                    System.out.println();
                    break;
                case "6":
                    departments = departmentController.findDepartmentHaveMoreThan2Acc();
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

    public void showDepartment(List<Department> departmentList, String searchName, Integer searchId) {
        if (searchName == null && searchId == null && departmentList.isEmpty()) {
            System.out.println("No departments found");
        } else if (departmentList.isEmpty()) {
            System.out.println("No department found with name: " + searchName + " and id: " + searchId);
        } else {
            System.out.println("+-----+--------------------+");
            System.out.printf("|%5s|%20s|\n", "ID", "NAME");
            System.out.println("+-----+--------------------+");
            for (Department department : departmentList) {
                System.out.printf("|%5s|%20s|\n", department.getDepartmentID(), department.getDepartmentName());
            }
            System.out.println("+-----+--------------------+");
        }
    }

    public void createDepartment() {
        System.out.print("Nhập tên phòng ban: ");
        String name = sc.nextLine();
        boolean check = departmentController.createDepartment(name);
        if (check) {
            System.out.println("Thêm mới phòng ban " + name + " thành công!!");
        } else {
            System.out.println("Thêm không thành công");
        }
    }

    public void deleteDepartment() {
        int id = inputInt(sc, "Nhập id: ");
        boolean check = departmentController.deleteDepartment(id);
        if (check) {
            System.out.println("Xóa phòng ban " + id + " thành công!!");
        } else {
            System.out.println("Xóa không thành công");
        }
    }

    public void updateDepartment() {
        int id = inputInt(sc, "Nhập id: ");
        System.out.print("Nhập tên muốn cập nhật: ");
        String name = sc.nextLine();
        boolean check = departmentController.updateDepartment(id, name);
        if (check) {
            System.out.println("Cập nhật tên phòng ban" + id + " : " + name + " thành công!!");
        } else {
            System.out.println("Cập nhật không thành công");
        }
    }

    public void findDepartmentByIdAndName() {
        int id = inputInt(sc, "Nhập id: ");
        System.out.print("Nhập tên phòng ban cần tìm: ");
        String name = sc.nextLine();
        List<Department> departments = departmentController.findDepartmentByIdAndName(name, id);
        this.showDepartment(departments, name, id);
    }
}

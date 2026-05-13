package buoi7.src.frontend;

import buoi7.src.backend.QLPosition;
import buoi7.src.entity.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static buoi7.src.backend.QLPosition.*;
import static buoi7.utils.InputUtils.inputInt;

public class PositionFunc {
    private static final Scanner sc = new Scanner(System.in);
    static List<Position> positions = new ArrayList<>();
    public static void run() {
        while (true) {
            System.out.println("============ MỜI BẠN CHỌN CHỨC NĂNG =============");
            System.out.println("1. Xem ds chức vụ");
            System.out.println("2. Thêm chức vụ");
            System.out.println("3. Cập nhật chức vụ");
            System.out.println("4. Xóa chức vụ");
            System.out.println("5. Tìm kiếm chức vụ");
            System.out.println("0. Thoát");

            String choice = sc.nextLine();
            switch (choice) {
                case "1":
                    positions = findAllPosition();
                    showPosition(positions, null);
                    System.out.println();
                    break;
                case "2":
                    createPosition();
                    System.out.println();
                    break;
                case "3":
                    updatePosition();
                    System.out.println();
                    break;
                case "4":
                    deletePosition();
                    System.out.println();
                    break;
                case "5":
                    findByPositionName();
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

    public static void createPosition() {
        System.out.print("Nhập tên chức vụ: ");
        String name = sc.nextLine();
        boolean check = QLPosition.createPosition(name);
        if (check) {
            System.out.println("Thêm mới chức vụ " + name + " thành công!!");
        } else {
            System.out.println("Thêm không thành công");
        }
    }

    public static void updatePosition() {
        int id = inputInt(sc, "Nhập id: ");
        System.out.print("Nhập tên muốn cập nhật: ");
        String name = sc.nextLine();
        boolean check = QLPosition.updatePosition(name, id);
        if (check) {
            System.out.println("Cập nhật tên chức vụ" + id + " : " + name + " thành công!!");
        } else {
            System.out.println("Cập nhật không thành công");
        }
    }

    public static void deletePosition() {
        System.out.print("Nhập tên chức vụ: ");
        String name = sc.nextLine();
        boolean check = QLPosition.deletePosition(name);
        if (check) {
            System.out.println("Xóa chức vụ " + name + " thành công!!");
        } else {
            System.out.println("Xóa không thành công");
        }
    }

    public static void findByPositionName() {
        System.out.print("Nhập tên chức vụ cần tìm: ");
        String name = sc.nextLine();
        List<Position> positions = QLPosition.findByPositionName(name);
        showPosition(positions, name);
    }
}

package com.vti.frontend;

import com.vti.backend.controller.PositionController;
import com.vti.entity.Position;
import com.vti.enums.PositionName;

import java.util.List;
import java.util.Scanner;

import static com.vti.utils.InputUtils.*;

public class PositionFunc {
    private final Scanner sc = new Scanner(System.in);
    private final PositionController positionController = new PositionController();
    List<Position> positions;

    public void run() {
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
                    positions = positionController.getAllPositions();
                    showPosition(positions, null);
                    System.out.println();
                    break;
                case "2":
                    this.createPosition();
                    System.out.println();
                    break;
                case "3":
                    this.updatePosition();
                    System.out.println();
                    break;
                case "4":
                    this.deletePosition();
                    System.out.println();
                    break;
                case "5":
                    this.findByPositionName();
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

    public void showPosition(List<Position> positions, String val) {
        if (positions.isEmpty()) {
            if (val == null) {
                System.out.println("No positions found");
            } else {
                System.out.println("No positions found with name " + val);
            }
            return;
        }

        System.out.println("+-----+--------------------+");
        System.out.printf("|%5s|%20s|\n", "ID", "NAME");
        System.out.println("+-----+--------------------+");

        for (Position position : positions) {
            System.out.printf("|%5s|%20s|\n",
                    position.getPositionID(),
                    position.getPositionName());
        }

        System.out.println("+-----+--------------------+");
    }

    public void createPosition() {
        PositionName name;
        while (true) {
            name = inputPositionName(sc);
            if (name == null) {
                System.out.println("Đã hủy thao tác thêm mới.");
                return;
            }

            // Validate để không báo lỗi enum ở DB
            try {
                PositionName.valueOf(String.valueOf(name));
            } catch (IllegalArgumentException e) {
                System.out.println("Tên chức vụ không hợp lệ!");
                continue;
            }

            if (positionController.checkExistPosition(String.valueOf(name), null)) {
                System.out.println("Tên '" + name + "' đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                break;
            }
        }

        boolean isCreated = positionController.createPosition(String.valueOf(name));
        if (isCreated) {
            System.out.println("Thêm mới chức vụ " + name + " thành công!!");
        } else {
            System.out.println("Thêm không thành công");
        }
    }

    public void updatePosition() {
        Integer id;
        String name;

        // Hiển thị danh sách position để user tham khảo
        positions = positionController.getAllPositions();
        showPosition(positions, null);

        while (true) {
            id = inputInt(sc, "Nhập ID cần cập nhật (Hoặc nhấn Enter để hủy): ");

            if (id == null) {
                System.out.println("Đã hủy thao tác cập nhật.");
                return;
            }

            if (!positionController.checkExistPosition(null, id)) {
                System.out.println("ID '" + id + "' không tồn tại! Vui lòng nhập id khác.");
            } else {
                break;
            }
        }

        // Nhập tên mới (Enter = hủy, không cập nhật)
        while (true) {
            name = inputStringBlank(sc, "Nhập tên muốn cập nhật (Hoặc nhấn Enter để hủy): ");
            if (name == null) {
                System.out.println("Đã hủy thao tác cập nhật.");
                return;
            }

            // Validate enum trước
            try {
                PositionName.valueOf(name.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Tên chức vụ không hợp lệ!");
                continue;
            }

            // Kiểm tra trùng lặp (tên không được trùng với position khác)
            if (positionController.checkExistPosition(name.trim(), id)) {
                System.out.println("Tên '" + name.trim() + "' đã tồn tại! Vui lòng nhập tên khác.");
            } else {
                break;
            }
        }

        boolean check = positionController.updatePosition(name.trim(), id);
        if (check) {
            System.out.println("Cập nhật tên chức vụ " + id + ": " + name.trim() + " thành công!!");
        } else {
            System.out.println("Cập nhật không thành công");
        }
    }

    public void deletePosition() {
        Integer id;
        while (true) {
            id = inputInt(sc, "Nhập ID cần xóa (Hoặc nhấn Enter để hủy): ");

            if (id == null) {
                System.out.println("Đã hủy thao tác xóa.");
                return;
            }
            if (!positionController.checkExistPosition(null, id)) {
                System.out.println("ID '" + id + "' không tồn tại! Vui lòng nhập id khác.");
            } else {
                break;
            }
        }

        boolean check = positionController.deletePosition(id);
        if (check) {
            System.out.println("Xóa chức vụ " + id + " thành công!!");
        } else {
            System.out.println("Xóa không thành công");
        }
    }

    public void findByPositionName() {
        String name = inputStringBlank(sc, "Nhập tên chức vụ cần tìm: ");
        if (name == null) return;
        List<Position> positions = positionController.findByPositionName(name);
        showPosition(positions, name);
    }
}
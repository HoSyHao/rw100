package com.vti.utils;

import com.vti.enums.PositionName;

import java.util.Scanner;

public class InputUtils {

    public static Integer inputInt(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập vào một số nguyên hợp lệ!");
            }
        }
    }

    public static Integer inputOptionalInt(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine();

            if (input == null ||input.isBlank()) {
                return null;
            }

            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập vào một số nguyên hoặc để trống!");
            }
        }
    }

    public static PositionName inputPositionName(Scanner sc) {
        while (true) {
            System.out.print("Nhập tên chức vụ (DEV, TEST, SCRUM_MASTER, PM) hoặc Enter để hủy: ");

            String input = sc.nextLine().trim();

            if (input.isBlank()) {
                return null;
            }

            try {
                return PositionName.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Tên chức vụ không hợp lệ!");
            }
        }
    }
}

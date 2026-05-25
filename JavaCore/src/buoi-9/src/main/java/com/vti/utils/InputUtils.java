package com.vti.utils;

import com.vti.enums.PositionName;

import java.util.Scanner;

public class InputUtils {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";

    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    public static Integer inputInt(Scanner sc, String message) {
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

    public static String inputString(Scanner sc, String message){
        while (true) {
            System.out.print(message);
            String input = sc.nextLine();

            // Nếu không trống, trả về
            if (!input.isBlank()) {
                return input.trim();
            }

            // Nếu trống, yêu cầu nhập lại
            System.out.println("Vui lòng nhập dữ liệu!");
        }
    }

    public static String inputStringBlank(Scanner sc, String message){
        System.out.print(message);
        String input = sc.nextLine();

        return input.isBlank() ? null : input.trim();
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

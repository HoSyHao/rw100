package com.vti.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class InputUtils {

    // Regex kiểm tra định dạng email
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";

    // Regex kiểm tra password: 6-12 ký tự, có ít nhất 1 chữ hoa
    public static final String PASSWORD_REGEX = "^(?=.*[A-Z]).{6,12}$";

    // Regex kiểm tra fullname: chỉ chứa chữ cái và khoảng trắng, không chứa ký tự đặc biệt
    public static final String FULLNAME_REGEX = "^[a-zA-Z\\s]+$";

    /**
     * Kiểm tra email hợp lệ
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    /**
     * Kiểm tra password hợp lệ (6-12 ký tự, ít nhất 1 chữ hoa)
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    /**
     * Kiểm tra fullname hợp lệ (chỉ chứa chữ, không chứa ký tự đặc biệt)
     */
    public static boolean isValidFullName(String fullName) {
        return fullName != null && fullName.matches(FULLNAME_REGEX);
    }

    /**
     * Kiểm tra expInYear hợp lệ (>= 0)
     */
    public static boolean isValidExpInYear(int expInYear) {
        return expInYear >= 0;
    }

    /**
     * Nhập chuỗi bắt buộc (không được để trống)
     */
    public static String inputString(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine();

            if (!input.isBlank()) {
                return input.trim();
            }

            System.out.println("Vui lòng nhập dữ liệu!");
        }
    }

    /**
     * Nhập số nguyên bắt buộc
     */
    public static int inputInt(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            String input = sc.nextLine();

            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập vào một số nguyên!");
            }
        }
    }

    /**
     * Nhập email hợp lệ (lặp cho đến khi đúng)
     */
    public static String inputEmail(Scanner sc) {
        while (true) {
            String email = inputString(sc, "Nhập email (VD: nguyen.vannam@vti.com.vn): ");
            if (isValidEmail(email)) {
                return email;
            }
            System.out.println("Email không đúng định dạng! Vui lòng nhập lại.");
        }
    }

    /**
     * Nhập password hợp lệ (lặp cho đến khi đúng)
     */
    public static String inputPassword(Scanner sc) {
        while (true) {
            String password = inputString(sc, "Nhập mật khẩu (6-12 ký tự, ít nhất 1 chữ hoa): ");
            if (isValidPassword(password)) {
                return password;
            }
            System.out.println("Mật khẩu không hợp lệ! Yêu cầu: 6-12 ký tự, có ít nhất 1 ký tự viết hoa.");
        }
    }

    /**
     * Nhập fullname hợp lệ (chỉ chứa chữ)
     */
    public static String inputFullName(Scanner sc) {
        while (true) {
            String fullName = inputString(sc, "Nhập họ tên (Full Name): ");
            if (isValidFullName(fullName)) {
                return fullName;
            }
            System.out.println("Họ tên không hợp lệ! Chỉ được chứa chữ cái, không chứa ký tự đặc biệt.");
        }
    }

    /**
     * Mã hóa password bằng SHA-256 trước khi lưu vào database
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            // Chuyển byte[] sang chuỗi hex
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Lỗi mã hóa mật khẩu!", e);
        }
    }
}

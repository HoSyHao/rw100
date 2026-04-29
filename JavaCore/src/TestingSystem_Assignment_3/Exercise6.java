package TestingSystem_Assignment_3;

import java.util.Arrays;
import java.util.Scanner;

public class Exercise6 {

    private final Scanner scanner = new Scanner(System.in);

    // Exercise 6: Debug
    //
    // Question 1:
    // 1 học viên đã code bài min, max và có 1 vài lỗi sai.
    // a) Trong function getMaxValue, hãy tìm xem tại vòng lặp i = 4
    // thì variable maxValue đang có giá trị bao nhiêu
    // b) Tại vòng lặp i = 4, hãy thử set lại maxValue = 5
    // c) Hãy tìm bug và sửa lại cho bài trên để tìm Max Value và Min Value cho đúng
    public void question1() {
        int[] numbers = {1, 5, 2, 8, 4, 9, 3};

        System.out.println("Array: " + Arrays.toString(numbers));
        System.out.println("Max value: " + getMaxValue(numbers));
        System.out.println("Min value: " + getMinValue(numbers));
    }

    // Question 2:
    // Trong bài tập Assignment 3 Debug, có 1 học viên đã làm sai flow:
    // B1: Chọn nhập chức năng thêm mới cán bộ
    // B2: Chọn thêm 1 cán bộ vào
    // B3: Nhập thông tin cán bộ vào
    // Nhưng hệ thống vẫn yêu cầu nhập thêm người nữa.
    // Debug để tìm lỗi và sửa lỗi.
    public void question2() {
        while (true) {
            System.out.println("========== MENU ==========");
            System.out.println("1. Thêm mới cán bộ");
            System.out.println("0. Thoát");

            int choice = inputInt("Chọn chức năng: ");

            switch (choice) {
                case 1:
                    addOfficer();
                    break;
                case 0:
                    System.out.println("Kết thúc chương trình!");
                    return;
                default:
                    System.out.println("Chọn sai, vui lòng chọn lại!");
            }

            boolean isContinue = askContinue();

            if (!isContinue) {
                System.out.println("Kết thúc chương trình!");
                return;
            }
        }
    }

    public int getMaxValue(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array không được null hoặc rỗng!");
        }

        int maxValue = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (i == 4) {
                System.out.println("Debug i = 4, maxValue = " + maxValue);
                // Có thể thử set:
                // maxValue = 5;
            }

            if (numbers[i] > maxValue) {
                maxValue = numbers[i];
            }
        }

        return maxValue;
    }

    public int getMinValue(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            throw new IllegalArgumentException("Array không được null hoặc rỗng!");
        }

        int minValue = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < minValue) {
                minValue = numbers[i];
            }
        }

        return minValue;
    }

    private void addOfficer() {
        System.out.println("------------ ADD OFFICER -----------");

        String fullName = inputRequiredString("Nhập họ tên cán bộ: ");
        int age = inputInt("Nhập tuổi: ");
        String address = inputRequiredString("Nhập địa chỉ: ");

        System.out.println("Thêm cán bộ thành công!");
        System.out.println("Họ tên: " + fullName);
        System.out.println("Tuổi: " + age);
        System.out.println("Địa chỉ: " + address);
    }

    private boolean askContinue() {
        while (true) {
            String answer = inputRequiredString("Bạn có muốn nhập thêm người nữa không? (Có/Không): ");

            if (answer.equalsIgnoreCase("Có")
                    || answer.equalsIgnoreCase("Co")
                    || answer.equalsIgnoreCase("Yes")
                    || answer.equalsIgnoreCase("Y")) {
                return true;
            }

            if (answer.equalsIgnoreCase("Không")
                    || answer.equalsIgnoreCase("Khong")
                    || answer.equalsIgnoreCase("No")
                    || answer.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Vui lòng nhập Có hoặc Không!");
        }
    }

    private int inputInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số nguyên!");
            }
        }
    }

    private String inputRequiredString(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Không được để trống!");
        }
    }
}
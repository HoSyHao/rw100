package TestingSystem_Assignment_3;

import java.util.Random;
import java.util.Scanner;

public class Exercise1 {

    static final Random random = new Random();
    static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        question1();
        question2();
        question3();
        question4();
    }

    // Question 1:
    // Khai báo 2 số lương có kiểu dữ liệu là float.
    // Khởi tạo Lương của Account 1 là 5240.5 $
    // Khởi tạo Lương của Account 2 là 10970.055$
    // Khai báo 1 số int để làm tròn Lương của Account 1 và in số int đó ra
    // Khai báo 1 số int để làm tròn Lương của Account 2 và in số int đó ra
    public static void question1() {
        float salary1 = 5240.5f;
        float salary2 = 10970.055f;

        int salaryInt1 = (int) salary1;
        int salaryInt2 = (int) salary2;

        System.out.println("Salary 1: " + salaryInt1);
        System.out.println("Salary 2: " + salaryInt2);
    }

    // Question 2:
    // Lấy ngẫu nhiên 1 số có 5 chữ số
    // những số dưới 5 chữ số thì sẽ thêm có số 0 ở đầu cho đủ 5 chữ số
    public static int question2() {
        int number = random.nextInt(100000);
        System.out.printf("Random 5 digits: %05d%n", number);
        return number;
    }

    // Question 3:
    // Lấy 2 số cuối của số ở Question 2 và in ra.
    public static void question3() {
        int number = question2();

        int lastTwoDigits = number % 100;

        System.out.printf("2 số cuối: %02d%n", lastTwoDigits);
    }

    // Question 4:
    // Viết 1 method nhập vào 2 số nguyên a và b và trả về thương của chúng
    public static float question4() {
        int a = inputInt("Nhập a: ");

        int b;
        while (true) {
            b = inputInt("Nhập b: ");
            if (b != 0) break;
            System.out.println("b phải khác 0!");
        }

        return (float) a / b;
    }

    private static int inputInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số nguyên!");
            }
        }
    }
}
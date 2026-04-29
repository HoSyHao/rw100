package TestingSystem_Assignment_3;

public class Exercise3 {

    // Question 1:
    // Khởi tạo lương có datatype là Integer có giá trị bằng 5000.
    // Sau đó convert lương ra float và hiển thị lương lên màn hình
    // với số float có 2 số sau dấu thập phân
    public void question1() {
        Integer salary = 5000;

        float salaryFloat = salary.floatValue();

        System.out.printf("Salary: %.2f%n", salaryFloat);
    }

    // Question 2:
    // Khai báo 1 String có value = "1234567"
    // Hãy convert String đó ra số int
    public void question2() {
        String value = "1234567";

        int number = Integer.parseInt(value);

        System.out.println("Number: " + number);
    }

    // Question 3:
    // Khởi tạo 1 số Integer có value là chữ "1234567"
    // Sau đó convert số trên thành datatype int
    public void question3() {
        Integer value = Integer.valueOf("1234567");

        int number = value;

        System.out.println("Number: " + number);
    }
}
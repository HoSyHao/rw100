package TestingSystem_Assignment_3;

import java.util.Arrays;
import java.util.Comparator;

public class Exercise5 {

    private final Department[] departments = createDepartments();

    // Question 1:
    // In ra thông tin của phòng ban thứ 1 (sử dụng toString())
    public void question1() {
        System.out.println(departments[0]);
    }

    // Question 2:
    // In ra thông tin của tất cả phòng ban (sử dụng toString())
    public void question2() {
        for (Department department : departments) {
            System.out.println(department);
        }
    }

    // Question 3:
    // In ra địa chỉ của phòng ban thứ 1
    public void question3() {
        System.out.println(departments[0].hashCode());
    }

    // Question 4:
    // Kiểm tra xem phòng ban thứ 1 có tên là "Phòng A" không?
    public void question4() {
        boolean result = departments[0].departmentName.equals("Phòng A");

        System.out.println(result);
    }

    // Question 5:
    // So sánh 2 phòng ban thứ 1 và phòng ban thứ 2 xem có bằng nhau không
    // bằng nhau khi tên của 2 phòng ban đó bằng nhau
    public void question5() {
        boolean result = departments[0].departmentName.equals(departments[1].departmentName);

        System.out.println(result);
    }

    // Question 6:
    // Khởi tạo 1 array phòng ban gồm 5 phòng ban,
    // sau đó in ra danh sách phòng ban theo thứ tự tăng dần theo tên
    // sắp xếp theo vần ABCD
    public void question6() {
        Department[] copiedDepartments = Arrays.copyOf(departments, departments.length);

        Arrays.sort(copiedDepartments, new Comparator<Department>() {
            @Override
            public int compare(Department d1, Department d2) {
                return d1.departmentName.compareTo(d2.departmentName);
            }
        });

        for (Department department : copiedDepartments) {
            System.out.println(department.departmentName);
        }
    }

    // Question 7:
    // Khởi tạo 1 array học sinh gồm 5 Phòng ban,
    // sau đó in ra danh sách phòng ban được sắp xếp theo tên
    public void question7() {
        Department[] copiedDepartments = Arrays.copyOf(departments, departments.length);

        Arrays.sort(copiedDepartments, new Comparator<Department>() {
            @Override
            public int compare(Department d1, Department d2) {
                String lastWord1 = getLastWord(d1.departmentName);
                String lastWord2 = getLastWord(d2.departmentName);

                return lastWord1.compareTo(lastWord2);
            }
        });

        for (Department department : copiedDepartments) {
            System.out.println(department.departmentName);
        }
    }

    private Department[] createDepartments() {
        Department d1 = new Department();
        d1.departmentID = 1;
        d1.departmentName = "Accounting";

        Department d2 = new Department();
        d2.departmentID = 2;
        d2.departmentName = "Boss of director";

        Department d3 = new Department();
        d3.departmentID = 3;
        d3.departmentName = "Marketing";

        Department d4 = new Department();
        d4.departmentID = 4;
        d4.departmentName = "Sale";

        Department d5 = new Department();
        d5.departmentID = 5;
        d5.departmentName = "Waiting room";

        return new Department[]{d1, d2, d3, d4, d5};
    }

    private String getLastWord(String input) {
        String[] words = input.trim().split("\\s+");
        return words[words.length - 1];
    }
}
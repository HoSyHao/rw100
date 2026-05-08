package TestingSystem_Assignment_3;

import java.util.Arrays;
import java.util.Comparator;

public class Exercise5 {

    public  static void main(String[] args) {
        question7();
    }

    private static final Department[] departments = createDepartments();

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
    public static void question6() {
        Department[] copiedDepartments = Arrays.copyOf(departments, departments.length);

        Arrays.sort(copiedDepartments, (d1, d2) -> d1.departmentName.compareTo(d2.departmentName));

        Arrays.sort(copiedDepartments, Comparator.comparing(d -> d.departmentName));

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
    public static void question7() {
        Department[] copiedDepartments = Arrays.copyOf(departments, departments.length);

        Arrays.sort(copiedDepartments, new Comparator<Department>() {
            @Override
            public int compare(Department d1, Department d2) {
                String[] w1 = d1.departmentName.trim().split("\\s+");
                String[] w2 = d2.departmentName.trim().split("\\s+");

                //Lấy tên
                String last1 = w1[w1.length - 1];
                String last2 = w2[w2.length - 1];

                int result = last1.compareTo(last2);
                if (result != 0) return result;

                //Lấy tên đêm
                int i = w1.length - 2;
                int j = w2.length - 2;
                while (i >= 0 && j >= 0) {
                    String mid1 = w1[i];
                    String mid2 = w2[j];
                    result = mid1.compareTo(mid2);
                    if (result != 0) return result;
                    i--;
                    j--;
                }

                //Lấy tên
                return w1[0].compareTo(w2[0]);

            }
        });

        //Lambda
        Arrays.sort(copiedDepartments, (d1, d2) -> {
            String[] w1 = d1.departmentName.trim().split("\\s+");
            String[] w2 = d2.departmentName.trim().split("\\s+");

            // 1. last word
            String last1 = w1[w1.length - 1];
            String last2 = w2[w2.length - 1];

            int result = last1.compareTo(last2);
            if (result != 0) return result;

            // 2. middle words (so từ phải qua trái)
            int i = w1.length - 2;
            int j = w2.length - 2;

            while (i >= 0 && j >= 0) {
                result = w1[i].compareTo(w2[j]);
                if (result != 0) return result;
                i--;
                j--;
            }

            // 3. first word
            return w1[0].compareTo(w2[0]);
        });

        //Comparator.comparing
        Arrays.sort(copiedDepartments,
                Comparator.comparing((Department d) -> {
                    String[] w = d.departmentName.trim().split("\\s+");
                    return w[w.length - 1]; // last word
                }).thenComparing(d -> {
                    String[] w = d.departmentName.trim().split("\\s+");

                    // middle word (lấy gần cuối)
                    return w.length > 1 ? w[w.length - 2] : "";
                }).thenComparing(d -> {
                    String[] w = d.departmentName.trim().split("\\s+");
                    return w[0]; // first word
                })
        );

        for (Department department : copiedDepartments) {
            System.out.println(department.departmentName);
        }
    }

    private static Department[] createDepartments() {
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

}
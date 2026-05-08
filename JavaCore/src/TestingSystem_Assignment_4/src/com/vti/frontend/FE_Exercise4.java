package TestingSystem_Assignment_4.src.com.vti.frontend;

import TestingSystem_Assignment_4.src.com.vti.entity.Student;

public class FE_Exercise4 {
    public static void main(String[] args) {
        //    a)	Tất cả các property sẽ để là private để các class khác không chỉnh sửa hay nhìn thấy
//    b)	Tạo constructor cho phép khi khởi tạo mỗi student thì người dùng sẽ nhập vào tên, hometown và có điểm học lực = 0
//    c)	Tạo 1 method cho phép set điểm vào
//    d)	Tạo 1 method cho phép cộng thêm điểm
//    e)	Tạo 1 method để in ra thông tin của sinh viên bao gồm có tên, điểm học lực ( nếu điểm <4.0 thì sẽ in ra là Yếu, nếu điểm > 4.0 và < 6.0 thì sẽ in ra là trung bình, nếu điểm > 6.0 và < 8.0 thì sẽ in ra là khá, nếu > 8.0 thì in ra là Giỏi) Demo các chức năng trên bằng class ở front-end.
        Student s1 = new Student("Hao", "HCM");
        Student s2 = new Student("An", "HN");
        Student s3 = new Student("Nam", "DN");
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);

        s1.setScore(7);
        System.out.println(s1);
        s1.plusScore(7.0F);
        System.out.println(s1);
        s1.plusScore(3.0F);
        System.out.println(s1);
        s2.setScore(-1);
        System.out.println(s1);
    }

}

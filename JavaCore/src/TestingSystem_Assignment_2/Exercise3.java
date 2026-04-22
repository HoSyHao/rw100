package TestingSystem_Assignment_2;

import java.time.format.DateTimeFormatter;

public class Exercise3 {
//    Question 1:
//    In ra thông tin Exam thứ 1 và property create date sẽ được format theo định dạng vietnamese
    public void question1(Exam exam1) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("Exam Title: " + exam1.title);
        System.out.println("Create Date: " + exam1.createDate.format(formatter));
    }

//    Question 2:
//    In ra thông tin: Exam đã tạo ngày nào theo định dạng
//    Năm – tháng – ngày – giờ – phút – giây
    public void question2(Exam exam1) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        System.out.println("Exam đã tạo ngày: " + exam1.createDate.format(formatter));
    }

//    Question 3:
//    Chỉ in ra năm của create date property trong Question 2
    public void question3(Exam exam1) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        System.out.println(exam1.createDate.format(formatter));
    }

//    Question 4:
//    Chỉ in ra tháng và năm của create date property trong Question 2
    public void question4(Exam exam1) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        System.out.println(exam1.createDate.format(formatter));
    }

//    Question 5:
//    Chỉ in ra "MM-DD" của create date trong Question 2
    public void question5(Exam exam1) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd");
        System.out.println(exam1.createDate.format(formatter));
    }
}

package TestingSystem_Assignment_1;

import java.time.LocalDate;

public class Program {
    public static void main(String[] args) {

//Question 2:
//Tạo file Program.java có chứa main() method và khởi tạo ít nhất 3 đối tượng đối với mỗi table trong java

        Department dep1 = new Department();
        dep1.departmentID = 1;
        dep1.departmentName = "Sale";

        Department dep2 = new Department();
        dep2.departmentID = 2;
        dep2.departmentName = "Marketing";

        Department dep3 = new Department();
        dep3.departmentID = 3;
        dep3.departmentName = "IT";

        Position pos1 = new Position();
        pos1.positionID = 1;
        pos1.positionName = PositionName.DEV;

        Position pos2 = new Position();
        pos2.positionID = 2;
        pos2.positionName = PositionName.TEST;

        Position pos3 = new Position();
        pos3.positionID = 3;
        pos3.positionName = PositionName.PM;

        Account acc1 = new Account();
        acc1.accountID = 1;
        acc1.email = "an@gmail.com";
        acc1.username = "an01";
        acc1.fullName = "Nguyen Van An";
        acc1.department = dep1;
        acc1.position = pos1;
        acc1.createDate = LocalDate.of(2024, 1, 10);

        Account acc2 = new Account();
        acc2.accountID = 2;
        acc2.email = "binh@gmail.com";
        acc2.username = "binh02";
        acc2.fullName = "Tran Thi Binh";
        acc2.department = dep2;
        acc2.position = pos2;
        acc2.createDate = LocalDate.of(2024, 2, 15);

        Account acc3 = new Account();
        acc3.accountID = 3;
        acc3.email = "cuong@gmail.com";
        acc3.username = "cuong03";
        acc3.fullName = "Le Van Cuong";
        acc3.department = dep3;
        acc3.position = pos3;
        acc3.createDate = LocalDate.of(2024, 3, 20);

        Group group1 = new Group();
        group1.groupID = 1;
        group1.groupName = "Java Fresher";
        group1.creator = acc1;
        group1.createDate = LocalDate.of(2024, 4, 1);

        Group group2 = new Group();
        group2.groupID = 2;
        group2.groupName = "Testing Team";
        group2.creator = acc2;
        group2.createDate = LocalDate.of(2024, 4, 2);

        Group group3 = new Group();
        group3.groupID = 3;
        group3.groupName = "PM Club";
        group3.creator = acc3;
        group3.createDate = LocalDate.of(2024, 4, 3);

        GroupAccount ga1 = new GroupAccount();
        ga1.group = group1;
        ga1.account = acc1;
        ga1.joinDate = LocalDate.of(2024, 4, 5);

        GroupAccount ga2 = new GroupAccount();
        ga2.group = group2;
        ga2.account = acc2;
        ga2.joinDate = LocalDate.of(2024, 4, 6);

        GroupAccount ga3 = new GroupAccount();
        ga3.group = group3;
        ga3.account = acc3;
        ga3.joinDate = LocalDate.of(2024, 4, 7);

        TypeQuestion type1 = new TypeQuestion();
        type1.typeID = 1;
        type1.typeName = TypeName.ESSAY;

        TypeQuestion type2 = new TypeQuestion();
        type2.typeID = 2;
        type2.typeName = TypeName.MULTIPLE_CHOICE;

        CategoryQuestion cate1 = new CategoryQuestion();
        cate1.categoryID = 1;
        cate1.categoryName = "Java";

        CategoryQuestion cate2 = new CategoryQuestion();
        cate2.categoryID = 2;
        cate2.categoryName = "SQL";

        CategoryQuestion cate3 = new CategoryQuestion();
        cate3.categoryID = 3;
        cate3.categoryName = ".NET";

        Question q1 = new Question();
        q1.questionID = 1;
        q1.content = "Java la gi?";
        q1.category = cate1;
        q1.type = type1;
        q1.creator = acc1;
        q1.createDate = LocalDate.of(2024, 5, 1);

        Question q2 = new Question();
        q2.questionID = 2;
        q2.content = "SQL dung de lam gi?";
        q2.category = cate2;
        q2.type = type2;
        q2.creator = acc2;
        q2.createDate = LocalDate.of(2024, 5, 2);

        Question q3 = new Question();
        q3.questionID = 3;
        q3.content = ".NET la gi?";
        q3.category = cate3;
        q3.type = type2;
        q3.creator = acc3;
        q3.createDate = LocalDate.of(2024, 5, 3);

        Answer ans1 = new Answer();
        ans1.answerID = 1;
        ans1.content = "Java la ngon ngu lap trinh";
        ans1.question = q1;
        ans1.isCorrect = true;

        Answer ans2 = new Answer();
        ans2.answerID = 2;
        ans2.content = "SQL dung de thao tac voi co so du lieu";
        ans2.question = q2;
        ans2.isCorrect = true;

        Answer ans3 = new Answer();
        ans3.answerID = 3;
        ans3.content = ".NET la nen tang phat trien phan mem";
        ans3.question = q3;
        ans3.isCorrect = true;

        Exam exam1 = new Exam();
        exam1.examID = 1;
        exam1.code = "EX001";
        exam1.title = "De thi Java";
        exam1.category = cate1;
        exam1.duration = 60;
        exam1.creator = acc1;
        exam1.createDate = LocalDate.of(2024, 6, 1);

        Exam exam2 = new Exam();
        exam2.examID = 2;
        exam2.code = "EX002";
        exam2.title = "De thi SQL";
        exam2.category = cate2;
        exam2.duration = 90;
        exam2.creator = acc2;
        exam2.createDate = LocalDate.of(2024, 6, 2);

        Exam exam3 = new Exam();
        exam3.examID = 3;
        exam3.code = "EX003";
        exam3.title = "De thi .NET";
        exam3.category = cate3;
        exam3.duration = 120;
        exam3.creator = acc3;
        exam3.createDate = LocalDate.of(2024, 6, 3);

        ExamQuestion eq1 = new ExamQuestion();
        eq1.exam = exam1;
        eq1.question = q1;

        ExamQuestion eq2 = new ExamQuestion();
        eq2.exam = exam2;
        eq2.question = q2;

        ExamQuestion eq3 = new ExamQuestion();
        eq3.exam = exam3;
        eq3.question = q3;

//Question 3:
//Trong file Program.java, hãy in ít nhất 1 giá trị của mỗi đối tượng ra

        System.out.println("Department: " + dep1.departmentName);
        System.out.println("Position: " + pos1.positionName);
        System.out.println("Account: " + acc1.fullName);
        System.out.println("Group: " + group1.groupName);
        System.out.println("GroupAccount: " + ga1.joinDate);
        System.out.println("TypeQuestion: " + type1.typeName);
        System.out.println("CategoryQuestion: " + cate1.categoryName);
        System.out.println("Question: " + q1.content);
        System.out.println("Answer: " + ans1.content);
        System.out.println("Exam: " + exam1.title);
        System.out.println("ExamQuestion: " + eq1.exam.title + " - " + eq1.question.content);
    }
}
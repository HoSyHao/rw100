package TestingSystem_Assignment_2;

import java.time.LocalDateTime;

public class Program {
    public static void main(String[] args) {

        // Department
        Department dep1 = new Department();
        dep1.departmentID = 1;
        dep1.departmentName = "Sale";

        Department dep2 = new Department();
        dep2.departmentID = 2;
        dep2.departmentName = "Marketing";

        Department dep3 = new Department();
        dep3.departmentID = 3;
        dep3.departmentName = "IT";

        // Position
        Position pos1 = new Position();
        pos1.positionID = 1;
        pos1.positionName = PositionName.DEV;

        Position pos2 = new Position();
        pos2.positionID = 2;
        pos2.positionName = PositionName.TEST;

        Position pos3 = new Position();
        pos3.positionID = 3;
        pos3.positionName = PositionName.PM;

        // Account
        Account acc1 = new Account();
        acc1.accountID = 1;
        acc1.email = "nguyenvana@gmail.com";
        acc1.username = "vana";
        acc1.fullName = "Nguyen Van A";
        acc1.department = dep1;
        acc1.position = pos1;
        acc1.createDate = LocalDateTime.of(2024, 1, 10, 9, 30, 15);

        Account acc2 = new Account();
        acc2.accountID = 2;
        acc2.email = "nguyenvanb@gmail.com";
        acc2.username = "vanb";
        acc2.fullName = "Nguyen Van B";
        acc2.department = dep2;
        acc2.position = pos2;
        acc2.createDate = LocalDateTime.of(2024, 2, 15, 10, 20, 30);

        Account acc3 = new Account();
        acc3.accountID = 3;
        acc3.email = "nguyenvanc@gmail.com";
        acc3.username = "vanc";
        acc3.fullName = "Nguyen Van C";
        acc3.department = dep3;
        acc3.position = pos3;
        acc3.createDate = LocalDateTime.of(2024, 3, 20, 8, 45, 10);

        // Group
        Group group1 = new Group();
        group1.groupID = 1;
        group1.groupName = "Java Fresher";
        group1.creator = acc1;
        group1.createDate = LocalDateTime.of(2024, 4, 1, 8, 0, 0);

        Group group2 = new Group();
        group2.groupID = 2;
        group2.groupName = "C# Fresher";
        group2.creator = acc2;
        group2.createDate = LocalDateTime.of(2024, 4, 2, 8, 0, 0);

        Group group3 = new Group();
        group3.groupID = 3;
        group3.groupName = "Testing Team";
        group3.creator = acc3;
        group3.createDate = LocalDateTime.of(2024, 4, 3, 8, 0, 0);

        Group group4 = new Group();
        group4.groupID = 4;
        group4.groupName = "PM Club";
        group4.creator = acc1;
        group4.createDate = LocalDateTime.of(2024, 4, 4, 8, 0, 0);

        // Gán account vào group
        group1.accounts = new Account[]{acc1};
        group2.accounts = new Account[]{acc1, acc2};
        group3.accounts = new Account[]{acc1, acc2, acc3};
        group4.accounts = new Account[]{acc1, acc2, acc3};

        // Gán group cho account
        acc1.groups = new Group[]{group1, group2, group3, group4};
        acc2.groups = new Group[]{group2, group3, group4};
        acc3.groups = new Group[]{group3, group4};

        // TypeQuestion
        TypeQuestion type1 = new TypeQuestion();
        type1.typeID = 1;
        type1.typeName = TypeName.ESSAY;

        TypeQuestion type2 = new TypeQuestion();
        type2.typeID = 2;
        type2.typeName = TypeName.MULTIPLE_CHOICE;

        TypeQuestion type3 = new TypeQuestion();
        type3.typeID = 3;
        type3.typeName = TypeName.ESSAY;

        // CategoryQuestion
        CategoryQuestion cate1 = new CategoryQuestion();
        cate1.categoryID = 1;
        cate1.categoryName = "Java";

        CategoryQuestion cate2 = new CategoryQuestion();
        cate2.categoryID = 2;
        cate2.categoryName = "SQL";

        CategoryQuestion cate3 = new CategoryQuestion();
        cate3.categoryID = 3;
        cate3.categoryName = ".NET";

        // Question
        Question q1 = new Question();
        q1.questionID = 1;
        q1.content = "Java la gi?";
        q1.category = cate1;
        q1.type = type1;
        q1.creator = acc1;
        q1.createDate = LocalDateTime.of(2024, 5, 1, 9, 0, 0);

        Question q2 = new Question();
        q2.questionID = 2;
        q2.content = "SQL dung de lam gi?";
        q2.category = cate2;
        q2.type = type2;
        q2.creator = acc2;
        q2.createDate = LocalDateTime.of(2024, 5, 2, 10, 0, 0);

        Question q3 = new Question();
        q3.questionID = 3;
        q3.content = ".NET la gi?";
        q3.category = cate3;
        q3.type = type2;
        q3.creator = acc3;
        q3.createDate = LocalDateTime.of(2024, 5, 3, 11, 0, 0);

        // Answer
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

        // Exam
        Exam exam1 = new Exam();
        exam1.examID = 1;
        exam1.code = "EX001";
        exam1.title = "De thi Java";
        exam1.category = cate1;
        exam1.duration = 60;
        exam1.creator = acc1;
        exam1.createDate = LocalDateTime.of(2024, 6, 1, 14, 20, 35);

        Exam exam2 = new Exam();
        exam2.examID = 2;
        exam2.code = "EX002";
        exam2.title = "De thi SQL";
        exam2.category = cate2;
        exam2.duration = 90;
        exam2.creator = acc2;
        exam2.createDate = LocalDateTime.of(2024, 6, 2, 15, 10, 25);

        Exam exam3 = new Exam();
        exam3.examID = 3;
        exam3.code = "EX003";
        exam3.title = "De thi .NET";
        exam3.category = cate3;
        exam3.duration = 120;
        exam3.creator = acc3;
        exam3.createDate = LocalDateTime.of(2024, 6, 3, 16, 5, 45);

        // ExamQuestion
        ExamQuestion eq1 = new ExamQuestion();
        eq1.exam = exam1;
        eq1.question = q1;

        ExamQuestion eq2 = new ExamQuestion();
        eq2.exam = exam2;
        eq2.question = q2;

        ExamQuestion eq3 = new ExamQuestion();
        eq3.exam = exam3;
        eq3.question = q3;

        // Arrays
        Account[] accounts = {acc1, acc2, acc3};
        Department[] departments = {dep1, dep2, dep3};
        Group[] groups = {group1, group2, group3, group4};
        Exam[] exams = {exam1, exam2, exam3};

        // Exercise objects
        Exercise1 ex1 = new Exercise1();
        Exercise2 ex2 = new Exercise2();
        Exercise3 ex3 = new Exercise3();

        // Ex1
        System.out.println("--------------------------Ex1--------------------------");
        System.out.print("1. ");
        ex1.question1(acc2);

        System.out.print("2. ");
        ex1.question2(acc2);

        System.out.print("3. ");
        ex1.question3(acc2);

        System.out.print("4. ");
        ex1.question4(acc1);

        System.out.print("5. ");
        ex1.question5(group1);

        System.out.print("6. ");
        ex1.question6(acc2);

        System.out.print("7. ");
        ex1.question7(acc2);

        System.out.print("8. ");
        ex1.question8(accounts);

        System.out.print("9. ");
        ex1.question9(departments);

        System.out.print("10. ");
        ex1.question10(accounts);

        System.out.print("11. ");
        ex1.question11(departments);

        System.out.print("12. ");
        ex1.question12(departments);

        System.out.print("13. ");
        ex1.question13(accounts);

        System.out.print("14. ");
        ex1.question14(accounts);

        System.out.print("15. ");
        ex1.question15();

        System.out.print("16. ");
        ex1.question16(accounts, departments);

        System.out.print("17. ");
        ex1.question17(accounts, departments);

        System.out.println("--------------------------Ex2--------------------------");
        // Ex2
        System.out.print("1. ");
        ex2.question1();

        System.out.print("2. ");
        ex2.question2();

        System.out.print("3. ");
        ex2.question3();

        System.out.print("4. ");
        ex2.question4();

        System.out.print("5. ");
        ex2.question5();

        System.out.print("6. ");
        ex2.question6(accounts);

        System.out.println("--------------------------Ex3--------------------------");
        // Ex3
        System.out.print("1. ");
        ex3.question1(exam1);

        System.out.print("2. ");
        ex3.question2(exam1);

        System.out.print("3. ");
        ex3.question3(exam1);

        System.out.print("4. ");
        ex3.question4(exam1);

        System.out.print("5. ");
        ex3.question5(exam1);
    }
}

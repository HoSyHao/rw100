package TestingSystem_Assignment_2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Exercise4 {
    public static void main(String[] args) {
        question1();
        question2();
        question3();
        question4();
        question5();
        question6();
        question7();

    }
    static Random rand = new Random();
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-uuuu");
//    Exercise 4 (Optional): Random Number
//    Question 1:
//    In ngẫu nhiên ra 1 số nguyên
//    Công thức lấy random các giá trị trong khoảgn min --> max: ( max - min + 1) + min
    public static void question1(){
        int x = rand.nextInt();
        System.out.println(x);
    }

//    Question 2:
//    In ngẫu nhiên ra 1 số thực
    public static void question2(){
        float x = rand.nextFloat();
        System.out.println(x);
    }
//
//    Question 3:  Khai báo 1 array bao gồm các tên của các bạn trong lớp, sau đó in ngẫu nhiên ra tên của 1 bạn
    public static void question3(){
        String[] studentName = {"なると","さすけ","いたち","さくら","かかし"};
        System.out.println(studentName[rand.nextInt(studentName.length)]);
    }

//    Question 4:
//    Lấy ngẫu nhiên 1 ngày trong khoảng thời gian 24-07-1995 tới ngày 20-12-1995
    public static void question4(){
        LocalDate start = LocalDate.of(1995,7,24);
        LocalDate end = LocalDate.of(1995,12,20);

        long startEpoch = start.toEpochDay();
        long endEpoch = end.toEpochDay();

//        System.out.println(startEpoch+" "+endEpoch);
//        System.out.println(LocalDate.ofEpochDay(startEpoch).format(formatter)+" "+LocalDate.ofEpochDay(endEpoch).format(formatter));

        long randomDay = ThreadLocalRandom.current().nextLong(startEpoch, endEpoch + 1);

        System.out.println((LocalDate.ofEpochDay(randomDay)).format(formatter));
    }
//    Question 5:
//    Lấy ngẫu nhiên 1 ngày trong khoảng thời gian 1 năm trở lại đây
    public static void question5(){
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusYears(1);

        //System.out.println(start.format(formatter));

        long startEpoch = start.toEpochDay();
        long endEpoch = end.toEpochDay();

        long randomDay = ThreadLocalRandom.current().nextLong(startEpoch,endEpoch + 1);
        System.out.println((LocalDate.ofEpochDay(randomDay)).format(formatter));

    }
//    Question 6:
//    Lấy ngẫu nhiên 1 ngày trong quá khứ
    public static void question6(){
        LocalDate start = LocalDate.MIN;
        LocalDate end = LocalDate.now().minusDays(1); // quá khứ, không lấy hôm nay

//        System.out.println(start.format(formatter));

        long startEpoch = start.toEpochDay();
        long endEpoch = end.toEpochDay();

        long randomDay = ThreadLocalRandom.current().nextLong(startEpoch,endEpoch + 1);
        System.out.println((LocalDate.ofEpochDay(randomDay)).format(formatter));
    }
//    Question 7:
//    Lấy ngẫu nhiên 1 số có 3 chữ số
    public static void question7(){
        int x = rand.nextInt(900) + 100;
        System.out.println(x);
    }

}

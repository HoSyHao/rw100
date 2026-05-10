package TestingSystem_Assignment_4.src.com.vti.backend;

import java.util.Scanner;

public class A5_ex1 {
    Scanner sc = new Scanner(System.in);
    public void genMenu(){
        while(true){
            System.out.println("==========MyNews==========");
            System.out.println("1. Insert news");
            System.out.println("2. View list news");
            System.out.println("3. Average rate");
            System.out.println("4. Exit");
            System.out.print("Vui lòng chọn chức năng: ");
            int choice = Integer.parseInt(sc.nextLine());
            switch(choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Nhập sai vui lòng nhập lại.");
            }
        }
    }
}

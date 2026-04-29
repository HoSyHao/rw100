package TestingSystem_Assignment_3;

import java.time.LocalDateTime;

public class Exercise2 {

    // Question 1:
    // Không sử dụng data đã insert từ bài trước, tạo 1 array Account
    // và khởi tạo 5 phần tử theo cú pháp (sử dụng vòng for để khởi tạo):
    // Email: "Email 1"
    // Username: "User name 1"
    // FullName: "Full name 1"
    // CreateDate: now
    public void question1() {
        Account[] accounts = new Account[5];

        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account();

            accounts[i].email = "Email " + (i + 1);
            accounts[i].username = "User name " + (i + 1);
            accounts[i].fullName = "Full name " + (i + 1);
            accounts[i].createDate = LocalDateTime.now();
        }

        for (Account account : accounts) {
            System.out.println(account);
        }
    }
}
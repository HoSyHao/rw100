package com.vti.frontend;

import com.vti.backend.controller.AccountController;
import com.vti.entity.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.vti.utils.InputUtils.inputInt;
import static com.vti.utils.InputUtils.inputOptionalInt;

public class AccountFunc {
    private final Scanner sc = new Scanner(System.in);
    List<Account> accounts = new ArrayList<>();
    private final AccountController accountController = new AccountController();
    public void run() {
        while (true) {
            System.out.println("============ MỜI BẠN CHỌN CHỨC NĂNG =============");
            System.out.println("1. Xem ds account");
            System.out.println("2. Thêm account");
            System.out.println("3. Cập nhật account");
            System.out.println("4. Xóa account");
            System.out.println("5. Tìm account theo fullname");
            System.out.println("6. Tìm account theo fullname + username");
            System.out.println("0. Thoát");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    accounts = accountController.getAllAccounts();
                    this.showAccount(accounts, null, null);
                    System.out.println();
                    break;

                case "2":
                    this.createAccount();
                    System.out.println();
                    break;

                case "3":
                    this.updateAccount();
                    System.out.println();
                    break;

                case "4":
                    this.deleteAccount();
                    System.out.println();
                    break;

                case "5":
                    this.findByFullname();
                    System.out.println();
                    break;

                case "6":
                    this.findByFullnameAndUsername();
                    System.out.println();
                    break;

                case "0":
                    System.out.println("=========== Đã thoát chương trình ========");
                    System.exit(0);

                default:
                    System.out.println("Vui lòng nhập đúng số!");
            }
        }
    }

    public void showAccount(List<Account> accounts, String val1, String val2) {
        if (accounts.isEmpty()) {
            if (val1 == null && val2 == null) {
                System.out.println("No accounts found");
            } else if (val2 == null) {
                System.out.println("No accounts found with fullname " + val1);
            } else {
                System.out.println("No accounts found with fullname " + val1 + " and username " + val2);
            }
            return;
        } else {
            System.out.println("+-----+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
            System.out.printf("|%5s|%20s|%20s|%20s|%20s|%20s|%20s|\n", "ID", "EMAIL", "USERNAME", "FULLNAME", "DEPARTMENT", "POSITION", "CREATE DATE");
            System.out.println("+-----+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
            for (Account account : accounts) {
                System.out.printf("|%5s|%20s|%20s|%20s|%20s|%20s|%20s|\n",
                        account.getAccountID(),
                        account.getEmail(),
                        account.getUsername(),
                        account.getFullName(),
                        account.getDepartment() != null ? account.getDepartment().getDepartmentName() : "NULL",
                        account.getPosition() != null ? account.getPosition().getPositionName() : "NULL",
                        account.getCreateDate()
                );
            }
            System.out.println("+-----+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
        }
    }

    public  void createAccount() {
        System.out.print("Nhập email: ");
        String email = sc.nextLine();

        System.out.print("Nhập username: ");
        String username = sc.nextLine();

        System.out.print("Nhập fullname: ");
        String fullname = sc.nextLine();

        Integer departmentId = inputOptionalInt(
                sc,
                "Nhập department id (Enter để bỏ qua): "
        );

        Integer positionId = inputOptionalInt(
                sc,
                "Nhập position id (Enter để bỏ qua): "
        );

        boolean check = accountController.createAccount(
                email,
                username,
                fullname,
                departmentId,
                positionId
        );

        if (check) {
            System.out.println("Thêm account thành công!");
        } else {
            System.out.println("Thêm thất bại!");
        }
    }

    public void updateAccount() {
        int accountId = inputInt(sc, "Nhập account id: ");

        System.out.print("Nhập email mới (Enter để bỏ qua): ");
        String email = sc.nextLine();

        System.out.print("Nhập username mới (Enter để bỏ qua): ");
        String username = sc.nextLine();

        System.out.print("Nhập fullname mới (Enter để bỏ qua): ");
        String fullname = sc.nextLine();

        Integer departmentId = inputOptionalInt(
                sc,
                "Nhập department id mới (Enter để bỏ qua): "
        );

        Integer positionId = inputOptionalInt(
                sc,
                "Nhập position id mới (Enter để bỏ qua): "
        );

        boolean check = accountController.updateAccount(
                email,
                username,
                fullname,
                departmentId,
                positionId,
                accountId
        );

        if (check) {
            System.out.println("Cập nhật account thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }

    public void deleteAccount() {
        int accountId = inputInt(sc, "Nhập account id: ");

        boolean check = accountController.deleteAccount(accountId);

        if (check) {
            System.out.println("Xóa account thành công!");
        } else {
            System.out.println("Xóa thất bại!");
        }
    }

    public void findByFullname() {
        System.out.print("Nhập fullname cần tìm: ");
        String fullname = sc.nextLine();

        List<Account> accounts = accountController.findByFullname(fullname);

        this.showAccount(accounts, fullname, null);
    }

    public void findByFullnameAndUsername() {
        System.out.print("Nhập fullname cần tìm: ");
        String fullname = sc.nextLine();

        System.out.print("Nhập username cần tìm: ");
        String username = sc.nextLine();

        List<Account> accounts =
                accountController.findByFullnameAndUsername(fullname, username);

        this.showAccount(accounts, fullname, username);
    }
}

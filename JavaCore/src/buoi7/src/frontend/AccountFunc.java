package buoi7.src.frontend;

import buoi7.src.backend.QLAccount;
import buoi7.src.entity.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static buoi7.src.backend.QLAccount.*;
import static buoi7.utils.InputUtils.inputInt;
import static buoi7.utils.InputUtils.inputOptionalInt;

public class AccountFunc {

    private static final Scanner sc = new Scanner(System.in);
    static List<Account> accounts = new ArrayList<>();

    public static void run() {
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
                    accounts = findAllAccount();
                    showAccount(accounts, null, null);
                    System.out.println();
                    break;

                case "2":
                    createAccount();
                    System.out.println();
                    break;

                case "3":
                    updateAccount();
                    System.out.println();
                    break;

                case "4":
                    deleteAccount();
                    System.out.println();
                    break;

                case "5":
                    findByFullname();
                    System.out.println();
                    break;

                case "6":
                    findByFullnameAndUsername();
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

    public static void createAccount() {
        System.out.print("Nhập email: ");
        String email = sc.nextLine();

        System.out.print("Nhập username: ");
        String username = sc.nextLine();

        System.out.print("Nhập fullname: ");
        String fullname = sc.nextLine();

        int departmentId = inputInt(sc, "Nhập department id: ");
        int positionId = inputInt(sc, "Nhập position id: ");

        boolean check = QLAccount.createAccount(
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

    public static void updateAccount() {
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

        boolean check = QLAccount.updateAccount(
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

    public static void deleteAccount() {
        int accountId = inputInt(sc, "Nhập account id: ");

        System.out.print("Nhập username: ");
        String username = sc.nextLine();

        boolean check = QLAccount.deleteAccount(username, accountId);

        if (check) {
            System.out.println("Xóa account thành công!");
        } else {
            System.out.println("Xóa thất bại!");
        }
    }

    public static void findByFullname() {
        System.out.print("Nhập fullname cần tìm: ");
        String fullname = sc.nextLine();

        List<Account> accounts = QLAccount.findByFullname(fullname);

        showAccount(accounts, fullname, null);
    }

    public static void findByFullnameAndUsername() {
        System.out.print("Nhập fullname cần tìm: ");
        String fullname = sc.nextLine();

        System.out.print("Nhập username cần tìm: ");
        String username = sc.nextLine();

        List<Account> accounts =
                QLAccount.findByFullnameAndUsername(fullname, username);

        showAccount(accounts, fullname, username);
    }
}
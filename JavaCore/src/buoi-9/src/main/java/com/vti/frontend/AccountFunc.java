package com.vti.frontend;

import com.vti.backend.controller.AccountController;
import com.vti.backend.controller.DepartmentController;
import com.vti.backend.controller.PositionController;
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
    private final DepartmentController departmentController = new DepartmentController();
    private final PositionController positionController = new PositionController();
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

    public void createAccount() {
        String email, username, fullname;
        Integer departmentId, positionId;

        // === Validate Email ===
        while (true) {
            System.out.print("Nhập email (Hoặc nhấn Enter để hủy): ");
            email = sc.nextLine().trim();
            if (email.isBlank()) {
                System.out.println("Đã hủy thao tác thêm mới.");
                return;
            }
            if (!isValidEmail(email)) {
                System.out.println("Email không đúng định dạng! Vui lòng nhập lại.");
                continue;
            }
            if (accountController.checkAccountExists(email, null, null)) {
                System.out.println("Email '" + email + "' đã tồn tại! Vui lòng nhập email khác.");
                continue;
            }
            break;
        }

        // === Validate Username ===
        while (true) {
            System.out.print("Nhập username (Hoặc nhấn Enter để hủy): ");
            username = sc.nextLine().trim();
            if (username.isBlank()) {
                System.out.println("Đã hủy thao tác thêm mới.");
                return;
            }
            if (accountController.checkAccountExists(null, username, null)) {
                System.out.println("Username '" + username + "' đã tồn tại! Vui lòng nhập username khác.");
                continue;
            }
            break;
        }

        System.out.print("Nhập fullname: ");
        fullname = sc.nextLine().trim();
        if (fullname.isBlank()) {
            System.out.println("Fullname không được để trống!");
            return;
        }

        // Department ID (nếu nhập phải tồn tại)
        while (true) {
            departmentId = inputOptionalInt(sc, "Nhập department id (Enter để bỏ qua): ");
            if (departmentId == null) break;

            if (!departmentController.checkExistDepartment(null, departmentId)) {
                System.out.println("Department ID " + departmentId + " không tồn tại! Vui lòng nhập lại.");
            } else {
                break;
            }
        }

        // Position ID (nếu nhập phải tồn tại)
        while (true) {
            positionId = inputOptionalInt(sc, "Nhập position id (Enter để bỏ qua): ");
            if (positionId == null) break;

            if (!positionController.checkExistPosition(null, positionId)) {
                System.out.println("Position ID " + positionId + " không tồn tại! Vui lòng nhập lại.");
            } else {
                break;
            }
        }

        boolean isCreated = accountController.createAccount(email, username, fullname, departmentId, positionId);

        if (isCreated) {
            System.out.println("Thêm mới account thành công!!");
        } else {
            System.out.println("Thêm account thất bại!");
        }
    }

    public void updateAccount() {
        Integer accountId;
        String email, username, fullname;
        Integer departmentId, positionId;

        // Nhập và kiểm tra Account ID tồn tại
        while (true) {
            accountId = inputOptionalInt(sc, "Nhập Account ID cần cập nhật (Enter để hủy): ");
            if (accountId == null) {
                System.out.println("Đã hủy thao tác cập nhật.");
                return;
            }
            if (!accountController.checkAccountExists(null, null, accountId)) {
                System.out.println("Account ID '" + accountId + "' không tồn tại! Vui lòng nhập lại.");
            } else {
                break;
            }
        }

        // Email mới (nếu nhập phải đúng format và không trùng)
        System.out.print("Nhập email mới (Enter để bỏ qua): ");
        email = sc.nextLine().trim();
        if (!email.isBlank()) {
            if (!isValidEmail(email)) {
                System.out.println("Email không đúng định dạng!");
                return;
            }
            if (accountController.checkAccountExists(email, null, accountId)) {
                System.out.println("Email đã tồn tại bởi tài khoản khác!");
                return;
            }
        }

        // Username mới (nếu nhập phải không trùng)
        System.out.print("Nhập username mới (Enter để bỏ qua): ");
        username = sc.nextLine().trim();
        if (!username.isBlank()) {
            if (accountController.checkAccountExists(null, username, accountId)) {
                System.out.println("Username đã tồn tại bởi tài khoản khác!");
                return;
            }
        }

        System.out.print("Nhập fullname mới (Enter để bỏ qua): ");
        fullname = sc.nextLine().trim();

        // Department ID mới
        while (true) {
            departmentId = inputOptionalInt(sc, "Nhập department id mới (Enter để bỏ qua): ");
            if (departmentId == null) break;

            if (!departmentController.checkExistDepartment(null, departmentId)) {
                System.out.println("Department ID " + departmentId + " không tồn tại! Vui lòng nhập lại.");
            } else {
                break;
            }
        }

        // Position ID mới
        while (true) {
            positionId = inputOptionalInt(sc, "Nhập position id mới (Enter để bỏ qua): ");
            if (positionId == null) break;

            if (!positionController.checkExistPosition(null, positionId)) {
                System.out.println("Position ID " + positionId + " không tồn tại! Vui lòng nhập lại.");
            } else {
                break;
            }
        }

        boolean isUpdated = accountController.updateAccount(
                email.isBlank() ? null : email,
                username.isBlank() ? null : username,
                fullname.isBlank() ? null : fullname,
                departmentId,
                positionId,
                accountId
        );

        if (isUpdated) {
            System.out.println("Cập nhật account ID " + accountId + " thành công!!");
        } else {
            System.out.println("Cập nhật account thất bại!");
        }
    }

    public void deleteAccount() {
        Integer accountId;

        while (true) {
            accountId = inputOptionalInt(sc, "Nhập Account ID cần xóa (Enter để hủy): ");

            if (accountId == null) {
                System.out.println("Đã hủy thao tác xóa.");
                return;
            }

            if (!accountController.checkAccountExists(null, null, accountId)) {
                System.out.println("Account ID '" + accountId + "' không tồn tại! Vui lòng nhập lại.");
            } else {
                break;
            }
        }

        boolean isDeleted = accountController.deleteAccount(accountId);

        if (isDeleted) {
            System.out.println("Xóa account ID " + accountId + " thành công!!");
        } else {
            System.out.println("Xóa account thất bại!");
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

    private boolean isValidEmail(String email) {
        if (email == null) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";
        return email.matches(emailRegex);
    }
}

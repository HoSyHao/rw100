package com.vti.frontend;

import com.vti.backend.controller.AccountController;
import com.vti.backend.controller.DepartmentController;
import com.vti.backend.controller.PositionController;
import com.vti.entity.Account;
import com.vti.entity.Department;
import com.vti.entity.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static com.vti.utils.InputUtils.*;

public class AccountFunc {
    private final Scanner sc = new Scanner(System.in);
    List<Account> accounts = new ArrayList<>();

    List<Position> positions = new ArrayList<>();
    List<Department> departments = new ArrayList<>();
    private final AccountController accountController = new AccountController();
    private final DepartmentController departmentController = new DepartmentController();
    private final PositionController positionController = new PositionController();
    PositionFunc positionFunc = new PositionFunc();
    DepartmentFunc departmentFunc = new DepartmentFunc();

    public void run() {
        while (true) {
            System.out.println("============ MỜI BẠN CHỌN CHỨC NĂNG =============");
            System.out.println("1. Xem ds account");
            System.out.println("2. Thêm account");
            System.out.println("3. Cập nhật account");
            System.out.println("4. Xóa account");
            System.out.println("5. Tìm account theo fullname");
            System.out.println("6. Tìm account theo fullname + username");
            System.out.println("7. Import account bằng csv");
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

                case "7":
                    this.importAccountCSV();
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
        if (accounts.isEmpty() || accounts == null) {
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
            accounts.forEach(account -> {
                System.out.printf("|%5s|%20s|%20s|%20s|%20s|%20s|%20s|\n",
                        account.getAccountID(),
                        account.getEmail(),
                        account.getUsername(),
                        account.getFullName(),
                        account.getDepartment() != null ? account.getDepartment().getDepartmentName() : "NULL",
                        account.getPosition() != null ? account.getPosition().getPositionName() : "NULL",
                        account.getCreateDate()
                );
            });
            System.out.println("+-----+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
        }
    }

    public void createAccount() {
        String email, username, fullname;
        Integer departmentId, positionId;

        // === Validate Email ===
        while (true) {
            email = inputString(sc, "Nhập email: ");
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
            username = inputString(sc, "Nhập username: ");
            if (accountController.checkAccountExists(null, username, null)) {
                System.out.println("Username '" + username + "' đã tồn tại! Vui lòng nhập username khác.");
                continue;
            }
            break;
        }

        fullname = inputString(sc, "Nhập fullname: ");


        // Department ID (nếu nhập phải tồn tại)
        while (true) {
            departments = departmentController.getAllDepartments();
            departmentFunc.showDepartment(departments,null,null);
            departmentId = inputInt(sc, "Chọn department id (Enter để bỏ qua): ");
            if (departmentId == null) break;

            if (!departmentController.checkExistDepartment(null, departmentId)) {
                System.out.println("Department ID " + departmentId + " không tồn tại! Vui lòng nhập lại.");
            } else {
                break;
            }
        }

        // Position ID (nếu nhập phải tồn tại)
        while (true) {
            positions = positionController.getAllPositions();
            positionFunc.showPosition(positions, null);
            positionId = inputInt(sc, "Chọn position id (Enter để bỏ qua): ");
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
            accountId = inputInt(sc, "Nhập Account ID cần cập nhật (Enter để hủy): ");
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

        // Lấy thông tin hiện tại
        Account existing = accountController.getAccountById(accountId);
        System.out.println("\nThông tin hiện tại:");
        System.out.println("Email: " + existing.getEmail());
        System.out.println("Username: " + existing.getUsername());
        System.out.println("Fullname: " + existing.getFullName());
        System.out.println("Department: " + (existing.getDepartment() != null ? existing.getDepartment().getDepartmentName() : "NULL"));
        System.out.println("Position: " + (existing.getPosition() != null ? existing.getPosition().getPositionName() : "NULL"));

        // Email mới (Enter để giữ nguyên)
        email = existing.getEmail();
        while (true) {
            String newEmail = inputStringBlank(sc, "Nhập email mới (Enter để giữ nguyên) [" + existing.getEmail() + "]: ");
            if (newEmail == null) {
                break;
            }
            if (!isValidEmail(newEmail)) {
                System.out.println("Email không đúng định dạng!");
                continue;
            }
            if (accountController.checkAccountExists(newEmail, null, accountId)) {
                System.out.println("Email đã tồn tại bởi tài khoản khác!");
                continue;
            }
            email = newEmail;
            break;
        }

        // Username mới (Enter để giữ nguyên)
        username = existing.getUsername();
        while (true) {
            String newUsername = inputStringBlank(sc, "Nhập username mới (Enter để giữ nguyên) [" + existing.getUsername() + "]: ");
            if (newUsername == null) {
                break;
            }
            if (accountController.checkAccountExists(null, newUsername, accountId)) {
                System.out.println("Username đã tồn tại bởi tài khoản khác!");
                continue;
            }
            username = newUsername;
            break;
        }

        // Fullname mới (Enter để giữ nguyên)
        fullname = existing.getFullName();
        String newFullname = inputStringBlank(sc, "Nhập fullname mới (Enter để giữ nguyên) [" + existing.getFullName() + "]: ");
        if (newFullname != null) {
            fullname = newFullname;
        }

        // Department ID mới (Enter để giữ nguyên)
        departmentId = existing.getDepartment() != null ? existing.getDepartment().getDepartmentID() : null;
        while (true) {
            departments = departmentController.getAllDepartments();
            departmentFunc.showDepartment(departments, null, null);
            String prompt = "Chọn department id mới (Enter để giữ nguyên) [" + (existing.getDepartment() != null ? existing.getDepartment().getDepartmentID() : "NULL") + "]: ";
            Integer newDept = inputInt(sc, prompt);
            if (newDept == null) {
                break;
            }

            if (!departmentController.checkExistDepartment(null, newDept)) {
                System.out.println("Department ID " + newDept + " không tồn tại! Vui lòng nhập lại.");
                continue;
            }
            departmentId = newDept;
            break;
        }

        // Position ID mới (Enter để giữ nguyên)
        positionId = existing.getPosition() != null ? existing.getPosition().getPositionID() : null;
        while (true) {
            positions = positionController.getAllPositions();
            positionFunc.showPosition(positions, null);
            String prompt = "Chọn position id mới (Enter để giữ nguyên) [" + (existing.getPosition() != null ? existing.getPosition().getPositionID() : "NULL") + "]: ";
            Integer newPos = inputInt(sc, prompt);
            if (newPos == null) {
                break;
            }

            if (!positionController.checkExistPosition(null, newPos)) {
                System.out.println("Position ID " + newPos + " không tồn tại! Vui lòng nhập lại.");
                continue;
            }
            positionId = newPos;
            break;
        }

        boolean isUpdated = accountController.updateAccount(
                email,
                username,
                fullname,
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
            accountId = inputInt(sc, "Nhập Account ID cần xóa (Enter để hủy): ");

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
        String fullname = inputStringBlank(sc, "Nhập fullname cần tìm: ");
        if (Objects.isNull(fullname)) return;

        List<Account> accounts = accountController.findByFullname(fullname);

        this.showAccount(accounts, fullname, null);
    }

    public void findByFullnameAndUsername() {
        String fullname = inputStringBlank(sc, "Nhập fullname cần tìm: ");
        if (Objects.isNull(fullname)) return;

        String username = inputStringBlank(sc, "Nhập username cần tìm: ");
        if (Objects.isNull(username)) return;

        List<Account> accounts =
                accountController.findByFullnameAndUsername(fullname, username);

        this.showAccount(accounts, fullname, username);
    }

    public void importAccountCSV() {
        System.out.println("=== Import Account ===");
        System.out.print("Mời bạn nhập đường dẫn: ");
        String pathName = sc.nextLine();
        String res = accountController.importAccountCSV(pathName);
        System.out.println(res);
    }
}

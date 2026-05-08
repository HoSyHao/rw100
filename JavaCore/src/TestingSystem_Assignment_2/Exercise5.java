package TestingSystem_Assignment_2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Random;
import java.util.Scanner;

public class Exercise5 {

    static Scanner scanner = new Scanner(System.in);
    static Random rand = new Random();

    static DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);

    static Account[] accounts = new Account[20];
    static Department[] departments = new Department[20];
    static Group[] groups = new Group[20];

    static int accountCount = 0;
    static int departmentCount = 0;
    static int groupCount = 0;

    public static void main(String[] args) {
        seedData();
        question8();
    }

    // Question 1:
    // Viết lệnh cho phép người dùng nhập 3 số nguyên vào chương trình
    public static void question1() {
        int x = inputInt("First number: ");
        int y = inputInt("Second number: ");
        int z = inputInt("Third number: ");

        System.out.println("Bạn vừa nhập: " + x + ", " + y + ", " + z);
    }

    // Question 2:
    // Viết lệnh cho phép người dùng nhập 2 số thực vào chương trình
    public static void question2() {
        double x = inputDouble("First number: ");
        double y = inputDouble("Second number: ");

        System.out.println("Bạn vừa nhập: " + x + ", " + y);
    }

    // Question 3:
    // Viết lệnh cho phép người dùng nhập họ và tên
    public static void question3() {
        String fullName = inputRequiredString("Họ và tên: ");
        System.out.println("Họ tên: " + fullName);
    }

    // Question 4:
    // Viết lệnh cho phép người dùng nhập vào ngày sinh nhật của họ
    public static void question4() {
        LocalDate birthday = inputDate("Ngày sinh (dd/MM/yyyy): ");
        System.out.println("Ngày sinh: " + birthday.format(dateFormatter));
    }

    // Question 5:
    // Viết lệnh cho phép người dùng tạo account (viết thành method)
    // Đối với property Position, Người dùng nhập vào 1 2 3 4
    // và chương trình sẽ chuyển thành Position.Dev, Position.Test,
    // Position.ScrumMaster, Position.PM
    public static Account question5() {
        if (accountCount >= accounts.length) {
            System.out.println("Danh sách account đã đầy!");
            return null;
        }

        Account account = new Account();

        System.out.println("------------CREATE ACCOUNT-----------");

        account.accountID = inputPositiveInt("Nhập id: ");
        account.email = inputEmail("Nhập email: ");
        account.username = inputUniqueUsername("Nhập username: ");
        account.fullName = inputRequiredString("Nhập fullName: ");

        System.out.println("Tạo department cho account:");
        account.department = question6();

        account.position = new Position();
        account.position.positionName = inputPosition();

        account.createDate = LocalDateTime.now();

        accounts[accountCount++] = account;

        System.out.println("Tạo account thành công!");
        System.out.println(account);

        return account;
    }

    // Question 6:
    // Viết lệnh cho phép người dùng tạo department (viết thành method)
    public static Department question6() {
        if (departmentCount >= departments.length) {
            System.out.println("Danh sách department đã đầy!");
            return null;
        }

        Department department = new Department();

        System.out.println("------------CREATE DEPARTMENT-----------");

        department.departmentID = inputPositiveInt("Nhập department id: ");
        department.departmentName = inputRequiredString("Nhập department name: ");

        departments[departmentCount++] = department;

        System.out.println("Tạo department thành công!");
        System.out.println(department);

        return department;
    }

    // Question 7:
    // Nhập số chẵn từ console
    public static void question7() {
        while (true) {
            int number = inputInt("Nhập số chẵn: ");

            if (number % 2 == 0) {
                System.out.println("Số chẵn bạn nhập là: " + number);
                return;
            }

            System.out.println("Đây không phải số chẵn, vui lòng nhập lại!");
        }
    }

    // Question 8:
    // Viết chương trình thực hiện theo flow sau:
    // Bước 1: Chương trình in ra text "mời bạn nhập vào chức năng muốn sử dụng"
    // Bước 2: Nếu người dùng nhập vào 1 thì sẽ thực hiện tạo account
    // Nếu người dùng nhập vào 2 thì sẽ thực hiện chức năng tạo department
    // Nếu người dùng nhập vào số khác thì in ra text "Mời bạn nhập lại" và quay trở lại bước 1
    //
    // Question 10:
    // Tiếp tục Question 8 và Question 9
    // Bổ sung thêm vào bước 2 của Question 8:
    // Nếu người dùng nhập vào 3 thì sẽ thực hiện chức năng thêm group vào account
    // Sau khi người dùng thực hiện xong chức năng thì hỏi:
    // "Bạn có muốn thực hiện chức năng khác không?"
    //
    // Question 11:
    // Nếu người dùng nhập vào 4 thì thêm account vào 1 nhóm ngẫu nhiên
    public static void question8() {
        while (true) {
            System.out.println();
            System.out.println("========== MENU ==========");
            System.out.println("1. Tạo account");
            System.out.println("2. Tạo department");
            System.out.println("3. Thêm group vào account");
            System.out.println("4. Thêm account vào 1 group ngẫu nhiên");
            System.out.println("0. Thoát");

            int choice = inputInt("Mời bạn nhập vào chức năng muốn sử dụng: ");

//            while (true) {
//                try {
//                    System.out.print(message);
//                    return Integer.parseInt(scanner.nextLine().trim());
//                } catch (Exception e) {
//                    System.out.println("Vui lòng nhập số nguyên!");
//                }
//            }

            switch (choice) {
                case 1:
                    question5();
                    break;
                case 2:
                    question6();
                    break;
                case 3:
                    question9();
                    break;
                case 4:
                    question11();
                    break;
                case 0:
                    System.out.println("Kết thúc chương trình!");
                    return;
                default:
                    System.out.println("Mời bạn nhập lại!");
                    continue;
            }

            if (!askContinue()) {
                System.out.println("Kết thúc chương trình!");
                return;
            }
        }
    }

    // Question 9:
    // Viết method cho phép người dùng thêm group vào account theo flow sau:
    // Bước 1: In ra tên các usernames của user cho người dùng xem
    // Bước 2: Yêu cầu người dùng nhập vào username của account
    // Bước 3: In ra tên các group cho người dùng xem
    // Bước 4: Yêu cầu người dùng nhập vào tên của group
    // Bước 5: Dựa vào username và tên của group người dùng vừa chọn,
    // hãy thêm account vào group đó.
    public static void question9() {
        if (accountCount == 0) {
            System.out.println("Chưa có account nào. Vui lòng tạo account trước!");
            return;
        }

        if (groupCount == 0) {
            System.out.println("Chưa có group nào!");
            return;
        }

        printUsernames();

        Account account = null;
        while (account == null) {
            String username = inputRequiredString("Nhập username của account: ");
            account = findAccountByUsername(username);

            if (account == null) {
                System.out.println("Không tìm thấy account, vui lòng nhập lại!");
            }
        }

        printGroups();

        Group group = null;
        while (group == null) {
            String groupName = inputRequiredString("Nhập tên group: ");
            group = findGroupByName(groupName);

            if (group == null) {
                System.out.println("Không tìm thấy group, vui lòng nhập lại!");
            }
        }

        addAccountToGroup(account, group);
    }

    // Question 11:
    // Nếu người dùng nhập vào 4 thì sẽ thực hiện chức năng thêm account vào 1 nhóm ngẫu nhiên,
    // chức năng sẽ được cài đặt như sau:
    // Bước 1: In ra tên các usernames của user cho người dùng xem
    // Bước 2: Yêu cầu người dùng nhập vào username của account
    // Bước 3: Sau đó chương trình sẽ chọn ngẫu nhiên 1 group
    // Bước 4: Thêm account vào group chương trình vừa chọn ngẫu nhiên
    public static void question11() {
        if (accountCount == 0) {
            System.out.println("Chưa có account nào. Vui lòng tạo account trước!");
            return;
        }

        if (groupCount == 0) {
            System.out.println("Chưa có group nào!");
            return;
        }

        printUsernames();

        Account account = null;
        while (account == null) {
            String username = inputRequiredString("Nhập username của account: ");
            account = findAccountByUsername(username);

            if (account == null) {
                System.out.println("Không tìm thấy account, vui lòng nhập lại!");
            }
        }

        Group randomGroup = groups[rand.nextInt(groupCount)];

        System.out.println("Group ngẫu nhiên được chọn: " + randomGroup.groupName);

        addAccountToGroup(account, randomGroup);
    }

    // Helper methods
    public static int inputInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số nguyên!");
            }
        }
    }

    public static int inputPositiveInt(String message) {
        while (true) {
            int number = inputInt(message);

            if (number > 0) {
                return number;
            }

            System.out.println("Vui lòng nhập số nguyên dương!");
        }
    }

    public static double inputDouble(String message) {
        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số thực!");
            }
        }
    }

    public static String inputRequiredString(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println("Không được để trống!");
        }
    }

    public static String inputEmail(String message) {
        while (true) {
            String email = inputRequiredString(message);

            if (email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
                return email;
            }

            System.out.println("Email không hợp lệ. Ví dụ: abc@gmail.com");
        }
    }

    public static String inputUniqueUsername(String message) {
        while (true) {
            String username = inputRequiredString(message);

            if (findAccountByUsername(username) == null) {
                return username;
            }

            System.out.println("Username đã tồn tại, vui lòng nhập username khác!");
        }
    }

    public static LocalDate inputDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();

                if (!input.matches("\\d{2}/\\d{2}/\\d{4}")) {
                    System.out.println("Sai định dạng. Ví dụ đúng: 11/11/2001");
                    continue;
                }

                return LocalDate.parse(input, dateFormatter);

            } catch (Exception e) {
                System.out.println("Ngày không hợp lệ!");
            }
        }
    }

    public static PositionName inputPosition() {
        while (true) {
            System.out.println("Chọn position:");
            System.out.println("1. DEV");
            System.out.println("2. TEST");
            System.out.println("3. SCRUM_MASTER");
            System.out.println("4. PM");

            int choice = inputInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1:
                    return PositionName.DEV;
                case 2:
                    return PositionName.TEST;
                case 3:
                    return PositionName.SCRUM_MASTER;
                case 4:
                    return PositionName.PM;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng nhập 1-4!");
            }
        }
    }

    public static boolean askContinue() {
        while (true) {
            System.out.print("Bạn có muốn thực hiện chức năng khác không? (Có/Không): ");
            String answer = scanner.nextLine().trim();

            if (answer.equalsIgnoreCase("Có")
                    || answer.equalsIgnoreCase("Co")
                    || answer.equalsIgnoreCase("Yes")
                    || answer.equalsIgnoreCase("Y")) {
                return true;
            }

            if (answer.equalsIgnoreCase("Không")
                    || answer.equalsIgnoreCase("Khong")
                    || answer.equalsIgnoreCase("No")
                    || answer.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Vui lòng nhập Có hoặc Không!");
        }
    }

    public static void printUsernames() {
        System.out.println("Danh sách username:");
        for (int i = 0; i < accountCount; i++) {
            System.out.println("- " + accounts[i].username);
        }
    }

    public static void printGroups() {
        System.out.println("Danh sách group:");
        for (int i = 0; i < groupCount; i++) {
            System.out.println("- " + groups[i].groupName);
        }
    }

    public static Account findAccountByUsername(String username) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].username.equalsIgnoreCase(username)) {
                return accounts[i];
            }
        }

        return null;
    }

    public static Group findGroupByName(String groupName) {
        for (int i = 0; i < groupCount; i++) {
            if (groups[i].groupName.equalsIgnoreCase(groupName)) {
                return groups[i];
            }
        }

        return null;
    }

    public static void addAccountToGroup(Account account, Group group) {
        if (group.accounts == null) {
            group.accounts = new Account[20];
        }

        for (int i = 0; i < group.accountCount; i++) {
            if (group.accounts[i].username.equalsIgnoreCase(account.username)) {
                System.out.println("Account đã tồn tại trong group này!");
                return;
            }
        }

        if (group.accountCount >= group.accounts.length) {
            System.out.println("Group đã đầy!");
            return;
        }

        group.accounts[group.accountCount++] = account;

        System.out.println("Thêm account \"" + account.username
                + "\" vào group \"" + group.groupName + "\" thành công!");
    }

    public static void seedData() {
        Group g1 = new Group();
        g1.groupID = 1;
        g1.groupName = "Java";
        g1.accounts = new Account[20];

        Group g2 = new Group();
        g2.groupID = 2;
        g2.groupName = "React";
        g2.accounts = new Account[20];

        Group g3 = new Group();
        g3.groupID = 3;
        g3.groupName = "Database";
        g3.accounts = new Account[20];

        groups[groupCount++] = g1;
        groups[groupCount++] = g2;
        groups[groupCount++] = g3;
    }
}
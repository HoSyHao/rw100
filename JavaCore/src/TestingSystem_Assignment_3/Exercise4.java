package TestingSystem_Assignment_3;

import java.util.Scanner;

public class Exercise4 {
    public static void main(String[] args) {
        question16();
    }

    private static final Scanner scanner = new Scanner(System.in);

    // Question 1:
    // Nhập một xâu kí tự, đếm số lượng các từ trong xâu kí tự đó
    // các từ có thể cách nhau bằng nhiều khoảng trắng
    public static void question1() {
        String input = inputString("Nhập chuỗi: ");

        String[] words = input.trim().split("\\s+");

        if (words.length == 0) {
            System.out.println("Số từ: 0");
        } else {
            System.out.println("Số từ: " + words.length);
        }
    }

    // Question 2:
    // Nhập hai xâu kí tự s1, s2 nối xâu kí tự s2 vào sau xâu s1
    public static void question2() {
        String s1 = inputString("Nhập s1: ");
        String s2 = inputString("Nhập s2: ");

        //Ưu tiên: StringBuilder nhanh hơn StringBuffer vì không tốn thời gian đóng/mở các thread thao tác string

        StringBuilder s3 = new StringBuilder();
        s3.append(s1).append(s2);

        System.out.println("Kết quả: " + s3);
    }

    // Question 3:
    // Viết chương trình để người dùng nhập vào tên và kiểm tra,
    // nếu tên chưa viết hoa chữ cái đầu thì viết hoa lên
    public static void question3() {
        String name = inputString("Nhập tên: ").trim();

        if (name.isEmpty()) {
            System.out.println("Tên rỗng!");
            return;
        }

        String[] names = name.split("\\s+" );
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : names){
            stringBuilder.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).append(" ");
        }

        System.out.println("Tên sau chuẩn hóa: " + stringBuilder);
    }

    // Question 4:
    // Viết chương trình để người dùng nhập vào tên in từng ký tự trong tên của người dùng ra
    public static void question4() {
        String name = inputString("Nhập tên: ");

        for (int i = 0; i < name.length(); i++) {
            System.out.println("Ký tự thứ " + (i + 1) + " là: " + name.charAt(i));
        }
    }

    // Question 5:
    // Viết chương trình để người dùng nhập vào họ,
    // sau đó yêu cầu người dùng nhập vào tên và hệ thống sẽ in ra họ và tên đầy đủ
    public static void question5() {
        String lastName = inputString("Nhập họ: ").trim();
        String firstName = inputString("Nhập tên: ").trim();

        System.out.println("Họ tên đầy đủ: " + lastName + " " + firstName);
    }

    // Question 6:
    // Viết chương trình yêu cầu người dùng nhập vào họ và tên đầy đủ
    // và sau đó hệ thống sẽ tách ra họ, tên, tên đệm
    public static void question6() {
//        System.out.println("Nhập họ tên đầy đủ: ");
//        String fullName = scanner.nextLine();
        String fullName = inputString("Nhập họ tên đầy đủ: ");

        String[] words = fullName.trim().split("\\s+");

        if (words.length < 2) {
            System.out.println("Cần nhập ít nhất họ và tên!");
            return;
        }

        StringBuilder lastName = new StringBuilder();
        StringBuilder firstName = new StringBuilder();

        lastName.append(Character.toUpperCase(words[0].charAt(0))).append(words[0].substring(1)).append(" ");
        firstName.append(Character.toUpperCase(words[words.length - 1].charAt(0))).append(words[words.length - 1].substring(1));

        StringBuilder middleName = new StringBuilder();

        for (int i = 1; i < words.length - 1; i++) {
            middleName.append(Character.toUpperCase(words[i].charAt(0))).append(words[i].substring(1)).append(" ");
        }

        System.out.println("Họ là: " + lastName);
        System.out.println("Tên đệm là: " + middleName);
        System.out.println("Tên là: " + firstName);
    }

    // Question 7:
    // Viết chương trình yêu cầu người dùng nhập vào họ và tên đầy đủ
    // và chuẩn hóa họ và tên của họ:
    // a) Xóa dấu cách ở đầu và cuối và giữa của chuỗi người dùng nhập vào
    // b) Viết hoa chữ cái mỗi từ của người dùng
    public static void question7() {
        String fullName = inputString("Nhập họ tên đầy đủ: ");

         String[] normalized = fullName.trim().split("\\s+");
         StringBuilder result = new StringBuilder();
         for(String s : normalized){
             result.append(Character.toUpperCase(s.charAt(0)))
                     .append(s.substring(1))
                     .append(" ");
        }
//        String normalized = normalizeFullName(fullName);

        System.out.println("Họ tên sau chuẩn hóa: " + result);
    }

    // Question 8:
    // In ra tất cả các group có chứa chữ "Java"
    public static void question8() {
        Group[] groups = createSampleGroups();

        for (Group group : groups) {
            if (group.groupName.contains("Java")) {
                System.out.println(group.groupName);
            }
        }
    }

    // Question 9:
    // In ra tất cả các group "Java"
    public static void question9() {
        Group[] groups = createSampleGroups();

        for (Group group : groups) {
            if (group.groupName.equals("Java")) {
                System.out.println(group.groupName);
            }
        }
    }

    // Question 10:
    // Kiểm tra 2 chuỗi có là đảo ngược của nhau hay không.
    // Nếu có xuất ra “OK” ngược lại “KO”.
    public static void question10() {
        System.out.print("Nhập chuỗi 1: ");
        StringBuilder s1 = new StringBuilder(scanner.nextLine());
        System.out.print("Nhập chuỗi 2: ");
        StringBuilder s2 = new StringBuilder(scanner.nextLine());

        if (s1.length() != s2.length()) {
            System.out.println("KO");
            return;
        }

        String reversed = new StringBuilder(s1).reverse().toString();

        if (reversed.equals(s2.toString())) {
            System.out.println("OK");
        } else {
            System.out.println("KO");
        }

//        for (int i = 0; i < s1.length(); i++) {
//            if (s1.charAt(i) != s2.charAt(s2.length() - 1 - i)) {
//                System.out.println("KO");
//                return;
//            }
//        }
//
//        System.out.println("OK");
    }

    // Question 11:
    // Tìm số lần xuất hiện ký tự "a" trong chuỗi
    public static void question11() {
        String input = inputString("Nhập chuỗi: ");
        int count = input.replaceAll("[^a]", "").length();


//        int count = 0;
//
//        for (int i = 0; i < input.length(); i++) {
//            if (input.charAt(i) == 'a') {
//                count++;
//            }
//        }

        System.out.println("Số lần xuất hiện ký tự a: " + count);
    }

    // Question 12:
    // Đảo ngược chuỗi sử dụng vòng lặp
    public static void question12() {
        String input = inputString("Nhập chuỗi: ");

        StringBuilder reversed = new StringBuilder();

        for (int i = input.length() - 1; i >= 0; i--) {
            reversed.append(input.charAt(i));
        }

        System.out.println("Chuỗi đảo ngược: " + reversed);
    }

    // Question 13:
    // Kiểm tra một chuỗi có chứa chữ số hay không,
    // nếu có in ra false ngược lại true.
    public static void question13() {
        String input = inputString("Nhập chuỗi: ");

        if (input == null || input.isEmpty()) {
            System.out.println(true);
            return;
        }

        for (int i = 0; i < input.length(); i++) {
            if (Character.isDigit(input.charAt(i))) {
                System.out.println(false);
                return;
            }
        }

        System.out.println(true);
    }

    // Question 14:
    // Cho một chuỗi str, chuyển các ký tự được chỉ định sang một ký tự khác cho trước.
    public static void question14() {
        String str = inputString("Nhập chuỗi: ");

        char oldChar = inputChar("Nhập ký tự cần thay: ");
        char newChar = inputChar("Nhập ký tự mới: ");

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == oldChar) {
                result.append(newChar);
            } else {
                result.append(str.charAt(i));
            }
        }

        System.out.println("Kết quả: " + result);
    }

    // Question 15:
    // Đảo ngược các ký tự của chuỗi cách nhau bởi dấu cách mà không dùng thư viện.
    // Ví dụ: "    I am developer      " => "developer am I".
    public static void question15() {
//        String input = normalizeSpace(inputString("Nhập chuỗi: "));
        System.out.print("Nhập chuỗi: ");
        String input = scanner.nextLine();

        if (input.isEmpty()) {
            System.out.println("");
            return;
        }

        String[] words = input.trim().split("\\s+");

        StringBuilder result = new StringBuilder();

        for (int i = words.length - 1; i >= 0; i--) {
            String word = words[i];

            for (int j = word.length() - 1; j >= 0; j--) {
                result.append(word.charAt(j));
            }

            if (i > 0) {
                result.append(" ");
            }
        }

        System.out.println("Kết quả: " + result);
    }

    // Question 16:
    // Cho một chuỗi str và số nguyên n >= 0.
    // Chia chuỗi str ra làm các phần bằng nhau với n ký tự.
    // Nếu chuỗi không chia được thì xuất ra màn hình “KO”.
    public static void question16() {
        String str = inputString("Nhập chuỗi: ");
        int n = inputNonNegativeInt("Nhập n: ");

        if (n == 0 || str.length() % n != 0) {
            System.out.println("KO");
            return;
        }

        for (int i = 0; i < str.length(); i += n) {
            System.out.println(str.substring(i, i + n));
        }
    }

    private static String inputString(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private static int inputNonNegativeInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                int n = Integer.parseInt(scanner.nextLine().trim());

                if (n >= 0) {
                    return n;
                }

                System.out.println("n phải >= 0!");
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số nguyên!");
            }
        }
    }

    private static char inputChar(String message) {
        while (true) {
            String input = inputString(message);

            if (input.length() == 1) {
                return input.charAt(0);
            }

            System.out.println("Vui lòng nhập đúng 1 ký tự!");
        }
    }

    private String normalizeSpace(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }

    private String normalizeFullName(String input) {
        String normalized = normalizeSpace(input).toLowerCase();

        String[] words = normalized.split(" ");

        String result = "";

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            result += word.substring(0, 1).toUpperCase() + word.substring(1);

            if (i < words.length - 1) {
                result += " ";
            }
        }

        return result;
    }

    private static Group[] createSampleGroups() {
        Group g1 = new Group();
        g1.groupID = 1;
        g1.groupName = "Java";

        Group g2 = new Group();
        g2.groupID = 2;
        g2.groupName = "Java Core";

        Group g3 = new Group();
        g3.groupID = 3;
        g3.groupName = "React";

        return new Group[]{g1, g2, g3};
    }
}
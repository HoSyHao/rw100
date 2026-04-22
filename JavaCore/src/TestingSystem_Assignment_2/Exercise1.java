package TestingSystem_Assignment_2;

public class Exercise1 {
//    IF
//    Question 1:
//    Kiểm tra account thứ 2
//    Nếu không có phòng ban (tức là department == null) thì sẽ in ra text
//      "Nhân viên này chưa có phòng ban"
//    Nếu không thì sẽ in ra text "Phòng ban của nhân viên này là …"
    public void question1(Account account) {
        if(account.department == null){
            System.out.println("Nhân viên này chưa có phòng ban");
        } else {
            System.out.println("Phòng ban của nhân viên này là " + account.department.departmentName);
        }
    }
//    Question 2:
//    Kiểm tra account thứ 2
//    Nếu không có group thì sẽ in ra text "Nhân viên này chưa có group"
//    Nếu có mặt trong 1 hoặc 2 group thì sẽ in ra text "Group của nhân viên này là Java Fresher, C# Fresher"
//    Nếu có mặt trong 3 Group thì sẽ in ra text "Nhân viên này là người quan trọng, tham gia nhiều group"
//    Nếu có mặt trong 4 group trở lên thì sẽ in ra text "Nhân viên này là người hóng chuyện, tham gia tất cả các group"
    public void question2(Account account) {
        int groups = account.groups.length;

        if(groups == 0){
            System.out.println("Nhân viên này chưa có group");
        }
        else if (groups <= 2) {
            System.out.print("Group của nhân viên này là ");
            for (int i = 0; i < groups; i++) {
                System.out.print(account.groups[i].groupName);
                if (i < groups - 1){
                    System.out.print(", ");
                }
            }
            System.out.println();
        }
        else if (groups == 3) {
            System.out.println("Nhân viên này là người quan trọng, tham gia nhiều group");
        } else {
            System.out.println("Nhân viên này là người hóng chuyện, tham gia tất cả các group");
        }
    }

//    Question 3:
//    Sử dụng toán tử ternary để làm Question 1
    public void question3(Account account) {
        String res = (account.department == null)
                ? "Nhân viên này chưa có phòng ban"
                : "Phòng ban của nhân viên này là " + account.department.departmentName;
        System.out.println(res);
    }

//    Question 4:
//    Sử dụng toán tử ternary để làm yêu cầu sau:
//    Kiểm tra Position của account thứ 1
//    Nếu Position = Dev thì in ra text "Đây là Developer"
//    Nếu không phải thì in ra text "Người này không phải là Developer"
    public void question4(Account account) {
        String res = (account.position.positionName == PositionName.DEV || "DEV".equals(account.position.positionName))
                ? "Đây là Developer"
                : "Người này không phải là Developer";
        System.out.println(res);
    }

//    SWITCH CASE
//    Question 5:
//    Lấy ra số lượng account trong nhóm thứ 1 và in ra theo format sau: Nếu số lượng account = 1 thì in ra "Nhóm có một thành viên"
//    Nếu số lượng account = 2 thì in ra "Nhóm có hai thành viên"
//    Nếu số lượng account = 3 thì in ra "Nhóm có ba thành viên"
//    Còn lại in ra "Nhóm có nhiều thành viên"
    public void question5(Group group) {
        int accounts = group.accounts.length;
        switch (accounts) {
            case 1:
                System.out.println("Nhóm có một thành viên");
                break;
            case 2:
                System.out.println("Nhóm có hai thành viên");
                break;
            case 3:
                System.out.println("Nhóm có ba thành viên");
                break;
            default:
                System.out.println("Nhóm có nhiều thành viên");
        }
    }
//    Question 6:
//    Sử dụng switch case để làm lại Question 2
    public void question6(Account account) {
        int groups = account.groups.length;
        switch  (groups) {
            case 0:
                System.out.println("Nhân viên này chưa có group");
                break;
            case 1:
            case 2:
                System.out.print("Group của nhân viên này là ");
                for (int i = 0; i < groups; i++) {
                    System.out.print(account.groups[i].groupName);
                    if (i < groups - 1){
                        System.out.print(", ");
                    }
                }
                System.out.println();
                break;
            case 3:
                System.out.println("Nhân viên này là người quan trọng, tham gia nhiều group");
                break;
            default:
                System.out.println("Nhân viên này là người hóng chuyện, tham gia tất cả các group");
        }
    }

//    Question 7:
//    Sử dụng switch case để làm lại Question 4
    public void question7(Account account) {
        switch (account.department.departmentName) {
            case "DEV":
                System.out.println("Đây là Developer");
                break;
            default:
                System.out.println("Người này không phải là Developer");
        }
    }

//    FOREACH
//    Question 8:
//    In ra thông tin các account bao gồm: Email, FullName và tên phòng ban của họ
    public void question8(Account[] accounts) {
        for(Account account : accounts){
            System.out.println("Email: " + account.email);
            System.out.println("FullName: " + account.fullName);
            System.out.println("Department: " + account.department.departmentName);
        }
    }

//    Question 9:
//    In ra thông tin các phòng ban bao gồm: id và name
    public void question9(Department[] departments) {
        for(Department department : departments){
            System.out.println("ID: " + department.departmentID);
            System.out.println("Name: " + department.departmentName);
        }
    }

//            FOR
//    Question 10:
//    In ra thông tin các account bao gồm: Email, FullName và tên phòng ban của họ theo định dạng như sau:
//    Thông tin account thứ 1 là:
//    Email: NguyenVanA@gmail.com
//    Full name: Nguyễn Văn A
//    Phòng ban: Sale
//
//    Thông tin account thứ 2 là:
//    Email: NguyenVanB@gmail.com
//    Full name: Nguyễn Văn B
//    Phòng ban: Marketting
    public void question10(Account[] accounts) {
        for (int i = 0; i < accounts.length; i++) {
            System.out.println("Thông tin account thứ " + (i + 1) + " là:");
            System.out.println("Email: " + accounts[i].email);
            System.out.println("FullName: " + accounts[i].fullName);
            System.out.println("Department: " + accounts[i].department.departmentName);
        }
    }

//    Question 11:
//    In ra thông tin các phòng ban bao gồm: id và name theo định dạng sau:
//    Thông tin department thứ 1 là:
//    Id: 1
//    Name: Sale
//    Thông tin department thứ 2 là:
//    Id: 2
//    Name: Marketing
    public void question11(Department[] departments) {
        for (int i = 0; i < departments.length; i++) {
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("ID: " + departments[i].departmentID);
            System.out.println("Name: " + departments[i].departmentName);
        }
    }

//    Question 12:
//    Chỉ in ra thông tin 2 department đầu tiên theo định dạng như Question 10
    public void question12(Department[] departments) {
        for (int i = 0; i < departments.length; i++) {
            if (i == 2) {
                break;
            }
            System.out.println("Thông tin department thứ " + (i + 1) + " là:");
            System.out.println("ID: " + departments[i].departmentID);
            System.out.println("Name: " + departments[i].departmentName);
        }
    }

//    Question 13:
//    In ra thông tin tất cả các account ngoại trừ account thứ 2
    public void question13(Account[] accounts) {
        for (int i = 0; i < accounts.length; i++) {
            if (i == 1) {
                continue;
            }
            System.out.println("Email: " + accounts[i].email);
            System.out.println("FullName: " + accounts[i].fullName);
        }
    }

//    Question 14:
//    In ra thông tin tất cả các account có id < 4
    public void question14(Account[] accounts) {
        for (Account account : accounts) {
            if (account.accountID < 4){
                System.out.println("Email: " + account.email);
                System.out.println("FullName: " + account.fullName);
            }
        }
    }

//    Question 15:
//    In ra các số chẵn nhỏ hơn hoặc bằng 20
    public void question15() {
        for(int i = 0; i <= 20; i += 2){
            System.out.println(i);
        }
    }

//    WHILE
//    Question 16:
//    Làm lại các Question ở phần FOR bằng cách sử dụng WHILE kết hợp với lệnh break, continue
    public void question16(Account[] accounts, Department[] departments) {
        int i = 0;
        while (i < accounts.length) {
            System.out.println(accounts[i].fullName);
            i++;
        }

        int j = 0;
        while (j < departments.length) {
            if (j == 2){
                break;
            }
            System.out.println(departments[j].departmentID);
            j++;
        }
    }

//    DO-WHILE
//    Question 17:
//    Làm lại các Question ở phần FOR bằng cách sử dụng DO-WHILE kết hợp với lệnh break, continue
    public void question17(Account[] accounts, Department[] departments) {
        int i =  0;
        do {
            System.out.println(accounts[i].fullName);
            i++;
        } while (i < accounts.length);

        int j = 0;
        do {
            if(j == 2){
                break;
            }
            System.out.println(departments[j].departmentID);
            j++;
        } while (j < departments.length);
    }
}

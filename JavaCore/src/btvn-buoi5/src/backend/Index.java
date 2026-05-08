package backend;

import entity.Account;
import entity.Department;
import entity.Position;
import enums.PositionName;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Index {
    Scanner sc = new Scanner(System.in);
    List<Position> positions = new ArrayList<>();
    List<Department> departments = new ArrayList<>();
    List<Account> accounts = new ArrayList<>();
    public void genMenu(){
        while(true){
            System.out.println();
            System.out.println("========== MENU ==========");
            System.out.println("Vui lòng chọn chức năng!!!");
            System.out.println("1. thêm phòng ban");
            System.out.println("2. thêm chức vụ");
            System.out.println("3. thêm account");
            System.out.println("4. xem ds phòng ban");
            System.out.println("5. xem ds chức vụ");
            System.out.println("6. xem ds account");
            System.out.println("7. tìm kiếm account theo tên phòng ban");
            System.out.println("8. tìm kiếm account theo tên chức vụ");
            System.out.println("9. xóa account theo fullname");
            System.out.println("0. Thoát");
            String choose = sc.nextLine();
            switch (choose){
                case "1":
                    addDepartment();
                    break;
                case "2":
                    addPositon();
                    break;
                case "3":
                    addAccount();
                    break;
                case "4":
                    showDepartment();
                    break;
                case "5":
                    showPositon();
                    break;
                case "6":
                    showAccount();
                    break;
                case "7":
                    searchAccountByDepartment();
                    break;
                case "8":
                    searchAccountByPositon();
                    break;
                case "9":
                    removeAccountByFullname();
                    break;
                case "0":
                    System.out.println("Thoát khỏi chương trình");
                    return;
                default:
                    System.out.println("Nhập sai vui lòng nhập lại!!!");

            }
        }
    }

//        1. thêm phòng ban
    public void addDepartment(){
        System.out.println("Nhập tên phòng ban");
        String departmentName = sc.nextLine();

        departments.add(new Department(departmentName));
        System.out.println("Tạo phòng ban " + departmentName + " thành công!!!!!!");
    }
//        2. thêm chức vụ
    public void addPositon(){
        if (!positions.isEmpty()) {
            return;
        }
        //Lấy tất cả PositionName
        PositionName[] positionNames = PositionName.values();

        //Lặp theo độ dài của PositionName để tạo Position
        for(int i = 0; i < positionNames.length; i++){
            positions.add(new Position(positionNames[i]));
        }
        System.out.println("Đã tạo thành công " + positions.size() + " chức vụ.");
    }

//        3. thêm account (phải có khóa ngoại department và position)
//        -
//                createDate là now
    public void addAccount(){
        if(departments.isEmpty()){
            System.out.println("Chưa có phòng ban nào, tạo phòng ban trước khi tạo account!!!!");
            addDepartment();
        }
        if(positions.isEmpty()){
            System.out.println("Chưa có chức vụ nào, tạo chức vụ trước khi tạo account!!!!");
            addPositon();
        }

        System.out.println("======Tạo account=======");
        System.out.print("Nhập email: ");
        String email = sc.nextLine();
        System.out.print("Nhập username: ");
        String username = sc.nextLine();
        System.out.print("Nhập firstName: ");
        String firstName = sc.nextLine();
        System.out.print("Nhập lastName: ");
        String lastName = sc.nextLine();

        Department department = chooseDepartment();

        Position position = choosePosition();

        Account account = new Account(email, username, firstName, lastName, department, position);
        accounts.add(account);
        System.out.println("Tạo account " + username + " thành công.");
    }

//        4. xem ds phòng ban
    public void showDepartment(){
        if(departments.isEmpty()){
            System.out.println("Chưa tạo phòng ban.");
            return;
        }
        System.out.println("Danh sách chức vụ:");

        for(Department department : departments){
            System.out.println(department);
        }
    }

//        5. xem ds chức vụ
    public void showPositon(){
        if(positions.isEmpty()){
            System.out.println("Chưa tạo chức vụ.");
            return;
        }

        System.out.println("Danh sách chức vụ:");

        for(Position position : positions){
            System.out.println(position);
        }
    }

//        6. xem ds account (id, fullname, email, username,
//                tên phòng ban, tên chức vụ, ngày tạp)
    public void showAccount(){
        if(accounts.isEmpty()){
            System.out.println("Chưa tạo account.");
            return;
        }

        System.out.println("Danh sách account:");

        for(Account account : accounts){
            System.out.println(account);
        }
    }
//        7. tìm kiếm account theo tên phòng ban
    public void searchAccountByDepartment(){
        String departmentName = chooseDepartment().getDepartmentName();

        boolean check = false;
        for(Account account : accounts){
            if(departmentName.equals(account.getDepartment().getDepartmentName())){
                System.out.println(account);
                check = true;
            }
        }

        if(!check){
            System.out.println("Phòng ban này chưa có accounts nào ");
        }
    }
//        8. tìm kiếm account theo tên chức vụ
    public void searchAccountByPositon(){
        PositionName positionName = choosePosition().getPositionName();

        boolean check = false;
        for(Account account : accounts){
            if(positionName.equals(account.getPosition().getPositionName())){
                System.out.println(account);
                check = true;
            }
        }

        if(!check){
            System.out.println("Chức vụ này này chưa giao cho accounts nào ");
        }
    }
//        9. xóa account theo fullname
    public void removeAccountByFullname(){
        System.out.println("Nhập fullname cần xóa:");
        String fullname = sc.nextLine();
        List<Account> accDels = new ArrayList<>();
        for(Account account : accounts){
            if(account.getFullName().equals(fullname)){
                accDels.add(account);
            }
        }
        boolean check = accounts.removeAll(accDels);
        if(check){
            System.out.println("Xóa thành công " + accDels.size() + " acc" );
        } else {
            System.out.println("fullname không tồn tại.");
        }
    }

    public Department chooseDepartment(){

        // Nếu chỉ có 1 department thì gán luôn
        if(departments.size() == 1){
            Department department = departments.get(0);
            System.out.println("Chọn phòng ban: " + department.getDepartmentName());
            return department;
        } else {
            int departmentChoose;
            while (true){
                System.out.println("Chọn phòng ban: ");
                for (int i = 0; i < departments.size(); i++) {
                    System.out.println((i+1) + ". " + departments.get(i).getDepartmentName());
                }
                try {
                    departmentChoose = Integer.parseInt(sc.nextLine());
                    // kiểm tra phạm vi
                    if (departmentChoose < 1
                            || departmentChoose > departments.size()) {

                        System.out.println("Lựa chọn không hợp lệ!");
                        continue;
                    }
                    return departments.get(departmentChoose - 1);
                } catch (Exception  e) {
                    System.out.println("Vui lòng nhập số hợp lệ!");
                }
            }
        }
    }

    public Position choosePosition(){
        while (true) {
            System.out.println("Chọn chức vụ: ");
            for (int i = 0; i < positions.size(); i++) {
                System.out.println((i + 1) + ". " + positions.get(i).getPositionName());
            }
            try {
                int positionChoose = Integer.parseInt(sc.nextLine());
                // kiểm tra phạm vi
                if (positionChoose < 1
                        || positionChoose > positions.size()) {

                    System.out.println("Lựa chọn không hợp lệ!");
                    continue;
                }
                return positions.get(positionChoose - 1);
            } catch (Exception e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }
}

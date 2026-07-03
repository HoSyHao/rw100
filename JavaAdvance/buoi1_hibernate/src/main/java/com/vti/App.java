package com.vti;

import com.vti.entity.*;
import com.vti.enums.PositionName;
import com.vti.repository.*;
import com.vti.repository.impl.*;

import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("--- Bắt đầu test toàn bộ Repository ---");
        
        // Khởi tạo các Repository
        IDepartmentRepository departmentRepo = new DepartmentRepositoryImpl();
        IPositionRepository positionRepo = new PositionRepositoryImpl();
        IAccountRepository accountRepo = new AccountRepositoryImpl();
        IGroupRepository groupRepo = new GroupRepositoryImpl();
        IGroupAccountRepository groupAccountRepo = new GroupAccountRepositoryImpl();

        long timeMillis = System.currentTimeMillis();

        try {
            // 1. Tạo Department
            System.out.println("\n[1] Tạo Department...");
            Department dept = new Department();
            dept.setName("Phòng ban " + timeMillis);
            departmentRepo.create(dept);
            System.out.println("-> OK: " + dept.getName());

            // 2. Tạo Position
            System.out.println("\n[2] Tạo Position...");
            Position pos = new Position();
            pos.setName(PositionName.DEV);
            positionRepo.create(pos);
            System.out.println("-> OK: " + pos.getName());

            // 3. Tạo Account
            System.out.println("\n[3] Tạo Account...");
            Account acc = new Account();
            acc.setUsername("user" + timeMillis);
            acc.setFullName("Nguyễn Văn " + timeMillis);
            acc.setEmail("user" + timeMillis + "@gmail.com");
            acc.setDepartment(dept);
            acc.setPosition(pos);
            accountRepo.create(acc);
            System.out.println("-> OK: " + acc.getUsername());

            // 4. Tạo Group
            System.out.println("\n[4] Tạo Group...");
            Group grp = new Group();
            grp.setGroupName("Group " + timeMillis);
            groupRepo.create(grp);
            System.out.println("-> OK: " + grp.getGroupName());

            // 5. Tạo GroupAccount
            System.out.println("\n[5] Tạo GroupAccount...");
            GroupAccount ga = new GroupAccount();
            ga.setAccount(acc);
            ga.setGroup(grp);
            groupAccountRepo.create(ga);
            System.out.println("-> OK: Đã thêm account vào group");

            // 6. Test Read (FindAll) cho toàn bộ
            System.out.println("\n[6] Kiểm tra dữ liệu (FindAll)...");
            System.out.println("- Số lượng Department: " + departmentRepo.findAll().size());
            System.out.println("- Số lượng Position: " + positionRepo.findAll().size());
            System.out.println("- Số lượng Account: " + accountRepo.findAll().size());
            System.out.println("- Số lượng Group: " + groupRepo.findAll().size());
            System.out.println("- Số lượng GroupAccount: " + groupAccountRepo.findAll().size());

            // 7. Test Update Account
            System.out.println("\n[7] Test Update Account...");
            acc.setFullName("Update FullName " + timeMillis);
            accountRepo.update(acc);
            System.out.println("-> Cập nhật xong tên thành: " + accountRepo.findById(acc.getId()).getFullName());

            // 8. Test Delete (Xoá ngược từ con lên cha để tránh lỗi khoá ngoại)
            System.out.println("\n[8] Test Delete...");
            
            System.out.println("- Xoá GroupAccount...");
            groupAccountRepo.delete(ga.getId());
            
            System.out.println("- Xoá Group...");
            groupRepo.delete(grp.getId());
            
            System.out.println("- Xoá Account...");
            accountRepo.delete(acc.getId());
            
            System.out.println("- Xoá Position...");
            positionRepo.delete(pos.getId());
            
            System.out.println("- Xoá Department...");
            departmentRepo.delete(dept.getId());

            System.out.println("-> Đã xoá toàn bộ dữ liệu test thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\n--- Kết thúc test ---");
    }
}
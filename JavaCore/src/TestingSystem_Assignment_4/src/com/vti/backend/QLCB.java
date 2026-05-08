package TestingSystem_Assignment_4.src.com.vti.backend;

import TestingSystem_Assignment_4.src.com.vti.Enum.GioiTinh;
import TestingSystem_Assignment_4.src.com.vti.entity.CanBo;
import TestingSystem_Assignment_4.src.com.vti.entity.CongNhan;
import TestingSystem_Assignment_4.src.com.vti.entity.KySu;
import TestingSystem_Assignment_4.src.com.vti.entity.NhanVien;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QLCB {
//    a)	Thêm mới cán bộ.
//    b)	Tìm kiếm theo họ tên.
//    c)	Hiện thị thông tin về danh sách các cán bộ.
//    d)	Nhập vào tên của cán bộ và delete cán bộ đó
//    e)	Thoát khỏi chương trình.

    public void qlcb() {
        Scanner sc = new Scanner(System.in);
        List<CanBo> canBos = new ArrayList<>();
        while (true) {
            System.out.println("==========Mời chọn chức năng============");
            System.out.println("a)\tThêm mới cán bộ.");
            System.out.println("b)\tTìm kiếm theo họ tên.");
            System.out.println("c)\tHiện thị thông tin về danh sách các cán bộ.");
            System.out.println("d)\tNhập vào tên của cán bộ và delete cán bộ đó");
            System.out.println("e)\tThoát khỏi chương trình.");
            String choose = sc.nextLine();
            switch (choose) {
                case "a":
                    System.out.println("Chức năng thêm mới cán bộ.");
                    System.out.println("Nhập họ tên:");
                    String hoTen = sc.nextLine();
                    System.out.println("Nhập tuổi:");
                    int tuoi = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Nhập địa chỉ:");
                    String diaChi = sc.nextLine();
                    System.out.println("Nhập giới tính: 1. Nam, 2. Nữ, Khác. Khác.");
                    String sexChoose = sc.nextLine();
                    GioiTinh gioiTinh;
                    switch(sexChoose){
                        case "1":
                            gioiTinh = GioiTinh.MALE;
                            break;
                        case "2":
                            gioiTinh = GioiTinh.FEMALE;
                            break;
                        default:
                            gioiTinh = GioiTinh.MALE;
                    }

                    System.out.println("==========Mời chọn cán bộ============");
                    System.out.println("1)\tCông nhân.");
                    System.out.println("2)\tKỹ sư");
                    System.out.println("Khác)\tNhân viên");
                    String cbChoose  = sc.nextLine();
                    switch (cbChoose) {
                        case "1":
                            System.out.println("Nhập bậc: ");
                            int bac = sc.nextInt();
                            sc.nextLine();
                            CanBo congNhan = new CongNhan(hoTen, tuoi, gioiTinh ,diaChi, bac);

                            canBos.add(congNhan);
                            System.out.println("Đã tạo công nhân thành công!!");
                            break;
                        case "2":
                            System.out.println("Nhập nghành: ");
                            String nganh = sc.nextLine();
                            CanBo kySu = new KySu(hoTen, tuoi, gioiTinh ,diaChi, nganh);

                            canBos.add(kySu);
                            System.out.println("Đã tạo kỹ sư thành công!!");
                            break;
                        default:
                            System.out.println("Nhập công việc: ");
                            String congViec = sc.nextLine();
                            CanBo nhanVien = new NhanVien(hoTen, tuoi, gioiTinh ,diaChi, congViec);

                            canBos.add(nhanVien);
                            System.out.println("Đã tạo nhân viên thành công!!");
                    }
                    System.out.println("======================");
                    break;
                case "b":
                    System.out.println("Chức năng Tìm kiếm theo họ tên.");
                    System.out.println("Nhập tên cần tìm");
                    String name = sc.nextLine();
                    boolean check = false;
                    for(CanBo canBo : canBos){
                        if(canBo.getHoTen().equals(name)){
                            System.out.println(canBo);
                            check = true;
                        }
                    }

                    if (!check){
                        System.out.println("Tên này không tồn tại");
                    }
                    System.out.println("============================================");
                    break;
                case "c":
                    System.out.println("Chức năng Hiện thị thông tin về danh sách các cán bộ.");
                    for (CanBo canBo : canBos) {
                        System.out.println(canBo);
                    }
                    System.out.println("==================================");
                    break;
                case "d":
                    System.out.println("Chức năng Nhập vào tên của cán bộ và delete cán bộ đó.");
                    System.out.println("Nhập tên cần xóa");
                    String tenCb =  sc.nextLine();
                    //C1:
//                    canBos.stream()
//                            .filter(canBo -> canBo.getHoTen().equals(tenCb))
//                            .forEach(System.out::println);
                    //C2:
//                    canBos.removeIf(canBo -> canBo.getHoTen().equals(tenCb));
                    List<CanBo> deletes = new ArrayList<>();
                    for (CanBo canBo : canBos) {
                        if (canBo.getHoTen().equals(tenCb)) {
                            deletes.add(canBo);
                        }
                    }
                    boolean checkDelete = canBos.removeAll(deletes);
                    if (checkDelete){
                        System.out.println("Xóa thành công!!");
                    }else{
                        System.out.println("Tên này không tồn tại trong hệ thống!!");
                    }

                    System.out.println("==================================");
                    break;
                case "e":
                    System.out.println("Thoát khỏi chương trình");
                    return;
                default:
                    System.out.println("Nhập sai vui lòng nhập lại!!!");

            }
        }
    }
}


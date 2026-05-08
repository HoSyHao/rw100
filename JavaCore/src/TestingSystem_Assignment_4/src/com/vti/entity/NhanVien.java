package TestingSystem_Assignment_4.src.com.vti.entity;

import TestingSystem_Assignment_4.src.com.vti.Enum.GioiTinh;

public class NhanVien extends CanBo {
    private String congViec;

    public NhanVien(String hoTen, int tuoi, GioiTinh gioiTinh, String diaChi, String congViec) {
        super(hoTen, tuoi, gioiTinh, diaChi);
        this.congViec = congViec;
    }

    public String getCongViec() {
        return congViec;
    }

    public void setCongViec(String congViec) {
        this.congViec = congViec;
    }

    @Override
    public String toString() {
        return "==Nhân viên== Họ tên: " + super.getHoTen() + ", Tuổi: "+ super.getTuoi() +
                ", Giới tính: " + super.getGioiTinh() + ", Địa chỉ: " + super.getDiaChi() +
                ", Công việc: " + this.congViec;
    }
}

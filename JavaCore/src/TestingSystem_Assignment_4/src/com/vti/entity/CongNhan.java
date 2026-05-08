package TestingSystem_Assignment_4.src.com.vti.entity;

import TestingSystem_Assignment_4.src.com.vti.Enum.GioiTinh;

public class CongNhan extends CanBo {
    private int bac;

    public CongNhan(String hoTen, int tuoi, GioiTinh gioiTinh, String diaChi, int bac) {
        super(hoTen, tuoi, gioiTinh, diaChi);
        this.bac = bac;
    }

    public int getBac() {
        return bac;
    }

    public void setBac(int bac) {
        this.bac = bac;
    }

    @Override
    public String toString() {
        return "==Công nhân== Họ tên: " + super.getHoTen() + ", Tuổi: "+ super.getTuoi() +
                ", Giới tính: " + super.getGioiTinh() + ", Địa chỉ: " + super.getDiaChi() +
                ", Bậc: " + this.bac;
    }
}

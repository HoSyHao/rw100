package TestingSystem_Assignment_4.src.com.vti.entity;

import TestingSystem_Assignment_4.src.com.vti.Enum.GioiTinh;

public class KySu extends CanBo {
    private String nganh;

    public KySu(String hoTen, int tuoi, GioiTinh gioiTinh, String diaChi, String nganh) {
        super(hoTen, tuoi, gioiTinh, diaChi);
        this.nganh = nganh;
    }

    public String getNganh() {
        return nganh;
    }

    public void setNganh(String nghanh) {
        this.nganh = nghanh;
    }

    @Override
    public String toString() {
        return "==Kỹ sư== Họ tên: " + super.getHoTen() + ", Tuổi: "+ super.getTuoi() +
                ", Giới tính: " + super.getGioiTinh() + ", Địa chỉ: " + super.getDiaChi() +
                ", Ngành: " + this.nganh;
    }
}

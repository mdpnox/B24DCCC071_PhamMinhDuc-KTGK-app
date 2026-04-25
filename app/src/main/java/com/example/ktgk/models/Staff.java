package com.example.ktgk.models;

public class Staff extends Employee {
    private int soNgayCong;

    public Staff(String maNV, String hoTen, double luongCoBan, int soNgayCong) {
        super(maNV, hoTen, luongCoBan);
        setSoNgayCong(soNgayCong);
    }

    public int getSoNgayCong() { return soNgayCong; }

    public void setSoNgayCong(int soNgayCong) {
        if (soNgayCong >= 0 && soNgayCong <= 26) {
            this.soNgayCong = soNgayCong;
        } else {
            throw new IllegalArgumentException("Số ngày công phải từ 0 đến 26");
        }
    }

    @Override
    public double tinhLuong() {
        return getLuongCoBan() * soNgayCong / 26.0;
    }

    @Override
    public String hienThiThongTin() {
        return "Số ngày công: " + soNgayCong;
    }

    @Override
    public String getLoaiNhanVien() { return "Staff"; }
}
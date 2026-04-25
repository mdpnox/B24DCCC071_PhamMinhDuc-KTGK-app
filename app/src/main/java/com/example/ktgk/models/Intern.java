package com.example.ktgk.models;

public class Intern extends Employee {
    private int soGioLam;

    public Intern(String maNV, String hoTen, double luongCoBan, int soGioLam) {
        super(maNV, hoTen, luongCoBan);
        setSoGioLam(soGioLam);
    }

    public int getSoGioLam() { return soGioLam; }

    public void setSoGioLam(int soGioLam) {
        if (soGioLam >= 0) this.soGioLam = soGioLam;
        else throw new IllegalArgumentException("Số giờ làm phải >= 0");
    }

    @Override
    public double tinhLuong() {
        return soGioLam * 30000.0;
    }

    @Override
    public String hienThiThongTin() {
        return "Số giờ làm: " + soGioLam;
    }

    @Override
    public String getLoaiNhanVien() { return "Intern"; }
}
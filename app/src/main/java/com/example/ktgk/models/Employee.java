package com.example.ktgk.models;
import java.io.Serializable;

public abstract class Employee implements Serializable {
    private String maNV;
    private String hoTen;
    private double luongCoBan;

    public Employee(String maNV, String hoTen, double luongCoBan) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        setLuongCoBan(luongCoBan);
    }

    public String getMaNV() { return maNV; }
    public String getHoTen() { return hoTen; }
    public double getLuongCoBan() { return luongCoBan; }

    public void setMaNV(String maNV) { this.maNV = maNV; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setLuongCoBan(double luongCoBan) {
        if (luongCoBan > 0) {
            this.luongCoBan = luongCoBan;
        } else {
            throw new IllegalArgumentException("Lương cơ bản phải > 0");
        }
    }

    public abstract double tinhLuong();
    public abstract String hienThiThongTin();
    public abstract String getLoaiNhanVien();
}
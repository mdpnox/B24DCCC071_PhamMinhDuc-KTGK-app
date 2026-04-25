package com.example.ktgk.models;

public class Manager extends Employee {
    private double phuCapChucVu;

    public Manager(String maNV, String hoTen, double luongCoBan, double phuCapChucVu) {
        super(maNV, hoTen, luongCoBan);
        setPhuCapChucVu(phuCapChucVu);
    }

    public double getPhuCapChucVu() { return phuCapChucVu; }

    public void setPhuCapChucVu(double phuCapChucVu) {
        if (phuCapChucVu >= 0) this.phuCapChucVu = phuCapChucVu;
        else throw new IllegalArgumentException("Phụ cấp phải >= 0");
    }

    @Override
    public double tinhLuong() {
        return getLuongCoBan() + phuCapChucVu;
    }

    @Override
    public String hienThiThongTin() {
        return "Phụ cấp: " + phuCapChucVu;
    }

    @Override
    public String getLoaiNhanVien() { return "Manager"; }
}
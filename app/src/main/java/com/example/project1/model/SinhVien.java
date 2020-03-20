package com.example.project1.model;

public class SinhVien {
    private String ten, ma;
    private double diem;

    public SinhVien() {
    }

    public SinhVien(String ten, String ma, double diem) {
        this.ten = ten;
        this.ma = ma;
        this.diem = diem;
    }

    public SinhVien(String ten, double diem) {
        this.ten = ten;
        this.diem = diem;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public double getDiem() {
        return diem;
    }

    public void setDiem(double diem) {
        this.diem = diem;
    }
}

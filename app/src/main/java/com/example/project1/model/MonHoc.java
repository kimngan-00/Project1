package com.example.project1.model;

public class MonHoc  {
    private String tenMH;
    private String maMH;

    public MonHoc(String tenMH, String maMH) {
        this.tenMH = tenMH;
        this.maMH = maMH;
    }

    public MonHoc(String tenMH) {
        this.tenMH = tenMH;
    }

    public MonHoc() {
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }
}

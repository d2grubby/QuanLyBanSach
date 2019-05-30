package com.example.qlnhasach;

public class BinhLuanModel {
    public String tenKH;
    public String noidung;
    public int danhgia;

    public BinhLuanModel(String tenKH, String noidung, int danhgia) {
        this.tenKH = tenKH;
        this.noidung = noidung;
        this.danhgia = danhgia;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public int getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(int danhgia) {
        this.danhgia = danhgia;
    }
}

package com.example.qlnhasach;

public class ThanhToanModel {
    public String tenKH;
    public String email;
    public String diaChi;
    public int SDT;
    public String ngayDH;
    public String ngayGH;

    public ThanhToanModel(String tenKH, String email, String diaChi, int SDT, String ngayDH, String ngayGH) {
        this.tenKH = tenKH;
        this.email = email;
        this.diaChi = diaChi;
        this.SDT = SDT;
        this.ngayDH = ngayDH;
        this.ngayGH = ngayGH;
    }
}

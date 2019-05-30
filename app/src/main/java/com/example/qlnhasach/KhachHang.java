package com.example.qlnhasach;

public class KhachHang {
    public int idKH;
    public String tenKH;
    public String ngayDK;
    public String tenDN;
    public String matKhau;
    public String email;
    public String diaChi;
    public int sdt;

    public KhachHang() {
    }

    public KhachHang(int idKH, String tenKH, String ngayDK, String tenDN, String matKhau, String email, String diaChi, int sdt) {
        this.idKH = idKH;
        this.tenKH = tenKH;
        this.ngayDK = ngayDK;
        this.tenDN = tenDN;
        this.matKhau = matKhau;
        this.email = email;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }

    public int getIdKH() {
        return idKH;
    }

    public void setIdKH(int idKH) {
        this.idKH = idKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getNgayDK() {
        return ngayDK;
    }

    public void setNgayDK(String ngayDK) {
        this.ngayDK = ngayDK;
    }

    public String getTenDN() {
        return tenDN;
    }

    public void setTenDN(String tenDN) {
        this.tenDN = tenDN;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }
}

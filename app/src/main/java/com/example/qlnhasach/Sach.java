package com.example.qlnhasach;

public class Sach {
    public int idSach;
    public int idTG;
    public int idTheLoai;
    public int idNXB;
    public String tenSach;
    public String moTa;
    public byte[] anhBia;
    public int giaTien;
    public int soLuongCon;

    public Sach(int idSach, int idTG, int idTheLoai, int idNXB, String tenSach,String moTa, byte[] anhBia, int giaTien, int soLuongCon) {
        this.idSach = idSach;
        this.idTG = idTG;
        this.idTheLoai = idTheLoai;
        this.idNXB = idNXB;
        this.tenSach = tenSach;
        this.moTa = moTa;
        this.anhBia = anhBia;
        this.giaTien = giaTien;
        this.soLuongCon = soLuongCon;
    }
}

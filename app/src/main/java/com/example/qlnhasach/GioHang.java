package com.example.qlnhasach;

public class GioHang {
    public int idsach;
    public String tensach;
    public int giatien;
    public byte[] hinhanh;
    public int soluongsach;

    public GioHang(int idsach, String tensach, int giatien, byte[] hinhanh, int soluongsach) {
        this.idsach = idsach;
        this.tensach = tensach;
        this.giatien = giatien;
        this.hinhanh = hinhanh;
        this.soluongsach = soluongsach;
    }

    public int getIdsach() {
        return idsach;
    }

    public void setIdsach(int idsach) {
        this.idsach = idsach;
    }

    public String getTensach() {
        return tensach;
    }

    public void setTensach(String tensach) {
        this.tensach = tensach;
    }

    public int getGiatien() {
        return giatien;
    }

    public void setGiatien(int giatien) {
        this.giatien = giatien;
    }

    public byte[] getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getSoluongsach() {
        return soluongsach;
    }

    public void setSoluongsach(int soluongsach) {
        this.soluongsach = soluongsach;
    }
}

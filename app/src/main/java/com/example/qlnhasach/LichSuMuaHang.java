package com.example.qlnhasach;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class LichSuMuaHang extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<HoaDon> list;
    AdapterHoaDon adapter;

    TextView txtThongBao;

    int idUser = DangNhap.mangUserType.get(0).getIdUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_mua_hang);
        addControls();
        readData();
    }
    private void addControls(){
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterHoaDon(this, list);
        listView.setAdapter(adapter);

    }
    private void readData(){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("select hoadon.idhd, tenkh, tongtien, hoadon.diachi, ngaydathang, ngaygiaohang, tinhtrang, loaithanhtoan from hoadon, khachhang where hoadon.idkh = khachhang.idkh and hoadon.idkh = ? group by hoadon.idhd, tenkh, tongtien, hoadon.diachi, ngaydathang, ngaygiaohang, tinhtrang, loaithanhtoan", new String[]{idUser + "",});
        txtThongBao = findViewById(R.id.txtThongBao);

        //Kiem tra
        if(cursor.getCount() <= 0){
            adapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else{
            adapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            list.clear();
            for(int i = 0; i < cursor.getCount(); i++)
            {
                cursor.moveToPosition(i);
                int id = cursor.getInt(0);
                String tenKH = cursor.getString(1);
                int tongtien = cursor.getInt(2);
                String diachi = cursor.getString(3);
                String ngaydh = cursor.getString(4);
                String ngaygh = cursor.getString(5);
                String tinhtrang = cursor.getString(6);
                String loaitt = cursor.getString(7);


                list.add(new HoaDon(id, tenKH, tongtien, diachi, ngaydh, ngaygh, tinhtrang, loaitt));
            }
            adapter.notifyDataSetChanged();
        }
    }
}

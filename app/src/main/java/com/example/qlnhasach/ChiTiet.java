package com.example.qlnhasach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ChiTiet extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<ChiTietHoaDon> list;
    AdapterCTHoaDon adapter;
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_chitiet);
        addControls();
        readData();
    }

    private void addControls() {
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterCTHoaDon(this, list);
        listView.setAdapter(adapter);
    }

    private void readData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", -1);
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("select tensach, soluong, dongia, thanhtien from sach, chitiethoadon where idhd = ? and sach.idsach = chitiethoadon.idsach group by tensach, soluong, dongia, thanhtien", new String[]{id + "",});
        list.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tensach = cursor.getString(0);
            int soluong = cursor.getInt(1);
            int dongia = cursor.getInt(2);
            int tongtien = cursor.getInt(3);

            list.add(new ChiTietHoaDon(tensach, soluong, dongia, tongtien));
        }
        adapter.notifyDataSetChanged();

    }
}


package com.example.qlnhasach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class danhSachTimKiem extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<Sach> list;
    AdapterSach adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tim_kiem);
        addControls();
        readData();
    }

    private void addControls() {
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterSach(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Sach sach = list.get(position);
                Intent intent = new Intent(danhSachTimKiem.this, Chitiet_sach.class);
                intent.putExtra("ID", sach.idSach);
                startActivity(intent);
            }
        });
    }

    private void readData() {
        Intent intent = getIntent();
        String tensach = intent.getStringExtra("123");
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM sach WHERE tensach LIKE ?", new String[]{"%" + tensach + "%",});
        list.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            int idSach = cursor.getInt(0);
            int idTG = cursor.getInt(1);
            int idTheLoai = cursor.getInt(2);
            int idNXB = cursor.getInt(3);

            String tenSach = cursor.getString(4);
            String moTa = cursor.getString(5);
            byte[] anhBia = cursor.getBlob(6);
            int giaTien = cursor.getInt(7);
            int soLuongCon = cursor.getInt(8);

            list.add(new Sach(idSach, idTG, idTheLoai, idNXB, tenSach,moTa, anhBia, giaTien, soLuongCon));
        }
        adapter.notifyDataSetChanged();
    }
}
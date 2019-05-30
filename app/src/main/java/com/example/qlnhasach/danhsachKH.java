package com.example.qlnhasach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class danhsachKH extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<KhachHang> list;
    AdapterKhachHang adapter;
    Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_kh);
        addControls();
        readData();
    }

    private void addControls() {
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(danhsachKH.this, addKhachHang.class);
                startActivity(intent);
            }
        });
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterKhachHang(this, list);
        listView.setAdapter(adapter);
    }

    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM KhachHang", null);
        list.clear();
        for(int i = 0;i<cursor.getCount();i++)
        {
            cursor.moveToPosition(i);
            int idKH = cursor.getInt(0);
            String tenKH = cursor.getString(1);
            String date = cursor.getString(2);
            String tenDN = cursor.getString(3);
            String matKhau = cursor.getString(4);
            String email = cursor.getString(5);
            String diaChi = cursor.getString(6);
            int SDT = cursor.getInt(7);
            list.add(new KhachHang(idKH,tenKH,date,tenDN,matKhau,email,diaChi,SDT));
        }
        adapter.notifyDataSetChanged();
    }
}

package com.example.qlnhasach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ThanhToanActivity extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<KhachHang> list;
    AdapterKhachHang adapter;
    Button btnThanhToan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewtt);
        addControls();
        readData();
    }
    private void addControls() {
        listView = (ListView) findViewById(R.id.listView);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToan);
        list = new ArrayList<>();
        adapter = new AdapterKhachHang(this, list);
        listView.setAdapter(adapter);
    }
    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM KhachHang where idkh = 1", null);
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

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Toast.makeText(getApplicationContext(),"Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


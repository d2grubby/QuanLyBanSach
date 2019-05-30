package com.example.qlnhasach;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

public class AddNhanXet extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    Button btnGui;
    EditText edtBinhLuan;
    int id = -1;
    RatingBar danhGia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_danh_gia);
        Intent intent = getIntent();
        id = intent.getIntExtra("ID",-1);
        addControls();
        addEvents();
    }

    private void addControls(){
        btnGui = (Button) findViewById(R.id.btnGui);
        edtBinhLuan = (EditText) findViewById(R.id.edtBinhLuan);
        danhGia = (RatingBar) findViewById(R.id.danhGia);
    }

    private void addEvents(){
        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
    }

    private void insert(){

        int idsach = id;
        int idkh = DangNhap.mangUserType.get(0).getIdUser();
        String noidung = edtBinhLuan.getText().toString();
        float danhgia = danhGia.getRating();



        ContentValues contentValues = new ContentValues();
        contentValues.put("idsach", idsach);
        contentValues.put("idkh", idkh);
        contentValues.put("noidung", noidung);
        contentValues.put("danhgia", danhgia);


        SQLiteDatabase database = Database.initDatabase(this, "qlnhasach.sqlite");
        database.insert("binhluan",null, contentValues);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

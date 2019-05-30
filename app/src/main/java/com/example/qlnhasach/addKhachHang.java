package com.example.qlnhasach;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class addKhachHang extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    Button btnLuu, btnHuy;
    EditText edtTen, edtDiaChi, edtEmail, edtTenDangNhap, edtMatKhau, edtSDT, edtNgayDK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_khach_hang);
        addControls();
        addEvents();
    }

    private void addControls(){
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        edtTen = (EditText) findViewById(R.id.edtTen);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTenDangNhap = (EditText) findViewById(R.id.edtTenDangNhap);
        edtMatKhau = (EditText) findViewById(R.id.edtMatKhau);
        edtSDT = (EditText) findViewById(R.id.edtSDT);
        edtNgayDK = (EditText) findViewById(R.id.edtNgayDK);

    }

    private void addEvents(){
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private void insert(){
        String ten = edtTen.getText().toString();
        String ngaydk = edtNgayDK.getText().toString();
        String diachi = edtDiaChi.getText().toString();
        String email = edtEmail.getText().toString();
        String tendangnhap = edtTenDangNhap.getText().toString();
        String matkhau = edtMatKhau.getText().toString();
        String sdt = edtSDT.getText().toString();


        ContentValues contentValues = new ContentValues();
        contentValues.put("tenkh", ten);
        contentValues.put("ngaydk", ngaydk);
        contentValues.put("tendn", tendangnhap);
        contentValues.put("matkhau", matkhau);
        contentValues.put("email", email);
        contentValues.put("diachi", diachi);
        contentValues.put("sdt", sdt);


        SQLiteDatabase database = Database.initDatabase(this, "qlnhasach.sqlite");
        database.insert("khachhang",null, contentValues);
        Intent intent = new Intent(this, danhsachKH.class);
        startActivity(intent);
    }

    private void cancel(){
        Intent intent = new Intent(this, danhsachKH.class);
        startActivity(intent);
    }
}

package com.example.qlnhasach;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DangKy extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    Button btnDangKy, btnDangNhap;
    EditText edtTen, edtDiaChi, edtEmail, edtTenDangNhap, edtMatKhau, edtSDT, edtCMatKhau;
    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String ngayDK = df.format(Calendar.getInstance().getTime());

    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database2.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        addControls();
        addEvents();
    }

    public Boolean checkUser(String user){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM khachhang WHERE tendn = ? or email = ?", new String[]{user});
        if(cursor.getCount() > 0) return false;
        else return true;
    }
    public Boolean checkEmail(String email){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM khachhang WHERE  email = ?", new String[]{email});
        if(cursor.getCount() > 0) return false;
        else return true;
    }
    private void addControls(){
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        edtTen = (EditText) findViewById(R.id.edtTen);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTenDangNhap = (EditText) findViewById(R.id.edtTenDangNhap);
        edtMatKhau = (EditText) findViewById(R.id.edtMatKhau);
        edtCMatKhau = (EditText) findViewById(R.id.edtCMatKhau);
        edtSDT = (EditText) findViewById(R.id.edtSDT);
    }

    private void addEvents(){
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = edtTen.getText().toString();
                String s2 = edtDiaChi.getText().toString();
                String s3 = edtEmail.getText().toString();
                String s4 = edtTenDangNhap.getText().toString();
                String s5 = edtMatKhau.getText().toString();
                String s6 = edtCMatKhau.getText().toString();
                String s7 = edtSDT.getText().toString();
                if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")||s5.equals("")||s6.equals("")||s7.equals("")){
                    Toast.makeText(getApplicationContext(), "Nhập thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(s5.equals(s6)){
                        Boolean checkEmail = checkEmail(s3);
                        Boolean checkUser = checkUser(s4);
                        if(checkEmail == true & checkUser == true){
                            insert();
                            pushToFirebase();
                        }
                        else if(checkEmail == false){
                            Toast.makeText(getApplicationContext(), "Email không khả dụng", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkUser == false){
                            Toast.makeText(getApplicationContext(), "Tên đăng nhập không khả dụng", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Mật khẩu và Xác nhận mật khẩu phải giống nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKy.this, DangNhap.class));
            }
        });
    }

    private void insert(){
        String ten = edtTen.getText().toString();
        String ngaydk = ngayDK;
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
        Intent intent = new Intent(this, DangNhap.class);
        startActivity(intent);
    }

        private void pushToFirebase()
    {
        //Lấy thông tin sách mới nhất từ SQLite
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM khachhang ORDER BY idkh DESC LIMIT 1", null);
        if(cursor.moveToFirst() && cursor.getCount() > 0) {
            int idkh = cursor.getInt(0);
            String tenkh = cursor.getString(1);
            String ngaydk = cursor.getString(2);
            String tendn = cursor.getString(3);
            String matkhau = cursor.getString(4);
            String email = cursor.getString(5);
            String diachi = cursor.getString(6);
            int sdt = cursor.getInt(7);
            KhachHang khachHang = new KhachHang(idkh, tenkh, ngaydk, tendn, matkhau, email, diachi, sdt);

            //Firebase

            myRef.child("KhachHang").push().setValue(khachHang);
        }
    }


}

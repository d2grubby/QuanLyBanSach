package com.example.qlnhasach;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateKhachHang extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    Button btnLuu, btnHuy;
    EditText edtTen, edtDiaChi, edtEmail, edtTenDangNhap, edtMatKhau, edtSDT, edtNgayDK, edtCMatKhau;
    int id = -1;
    String oldEmail, oldTendn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_khach_hang);
        addControls();
        initUI();
        addEvents();
    }

    private void initUI() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", -1);
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM khachhang WHERE idkh = ?", new String[]{id + "",});
        cursor.moveToFirst();
        String ten = cursor.getString(1);
        String diachi = cursor.getString( 6);
        String email = cursor.getString(5);
        String tendangnhap = cursor.getString(3);
        String matkhau = cursor.getString(4);
        String sdt = cursor.getString(7);


        edtTen.setText(ten);
        edtDiaChi.setText(diachi);
        edtEmail.setText(email);
        edtTenDangNhap.setText(tendangnhap);
        edtMatKhau.setText(matkhau);
        edtSDT.setText(sdt);

        oldTendn = tendangnhap;
        oldEmail = email;
    }

    private void addControls(){
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        edtTen = (EditText) findViewById(R.id.edtTen);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtTenDangNhap = (EditText) findViewById(R.id.edtTenDangNhap);
        edtMatKhau = (EditText) findViewById(R.id.edtMatKhau);
        edtCMatKhau = (EditText) findViewById(R.id.edtCMatKhau);
        edtSDT = (EditText) findViewById(R.id.edtSDT);

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

    private void addEvents(){
        btnLuu.setOnClickListener(new View.OnClickListener() {
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
                        if(checkEmail == true & s3 != oldEmail){
                            update();
//                            pushToFirebase();
                            Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkUser == true & s4 != oldTendn){
                            update();
//                            pushToFirebase();
                            Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkEmail == false & s4 != oldTendn){
                            Toast.makeText(getApplicationContext(), "Email không khả dụng2", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkUser == false & s3 != oldEmail){
                            Toast.makeText(getApplicationContext(), "Tên đăng nhập không khả dụng3", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkUser == false & checkEmail == false & s3.equals(oldEmail) & s4.equals(oldTendn)){
                            update();
                        }
                        else if(checkUser == false & s3.equals(oldEmail)){
                            Toast.makeText(getApplicationContext(), "Tên đăng nhập không khả dụng", Toast.LENGTH_SHORT).show();
                        }
                        else if(checkEmail == false & s4.equals(oldTendn)){
                            Toast.makeText(getApplicationContext(), "Email không khả dụng", Toast.LENGTH_SHORT).show();
                        }

//                        else if(checkEmail == false){
//                            Toast.makeText(getApplicationContext(), "Email không khả dụng", Toast.LENGTH_SHORT).show();
//                        }
//                        else if(checkUser == false){
//                            Toast.makeText(getApplicationContext(), "Tên đăng nhập không khả dụng", Toast.LENGTH_SHORT).show();
//                        }
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Mật khẩu và Xác nhận mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private void update(){
        String ten = edtTen.getText().toString();
        String diachi = edtDiaChi.getText().toString();
        String email = edtEmail.getText().toString();
        String tendangnhap = edtTenDangNhap.getText().toString();
        String matkhau = edtMatKhau.getText().toString();
        String sdt = edtSDT.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tenkh", ten);
        contentValues.put("tendn", tendangnhap);
        contentValues.put("matkhau", matkhau);
        contentValues.put("email", email);
        contentValues.put("diachi", diachi);
        contentValues.put("sdt", sdt);


        SQLiteDatabase database = Database.initDatabase(this, "qlnhasach.sqlite");
        database.update("khachhang", contentValues, "idkh = ?", new String[] {id +  ""});
        Intent intent = new Intent(this, danhsachKH.class);
        startActivity(intent);
    }

    private void cancel(){
        Intent intent = new Intent(this, danhsachKH.class);
        startActivity(intent);
    }
}

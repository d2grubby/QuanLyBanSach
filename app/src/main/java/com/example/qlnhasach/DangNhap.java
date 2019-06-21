package com.example.qlnhasach;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class DangNhap extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    EditText edtUser, edtPass;
    Button btnLogin, btnRegister;
    public static ArrayList<UserType> mangUserType;
    int idUser;
    public static ArrayList<KhachHang> thongTinUser;
    int idKH;
    String tenKH;
    String ngayDK;
    String tenDN;
    String matKhau;
    String email;
    String diaChi;
    int SDT;
    private AdView mAdView;


    public int userpassword(String user, String password){
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM khachhang WHERE tendn = ? and matkhau = ?", new String[]{user, password});
        Cursor cursor2 = database.rawQuery("SELECT * FROM taikhoan WHERE tendn = ? and matkhau = ?", new String[]{user, password});
        if(cursor.moveToFirst() && cursor.getCount() > 0) {
            idUser = cursor.getInt( 0);

            idKH = cursor.getInt(0);
            tenKH = cursor.getString(1);
            ngayDK = cursor.getString(2);
            tenDN = cursor.getString(3);
            matKhau = cursor.getString(4);
            email = cursor.getString(5);
            diaChi = cursor.getString(6);
            SDT = cursor.getInt(7);
            return 2;
        }
        else if(cursor2.moveToFirst() && cursor2.getCount() > 0) {
            idUser = cursor2.getInt( 0);
            return 1;
        }
        else return 0;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        addControls();

        //Google Ads
        MobileAds.initialize(this, "ca-app-pub-9301738350165510~8301387694");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    private void addControls(){
        database = Database.initDatabase(this, DATABASE_NAME);

        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = edtPass.getText().toString();
                String user = edtUser.getText().toString();
                int checkUserPass = userpassword(user, pass);
                if(checkUserPass == 1) {
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    String adminID = "admin";
                    mangUserType.add(new UserType(adminID, idUser));
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
                else if(checkUserPass == 2){
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    String userID = "user";
                    mangUserType.add(new UserType(userID, idUser));
                    thongTinUser.add(new KhachHang(idKH,tenKH,ngayDK,tenDN,matKhau,email,diaChi,SDT));
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangNhap.this, DangKy.class));
            }
        });
    }
}

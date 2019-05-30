package com.example.qlnhasach;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    ListView listView;
    ArrayList<Sach> list;
    AdapterSach adapter;
    public static ArrayList<GioHang> manggiohang;
    FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database2.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Tạo ArrayList rỗng cho các biến ArrayList, nếu không sẽ bị null
        if(DangNhap.mangUserType != null){

        }else{
            DangNhap.mangUserType = new ArrayList<>();
        }
        //Tạo ArrayList rỗng cho các biến ArrayList, nếu không sẽ bị null
        if(DangNhap.thongTinUser != null){
        }else{
            DangNhap.thongTinUser = new ArrayList<>();
        }
        addControls();
        readData();
        importKhachHangFirebaseDataToSQLite();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuqlnhasach, menu);

        return super.onCreateOptionsMenu(menu);

    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem qlKH = menu.findItem(R.id.menuQLKH);
        MenuItem qlHD = menu.findItem(R.id.menuQLHoaDon);
        MenuItem qlSach = menu.findItem(R.id.menuQLSach);
        MenuItem login = menu.findItem(R.id.menuLogin);
        MenuItem register = menu.findItem(R.id.menuRegister);
        MenuItem logout = menu.findItem(R.id.menuLogout);
        MenuItem lsmh = menu.findItem(R.id.menuLSMH);
        MenuItem giohang = menu.findItem(R.id.menuGioHang);

        if(DangNhap.mangUserType.size() == 0)
        {
        }
        else if(DangNhap.mangUserType.get(0).getUserType().equals("user"))
        {
            login.setVisible(false);
            register.setVisible(false);
            logout.setVisible(true);
            lsmh.setVisible(true);
        }
        else if(DangNhap.mangUserType.get(0).getUserType().equals("admin"))
        {
            qlKH.setVisible(true);
            qlHD.setVisible(true);
            qlSach.setVisible(true);
            lsmh.setVisible(false);
            login.setVisible(false);
            register.setVisible(false);
            logout.setVisible(true);
            giohang.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menuTheLoai){
            startActivity(new Intent(this, TheLoai.class));
        }
        else if(id == R.id.menutacgia){
            startActivity(new Intent(this, TacGia.class));
        }
        else if(id == R.id.menuTrangChu){
            startActivity(new Intent(this, MainActivity.class));
        }
        else if(id == R.id.menuNXB){
            startActivity(new Intent(this, NXB.class));
        }
        else if(id == R.id.menuQLKH){
            startActivity(new Intent(this, danhsachKH.class));
        }
        else if(id == R.id.menuQLHoaDon){
            startActivity(new Intent(this, danhsachHoaDon.class));
        }
        else if(id == R.id.menuQLSach){
            startActivity(new Intent(this, QuanLySach.class));
        }
        else if(id == R.id.menuLogin){
            startActivity(new Intent(this, DangNhap.class));
        }
        else if(id == R.id.menuRegister){
            startActivity(new Intent(this, DangKy.class));
        }
        else if(id == R.id.menuSearch){
            startActivity(new Intent(this, timKiem.class));
        }
        else if(id == R.id.menuHoTro){
            startActivity(new Intent(this, HoTro.class));
        }
        else if(id == R.id.menuGioHang){
            startActivity(new Intent(this, DanhSachGioHang.class));
        }
        else if(id == R.id.menuLSMH){
            startActivity(new Intent(this, LichSuMuaHang.class));
        }
        else if(id == R.id.menuLogout){
            DangNhap.mangUserType.clear();
            DangNhap.thongTinUser.clear();
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(getApplicationContext(), "Đã đăng xuất", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
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
                Intent intent = new Intent(MainActivity.this, Chitiet_sach.class);
                intent.putExtra("ID", sach.idSach);
                startActivity(intent);
            }
        });
        if(manggiohang != null){
        }
        else{
            manggiohang = new ArrayList<>();
        }
    }

    private void readData() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT sach.idsach, idTG, idTheLoai, idNXB, tenSach,moTa, anhBia, giaTien, soLuongCon FROM sach, binhluan WHERE sach.idsach = binhluan.idsach group by sach.idsach, idTG, idTheLoai, idNXB, tenSach,moTa, anhBia, giaTien, soLuongCon having count(idkh) >= 0 order by count(idkh) desc", null);
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

    //Firebase
    //KhachHang
    private void importKhachHangFirebaseDataToSQLite(){

        myRef.child("KhachHang").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                KhachHang khachHang = dataSnapshot.getValue(KhachHang.class);
                String tendn = khachHang.tenDN;

                database = Database.initDatabase(MainActivity.this, DATABASE_NAME);
                Cursor cursor = database.rawQuery("SELECT * FROM khachhang WHERE tendn = ? ", new String[]{tendn});
                if(cursor.getCount() > 0) {

                }
                else {
                    String ten = khachHang.tenKH;
                    String ngaydk = khachHang.ngayDK;
                    String diachi = khachHang.diaChi;
                    String email = khachHang.email;
                    String tendangnhap = khachHang.tenDN;
                    String matkhau = khachHang.matKhau;
                    int sdt = khachHang.sdt;

                    ContentValues contentValues = new ContentValues();
                    contentValues.put("tenkh", ten);
                    contentValues.put("ngaydk", ngaydk);
                    contentValues.put("tendn", tendangnhap);
                    contentValues.put("matkhau", matkhau);
                    contentValues.put("email", email);
                    contentValues.put("diachi", diachi);
                    contentValues.put("sdt", sdt);

                    SQLiteDatabase database = Database.initDatabase(MainActivity.this, "qlnhasach.sqlite");
                    database.insert("khachhang",null, contentValues);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

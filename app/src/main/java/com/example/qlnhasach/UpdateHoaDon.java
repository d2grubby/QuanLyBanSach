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

public class UpdateHoaDon extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    Button btnLuu, btnHuy;
    EditText edtDiaChi, edtNgayGH, edtNgayDH, edtTongTien, edtTT;
    int id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_hoa_don);
        addControls();
        addEvents();
        initUI();
    }

    private void initUI() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", -1);
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM hoadon WHERE idhd = ?", new String[]{id + "",});
        cursor.moveToFirst();
        String tongtien = cursor.getString( 2);
        String diachi = cursor.getString(3);
        String ngaydathang = cursor.getString(4);
        String ngaygiaohang = cursor.getString(5);
        String tinhtrang = cursor.getString(6);


        edtDiaChi.setText(diachi);
        edtNgayDH.setText(ngaydathang);
        edtNgayGH.setText(ngaygiaohang);
        edtTongTien.setText(tongtien);
        edtTT.setText(tinhtrang);
    }

    private void addControls(){
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        edtNgayDH = (EditText) findViewById(R.id.edtNgayDH);
        edtNgayGH = (EditText) findViewById(R.id.edtNgayGiaoHang);
        edtTongTien = (EditText) findViewById(R.id.edtTongTien);
        edtTT = (EditText) findViewById(R.id.edtTT);

    }

    private void addEvents(){
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
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
        String diachi = edtDiaChi.getText().toString();
        String tongtien = edtTongTien.getText().toString();
        String ngaydh = edtNgayDH.getText().toString();
        String ngaygh = edtNgayGH.getText().toString();
        String tinhtrang = edtTT.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tongtien", tongtien);
        contentValues.put("diachi", diachi);
        contentValues.put("ngaydathang", ngaydh);
        contentValues.put("ngaygiaohang", ngaygh);
        contentValues.put("tinhtrang", tinhtrang);

        SQLiteDatabase database = Database.initDatabase(this, "qlnhasach.sqlite");
        database.update("hoadon", contentValues, "idhd = ?", new String[] {id +  ""});
        Intent intent = new Intent(this, danhsachHoaDon.class);
        startActivity(intent);
    }

    private void cancel(){
        Intent intent = new Intent(this, danhsachHoaDon.class);
        startActivity(intent);
    }
}


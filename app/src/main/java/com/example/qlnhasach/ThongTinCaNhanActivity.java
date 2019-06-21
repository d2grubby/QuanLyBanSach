package com.example.qlnhasach;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThongTinCaNhanActivity extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    Button btnLuu, btnHuy;
    EditText edtTen, edtDiaChi, edtMatKhau, edtSDT, edtCMatKhau;
    int id = DangNhap.thongTinUser.get(0).getIdKH();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_ca_nhan);

        addControls();
        initUI();
        addEvents();
    }

    private void initUI() {
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM khachhang WHERE idkh = ?", new String[]{id + "",});
        cursor.moveToFirst();
        String ten = cursor.getString(1);
        String diachi = cursor.getString( 6);
        String matkhau = cursor.getString(4);
        String sdt = cursor.getString(7);


        edtTen.setText(ten);
        edtDiaChi.setText(diachi);

        edtMatKhau.setText(matkhau);
        edtSDT.setText(sdt);
    }

    private void addControls(){
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        edtTen = (EditText) findViewById(R.id.edtTen);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        edtMatKhau = (EditText) findViewById(R.id.edtMatKhau);
        edtCMatKhau = (EditText) findViewById(R.id.edtCMatKhau);
        edtSDT = (EditText) findViewById(R.id.edtSDT);

    }

    private void addEvents(){
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = edtTen.getText().toString();
                String s2 = edtDiaChi.getText().toString();
                String s5 = edtMatKhau.getText().toString();
                String s6 = edtCMatKhau.getText().toString();
                String s7 = edtSDT.getText().toString();
                if(s1.equals("")||s2.equals("")||s5.equals("")||s6.equals("")||s7.equals("")){
                    Toast.makeText(getApplicationContext(), "Nhập thiếu thông tin", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(s5.equals(s6)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(ThongTinCaNhanActivity.this);
                        builder.setIcon(android.R.drawable.ic_delete);
                        builder.setTitle("Xác nhận Lưu");
                        builder.setMessage("Bạn có chắc chắn muốn Lưu ?");
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                update();
                                Intent intent = new Intent(ThongTinCaNhanActivity.this, MainActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Đã lưu thông tin cá nhân", Toast.LENGTH_SHORT).show();
                            }
                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Mật khẩu và Xác nhận mật khẩu phải giống nhau", Toast.LENGTH_SHORT).show();
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
        String matkhau = edtMatKhau.getText().toString();
        String sdt = edtSDT.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("tenkh", ten);
        contentValues.put("matkhau", matkhau);
        contentValues.put("diachi", diachi);
        contentValues.put("sdt", sdt);


        SQLiteDatabase database = Database.initDatabase(this, "qlnhasach.sqlite");
        database.update("khachhang", contentValues, "idkh = ?", new String[] {id +  ""});
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void cancel(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

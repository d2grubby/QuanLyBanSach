package com.example.qlnhasach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.qlnhasach.MainActivity.manggiohang;

public class DanhSachGioHang extends MainActivity {

    ListView listview;
    TextView txtThongBao;
    TextView txtTongTien;
    Button btnTiepTucMua,btnMua;
    AdapterGiohang adapterGioHang;
    int tongtien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        Anhxa();
        Checkdata();
        EventUtils();
        addEvents();
    }
    private void Anhxa(){
        listview = (ListView) findViewById(R.id.listview);
        txtThongBao = (TextView) findViewById(R.id.textViewThongBao);
        txtTongTien = (TextView) findViewById(R.id.txtTongTien);
        btnMua = (Button) findViewById(R.id.btnMua);
        btnTiepTucMua = (Button) findViewById(R.id.btnTiepTucMua);
        adapterGioHang = new AdapterGiohang(DanhSachGioHang.this, manggiohang);
        listview.setAdapter(adapterGioHang);

    }
    private void Checkdata(){
        if(manggiohang.size() <= 0){
            adapterGioHang.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            txtTongTien.setVisibility(View.GONE);
            listview.setVisibility(View.GONE);
            btnTiepTucMua.setVisibility(View.GONE);
            btnMua.setVisibility(View.GONE);
        }
        else{
            adapterGioHang.notifyDataSetChanged();
            txtThongBao.setVisibility(View.GONE);
            listview.setVisibility(View.VISIBLE);
        }
    }
    private void EventUtils(){
        tongtien = 0;
        for(int i = 0; i< manggiohang.size(); i++){
            tongtien += MainActivity.manggiohang.get(i).getGiatien()*MainActivity.manggiohang.get(i).getSoluongsach();
        }
        txtTongTien.setText("Tổng tiền hóa đơn: "+tongtien + " đ");
    }

    private void addEvents() {
        btnTiepTucMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachGioHang.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(DangNhap.mangUserType.size() > 0)
                {
                    startActivity(new Intent(DanhSachGioHang.this, ChiTietDatHang.class)
                            .putExtra("tongtien", tongtien));
                }
                else
                {
                    startActivity(new Intent(DanhSachGioHang.this, DangNhap.class));
                }
            }
        });
    }
}

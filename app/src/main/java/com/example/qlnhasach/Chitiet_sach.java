package com.example.qlnhasach;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class Chitiet_sach extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    final int REQUEST_CHOOSE_PHOTO = 321;
    int id = -1;
    SQLiteDatabase database;
    Button btnDatMua,btnGuiBL;
    TextView txtTenSach,txtGiaTien,txtMoTa;
    ImageView imgSach2;
    private Spinner spinner;
    ArrayList<BinhLuanModel> listBL;
    AdapterBinhLuan adapterBL;
    ListView listViewBL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sach);

        addControl();
        addEvents();
        initUI();
        readData();
    }

    private void initUI() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID",-1);
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("Select * from Sach where idsach = ?",new String[]{id + ""});
        cursor.moveToFirst();

        final String tenSach = cursor.getString(4);
        String moTa = cursor.getString(5);
        final byte[] anhBia = cursor.getBlob(6);
        final String giaTien = cursor.getString(7);

        Bitmap bmSach = BitmapFactory.decodeByteArray(anhBia, 0, anhBia.length);
        imgSach2.setImageBitmap(bmSach);
        txtTenSach.setText(tenSach);
        txtGiaTien.setText("Giá: " + giaTien + " đ");
        txtMoTa.setText(moTa);

        Integer[] soLuong =  new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter =  new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item,soLuong);
        spinner.setGravity(Gravity.CENTER);
        spinner.setAdapter(arrayAdapter);
        btnDatMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.manggiohang.size()>0){
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists = false;
                    int id = getIntent().getIntExtra("ID",-1);
                    for(int i =0;i<MainActivity.manggiohang.size();i++){
                        if(MainActivity.manggiohang.get(i).getIdsach() == id){
                            MainActivity.manggiohang.get(i).setSoluongsach(MainActivity.manggiohang.get(i).getSoluongsach() + sl);
                            if(MainActivity.manggiohang.get(i).getSoluongsach()>=10){
                                MainActivity.manggiohang.get(i).setSoluongsach(10);
                            }
                            MainActivity.manggiohang.get(i).setGiatien(Integer.parseInt(giaTien) * MainActivity.manggiohang.get(i).getSoluongsach());
                            exists = true;
                        }
                    }
                    if(exists == false){
                        int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                        int giaMoi = soLuong * Integer.parseInt(giaTien);
                        id = getIntent().getIntExtra("ID",-1);
                        MainActivity.manggiohang.add(new GioHang(id, tenSach, giaMoi, anhBia, soLuong));
                    }
                }
                else{
                    int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                    int giaMoi = soLuong * Integer.parseInt(giaTien);
                    int id = getIntent().getIntExtra("ID",-1);
                    MainActivity.manggiohang.add(new GioHang(id, tenSach, giaMoi, anhBia, soLuong));
                }
                Intent intent1 = new Intent(getApplicationContext(), DanhSachGioHang.class);
                startActivity(intent1);
            }
        });
        btnGuiBL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DangNhap.mangUserType.size() == 0)
                {
                    startActivity(new Intent(Chitiet_sach.this, DangNhap.class));
                }
                else
                {
                    startActivity(new Intent(Chitiet_sach.this, AddNhanXet.class).putExtra("ID", id));
                }
            }
        });
    }

    private void addControl() {
        btnDatMua = (Button) findViewById(R.id.btnDatMua);
        txtTenSach = (TextView) findViewById(R.id.txtTenSach);
        txtGiaTien = (TextView) findViewById(R.id.txtGiaTien);
        txtMoTa = (TextView) findViewById(R.id.txtMoTa);
        imgSach2 = (ImageView) findViewById(R.id.imgSach2);
        spinner = (Spinner) findViewById(R.id.spinnerSoLuong);
        btnGuiBL = (Button) findViewById(R.id.btnGuiBL);

        listViewBL = (ListView) findViewById(R.id.listViewBL);
        listBL = new ArrayList<>();
        adapterBL = new AdapterBinhLuan(this, listBL);
        listViewBL.setAdapter(adapterBL);

    }

    private void addEvents()
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK)
            if(requestCode == REQUEST_CHOOSE_PHOTO) {
                try{
                    Uri imageUrl = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgSach2.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }

    private void readData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID",-1);
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT tenkh, noidung, danhgia FROM sach, binhluan, khachhang WHERE sach.idsach = binhluan.idsach and khachhang.idkh = binhluan.idkh and binhluan.idsach = ? group by tenkh, noidung, danhgia", new String[]{id + ""});
        listBL.clear();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String tenKH = cursor.getString(0);
            String noidung = cursor.getString(1);
            int ratingBar = cursor.getInt(2);

            listBL.add(new BinhLuanModel(tenKH, noidung, ratingBar));
        }
        adapterBL.notifyDataSetChanged();
    }
}

package com.example.qlnhasach;

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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class UpdateSachActivity extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    final int REQUEST_CHOOSE_PHOTO = 321;
    int id = -1;
    SQLiteDatabase database;
    Button btnChonHinh, btnLuu, btnHuy;
    EditText edtIDTG,edtIDTheLoai,edtIDNXB,edtTenSach,edtGiaTien,edtSLCon,edtMoTa;
    ImageView imgHinhDaiDien;
    Spinner spinnerTheLoai, spinnerNXB, spinnerTacGia;

    ArrayList<TheLoaiModel> listTheLoai;
    ArrayList<NXBModel> listNXB;
    ArrayList<TacGiaModel> listTacGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sach);
        addControl();
        spinnerTheLoai();
        spinnerNXB();
        spinnerTacGia();
        addEvents();
        initUI();
    }
    private void spinnerTacGia(){
        spinnerTacGia = (Spinner)findViewById(R.id.spinnerTacGia);
        listTacGia = new ArrayList<>();
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM tacgia", null);
        listTacGia.clear();
        for(int i = 0;i<cursor.getCount();i++)
        {
            cursor.moveToPosition(i);
            int idTG = cursor.getInt(0);
            String tenTG = cursor.getString(1);

            listTacGia.add(new TacGiaModel(idTG, tenTG));
        }

        AdapterTacGia adapterTacGia = new AdapterTacGia(this,R.layout.activity_dong_tac_gia, listTacGia);
        adapterTacGia.notifyDataSetChanged();
        spinnerTacGia.setAdapter(adapterTacGia);
        spinnerTacGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtIDTG.setText(Integer.toString(listTacGia.get(position).idTG));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void spinnerNXB(){
        spinnerNXB = (Spinner)findViewById(R.id.NXBSpinner);
        listNXB = new ArrayList<>();
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM nxb", null);
        listNXB.clear();
        for(int i = 0;i<cursor.getCount();i++)
        {
            cursor.moveToPosition(i);
            int idNXB = cursor.getInt(0);
            String tenNXB = cursor.getString(1);

            listNXB.add(new NXBModel(idNXB, tenNXB));
        }

        AdapterNXB adapterNXB = new AdapterNXB(this,R.layout.activity_dong_nxb, listNXB);
        adapterNXB.notifyDataSetChanged();
        spinnerNXB.setAdapter(adapterNXB);
        spinnerNXB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtIDNXB.setText(Integer.toString(listNXB.get(position).idNXB));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void spinnerTheLoai(){
        spinnerTheLoai = (Spinner)findViewById(R.id.theLoaiSpinner);
        listTheLoai = new ArrayList<>();
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM TheLoai", null);
        listTheLoai.clear();
        for(int i = 0;i<cursor.getCount();i++)
        {
            cursor.moveToPosition(i);
            int idTL = cursor.getInt(0);
            String tenTheLoai = cursor.getString(1);

            listTheLoai.add(new TheLoaiModel(idTL, tenTheLoai));
        }

        AdapterTheLoai adapterTL = new AdapterTheLoai(this,R.layout.activity_dong_the_loai, listTheLoai);
        adapterTL.notifyDataSetChanged();
        spinnerTheLoai.setAdapter(adapterTL);
        spinnerTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edtIDTheLoai.setText(Integer.toString(listTheLoai.get(position).idTheLoai));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void initUI() {
        Intent intent = getIntent();
        id = intent.getIntExtra("ID",-1);
        database = Database.initDatabase(this, DATABASE_NAME);
        Cursor cursor = database.rawQuery("Select * from Sach where idsach = ?",new String[]{id + ""});
        cursor.moveToFirst();
        String idTG = cursor.getString(1);
        String idTheLoai = cursor.getString(2);
        String idNXB = cursor.getString(3);

        String tenSach = cursor.getString(4);
        String moTa = cursor.getString(5);
        byte[] anhBia = cursor.getBlob(6);
        String giaTien = cursor.getString(7);
        String soLuongCon = cursor.getString(8);

        Bitmap bmSach = BitmapFactory.decodeByteArray(anhBia, 0, anhBia.length);
        imgHinhDaiDien.setImageBitmap(bmSach);
        edtIDTG.setText(idTG);
        edtIDTheLoai.setText(idTheLoai);
        edtIDNXB.setText(idNXB);
        edtTenSach.setText(tenSach);
        edtMoTa.setText(moTa);
        edtGiaTien.setText(giaTien);
        edtSLCon.setText(soLuongCon);
    }
    private void addControl() {
        btnChonHinh = (Button) findViewById(R.id.btnChonHinh);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnHuy = (Button) findViewById(R.id.btnHuy);
        edtIDTG = (EditText) findViewById(R.id.edtIDTG);
        edtIDTheLoai = (EditText) findViewById(R.id.edtIDTheLoai);
        edtIDNXB = (EditText) findViewById(R.id.edtIDNXB);
        edtTenSach = (EditText) findViewById(R.id.edtTenSach);
        edtMoTa = (EditText) findViewById(R.id.edtMoTa);
        edtGiaTien = (EditText) findViewById(R.id.edtGiaTien);
        edtSLCon = (EditText) findViewById(R.id.edtSLCon);
        imgHinhDaiDien = (ImageView) findViewById(R.id.imgSach);
    }

    private void addEvents()
    {
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });
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

    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK)
            if(requestCode == REQUEST_CHOOSE_PHOTO) {
                try{
                    Uri imageUrl = data.getData();
                    InputStream is = getContentResolver().openInputStream(imageUrl);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imgHinhDaiDien.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }

    private void update(){
        String idTG = edtIDTG.getText().toString();
        String idTheLoai = edtIDTheLoai.getText().toString();
        String idNXB = edtIDNXB.getText().toString();
        String tenSach = edtTenSach.getText().toString();
        String moTa = edtMoTa.getText().toString();
        String giaTien = edtGiaTien.getText().toString();
        String soLuongCon = edtSLCon.getText().toString();
        byte[] anh = getByteArrayFromImageView( imgHinhDaiDien);

        ContentValues contentValues = new ContentValues();
        contentValues.put("idTG", idTG);
        contentValues.put("idTheLoai", idTheLoai);
        contentValues.put("idNXB", idNXB);
        contentValues.put("TenSach", tenSach);
        contentValues.put("MoTa", moTa);
        contentValues.put("GiaTien", giaTien);
        contentValues.put("SoLuongCon", soLuongCon);

        SQLiteDatabase database = Database.initDatabase(this,"qlnhasach.sqlite");
        database.update("Sach", contentValues, "idSach = ?",new String[] {id + ""});
        Intent intent = new Intent(this, QuanLySach.class);
        startActivity(intent);
    }

    private void cancel(){
        Intent intent = new Intent(this, QuanLySach.class);
        startActivity(intent);
    }

    private byte[] getByteArrayFromImageView(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp =  drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}

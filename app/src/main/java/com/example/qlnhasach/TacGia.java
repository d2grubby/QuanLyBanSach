package com.example.qlnhasach;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class TacGia extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    int id = -1;
    SQLiteDatabase database;

    AdapterSach adapter;
    ListView listView;
    ArrayList<Sach> list;

    Spinner spinnerTacGia;
    ArrayList<TacGiaModel> listTacGia;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tacgia);
        addControls();
        spinnerTacGia();
    }
    private void addControls() {
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterSach(this, list);
        listView.setAdapter(adapter);
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
                database = Database.initDatabase(TacGia.this, DATABASE_NAME);
                Cursor cursor = database.rawQuery("SELECT * FROM Sach WHERE idtg = ?", new String[]{listTacGia.get(position).idTG + ""});
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
                adapter.notifyDataSetChanged();            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}

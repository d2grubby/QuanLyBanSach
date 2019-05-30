package com.example.qlnhasach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class NXB extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    int id = -1;
    SQLiteDatabase database;

    AdapterSach adapter;
    ListView listView;
    ArrayList<Sach> list;

    Spinner spinnerNXB;
    ArrayList<NXBModel> listNXB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nxb);
        addControls();
        spinnerNXB();
    }
    private void addControls() {
        listView = (ListView) findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new AdapterSach(this, list);
        listView.setAdapter(adapter);
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
                database = Database.initDatabase(NXB.this, DATABASE_NAME);
                Cursor cursor = database.rawQuery("SELECT * FROM Sach WHERE idnxb = ?", new String[]{listNXB.get(position).idNXB + ""});
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

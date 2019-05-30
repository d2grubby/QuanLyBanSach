package com.example.qlnhasach;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class timKiem extends MainActivity {
    EditText edtSearch, edtGiaFrom, edtGiaTo;
    Button btnSearch;
    Button btnSearchPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        edtSearch = (EditText) findViewById(R.id.edtSearch);
        edtGiaFrom = (EditText) findViewById(R.id.edtGiaFrom);
        edtGiaTo = (EditText) findViewById(R.id.edtGiaTo);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearchPrice = (Button) findViewById(R.id.btnSearchPrice);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tensach = edtSearch.getText().toString();
                Intent intent = new Intent(timKiem.this, danhSachTimKiem.class);
                intent.putExtra("123", tensach);
                startActivity(intent);
            }
        });

        btnSearchPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String giaFrom = edtGiaFrom.getText().toString();
                String giaTo = edtGiaTo.getText().toString();
                Intent intent = new Intent(timKiem.this, danhSachTimKiemGiaTien.class);
                intent.putExtra("From", giaFrom);
                intent.putExtra("To", giaTo);
                startActivity(intent);
            }
        });
    }
}

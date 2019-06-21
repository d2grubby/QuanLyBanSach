package com.example.qlnhasach;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;

public class HoTro extends MainActivity {
    Button btnGui,btnChonHinh;
    EditText edtNoiDung,edtChuDe;
    TextView txtEmailHoTro;
    ImageView imageView;
    final int RQS_SENDEMAIL = 1;
    final int RQS_LOADIMAGE = 0;
    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotrokhachhang);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnGui = (Button) findViewById(R.id.btnGui);
        btnChonHinh = (Button) findViewById(R.id.btnChonHinh);
        txtEmailHoTro = (TextView) findViewById(R.id.txtEmailHoTro);
        edtNoiDung = (EditText) findViewById(R.id.edtNoiDung);
        edtChuDe = (EditText) findViewById(R.id.edtChuDe);
        btnChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhoto();
            }
        });

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = edtNoiDung.getText().toString();
                String s2 = edtChuDe.getText().toString();

                if(s1.equals("")||s2.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Nhập thiếu thông tin", Toast.LENGTH_SHORT).show();
                }else
                    sendMail();
            }
        });
    }
    private void choosePhoto(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RQS_LOADIMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            switch(requestCode){
                case RQS_LOADIMAGE:
                    try {
                        imageUri = data.getData();
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        imageView.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case RQS_SENDEMAIL:
                    break;
            }
        }
    }


    private void sendMail(){
        String[] emailHoTro = {txtEmailHoTro.getText().toString()};
        String subject = edtChuDe.getText().toString();
        String message = edtNoiDung.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, emailHoTro);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if(imageUri != null){
            intent.putExtra(Intent.EXTRA_STREAM, imageUri);
            intent.setType("image/png");
        }else{
            intent.setType("plain/text");
        }

        intent.setType("message/rfc822");

        try {
            startActivity(Intent.createChooser(intent, "Chọn ứng dụng Email của bạn"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(HoTro.this, "Bạn hiện đang không có ứng dụng Email nào", Toast.LENGTH_SHORT).show();
        }
    }
}

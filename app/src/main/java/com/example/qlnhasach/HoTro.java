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
    final int REQUEST_CHOOSE_PHOTO = 321;
    ImageView imageView;
    URI FILENAME;
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
                    imageView.setImageBitmap(bitmap);
//                    Bitmap myBitmap = imageView.getDrawingCache();
//                    FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_WORLD_READABLE);
//                    myBitmap.compress(Bitmap.CompressFormat.PNG, 0, fos);
//                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
    }
    private byte[] getByteArrayFromImageView(ImageView imgv){
        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp =  drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void sendMail(){
        String emailHoTro = txtEmailHoTro.getText().toString();

        String subject = edtChuDe.getText().toString();
        String message = edtNoiDung.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, emailHoTro);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
//        intent.putExtra(Intent.EXTRA)

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
    }
}

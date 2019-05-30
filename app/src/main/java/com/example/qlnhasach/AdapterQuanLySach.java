package com.example.qlnhasach;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterQuanLySach extends BaseAdapter {
    Activity context;
    ArrayList<Sach> list;

    public AdapterQuanLySach(Activity context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_danhsach_quanly_sach, null);
        ImageView imgSach = (ImageView) row.findViewById(R.id.imageSach);
        TextView txtID = (TextView) row.findViewById(R.id.txtID);
        TextView txtTG = (TextView) row.findViewById(R.id.txtTG);
        TextView txtTheLoai = (TextView) row.findViewById(R.id.txtTheLoai);
        TextView txtNXB = (TextView) row.findViewById(R.id.txtNXB);
        TextView txtTenSach = (TextView) row.findViewById(R.id.txtTenSach);
        TextView txtGiaTien = (TextView) row.findViewById(R.id.txtGiaTien);
        TextView txtSLCon = (TextView) row.findViewById(R.id.txtSLCon);
        TextView txtMoTa = (TextView) row.findViewById(R.id.txtMoTa);
        Button btnEdit = (Button) row.findViewById(R.id.btnEdit);
        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);

        final Sach sach = list.get(position);
        txtID.setText(sach.idSach + "");
        txtMoTa.setText(sach.moTa + "");
        txtTG.setText(sach.idTG + "");
        txtTheLoai.setText(sach.idTheLoai + "");
        txtNXB.setText(sach.idNXB + "");
        txtTenSach.setText(sach.tenSach + "");
        txtGiaTien.setText("Giá tiền: "+ sach.giaTien + "");
        txtSLCon.setText("Số lượng còn: "+ sach.soLuongCon + "");

        Bitmap bmSach = BitmapFactory.decodeByteArray(sach.anhBia, 0, sach.anhBia.length);
        imgSach.setImageBitmap(bmSach);

        btnEdit.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateSachActivity.class);
                intent.putExtra("ID",sach.idSach);
                context.startActivity(intent);
            }
        });
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận Xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa ? ");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(sach.idSach);
                        Intent intent = new Intent(context, QuanLySach.class);
                        context.startActivity(intent);
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
        });

        return row;
    }

    private void delete(int id){
        SQLiteDatabase database = Database.initDatabase(context,"qlnhasach.sqlite");
        database.delete("sach","idsach = ?", new String[]{id + ""});

        Cursor cursor = database.rawQuery("SELECT * FROM sach", null);
        while (cursor.moveToNext()){
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
        notifyDataSetChanged();
    }
}

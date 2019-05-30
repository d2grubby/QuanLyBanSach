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

public class AdapterKhachHang extends BaseAdapter {
    Activity context;
    ArrayList<KhachHang> list;

    public AdapterKhachHang(Activity context, ArrayList<KhachHang> list) {
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
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_khach_hang,null);
        TextView txtID = (TextView) row.findViewById(R.id.txtID);
        TextView txtTen = (TextView) row.findViewById(R.id.txtTen);
        TextView txtNgayDK = (TextView) row.findViewById(R.id.txtNgayDK);
        TextView txtTenDN = (TextView) row.findViewById(R.id.txtTenDN);
        TextView txtMatKhau = (TextView) row.findViewById(R.id.txtMatKhau);
        TextView txtEmail = (TextView) row.findViewById(R.id.txtEmail);
        TextView txtDiaChi = (TextView) row.findViewById(R.id.txtDiaChi);
        TextView txtSDT = (TextView) row.findViewById(R.id.txtSDT);
        Button btnCapNhat = (Button) row.findViewById(R.id.btnCapNhat);
        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);


        final KhachHang khachHang = list.get(position);
        txtID.setText(khachHang.idKH + "");
        txtTen.setText(khachHang.tenKH + "");
        txtNgayDK.setText("Ngày đăng ký: " + khachHang.ngayDK + "");
        txtTenDN.setText("Tên đăng nhập: " + khachHang.tenDN + "");
        txtMatKhau.setText("Mật khẩu: " + khachHang.matKhau + "");
        txtEmail.setText("Email: " + khachHang.email + "");
        txtDiaChi.setText("Địa chỉ: " + khachHang.diaChi + "");
        txtSDT.setText("SĐT: " + khachHang.sdt + "");

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateKhachHang.class);
                intent.putExtra("ID", khachHang.idKH);
                context.startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delete(khachHang.idKH);
                        Intent intent = new Intent(context, danhsachKH.class);
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
    private void delete (int idkh){
        SQLiteDatabase database = Database.initDatabase(context, "qlnhasach.sqlite");
        database.delete("khachhang", "idkh = ?", new String[]{idkh + ""});

        Cursor cursor = database.rawQuery("Select * from khachhang",null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String tenkh = cursor.getString(1);
            String ngaydk = cursor.getString(2);
            String tendn = cursor.getString(3);
            String matkhau = cursor.getString(4);
            String email = cursor.getString(5);
            String diachi = cursor.getString(6);
            int sdt = cursor.getInt(7);

            list.add(new KhachHang(id, tenkh, ngaydk, tendn, matkhau, email, diachi, sdt));
        }
        notifyDataSetChanged();
    }
}

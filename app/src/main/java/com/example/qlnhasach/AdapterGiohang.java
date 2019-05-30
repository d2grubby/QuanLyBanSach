package com.example.qlnhasach;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterGiohang extends BaseAdapter {
    Activity context;
    ArrayList<GioHang> list;

    public AdapterGiohang(Activity context, ArrayList<GioHang> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_giohang_chitiet, null);
        ImageView imageView2 = (ImageView) row.findViewById(R.id.imageView2);
        TextView txtTenSach = (TextView) row.findViewById(R.id.txtTenSach);
        TextView txtSoLuong = (TextView) row.findViewById(R.id.txtSoLuong);
        TextView txtDonGia = (TextView) row.findViewById(R.id.txtDonGia);
        TextView txtThanhTien = (TextView) row.findViewById(R.id.txtThanhTien);
        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);

        GioHang gioHang = list.get(position);
        txtTenSach.setText(gioHang.getTensach() + "");
        txtSoLuong.setText("Số lượng: " +gioHang.getSoluongsach() + "");
        txtDonGia.setText("Đơn giá: " +gioHang.getGiatien() + "");
        txtThanhTien.setText("Tổng tiền: " + gioHang.getGiatien() * gioHang.getSoluongsach() + " đ");

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
                        MainActivity.manggiohang.remove(MainActivity.manggiohang.get(position));
                        Intent intent = new Intent(context, MainActivity.class);
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

        Bitmap bmSach1 = BitmapFactory.decodeByteArray(gioHang.getHinhanh(), 0, gioHang.getHinhanh().length);
        imageView2.setImageBitmap(bmSach1);

        return row;
    }
}

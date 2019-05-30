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

public class AdapterCTHoaDon extends BaseAdapter{
    Activity context;
    ArrayList<ChiTietHoaDon> list;

    public AdapterCTHoaDon(Activity context, ArrayList<ChiTietHoaDon> list) {
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
        View row = inflater.inflate(R.layout.activity_chitiet_hoadon, null);
        TextView txtTenSach = (TextView) row.findViewById(R.id.txtTenSach);
        TextView txtSoLuong = (TextView) row.findViewById(R.id.txtSoLuong);
        TextView txtDonGia = (TextView) row.findViewById(R.id.txtDonGia);
        TextView txtThanhTien = (TextView) row.findViewById(R.id.txtThanhTien);

        ChiTietHoaDon chiTietHoaDon = list.get(position);
        txtTenSach.setText(chiTietHoaDon.tenSach + "");
        txtSoLuong.setText("Số lượng: " + chiTietHoaDon.soluong + "");
        txtDonGia.setText("Đơn giá: " + chiTietHoaDon.dongia + " đ");
        txtThanhTien.setText("Thành tiền: " + chiTietHoaDon.thanhtien + " đ");

        return row;
    }
}


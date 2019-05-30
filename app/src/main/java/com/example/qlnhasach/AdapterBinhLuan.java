package com.example.qlnhasach;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterBinhLuan extends BaseAdapter {
    Activity context;
    ArrayList<BinhLuanModel> list;

    public AdapterBinhLuan(Activity context, ArrayList<BinhLuanModel> list) {
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
        View row = inflater.inflate(R.layout.activity_binh_luan, null);
        TextView txtTenKH = (TextView) row.findViewById(R.id.txtTenKH);
        TextView txtBL = (TextView) row.findViewById(R.id.txtBL);
        final RatingBar danhGia = (RatingBar) row.findViewById(R.id.danhGia);

        final BinhLuanModel binhLuan = list.get(position);
        txtTenKH.setText(binhLuan.tenKH + "");
        txtBL.setText(binhLuan.noidung);
        danhGia.setRating(binhLuan.danhgia);

        return row;
    }
}

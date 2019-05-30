package com.example.qlnhasach;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterThanhToan extends BaseAdapter{
    Activity context;
    ArrayList<ThanhToanModel> list;

    public AdapterThanhToan(Activity context, ArrayList<ThanhToanModel> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_thanhtoan, null);
        TextView txtTenKH = (TextView) row.findViewById(R.id.txtTenKH);
        TextView txtEmail = (TextView) row.findViewById(R.id.txtEmail);
        TextView txtSDT = (TextView) row.findViewById(R.id.txtSDT);
        TextView txtDiaChi = (TextView) row.findViewById(R.id.txtDiaChi);
        TextView txtNgayDH = (TextView) row.findViewById(R.id.txtNgayDH);
        TextView txtNgayGH = (TextView) row.findViewById(R.id.txtNgayGH);

        ThanhToanModel thanhToanModel = list.get(position);
        txtTenKH.setText(thanhToanModel.tenKH + "");
        txtEmail.setText(thanhToanModel.email + "");
        txtSDT.setText(thanhToanModel.SDT + "");
        txtDiaChi.setText(thanhToanModel.diaChi + "");
        txtNgayDH.setText(thanhToanModel.ngayDH + "");
        txtNgayGH.setText(thanhToanModel.ngayGH + "");

        return row;
    }
}

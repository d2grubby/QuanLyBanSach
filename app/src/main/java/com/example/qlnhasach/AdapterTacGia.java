package com.example.qlnhasach;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTacGia extends BaseAdapter {

    Activity context;
    int myLayout;
    ArrayList<TacGiaModel> list;

    public AdapterTacGia(Activity context, int myLayout, ArrayList<TacGiaModel> list) {
        this.context = context;
        this.myLayout = myLayout;
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
        convertView = inflater.inflate(myLayout, null);
        TextView txtTacGia = (TextView) convertView.findViewById(R.id.txtTacGia);

        TacGiaModel tacGiaModel = list.get(position);
        txtTacGia.setText(tacGiaModel.tenTacGia + "");
        return convertView;
    }
}
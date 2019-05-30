package com.example.qlnhasach;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterSach extends BaseAdapter {

    Activity context;
    ArrayList<Sach> list;

    public AdapterSach(Activity context, ArrayList<Sach> list) {
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
        View row = inflater.inflate(R.layout.sach, null);
        ImageView imgSach = (ImageView) row.findViewById(R.id.imgSach);
        TextView txtTenSach1 = (TextView) row.findViewById(R.id.txtTenSach1);
        TextView txtGiaTien1 = (TextView) row.findViewById(R.id.txtGiaTien1);

        Sach sach = list.get(position);
        txtTenSach1.setText(sach.tenSach + "");
        txtGiaTien1.setText(sach.giaTien + " đ");

        Bitmap bmSach1 = BitmapFactory.decodeByteArray(sach.anhBia, 0, sach.anhBia.length);
        imgSach.setImageBitmap(bmSach1);

        return row;
    }
}

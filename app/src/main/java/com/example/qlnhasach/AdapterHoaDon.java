package com.example.qlnhasach;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterHoaDon extends BaseAdapter {
    Activity context;
    ArrayList<HoaDon> list;

    public AdapterHoaDon(Activity context, ArrayList<HoaDon> list) {
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
        View row = inflater.inflate(R.layout.activity_hoa_don, null);
        TextView txtIDHD = (TextView) row.findViewById(R.id.txtIDHD);
        TextView txtTenKH = (TextView) row.findViewById(R.id.txtTenKH);
        TextView txtTongTien = (TextView) row.findViewById(R.id.txtTongTien);
        TextView txtDC = (TextView) row.findViewById(R.id.txtDC);
        TextView txtNgayDH = (TextView) row.findViewById(R.id.txtNgayDH);
        TextView txtNgayGH = (TextView) row.findViewById(R.id.txtNgayGH);
        TextView txtTT = (TextView) row.findViewById(R.id.txtTT);
        TextView txtLTT = (TextView) row.findViewById(R.id.txtLTT);
        Button btnCapNhat = (Button) row.findViewById(R.id.btnCapNhat);
        Button btnXoa = (Button) row.findViewById(R.id.btnXoa);
        Button btnChiTiet = (Button) row.findViewById(R.id.btnChiTiet);

        final HoaDon hoaDon = list.get(position);
        txtIDHD.setText(hoaDon.idHD + "");
        txtTenKH.setText(hoaDon.tenKH + "");
        txtTongTien.setText("Tổng tiền: " + hoaDon.tongTien + " đ");
        txtDC.setText("Địa chỉ: " + hoaDon.diaChi + "");
        txtNgayDH.setText("Ngày đặt hàng: " + hoaDon.ngayDH + "");
        txtNgayGH.setText("Ngày giao hàng: " + hoaDon.ngayGH + "");
        txtTT.setText("Tình trạng: " + hoaDon.tinhTrang + "");
        txtLTT.setText(hoaDon.loaitt);

        if (txtTT.getText().equals("Tình trạng: Đang xử lý"))
            btnXoa.setVisibility(View.VISIBLE);
        else
            btnXoa.setVisibility(View.INVISIBLE);

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateHoaDon.class);
                intent.putExtra("ID", hoaDon.idHD);
                context.startActivity(intent);
            }
        });

        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ChiTiet.class);
                intent.putExtra("ID", hoaDon.idHD);
                context.startActivity(intent);
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận Hủy");
                builder.setMessage("Bạn có chắc chắn muốn Hủy ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateAndDelete(hoaDon.idHD);
                        if(DangNhap.mangUserType.get(0).getUserType().equals("user"))
                        {
                            Intent intent2 = new Intent(context, LichSuMuaHang.class);
                            context.startActivity(intent2);
                        }
                        else if(DangNhap.mangUserType.get(0).getUserType().equals("admin"))
                        {
                            Intent intent = new Intent(context, danhsachHoaDon.class);
                            context.startActivity(intent);
                        }
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

    //Xóa hóa đơn
    private void updateAndDelete (int idhd){
        SQLiteDatabase database = Database.initDatabase(context, "qlnhasach.sqlite");

        //Update số lượng còn của những cuốn sách trong hóa đơn
        Cursor cursor2 = database.rawQuery("SELECT * FROM chitiethoadon WHERE idhd = ?", new String[]{idhd + "",});

        for (int i = 0; i < cursor2.getCount(); i++) {
            cursor2.moveToPosition(i);
            int idsach = cursor2.getInt(1);
            int soluong = cursor2.getInt(2);

            //Update số lượng còn
            Cursor cursor3 = database.rawQuery("SELECT * FROM sach WHERE idsach = ?", new String[]{idsach + "",});
            cursor3.moveToFirst();
            int soluongcon = cursor3.getInt(8);
            int soluongmoi = soluongcon + soluong;
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("soluongcon", soluongmoi);
            database.update("sach", contentValues2, "idsach = ?", new String[]{idsach + ""});
        }
        database.delete("chitiethoadon", "idhd = ?", new String[]{idhd + ""});
        database.delete("hoadon", "idhd = ?", new String[]{idhd + ""});

    }
}

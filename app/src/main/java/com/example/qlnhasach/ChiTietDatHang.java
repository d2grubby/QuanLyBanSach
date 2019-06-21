package com.example.qlnhasach;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ChiTietDatHang extends MainActivity {
    final String DATABASE_NAME = "qlnhasach.sqlite";
    SQLiteDatabase database;
    TextView txtTen, txtDiaChi, txtSDT, txtNgayDH, txtNgayGH;
    Button btnDatHang, btnPaypal;

    String tenKH = DangNhap.thongTinUser.get(0).getTenKH();
    String diaChi = DangNhap.thongTinUser.get(0).getDiaChi();
    int sdt = DangNhap.thongTinUser.get(0).getSdt();

    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    String ngayDH = df.format(Calendar.getInstance().getTime());

    Calendar calendar = Calendar.getInstance();
    String ngayGH;

    int tongtien1;
    int idmoinhat;
    String loaithanhtoan;

    //Paypal
    public static String PAYPAL_CLIENT_ID = "AW76nDJdIlriwodD9jSeTUegjTSkwhpfdfRliHF20zuMux_gLXdEE_8A6pQ_4GY-2s8muaBVdHBdNmmt";
    public static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID);
    String amount;

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    loaithanhtoan = "Thanh toán Paypal";
                    insertHoaDon();
                    layIDMoiNhat();
                    insertChiTietHoaDon();
                    updateSoLuongCon(); //Update số lượng còn trong kho
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Bạn chưa thanh toán hóa đơn !", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Bạn không đủ tiền thanh toán", Toast.LENGTH_SHORT).show();
    }

    private double currencyConverter() {
        double currencyUSD = 23383.13;
        double uSD = tongtien1 / currencyUSD;
        return uSD;
    }

    private void processPayment() {
        amount = String.valueOf(currencyConverter());
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "USD",
                "Thanh toán hóa đơn sách", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dat_hang);
        tongtien1 = getIntent().getExtras().getInt("tongtien");

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        addControl();
        initUI();

    }

    private void addControl() {
        txtTen = (TextView) findViewById(R.id.txtTen);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChi);
        txtSDT = (TextView) findViewById(R.id.txtSDT);
        txtNgayDH = (TextView) findViewById(R.id.txtNgayDH);
        txtNgayGH = (TextView) findViewById(R.id.txtNgayGH);
        btnDatHang = (Button) findViewById(R.id.btnDatHang);
        btnPaypal = (Button) findViewById(R.id.btnPaypal);

        calendar.add(Calendar.DATE, 7);
        ngayGH = df.format(calendar.getTime());

    }

    private void initUI() {
        txtTen.setText(tenKH);
        txtDiaChi.setText("Địa chỉ: " + diaChi);
        txtSDT.setText("Số điện thoại: " + String.valueOf(sdt));
        txtNgayDH.setText("Ngày đặt hàng: " + ngayDH);
        txtNgayGH.setText("Ngày giao hàng: " + ngayGH);

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), String.valueOf(tongtien1), Toast.LENGTH_SHORT).show(); --Test xem extra tongtien1 có qua không
                loaithanhtoan = "Thanh toán COD";
                insertHoaDon(); //Insert bảng hóa đơn đầu tiên
                layIDMoiNhat(); //Lấy id của hóa đơn vừa tạo
                insertChiTietHoaDon(); //Dùng id của hóa đơn vừa lấy + với các thông tin của các quyển sách đang có trong ArrayList manggiohang. Insert hết vào bảng chitiethoadon
                updateSoLuongCon(); //Update số lượng còn trong kho
                startActivity(new Intent(ChiTietDatHang.this, MainActivity.class));
                Toast.makeText(getApplicationContext(), "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
            }
        });

        btnPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });
    }

    private void insertHoaDon() {
        int idkh = DangNhap.thongTinUser.get(0).getIdKH();
        int tongtien = tongtien1;
        String diachi = DangNhap.thongTinUser.get(0).getDiaChi();
        String ngaydh = ngayDH;
        String ngaygh = ngayGH;
        String tinhtrang = "Đang xử lý";


        ContentValues contentValues = new ContentValues();
        contentValues.put("idkh", idkh);
        contentValues.put("tongtien", tongtien);
        contentValues.put("diachi", diachi);
        contentValues.put("ngaydathang", ngaydh);
        contentValues.put("ngaygiaohang", ngaygh);
        contentValues.put("tinhtrang", tinhtrang);
        contentValues.put("loaithanhtoan", loaithanhtoan);


        SQLiteDatabase database = Database.initDatabase(this, "qlnhasach.sqlite");
        database.insert("hoadon", null, contentValues);
    }

    private void layIDMoiNhat() { //Lấy id hóa đơn gần đây nhất (vừa insert vào bảng) của khách hàng đã đăng nhập
        database = Database.initDatabase(this, DATABASE_NAME);
        int idUser = DangNhap.mangUserType.get(0).getIdUser();
        Cursor cursor = database.rawQuery("SELECT * FROM hoadon where idkh = ? ORDER BY idhd DESC LIMIT 1", new String[]{idUser + "",});
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            idmoinhat = cursor.getInt(0);
        }
    }

    private void insertChiTietHoaDon() {
        for (int i = 0; i < MainActivity.manggiohang.size(); i++) { //Có bao nhiêu sách trong mảng arraylist trong giỏ hàng thì insert bấy nhiêu
            int idhd = idmoinhat;
            int idsach = MainActivity.manggiohang.get(i).getIdsach();
            int soluong = MainActivity.manggiohang.get(i).getSoluongsach();
            int dongia = MainActivity.manggiohang.get(i).getGiatien();
            int thanhtien = MainActivity.manggiohang.get(i).getGiatien() * MainActivity.manggiohang.get(i).getSoluongsach();

            ContentValues contentValues = new ContentValues();
            contentValues.put("idhd", idhd);
            contentValues.put("idsach", idsach);
            contentValues.put("soluong", soluong);
            contentValues.put("dongia", dongia);
            contentValues.put("thanhtien", thanhtien);

            SQLiteDatabase database = Database.initDatabase(this, "qlnhasach.sqlite");
            database.insert("chitiethoadon", null, contentValues);
        }
    }

    private void updateSoLuongCon() {
        for (int i = 0; i < MainActivity.manggiohang.size(); i++) { //Có bao nhiêu sách trong mảng arraylist trong giỏ hàng thì update số lượng bấy nhiêu
            int idsach = MainActivity.manggiohang.get(i).getIdsach();
            int soluong = MainActivity.manggiohang.get(i).getSoluongsach();

            //Update số lượng còn
            database = Database.initDatabase(this, DATABASE_NAME);
            Cursor cursor = database.rawQuery("SELECT * FROM sach WHERE idsach = ?", new String[]{idsach + "",});
            cursor.moveToFirst();
            int soluongcon = cursor.getInt(8);
            int soluongmoi = soluongcon - soluong;
            if(soluongmoi <= 0)
            {
                soluongmoi = 0;
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("soluongcon", soluongmoi);
                database.update("sach", contentValues2, "idsach = ?", new String[]{idsach + ""});
            }
            else
            {
                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("soluongcon", soluongmoi);
                database.update("sach", contentValues2, "idsach = ?", new String[]{idsach + ""});
            }
        }
    }
}
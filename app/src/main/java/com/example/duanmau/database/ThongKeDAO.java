package com.example.duanmau.database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.duanmau.model.Sach;
import com.example.duanmau.model.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO  {
    Context context;
    SQLiteDatabase db;
    SQLite sqLite;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public ThongKeDAO(Context context){
        this.context = context;
        sqLite = new SQLite(context);
        db = sqLite.getWritableDatabase();
    }
    @SuppressLint("Range")
    public List<Top> getTop(){
        String sqlTop = "SELECT maSach, count(maSach) as soLuong FROM PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10";
        List<Top> list = new ArrayList<>();
        SachDao sachDao = new SachDao(context);
        Cursor c =db.rawQuery(sqlTop,null);
        while (c.moveToNext()){
            Top top = new Top();
             Sach sach = sachDao.getID(c.getString(c.getColumnIndex("maSach")));
             top.setTenSach(sach.getTenSach());
             top.setSoLuong(Integer.parseInt(String.valueOf(c.getColumnIndex("soLuong"))));
             list.add(top);
        }
        return  list;

    }
    @SuppressLint("Range")
    public int getDoanhThu(String tuNgay, String denNgay){
        String sqlDoanhThu = "SELECT SUM(tienThue) as doanhThu FROM PhieuMuon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<>();
        Cursor c = db.rawQuery(sqlDoanhThu,new String[]{tuNgay,denNgay});
        while (c.moveToNext()){
            try {
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhThu"))));
            }
            catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
}

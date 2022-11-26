package com.example.duanmau.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.example.duanmau.adapter.SachAdapter;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDao {
    private SQLiteDatabase db;
    SQLite sqLite;
    Context context;

    public SachDao(Context context){
        this.context = context;
        sqLite = new SQLite(context);
        db = sqLite.getReadableDatabase();
    }
    public int insert(Sach sach){
        ContentValues values = new ContentValues();
        values.put("maSach",sach.getMaSach());
        values.put("maLoai",sach.getMaLoai());
        values.put("tenSach",sach.getTenSach());
        values.put("iaThue",sach.getGiaThue());
        values.put("giaNhap",sach.getGiaNhap());
        long kq = db.insert("Sach",null,values);
        if (kq <=0){
            return -1;
        }
        return 1;
    }
    public int update(Sach sach){
        ContentValues values = new ContentValues();
        values.put("maSach",sach.getMaSach());
        values.put("maLoai",sach.getMaLoai());
        values.put("tenSach",sach.getTenSach());
        values.put("iaThue",sach.getGiaThue());
        values.put("giaNhap",sach.getGiaNhap());

        long kq = db.update("Sach",values,"maSach=?",new String[]{String.valueOf(sach.getMaSach())});
        if (kq <=0){
            return -1;
        }
        return 1;
    }
    public int delete(String id){
        return db.delete("Sach","maSach=?",new String[]{id});
    }
    @SuppressLint("Range")
    public List<Sach> getData(String sql, String...selectionArgs){
        List<Sach> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            Sach sach = new Sach();
            sach.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("maSach"))));
            sach.setTenSach(c.getString(c.getColumnIndex("tenSach")));
            sach.setGiaThue(Integer.parseInt(c.getString(c.getColumnIndex("iaThue"))));
            sach.setGiaNhap(Integer.parseInt(c.getString(c.getColumnIndex("giaNhap"))));
            sach.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maloai"))));

            list.add(sach);
        }
        return list;
    }

    public List<Sach> getAll(){
        String sql = "SELECT * FROM Sach";
        return getData(sql);
    }

    public Sach getID(String id){
        String sql = "SELECT * FROM Sach WHERE maSach=?";
        List<Sach> sach = getData(sql,id);
        return sach.get(0);
    }

}

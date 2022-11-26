package com.example.duanmau.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.SQLite;
import com.example.duanmau.database.SQLite;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDao {

    private SQLiteDatabase db;
    SQLite sqLite;
    Context context;
    public List<LoaiSach> list = new ArrayList<>();

    public LoaiSachDao(Context context){
        this.context = context;
        sqLite = new SQLite(context);
        db = sqLite.getReadableDatabase();
    }
    public int insert(LoaiSach loai){
        ContentValues values = new ContentValues();
        values.put("maLoai",loai.getMaLoai());
        values.put("tenLoai",loai.getTenLoai());

        long kq = db.insert("LoaiSach",null,values);
        if (kq <=0){
            return -1;
        }
        return 1;
    }
    public int update(LoaiSach loai){
        ContentValues values = new ContentValues();
        values.put("maLoai",loai.getMaLoai());
        values.put("tenLoai",loai.getTenLoai());
        long kq = db.update("LoaiSach",values,"maLoai=?",new String[]{String.valueOf(loai.getMaLoai())});
        if (kq <=0){
            return -1;
        }
        return 1;
    }
    public int delete(String id){
        return db.delete("LoaiSach","maLoai=?",new String[]{id});
    }
    @SuppressLint("Range")
    public List<LoaiSach> getData(String sql, String...selectionArgs){
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            LoaiSach loai = new LoaiSach();
            loai.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maLoai"))));
            loai.setTenLoai(c.getString(c.getColumnIndex("tenLoai")));
            list.add(loai);

        }
        return list;
    }

    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM LoaiSach";
        return getData(sql);
    }

    public LoaiSach getID(String id){
        String sql = "SELECT * FROM LoaiSach WHERE maLoai=?";
        List<LoaiSach> loai = getData(sql,id);
        return loai.get(0);
    }
}

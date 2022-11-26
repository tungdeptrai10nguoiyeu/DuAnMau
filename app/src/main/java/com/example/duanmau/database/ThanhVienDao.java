package com.example.duanmau.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDao {
    private SQLiteDatabase db;
    SQLite sqLite;
    Context context;
    public ThanhVienDao(Context context){
        this.context = context;
        sqLite = new SQLite(context);
        db = sqLite.getWritableDatabase();
    }
    public int insert(ThanhVien tv){
        ContentValues values = new ContentValues();
        values.put("maTV",tv.getMaTV());
        values.put("hoTen",tv.getHoTen());
        values.put("namSinh",tv.getNamSinh());
        long kq =  db.insert("ThanhVien",null,values);
        if (kq <= 0){
            return -1;
        }
        return 1;
    }
    public int update(ThanhVien tv){
        ContentValues values = new ContentValues();
        values.put("maTV",tv.getMaTV());
        values.put("hoTen",tv.getHoTen());
        values.put("namSinh",tv.getNamSinh());
        long kq = db.update("ThanhVien",values,"maTV=?",new String[]{String.valueOf(tv.getMaTV())});
        if (kq <= 0){
            return -1;
        }
        return 1;

    }
    public int delete(String id){
        return db.delete("ThanhVien","maTV=?",new String[]{id});
    }
    @SuppressLint("Range")
    public List<ThanhVien> getData(String sql, String...selectionArgs){
        List<ThanhVien> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ThanhVien tv = new ThanhVien();
            tv.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            tv.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            tv.setNamSinh(c.getString(c.getColumnIndex("namSinh")));
            list.add(tv);
        }
        return list;
    }

    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM ThanhVien";
        return getData(sql);
    }

    public ThanhVien getID(String id){
        String sql = "SELECT * FROM ThanhVien WHERE maTV=?";
        List<ThanhVien> tv = getData(sql,id);
        return tv.get(0);
    }
}

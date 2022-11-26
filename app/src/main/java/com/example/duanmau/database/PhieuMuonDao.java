package com.example.duanmau.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duanmau.database.PhieuMuonDao;
import com.example.duanmau.database.SQLite;
import com.example.duanmau.model.PhieuMuon;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDao {
    private SQLiteDatabase db;
    SQLite sqLite;
    Context context;
    public PhieuMuonDao(Context context){
        this.context = context;
        sqLite = new SQLite(context);
        db = sqLite.getReadableDatabase();
    }
    public int insert(PhieuMuon pm){
        ContentValues values = new ContentValues();
        values.put("maPM",pm.getMaPM());
        values.put("maTV",pm.getMaTV());
        values.put("maSach",pm.getMaSach());
        values.put("maTT",pm.getMaTT());
        values.put("ngay",pm.getNgay());
        values.put("traSach",pm.getTraSach());
        values.put("tienThue",pm.getTienThue());

        long kq = db.insert("PhieuMuon",null,values);
        if (kq <= 0){
            return -1;
        }
            return 1;
    }
    public int update(PhieuMuon pm){
        ContentValues values = new ContentValues();
        values.put("maPM",pm.getMaPM());
        values.put("maTV",pm.getMaTV());
        values.put("maSach",pm.getMaSach());
        values.put("maTT",pm.getMaTT());
        values.put("ngay",pm.getNgay());
        values.put("traSach",pm.getTraSach());
        values.put("tienThue",pm.getTienThue());

        long kq = db.update("PhieuMuon",values,"maPM=?",new String[]{String.valueOf(pm.getMaPM())});
        if (kq <=0){
            return -1;
        }
        return 1;
    }
    public int delete(String id){
        return db.delete("PhieuMuon","maPM=?",new String[]{id});
    }
    @SuppressLint("Range")
    public List<PhieuMuon> getData(String sql, String...selectionArgs){
        List<PhieuMuon> list = new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            PhieuMuon pm = new PhieuMuon();
            pm.setMaPM(Integer.parseInt(c.getString(c.getColumnIndex("maPM"))));
            pm.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("maTV"))));
            pm.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("maSach"))));
            pm.setMaTT(c.getString(c.getColumnIndex("maTT")));
            pm.setNgay(c.getString(c.getColumnIndex("ngay")));
            pm.setTraSach(Integer.parseInt(c.getString(c.getColumnIndex("traSach"))));
            pm.setTienThue(Integer.parseInt(c.getString(c.getColumnIndex("tienThue"))));
            list.add(pm);
        }
        return list;
    }

    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM PhieuMuon";
        return getData(sql);
    }

    public PhieuMuon getID(String id){
        String sql = "SELECT * FROM PhieuMuon WHERE maPM=?";
        List<PhieuMuon> pm = getData(sql,id);
        return pm.get(0);
    }
}

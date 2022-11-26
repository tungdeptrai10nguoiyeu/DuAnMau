package com.example.duanmau.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Delete;

import com.example.duanmau.model.ThanhVien;
import com.example.duanmau.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    SQLite sqLite;
    SQLiteDatabase db;
    Context context;
    public ThuThuDAO(Context context){
        this.context = context;
        sqLite = new SQLite(context);
        db = sqLite.getWritableDatabase();
    }
    public int insert(ThuThu tt){
        ContentValues values = new ContentValues();
        values.put("maTT",tt.getMaTT());
        values.put("hoTen",tt.getHoTen());
        values.put("matKhau",tt.getMatKhau());
        long kq = db.insert("ThuThu",null,values);
        if (kq <= 0){
            return -1;
        }
        return 1;
    }
    public  int update(ThuThu tt){
        ContentValues values = new ContentValues();
        values.put("maTT",tt.getMaTT());
        values.put("hoTen",tt.getHoTen());
        values.put("matKhau",tt.getMatKhau());
        long kq = db.update("ThuThu",values,"maTT=?",new String[]{String.valueOf(tt.getMaTT())});
        if (kq <= 0){
            return -1;
        }
        return 1;

    }

    public int delete(String id){
        return db.delete("ThuThu","maTT=?",new String[]{id});
    }


    @SuppressLint("Range")
    public List<ThuThu> getData(String sql, String ...selectionArgs){
        List<ThuThu> thuList =  new ArrayList<>();
        Cursor c = db.rawQuery(sql,selectionArgs);
        while (c.moveToNext()){
            ThuThu tt = new ThuThu();
            tt.setMaTT(c.getString(c.getColumnIndex("maTT")));
            tt.setHoTen(c.getString(c.getColumnIndex("hoTen")));
            tt.setMatKhau(c.getString(c.getColumnIndex("matKhau")));
            thuList.add(tt);
        }
        return thuList;
    }
    public ThuThu getID(String id){
        String sql = "SELECT * FROM ThuThu WHERE maTT=?";
        List<ThuThu> thuList = getData(sql,id);
        return thuList.get(0);
    }
    public List<ThuThu> getAll(){
        String sql ="SELECT * FROM ThuThu";
        return getData(sql);
    }
    public int checkLogin(String strUser, String strPass){
        List<ThuThu> ls = getAll();
        for (ThuThu t:ls) {
            if(t.getMaTT().equals(strUser) && t.getMatKhau().equals(strPass)){
                return 1;
            }
        }return -1;
    }
    public  int checkTKdau(){
        List<ThuThu> ls = getAll();
        if (ls.size() <= 0){
            return 1;
        }
        return -1;
    }



}

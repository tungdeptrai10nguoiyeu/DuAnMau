package com.example.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.duanmau.model.ThuThu;

public class SQLite extends SQLiteOpenHelper {
    public  static final  String Table_ThanhVien = "CREATE TABLE ThanhVien(" +
            "maTV integer PRIMARY KEY AUTOINCREMENT" +
            ",hoTen text," +
            " namSinh text);";

    public  static final String Table_ThuThu = "CREATE TABLE ThuThu(" +
            "maTT text PRIMARY KEY," +
            "hoTen text," +
            "matKhau text);";

    public static final String Table_LoaiSach="CREATE TABLE LoaiSach(" +
            "maLoai integer PRIMARY KEY," +
            "tenLoai text);";

    public static final String Table_Sach = "CREATE TABLE Sach(" +
            "maSach integer PRIMARY key AUTOINCREMENT," +
            "tenSach text," +
            "iaThue integer," +
            "giaNhap integer," +
            "maloai interger REFERENCES LoaiSach(maloai));";

    public static final String Table_PhieuMuon= "CREATE TABLE PhieuMuon(" +
            "maPM interger PRIMARY KEY," +
            " maTT text REFERENCES ThuThu(matt)," +
            "maTV integer REFERENCES ThanhVien(matv)," +
            " maSach integer REFERENCES Sach(masach)," +
            "tienThue real," +
            " traSach integer," +
            "ngay date);";



    public SQLite(@Nullable Context context) {
        super(context, "database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table_ThuThu);
        db.execSQL(Table_LoaiSach);
        db.execSQL(Table_ThanhVien);
        db.execSQL(Table_PhieuMuon);
        db.execSQL(Table_Sach);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if EXISTS ThanhVien");
        db.execSQL("DROP TABLE if EXISTS ThuThu");
        db.execSQL("DROP TABLE if EXISTS LoaiSach");
        db.execSQL("DROP TABLE if EXISTS Sach");
        db.execSQL("DROP TABLE if EXISTS PhieuMuon");

        onCreate(db);
    }
}

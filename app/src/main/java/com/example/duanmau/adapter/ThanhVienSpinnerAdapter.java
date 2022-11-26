package com.example.duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duanmau.R;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienSpinnerAdapter extends ArrayAdapter<ThanhVien> {
    Context context;
    ArrayList<ThanhVien> list;
    TextView tvMaTv,tvTenTv;
    public ThanhVienSpinnerAdapter(@NonNull Context context, ArrayList<ThanhVien> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.spinner_layout,null);
        }
        final  ThanhVien tv = list.get(position);
        if (v != null){
            tvMaTv = v.findViewById(R.id.spn_tvMaloai);
            tvMaTv.setText(tv.getMaTV()+".");
            tvTenTv = v.findViewById(R.id.spn_tvTenSach);
            tvTenTv.setText(tv.getHoTen());
        }

        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;


        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.spinner_layout,null);
        }
        final  ThanhVien tv = list.get(position);
        if (v != null){
            tvMaTv = v.findViewById(R.id.spn_tvMaloai);
            tvMaTv.setText(tv.getMaTV()+".");
            tvTenTv = v.findViewById(R.id.spn_tvTenSach);
            tvTenTv.setText(tv.getHoTen());
        }

        return v;
    }
}

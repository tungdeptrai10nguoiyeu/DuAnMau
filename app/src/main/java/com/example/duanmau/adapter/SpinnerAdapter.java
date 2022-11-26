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

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<LoaiSach> {
    Context context;
    ArrayList<LoaiSach> list;
    TextView tvMaLoai,tvTenLoai;
    public SpinnerAdapter(@NonNull Context context, ArrayList<LoaiSach> list) {
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
        final  LoaiSach loaiSach = list.get(position);
        if (v != null){
            tvMaLoai = v.findViewById(R.id.spn_tvMaloai);
            tvMaLoai.setText(loaiSach.getMaLoai()+".");
            tvTenLoai = v.findViewById(R.id.spn_tvTenSach);
            tvTenLoai.setText(loaiSach.getTenLoai());
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
        final  LoaiSach loaiSach = list.get(position);
        if (v != null){
            tvMaLoai = v.findViewById(R.id.spn_tvMaloai);
            tvMaLoai.setText(loaiSach.getMaLoai()+".");
            tvTenLoai = v.findViewById(R.id.spn_tvTenSach);
            tvTenLoai.setText(loaiSach.getTenLoai());
        }

        return v;
    }
}

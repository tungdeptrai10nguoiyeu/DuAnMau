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
import com.example.duanmau.model.Sach;

import java.util.ArrayList;

public class SachSpinnerAdapter extends ArrayAdapter<Sach> {
    Context context;
    ArrayList<Sach> list;
    TextView tvMaSach,tvTenSach;
    public SachSpinnerAdapter(@NonNull Context context, ArrayList<Sach> list) {
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
        final  Sach s = list.get(position);
        if (v != null){
            tvMaSach = v.findViewById(R.id.spn_tvMaloai);
            tvMaSach.setText(s.getMaSach()+".");
            tvTenSach = v.findViewById(R.id.spn_tvTenSach);
            tvTenSach.setText(s.getTenSach());
        }

        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.spinner_layout, null);
        }
        final Sach s = list.get(position);
        if (v != null) {
            tvMaSach = v.findViewById(R.id.spn_tvMaloai);
            tvMaSach.setText(s.getMaSach()+".");
            tvTenSach = v.findViewById(R.id.spn_tvTenSach);
            tvTenSach.setText(s.getTenSach());
        }

        return v;
    }
}

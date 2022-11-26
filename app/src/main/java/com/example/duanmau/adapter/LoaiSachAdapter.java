package com.example.duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duanmau.Fragment.LoaiSachFragment;
import com.example.duanmau.R;
import com.example.duanmau.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachAdapter extends ArrayAdapter<LoaiSach> {
    LoaiSachFragment fragment;
    ArrayList<LoaiSach>list ;
    Context context;
    TextView tvMaLoai,tvTenLoai;
    ImageView imgDel;
    public LoaiSachAdapter(@NonNull Context context, LoaiSachFragment fragment,ArrayList<LoaiSach>list ) {
        super(context,0,list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if ( v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_loaisach,null);

        }
        final LoaiSach ls = list.get(position);
        if (ls != null){
            tvMaLoai = v.findViewById(R.id.ls_tvMaLoai);
            tvTenLoai = v.findViewById(R.id.ls_tvTenLoai);
            imgDel = v.findViewById(R.id.ls_imgDel);

            tvMaLoai.setText("Mã loai: "+ ls.getMaLoai());
            tvTenLoai.setText("Tên loại: "+ ls.getTenLoai());

        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(ls.getMaLoai()));
            }
        });
        return v;
    }
}

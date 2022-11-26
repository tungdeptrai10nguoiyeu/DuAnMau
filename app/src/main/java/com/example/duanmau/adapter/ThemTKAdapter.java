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

import com.example.duanmau.Fragment.ThemTKFragment;
import com.example.duanmau.R;
import com.example.duanmau.model.ThuThu;

import java.util.List;

public class ThemTKAdapter extends ArrayAdapter<ThuThu> {
    Context context;
    ThemTKFragment fragment;
     List<ThuThu> objects;
     TextView tvMatt,tvHoten,tvPass;
    public ThemTKAdapter(@NonNull Context context, ThemTKFragment fragment, @NonNull List<ThuThu> objects) {
        super(context, 0, objects);
        this.context = context;
        this.fragment = fragment;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v  = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_them_tk,null);
        }
        final ThuThu tt = objects.get(position);
        if (tt != null){
            tvMatt = v.findViewById(R.id.itemTK_tvmaTT);
            tvHoten = v.findViewById(R.id.itemTK_tvHoten);
            tvPass = v.findViewById(R.id.itemTK_tvPass);


            tvMatt.setText("User: "+tt.getMaTT());
            tvHoten.setText("Họ tên: " + tt.getHoTen());
            tvPass.setText("Passwork: "+ tt.getMatKhau());

        }

        return v;
    }
}

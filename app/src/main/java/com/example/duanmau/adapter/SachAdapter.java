package com.example.duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duanmau.Fragment.SachFragment;
import com.example.duanmau.R;
import com.example.duanmau.database.LoaiSachDao;
import com.example.duanmau.database.SachDao;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.Sach;

import java.util.List;

public class SachAdapter extends ArrayAdapter<Sach> {
    List<Sach> list;
    SachFragment fragment;
    Context context;
    LoaiSach loaiSach;
    TextView tvmaSach,tvTen,tvGia,tvLoai,tvgiaNhap;
    ImageView imgDel;
    public SachAdapter(@NonNull Context context, SachFragment fragment, List<Sach> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_sach,null);
        }

        final Sach s = list.get(position);

        if (s != null){

            LoaiSachDao dao = new LoaiSachDao(context);
            String a = String.valueOf(s.getMaLoai());
            loaiSach = dao.getID(a);



            tvmaSach = v.findViewById(R.id.itemSach_tvMaSach);
            tvTen = v.findViewById(R.id.itemSach_tvTenSach);
            tvGia = v.findViewById(R.id.itemSach_tvGia);
            tvgiaNhap = v.findViewById(R.id.itemSach_tvgiaNhap);
            tvLoai = v.findViewById(R.id.itemSach_tvLoaiSach);
            imgDel = v.findViewById(R.id.itemSach_imgDel);

            tvmaSach.setText("Mã Sách: " + s.getMaSach());
            tvTen.setText("Tên Sách: "+ s.getTenSach());
            tvGia.setText("Giá :" + s.getGiaThue());
            tvgiaNhap.setText("Giá Nhập: " + s.getGiaNhap());
            tvLoai.setText("Loại Sách: "+ loaiSach.getTenLoai());

        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(s.getMaSach()));
            }
        });



        return v;
    }
}

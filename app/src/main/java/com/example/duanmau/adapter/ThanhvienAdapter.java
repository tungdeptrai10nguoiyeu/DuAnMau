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
import androidx.room.Room;

import com.example.duanmau.Fragment.ThanhVienFragment;
import com.example.duanmau.R;
import com.example.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhvienAdapter extends ArrayAdapter<ThanhVien> {
    Context context;
    ThanhVienFragment fragment;
    ArrayList<ThanhVien> list;
    TextView tvmatv;
    TextView tvten;
    TextView tvnamsinh;
    ImageView imgDel;


    public ThanhvienAdapter(@NonNull Context context, ThanhVienFragment fragment, ArrayList<ThanhVien> list) {
        super(context,0,list);
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
            v = inflater.inflate(R.layout.item_thanhvien,null);

        }
        final ThanhVien item = list.get(position);
        if (item != null){
            tvmatv = v.findViewById(R.id.item_matv);
            tvmatv.setText("Mã thành viên: "+item.getMaTV());

            tvten = v.findViewById(R.id.item_nameTv);
            tvten.setText("Tên thành viên:"+ item.getHoTen());

            tvnamsinh = v.findViewById(R.id.item_namsinh);
            tvnamsinh.setText("Năm sinh: "+item.getNamSinh());

            imgDel = v.findViewById(R.id.item_imgdel);

        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(item.getMaTV()));
            }
        });
        return v;

    }
}

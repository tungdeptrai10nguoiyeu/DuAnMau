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
import com.example.duanmau.model.Top;

import java.util.List;

public class TopAdapter extends ArrayAdapter<Top> {
    Context context;
    List<Top> objects;
    TextView tvTenSach,tvSL;
    int temp = 0;
    public TopAdapter(@NonNull Context context,  @NonNull List<Top> objects) {
        super(context, 0, objects);
        this.context = context;
        this.objects = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_lv_top,null);
        }
        final  Top top = objects.get(position);
        if (top !=null){
            temp++;
            tvTenSach = v.findViewById(R.id.item_top_tvTenSach);
            tvSL = v.findViewById(R.id.item_top_tvSL);

            tvTenSach.setText("Tên Sách: "+top.getTenSach());
            tvSL.setText("Số lượng: "+String.valueOf(temp));
        }

        return v;
    }
}

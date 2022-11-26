package com.example.duanmau.adapter;

import static java.util.Calendar.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.duanmau.Fragment.PhieuMuonFragment;
import com.example.duanmau.R;
import com.example.duanmau.database.SachDao;
import com.example.duanmau.database.ThanhVienDao;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.Sach;
import com.example.duanmau.model.ThanhVien;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {
    Context context;
    PhieuMuonFragment fragment;
    List<PhieuMuon> list;

    ThanhVienDao thanhVienDao;
    ThanhVien tv = new ThanhVien();

    SachDao  sachDao;
    Sach sach = new Sach();
    TextView tvMaPm,tvTenTV,tvTenSach,tvTienThue,tvTraSach,tvNgaytra;
    ImageView imgDel;
    LinearLayout linearLayout;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public PhieuMuonAdapter(@NonNull Context context, PhieuMuonFragment fragment, @NonNull List<PhieuMuon> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_phieumuon,null);
        }
        final PhieuMuon pm = list.get(position);

        if (pm != null){
            thanhVienDao = new ThanhVienDao(getContext());
            String idTV = String.valueOf(pm.getMaTV());
            tv = thanhVienDao.getID(idTV);

            sachDao = new SachDao(getContext());
            String idSach = String.valueOf(pm.getMaSach());
            sach = sachDao.getID(idSach);

            linearLayout = v.findViewById(R.id.itemPm_layout);
            tvMaPm = v.findViewById(R.id.itemPm_maPm);
            tvTenTV = v.findViewById(R.id.itemPm_tenTV);
            tvTenSach = v.findViewById(R.id.itemPm_tenSach);
            tvTienThue = v.findViewById(R.id.itemPm_tienThue);
            tvTraSach = v.findViewById(R.id.itemPm_traSach);
            tvNgaytra =  v.findViewById(R.id.itemPm_ngayTra);
            imgDel = v.findViewById(R.id.itemPm_imgDel);

            tvMaPm.setText("Mã Phiếu: " + pm.getMaPM());
            tvTenTV.setText("Tên thành viên: " + tv.getHoTen());
            tvTenSach.setText("Tên Sach: "+sach.getTenSach());
            tvTienThue.setText("Tiền thuê: "+ sach.getGiaThue());
            tvNgaytra.setText( simpleDateFormat.format(getInstance().getTime()));

//           String date= tvNgaytra.getText().toString();
//            try {
//                Date  date1 = new SimpleDateFormat("yyyy/MM/dd").parse(date);
//
//                Date now = Calendar.getInstance().getTime();
//                long lis = now.getDay() - date1.getDay();
//                tvNgaytra.setText(String.valueOf(lis));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }


            //===============
            if (pm.getTraSach() == 1){
                tvTraSach.setTextColor(Color.BLUE);
                tvTraSach.setText("Đã trả sách");
            }else {
                tvTraSach.setTextColor(Color.RED);
                tvTraSach.setText("Chưa trả sách");
            }
            //====================
            if(pm.getMaPM() % 2 == 0){
                tvMaPm.setTypeface(null, Typeface.BOLD);
            }else {
                tvMaPm.setTypeface(null,Typeface.ITALIC);
            }
            //======================
            if (sach.getTenSach().indexOf('n') > 0){
                tvTenTV.setTextColor(Color.BLACK);
            }



        }
        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.xoa(String.valueOf(pm.getMaPM()));
            }
        });

        return v;
    }
}

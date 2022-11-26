package com.example.duanmau.Fragment;

import android.app.DatePickerDialog;
import android.app.GameManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.duanmau.R;
import com.example.duanmau.database.ThongKeDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class DoanhThuFragment extends Fragment {

    Button btnTungay,btnDennay,btnDoanhThu;
    EditText edTungay,edDenngay;
    TextView tvDoangThu;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int myear,mmonth,mday;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        edTungay = v.findViewById(R.id.fragDT_edtungay);
        edDenngay = v.findViewById(R.id.fragDT_edDenngay);
        btnTungay =  v.findViewById(R.id.fragDT_btntungay);
        btnDennay = v.findViewById(R.id.fragDT_btnDenngay);
        btnDoanhThu = v.findViewById(R.id.fragDT_btnDoanhThu);
        tvDoangThu = v.findViewById(R.id.fragDT_tvDoanhthu);
        btnTungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                myear = c.get(Calendar.YEAR);
                mmonth = c.get(Calendar.MONTH);
                mday = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0,mDateTuNgay,myear,mmonth,mday);
                d.show();
            }
        });
        btnDennay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                myear = c.get(Calendar.YEAR);
                mmonth = c.get(Calendar.MONTH);
                mday = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),
                        0,mDateDenngay,myear,mmonth,mday);
                d.show();
            }
        });
        btnDoanhThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tungay = edTungay.getText().toString();
                String denngay = edDenngay.getText().toString();
                ThongKeDAO dao = new ThongKeDAO(getContext());
                tvDoangThu.setText("Doanh Thu: "+dao.getDoanhThu(tungay,denngay)+" VNĐ");
            }
        });
        return v;

    }
    DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myear = year;
            mmonth = month;
            mday = dayOfMonth;
            GregorianCalendar  c = new GregorianCalendar(myear,mmonth,mday);
            edTungay.setText(sdf.format(c.getTime()));

        }
    };
    DatePickerDialog.OnDateSetListener mDateDenngay = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myear = year;
            mmonth = month;
            mday = dayOfMonth;
            GregorianCalendar  c = new GregorianCalendar(myear,mmonth,mday);
            edDenngay.setText(sdf.format(c.getTime()));

        }
    };
}
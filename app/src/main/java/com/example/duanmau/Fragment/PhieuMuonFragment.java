package com.example.duanmau.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau.R;
import com.example.duanmau.adapter.PhieuMuonAdapter;
import com.example.duanmau.adapter.SachSpinnerAdapter;
import com.example.duanmau.adapter.ThanhVienSpinnerAdapter;
import com.example.duanmau.database.PhieuMuonDao;
import com.example.duanmau.database.SachDao;
import com.example.duanmau.database.ThanhVienDao;
import com.example.duanmau.model.PhieuMuon;
import com.example.duanmau.model.Sach;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class PhieuMuonFragment extends Fragment {
    ListView listview;
    FloatingActionButton fab;

    EditText edMaPm;
    TextView tvTile,tvNgay,tvTien;
    Spinner spnTV,spnSach;
    CheckBox ckbTraSach;
    Button btnSave,btnCancel;

    PhieuMuon pm;
    PhieuMuonDao dao;
    PhieuMuonAdapter phieuMuonAdapter;
    ArrayList<PhieuMuon> list;

    ThanhVienSpinnerAdapter spinnerTV;
    ArrayList<ThanhVien> listThanhVien;
    ThanhVienDao thanhVienDao;

    int matv;

    SachSpinnerAdapter spinnerSach;
    ArrayList<Sach> listSach;
    SachDao sachDao;

    int maSach,tienSach;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        dao = new PhieuMuonDao(getContext());

        listview = v.findViewById(R.id.fragPM_lv);
        fab = v.findViewById(R.id.fragFM_fab);
        capNhap();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogInsert();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pm = list.get(position);
                openDialogUpdate(pm);
            }
        });

        return v;
    }
    public void openDialogInsert(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_pm);

        edMaPm = dialog.findViewById(R.id.dialogpm_edmaPm);
        spnTV = dialog.findViewById(R.id.dialogpm_spn1);
        spnSach = dialog.findViewById(R.id.dialogpm_spn2);
        tvNgay = dialog.findViewById(R.id.dialogpm_tvNgay);
        tvTien = dialog.findViewById(R.id.dialogpm_tvTienThue);
        ckbTraSach = dialog.findViewById(R.id.dialogpm_chk);
        btnSave = dialog.findViewById(R.id.dialogpm_btnSave);
        btnCancel = dialog.findViewById(R.id.dialogpm_btnCancel);

        listThanhVien = new ArrayList<>();
        thanhVienDao = new ThanhVienDao(getContext());
        listThanhVien = (ArrayList<ThanhVien>) thanhVienDao.getAll();

        spinnerTV = new ThanhVienSpinnerAdapter(getContext(),listThanhVien);
        spnTV.setAdapter(spinnerTV);
        spnTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                matv = listThanhVien.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listSach = new ArrayList<>();
        sachDao = new SachDao(getContext());
        listSach = (ArrayList<Sach>) sachDao.getAll();

        spinnerSach = new SachSpinnerAdapter(getContext(),listSach);
        spnSach.setAdapter(spinnerSach);
        spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                tienSach = listSach.get(position).getGiaThue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String datetime = sdf.format(c.getTime());

        tvNgay.setText("Ngày thuê: "+datetime);
        tvTien.setText("Tiền Thuê: "+ tienSach);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm = new PhieuMuon();
                pm.setMaPM(Integer.parseInt(edMaPm.getText().toString()));
                pm.setMaTV(matv);
                pm.setMaSach(maSach);

                pm.setNgay(datetime);
                pm.setTienThue(tienSach);

                if (ckbTraSach.isChecked()){
                    pm.setTraSach(1);
                }else {
                    pm.setTraSach(0);
                }
                if (validate()>0) {
                    int kq = dao.insert(pm);
                    if (kq == 1) {
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                capNhap();
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public void openDialogUpdate(PhieuMuon pm){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_pm);

        edMaPm = dialog.findViewById(R.id.dialogpm_edmaPm);
        spnTV = dialog.findViewById(R.id.dialogpm_spn1);
        spnSach = dialog.findViewById(R.id.dialogpm_spn2);
        tvTile = dialog.findViewById(R.id.dialogpm_Tile);
        tvTile.setText("Update Phiếu mượn");
        tvNgay = dialog.findViewById(R.id.dialogpm_tvNgay);
        tvTien = dialog.findViewById(R.id.dialogpm_tvTienThue);
        ckbTraSach = dialog.findViewById(R.id.dialogpm_chk);
        btnSave = dialog.findViewById(R.id.dialogpm_btnSave);
        btnCancel = dialog.findViewById(R.id.dialogpm_btnCancel);

        edMaPm.setEnabled(false);
        edMaPm.setText(String.valueOf(pm.getMaPM()));
        listThanhVien = new ArrayList<>();
        thanhVienDao = new ThanhVienDao(getContext());
        listThanhVien = (ArrayList<ThanhVien>) thanhVienDao.getAll();

        spinnerTV = new ThanhVienSpinnerAdapter(getContext(),listThanhVien);
        spnTV.setAdapter(spinnerTV);
        spnTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                matv = listThanhVien.get(position).getMaTV();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listSach = new ArrayList<>();
        sachDao = new SachDao(getContext());
        listSach = (ArrayList<Sach>) sachDao.getAll();

        spinnerSach = new SachSpinnerAdapter(getContext(),listSach);
        spnSach.setAdapter(spinnerSach);
        spnSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = listSach.get(position).getMaSach();
                tienSach = listSach.get(position).getGiaThue();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String datetime = sdf.format(c.getTime());

        tvNgay.setText("Ngày thuê: "+datetime);
        tvTien.setText("Tiền Thuê: "+ tienSach);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pm.setMaPM(Integer.parseInt(edMaPm.getText().toString()));
                pm.setMaTV(matv);
                pm.setMaSach(maSach);

                pm.setNgay(datetime);
                pm.setTienThue(tienSach);

                if (ckbTraSach.isChecked()){
                    pm.setTraSach(1);
                }else {
                    pm.setTraSach(0);
                }
                int kq = dao.update(pm);
                if (kq == 1){
                    Toast.makeText(getContext(), "Update thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getContext(), "Update không thành công", Toast.LENGTH_SHORT).show();
                }
                capNhap();
                dialog.dismiss();
            }
        });

        dialog.show();

    }
    public void xoa(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có chắc muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(id);
                capNhap();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        builder.show();
    }
    public void capNhap(){
        list = (ArrayList<PhieuMuon>) dao.getAll();
        phieuMuonAdapter = new PhieuMuonAdapter(getContext(),this,list);
        listview.setAdapter(phieuMuonAdapter);
    }
    public int validate(){
        int check = 1;
        if (edMaPm.getText().length()==0){
            check = -1;
        }
        return check;
    }

}
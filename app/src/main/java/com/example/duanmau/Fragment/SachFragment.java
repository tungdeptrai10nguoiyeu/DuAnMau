package com.example.duanmau.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duanmau.R;
import com.example.duanmau.adapter.SachAdapter;
import com.example.duanmau.adapter.SpinnerAdapter;
import com.example.duanmau.database.LoaiSachDao;
import com.example.duanmau.database.SachDao;
import com.example.duanmau.model.LoaiSach;
import com.example.duanmau.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class SachFragment extends Fragment {
    SachDao dao;
    List<Sach> list;
    SachAdapter Adapter;
    Sach sach;

    SpinnerAdapter spinnerAdapter;
    ArrayList<LoaiSach> listLoaiSach;
    LoaiSachDao loaiSachDao;
    int maLoai;
    ListView lv;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaSach,edTenSach,edGia,edGiaNhap,edTim;
    Spinner spinner;
    Button btnSave,btnCancel,btnTim;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sach, container, false);
        dao = new SachDao(getContext());

        lv = v.findViewById(R.id.fragSach_lv);
        fab = v.findViewById(R.id.fragSach_fab);
        edTim = v.findViewById(R.id.fragSach_edtim);


        capnhap();


        edTim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty()){
                    capnhap();
                }else {
//                    loadMa(Integer.parseInt(s.toString()));
                    try {
                        loadTen(s.toString());
                    }catch (Exception e){
                        Toast.makeText(getContext(), "Khong tìm thấy", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        listLoaiSach = new ArrayList<>();
        loaiSachDao = new LoaiSachDao(getContext());
        listLoaiSach = (ArrayList<LoaiSach>) loaiSachDao.getAll();


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sach = list.get(position);
                openDialogUpdate(sach);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    openDialogInsert();

            }
        });


        return v;
    }

    public void loadMa(int s){
        list = dao.getAll();
        List<Sach> list1 = new ArrayList<>();
        for (Sach sach:list) {
            if (sach.getMaSach()==s){
                list1.add(sach);
            }
        }
        Adapter = new SachAdapter(getContext(),this,list1);
        lv.setAdapter(Adapter);
    }
    public void loadTen(String s){
        list = (List<Sach>) dao.getID(s);
        List<Sach> ls = new ArrayList<>();
        for (Sach sach1:list){
            sach = dao.getID(s);
            ls.add(sach1);

        }
        Adapter = new SachAdapter(getContext(),this,ls);
        lv.setAdapter(Adapter);
    }
    public void openDialogInsert(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_sach);

        edMaSach = dialog.findViewById(R.id.dialogSach_edMaSach);
        edTenSach = dialog.findViewById(R.id.dialogSach_edTenSach);
        edGia = dialog.findViewById(R.id.dialogSach_edGia);
        edGiaNhap = dialog.findViewById(R.id.dialogSach_edgiaNhap);
        btnCancel =  dialog.findViewById(R.id.dialogSach_btnCancel);
        btnSave = dialog.findViewById(R.id.dialogSach_btnSave);
        spinner = dialog.findViewById(R.id.dialogSach_spn);






        spinnerAdapter = new SpinnerAdapter(getContext(),listLoaiSach);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoai = listLoaiSach.get(position).getMaLoai();
                Toast.makeText(getContext(), "Mã loại: " + maLoai, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sach = new Sach();
                    sach.setMaSach(Integer.parseInt(edMaSach.getText().toString()));
                    sach.setTenSach(edTenSach.getText().toString());
                    sach.setGiaThue(Integer.parseInt(edGia.getText().toString()));
                    sach.setGiaNhap(Integer.parseInt(edGiaNhap.getText().toString()));
                    sach.setMaLoai(Integer.parseInt(String.valueOf(maLoai)));
                    if (validate()>0){
                        int kq = dao.insert(sach);
                        if (kq == 1){
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "Thêm Ko thành công", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getContext(), "Nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                    }
                    capnhap();
                    dialog.dismiss();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
    }
    public void openDialogUpdate(Sach sach){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_sach);

        edMaSach = dialog.findViewById(R.id.dialogSach_edMaSach);
        edTenSach = dialog.findViewById(R.id.dialogSach_edTenSach);
        edGia = dialog.findViewById(R.id.dialogSach_edGia);
        edGiaNhap = dialog.findViewById(R.id.dialogSach_edgiaNhap);
        btnCancel =  dialog.findViewById(R.id.dialogSach_btnCancel);
        btnSave = dialog.findViewById(R.id.dialogSach_btnSave);
        spinner = dialog.findViewById(R.id.dialogSach_spn);

        listLoaiSach = new ArrayList<>();
        loaiSachDao = new LoaiSachDao(getContext());
        listLoaiSach = (ArrayList<LoaiSach>) loaiSachDao.getAll();


        edMaSach.setEnabled(false);
        edMaSach.setText(String.valueOf(sach.getMaSach()));
        spinnerAdapter = new SpinnerAdapter(getContext(),listLoaiSach);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maLoai = listLoaiSach.get(position).getMaLoai();
                Toast.makeText(getContext(), "Mã loại: " + maLoai, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sach.setMaSach(Integer.parseInt(edMaSach.getText().toString()));
                sach.setTenSach(edTenSach.getText().toString());
                sach.setGiaThue(Integer.parseInt(edGia.getText().toString()));
                sach.setGiaNhap(Integer.parseInt(edGiaNhap.getText().toString()));
                sach.setMaLoai(maLoai);

                if (validate()>0) {
                    int kq = dao.update(sach);
                    if (kq == 1) {
                        Toast.makeText(getContext(), "Update thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Update Ko thành công", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Vui lòng điều đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                capnhap();
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public void xoa(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(id);
                capnhap();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog  alertDialog = builder.create();
        builder.show();
    }
    public void capnhap(){
        list =  dao.getAll();
        Adapter = new SachAdapter(getContext(),this,list);
        lv.setAdapter(Adapter);
    }
    public int validate(){
        int check = 1;
        if (edMaSach.getText().length()==0|| edTenSach.getText().length()==0||edGia.getText().length()==0){
            check = -1;
        }
        return check;
    }

}
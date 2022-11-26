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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau.R;
import com.example.duanmau.adapter.LoaiSachAdapter;
import com.example.duanmau.database.LoaiSachDao;
import com.example.duanmau.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class LoaiSachFragment extends Fragment {
    ListView listView;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edMaloai,edTenLoai;
    Button btnSave,btnCancel;

    ArrayList<LoaiSach> listLoai;
    LoaiSach ls;
    LoaiSachDao dao;
    LoaiSachAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_loai_sach, container, false);
        dao = new LoaiSachDao(getContext());
        listView = v.findViewById(R.id.fragLs_lv);
        fab = v.findViewById(R.id.fragLs_fab);
        capNhap();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialodInsert();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ls = listLoai.get(position);
                openDialodUpdate(ls);
            }
        });

        return v;
    }
    public void openDialodInsert(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loaisach);
        edMaloai = dialog.findViewById(R.id.dialogLs_edMaLoai);
        edTenLoai = dialog.findViewById(R.id.dialogLs_edTenLoai);
        btnSave = dialog.findViewById(R.id.dialogLs_btnSave);
        btnCancel = dialog.findViewById(R.id.dialogLs_btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ls = new LoaiSach();
                ls.setMaLoai(Integer.parseInt(edMaloai.getText().toString()));
                ls.setTenLoai(edTenLoai.getText().toString());
                if (validate()>0) {
                    int kq = dao.insert(ls);
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
    public void openDialodUpdate(LoaiSach ls){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_loaisach);
        edMaloai = dialog.findViewById(R.id.dialogLs_edMaLoai);
        edTenLoai = dialog.findViewById(R.id.dialogLs_edTenLoai);
        btnSave = dialog.findViewById(R.id.dialogLs_btnSave);
        btnCancel = dialog.findViewById(R.id.dialogLs_btnCancel);

        edMaloai.setEnabled(false);
        edMaloai.setText(String.valueOf(ls.getMaLoai()));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ls.setMaLoai(Integer.parseInt(edMaloai.getText().toString()));
                ls.setTenLoai(edTenLoai.getText().toString());
                if (validate()>0) {
                    int kq = dao.update(ls);
                    if (kq == 1) {
                        Toast.makeText(getContext(), "Update thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Update không thành công", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Vui lòng điều đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                capNhap();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void xoa(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Bạn có chắc muộn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(id);
                capNhap();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("NO",(dialog1, which) -> {dialog.cancel();});
        AlertDialog alertDialog = builder.create();
        builder.show();

    }
    public void  capNhap(){
        listLoai = (ArrayList<LoaiSach>) dao.getAll();
        adapter = new LoaiSachAdapter(getContext(),this, listLoai);
        listView.setAdapter(adapter);
    }
    public int validate(){
        int check = 1;
        if (edMaloai.getText().length()==0|| edTenLoai.getText().length()==0){
            check = -1;
        }
        return check;
    }
}
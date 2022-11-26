package com.example.duanmau.Fragment;

import android.app.Dialog;
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
import com.example.duanmau.adapter.ThemTKAdapter;
import com.example.duanmau.database.ThuThuDAO;
import com.example.duanmau.model.ThuThu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ThemTKFragment extends Fragment {
    ListView listView;
    FloatingActionButton fab;
    ThemTKAdapter adapter;
    Dialog dialog;
    ThuThuDAO dao;
    ArrayList<ThuThu> list;
    ThuThu tt;

    EditText edMaTT,edHoTen,edPass;
    Button btnSave,btnCancel;
    TextView tvtitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_them, container, false);
        dao = new ThuThuDAO(getContext());
        listView = v.findViewById(R.id.fragTK_lv);
        fab = v.findViewById(R.id.fragTK_fab);
        CapNhat();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tt = list.get(position);
                openDialogUpdate(tt);
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
    public void openDialogInsert(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_themtk);

        edMaTT = dialog.findViewById(R.id.dialogtk_maTT);
        edHoTen = dialog.findViewById(R.id.dialogtk_name);
        edPass = dialog.findViewById(R.id.dialogtk_pass);
        btnSave = dialog.findViewById(R.id.dialogtk_btnSave);
        btnCancel = dialog.findViewById(R.id.dialogtk_btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tt = new ThuThu();
                tt.setMaTT(edMaTT.getText().toString());
                tt.setHoTen(edHoTen.getText().toString());
                tt.setMatKhau(edPass.getText().toString());

                if (validate()>0){
                    int kq =  dao.insert(tt);
                    if (kq == 1){
                        Toast.makeText(getContext(), "Thêm Thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                CapNhat();
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
    public void openDialogUpdate(ThuThu tt){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_themtk);

        tvtitle = dialog.findViewById(R.id.dialogtk_tvTile);
        tvtitle.setText("Update tài khoản");
        edMaTT = dialog.findViewById(R.id.dialogtk_maTT);
        edHoTen = dialog.findViewById(R.id.dialogtk_name);
        edPass = dialog.findViewById(R.id.dialogtk_pass);
        btnSave = dialog.findViewById(R.id.dialogtk_btnSave);
        btnCancel = dialog.findViewById(R.id.dialogtk_btnCancel);

        edMaTT.setEnabled(false);
        edMaTT.setText(String.valueOf(tt.getMaTT()));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tt.setMaTT(edMaTT.getText().toString());
                tt.setHoTen(edHoTen.getText().toString());
                tt.setMatKhau(edPass.getText().toString());

                if (validate()>0){
                    int kq =  dao.update(tt);
                    if (kq == 1){
                        Toast.makeText(getContext(), "Update Thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Update không thành công", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                CapNhat();
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

    public void CapNhat(){
        list = (ArrayList<ThuThu>) dao.getAll();
        adapter = new ThemTKAdapter(getContext(),this,list);
        listView.setAdapter(adapter);

    }
    public int  validate(){
        if (edMaTT.getText().length() == 0|| edHoTen.getText().length()==0||edPass.getText().length() == 0){
            return -1;
        }
        return 1;

    }

}
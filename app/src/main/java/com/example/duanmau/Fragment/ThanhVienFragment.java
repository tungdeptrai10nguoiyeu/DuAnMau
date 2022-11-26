package com.example.duanmau.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duanmau.R;
import com.example.duanmau.adapter.ThanhvienAdapter;
import com.example.duanmau.database.ThanhVienDao;
import com.example.duanmau.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ThanhVienFragment extends Fragment {
    ListView lv;
    ArrayList<ThanhVien> list;
    FloatingActionButton fab;
    Dialog dialog;
    EditText edmatv,edname,ednamsinh;
    Button btnSave,btnCancel;


    ThanhVienDao Dao;
    ThanhvienAdapter adapter;
    ThanhVien item;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        lv = view.findViewById(R.id.tv_lv);
        fab = view.findViewById(R.id.tv_fab);

        Dao = new ThanhVienDao(getContext());
        capNhat();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogInsert();
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item = list.get(position);
                openDialogUpdate(item);

            }
        });
        return view;
    }



    protected void openDialogInsert(){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_themtv);
        edmatv = dialog.findViewById(R.id.dialogtv_maTv);
        edname = dialog.findViewById(R.id.dialogtv_name);
        ednamsinh = dialog.findViewById(R.id.dialogtv_namsinh);
        btnSave = dialog.findViewById(R.id.dialogtv_btnSave);
        btnCancel = dialog.findViewById(R.id.dialogtv_btnCancel);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new ThanhVien();
                item.setMaTV(Integer.parseInt(edmatv.getText().toString()));
                item.setHoTen(edname.getText().toString());
                item.setNamSinh(ednamsinh.getText().toString());

                if (validate()>0){
                    int kq =  Dao.insert(item);
                    if (kq == 1){
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Thêm Thất bại", Toast.LENGTH_SHORT).show();
                    }

                    capNhat();
                    dialog.cancel();
                }
            }
        });
        dialog.show();
    }

    protected void openDialogUpdate(ThanhVien tv){
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_themtv);
        edmatv = dialog.findViewById(R.id.dialogtv_maTv);
        edname = dialog.findViewById(R.id.dialogtv_name);
        ednamsinh = dialog.findViewById(R.id.dialogtv_namsinh);
        btnSave = dialog.findViewById(R.id.dialogtv_btnSave);
        btnCancel = dialog.findViewById(R.id.dialogtv_btnCancel);

        edmatv.setEnabled(false);
        edmatv.setText(String.valueOf(tv.getMaTV()));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = new ThanhVien();
                item.setMaTV(Integer.parseInt(edmatv.getText().toString()));
                item.setHoTen(edname.getText().toString());
                item.setNamSinh(ednamsinh.getText().toString());

                if (validate()>0){
                    int kq =  Dao.update(item);
                    if (kq == 1){
                        Toast.makeText(getContext(), "Update thành công", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getContext(), "Update Thất bại", Toast.LENGTH_SHORT).show();
                    }

                    capNhat();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
    public void xoa( String  id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Bạn có chắc muộn xóa không?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dao.delete(id);
                capNhat();
                dialog.cancel();
            }
        });
        builder.setNegativeButton("NO",(dialog1, which) -> {dialog.cancel();});
        AlertDialog alertDialog = builder.create();
        builder.show();
    }
    void  capNhat(){
        list = (ArrayList<ThanhVien>) Dao.getAll();
        adapter = new ThanhvienAdapter(getContext(),this,list);
        lv.setAdapter(adapter);
    }
    public int validate(){
        int check = 1;
        if (edname.getText().length()==0|| ednamsinh.getText().length()==0){
            Toast.makeText(getContext(), "Bạn phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        }
        return check;
    }

}
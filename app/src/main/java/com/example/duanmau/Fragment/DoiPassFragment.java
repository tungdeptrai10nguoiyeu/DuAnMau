package com.example.duanmau.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duanmau.R;
import com.example.duanmau.database.ThuThuDAO;
import com.example.duanmau.model.ThuThu;

import java.util.ArrayList;
import java.util.List;


public class DoiPassFragment extends Fragment {
    EditText edPassOld,edPassNew,edRePass;
    Button btnSave,btnCancel;
    ThuThuDAO dao;
    ThuThu thuThu;
    List<ThuThu> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doi_pass, container, false);
        edPassOld = v.findViewById(R.id.dp_edPassOld);
        edPassNew = v.findViewById(R.id.dp_edPassChange);
        edRePass = v.findViewById(R.id.dp_RePassChange);

        btnSave = v.findViewById(R.id.dp_btnSave);
        btnCancel = v.findViewById(R.id.dp_btnCancel);
        dao = new ThuThuDAO(getContext());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("user_file",Context.MODE_PRIVATE);
                String user = preferences.getString("USERNAME","");

                if (validate()>0) {
                    ThuThu thuThu = dao.getID(user);

                    thuThu.setMaTT(thuThu.getMaTT());
                    thuThu.setHoTen(thuThu.getMatKhau());
                    thuThu.setMatKhau(edPassNew.getText().toString());
                    int kq = dao.update(thuThu);
                    if (kq == 1){
                        Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edPassNew.setText("");
                        edRePass.setText("");
                    }else {
                        Toast.makeText(getContext(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        edPassOld.setText("");
                        edPassNew.setText("");
                        edRePass.setText("");
                    }
                }

            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassOld.setText("");
                edPassNew.setText("");
                edRePass.setText("");
            }
        });


        return v;
    }

    public int validate(){
        int check = 1;
        if (edPassOld.getText().length() == 0 ||
                edPassNew.getText().length() == 0
                || edRePass.getText().length() == 0)
        {
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }else {
            SharedPreferences preferences = getActivity().getSharedPreferences("user_file", Context.MODE_PRIVATE);
            String PassOld = preferences.getString("PASSWORD","");
            String PassNew = edPassNew.getText().toString();
            String RePass = edRePass.getText().toString();
            if (!PassOld.equals(edPassOld.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();

                check = -1;
            }
            if (!RePass.equals(PassNew)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }



        return check;
    }
}
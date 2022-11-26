package com.example.duanmau.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.duanmau.R;
import com.example.duanmau.adapter.TopAdapter;
import com.example.duanmau.database.ThongKeDAO;
import com.example.duanmau.model.Top;

import java.util.List;


public class TopFragment extends Fragment {

    ListView lv;

    ThongKeDAO dao;
    List<Top> list;
    TopAdapter topAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_top, container, false);
        lv = v.findViewById(R.id.fragTop_lv);
        load();
        return v;
    }
    public void load(){
        dao = new ThongKeDAO(getContext());
        list = dao.getTop();
        topAdapter = new TopAdapter(getContext(),list);
        lv.setAdapter(topAdapter);
    }
}
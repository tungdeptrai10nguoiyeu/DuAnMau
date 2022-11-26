package com.example.duanmau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau.Fragment.DoanhThuFragment;
import com.example.duanmau.Fragment.DoiPassFragment;
import com.example.duanmau.Fragment.LoaiSachFragment;
import com.example.duanmau.Fragment.PhieuMuonFragment;
import com.example.duanmau.Fragment.SachFragment;
import com.example.duanmau.Fragment.ThanhVienFragment;
import com.example.duanmau.Fragment.ThemTKFragment;
import com.example.duanmau.Fragment.TopFragment;
import com.example.duanmau.adapter.ViewpageAdapter;
import com.example.duanmau.database.ThuThuDAO;
import com.example.duanmau.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ViewPager pager;
    ViewpageAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navtion);
        pager = findViewById(R.id.page);
        addFragment(pager);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.openDWR,R.string.closeDWR);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        onContextMenuClosed();
        toolbar.setTitle("Quản Lý Phiếu Mượn");

//        tvlogin = findViewById(R.id.login_tv1);

        List<ThuThu> list = new ArrayList<>();
        ThuThuDAO dao = new ThuThuDAO(this);
        ThuThu tt= new ThuThu();
        Intent intent = getIntent();
        String user=  intent.getStringExtra("user");
        list = (ArrayList<ThuThu>) dao.getAll();


        if (user.equals("admin")){
            navigationView.getMenu().findItem(R.id.themND).setVisible(true);
        }else {

            navigationView.getMenu().findItem(R.id.themND).setVisible(false);
        }




    }
    private void onContextMenuClosed() {

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.QLPM){
            pager.setAdapter(adapter);
            pager.setCurrentItem(0);
            toolbar.setTitle("Quản Lý Phiếu Mượn");

            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.QLLS){
            pager.setAdapter(adapter);
            pager.setCurrentItem(1);
            toolbar.setTitle("Quản Lý Loại Sách");

            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.QLS){
            pager.setAdapter(adapter);
            pager.setCurrentItem(2);
            toolbar.setTitle("Quản Lý Sách");

            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.QLTV){
            pager.setAdapter(adapter);
            pager.setCurrentItem(3);
            toolbar.setTitle("Quản Lý Thành Viên");

            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.topSach){
            pager.setAdapter(adapter);
            pager.setCurrentItem(4);
            item.setCheckable(true);
            toolbar.setTitle("Top 10 Sách");

            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.doanhThu){
            pager.setAdapter(adapter);
            pager.setCurrentItem(5);
            item.setCheckable(true);
            toolbar.setTitle("Doanh Thu");

            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.themND){
            pager.setAdapter(adapter);
            pager.setCurrentItem(6);
            item.setCheckable(true);
            toolbar.setTitle("Thêm Tài Khoản");

            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.doiPass){
            pager.setAdapter(adapter);
            pager.setCurrentItem(7);
            item.setCheckable(true);
            toolbar.setTitle("Đổi Mật Khẩu");

            Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        }else if (item.getItemId() == R.id.dangXuat){
            Intent intent= new Intent(getApplicationContext(),Splash_screen_MainActivity2.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawers();
        return true;

    }

    public void addFragment(ViewPager viewPager){
        adapter = new ViewpageAdapter(getSupportFragmentManager());
        adapter.addFragment(new PhieuMuonFragment(),"Phiếu mượn");
        adapter.addFragment(new LoaiSachFragment(),"Loại sách");
        adapter.addFragment(new SachFragment(),"Sách");
        adapter.addFragment(new ThanhVienFragment(),"Thành viên");
        adapter.addFragment(new TopFragment(),"Top10");
        adapter.addFragment(new DoanhThuFragment(),"Doanh thu");
        adapter.addFragment(new ThemTKFragment(),"Thêm thanh viên");
        adapter.addFragment(new DoiPassFragment(),"Đổi pass");
        pager.setAdapter(adapter);

    }
}
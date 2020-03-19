package com.example.project1.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.project1.Fragment.FragmentAnUong;
import com.example.project1.Fragment.FragmentCheckIn;
import com.example.project1.Fragment.FragmentChoO;
import com.example.project1.Fragment.FragmentDoiMatKhau;
import com.example.project1.Fragment.FragmentTravelBlog;
import com.example.project1.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toast.makeText(this, "Tran Quoc Hao", Toast.LENGTH_SHORT).show();
        initView();
    }

    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentChoO()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ChoO:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentChoO()).commit();
                break;
            case R.id.menu_AnUong:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentAnUong()).commit();
                break;
            case R.id.menu_CheckIn:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentCheckIn()).commit();
                break;
            case R.id.menu_Blog:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentTravelBlog()).commit();
                break;
            case R.id.menu_DoiMK:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new FragmentDoiMatKhau()).commit();
                break;
            case R.id.menu_DangXuat:
                finish();
                break;
            case R.id.menu_Thoat:
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}

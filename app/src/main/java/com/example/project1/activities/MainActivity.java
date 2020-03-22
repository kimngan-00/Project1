package com.example.project1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.project1.fragment.Fragment_Food;
import com.example.project1.fragment.Fragment_CheckIn;
import com.example.project1.fragment.Fragment_Hotel;
import com.example.project1.fragment.Fragment_User;
import com.example.project1.fragment.Fragment_Blog;
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
        initView();
    }

    private void initView() {
        navigationView = (NavigationView) findViewById(R.id.main_navigationView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView.setNavigationItemSelectedListener(this);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new Fragment_Hotel()).commit();

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
            case R.id.menu_Hotel:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new Fragment_Hotel()).commit();
                break;
            case R.id.menu_Food:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new Fragment_Food()).commit();
                break;
            case R.id.menu_CheckIn:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new Fragment_CheckIn()).commit();
                break;
            case R.id.menu_Blog:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new Fragment_Blog()).commit();
                break;
            case R.id.menu_User:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new Fragment_User()).commit();
                break;
            case R.id.menu_LogOut:
                finish();
                break;
            case R.id.menu_Exit:
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void log(String s){
        Log.d("log", s);
    }
}

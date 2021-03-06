package com.example.project1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.project1.fragment.Fragment_Accommodations;
import com.example.project1.fragment.Fragment_BeautifulPlaces;
import com.example.project1.fragment.Fragment_Blog;
import com.example.project1.fragment.Fragment_Restaurant;
import com.example.project1.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    DatabaseReference databaseReference;

// Commit

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
        getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new Fragment_Restaurant()).commit();

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
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new Fragment_Accommodations()).commit();
                break;
            case R.id.menu_Food:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new Fragment_Restaurant()).commit();
                break;
            case R.id.menu_CheckIn:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new Fragment_BeautifulPlaces()).commit();
                break;
            case R.id.menu_Blog:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout, new Fragment_Blog()).commit();
                break;
            case R.id.menu_Admin:
                finish();
                startActivity(new Intent(MainActivity.this, ActivityAdmin.class));

                break;
            case R.id.menu_UserManagement:

                break;
            case R.id.menu_LogOut:
                FirebaseAuth.getInstance().signOut();
                Intent loginActivity = new Intent(getApplicationContext(), ActivityLogin.class);
                startActivity(loginActivity);
                finish();
                startActivity(new Intent(MainActivity.this, ActivityRegister.class));
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

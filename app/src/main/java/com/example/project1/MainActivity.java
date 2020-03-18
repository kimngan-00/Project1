package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project1.R;

public class MainActivity extends AppCompatActivity {
    static final String LOG = "LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toast("Tran Quoc Hao");
    }
    private void toast(String s){
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
    private void log(String s){
        Log.d(LOG,s);
    }
}

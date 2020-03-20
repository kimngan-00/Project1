package com.example.project1.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project1.model.SinhVien;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAO_SinhVien {
    Context context;
    Fragment fragment;
    DatabaseReference database;

    public DAO_SinhVien(Context context) {
        this.context = context;
        this.database = FirebaseDatabase.getInstance().getReference("sinhvien1");
    }

    public void insert(SinhVien sinhVien){
        sinhVien.setMa(database.push().getKey());
        database.child(sinhVien.getMa()).setValue(sinhVien, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null){
                    toast("thêm thành công");
                }else {
                    toast("lỗi");
                }
            }
        });
    }

    public void update(String id, SinhVien sinhVien){
        database.child(id).setValue(sinhVien);

    }

    public void delete(String id){
        database.child(id).removeValue();
    }
    private void toast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}

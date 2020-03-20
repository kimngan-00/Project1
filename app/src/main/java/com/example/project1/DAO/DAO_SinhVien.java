package com.example.project1.DAO;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project1.Fragment.FragmentChoO;
import com.example.project1.model.SinhVien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DAO_SinhVien {
    Context context;
    Fragment fragment;
    DatabaseReference database;

    public DAO_SinhVien(Context context) {
        this.context = context;
        this.database = FirebaseDatabase.getInstance().getReference("sinhvien1");
    }

    public DAO_SinhVien(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.database = FirebaseDatabase.getInstance().getReference("sinhvien1");
    }

    public void them(SinhVien sinhVien){
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
    public void sua(String id, SinhVien sinhVien){
        sinhVien.setMa(id);
        database.child(id).setValue(sinhVien);
    }

    public void xoa(String id){
        database.child(id).removeValue();
    }

    public List laydulieu(){
        final List<SinhVien> sinhVienList = new ArrayList<>();
       database.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               sinhVienList.clear();
               for (DataSnapshot ds : dataSnapshot.getChildren()){
                   SinhVien sinhVien = ds.getValue(SinhVien.class);
                   sinhVienList.add(sinhVien);

               }
               ((FragmentChoO)fragment).lamMoiDanhSachSinhVien();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

        return sinhVienList;
    }
    private void log (String s){
        Log.d("log", s);
    }
    private void toast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}

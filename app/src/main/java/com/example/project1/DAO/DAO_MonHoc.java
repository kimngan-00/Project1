package com.example.project1.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project1.Activities.Main2Activity;
import com.example.project1.Fragment.FragmentChoO;
import com.example.project1.model.MonHoc;
import com.example.project1.model.SinhVien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DAO_MonHoc  {
    Context context;
    DatabaseReference database;

    public DAO_MonHoc(Context context) {
        this.context = context;
        this.database = FirebaseDatabase.getInstance().getReference("monhoc");
    }

    public void insert(MonHoc monHoc){
        monHoc.setMaMH(database.push().getKey());
        database.child(monHoc.getMaMH()).setValue(monHoc, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError == null){
                    toast("thêm thành công");
                }else {
                    toast("thất bại");
                }
            }
        });
    }

    public void update(String id, MonHoc monHoc){
        monHoc.setMaMH(id);
        database.child(id).setValue(monHoc);
    }

    public void delete(String id){

        database.child(id).removeValue();
    }

    private void toast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public List laydulieu(){
        final List<MonHoc> monHocList = new ArrayList<>();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                monHocList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    MonHoc monHoc =ds.getValue(MonHoc.class );
                    monHocList.add(monHoc);
                }
                ((Main2Activity)context).refreshLV();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return monHocList;
    }


}

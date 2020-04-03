package com.example.project1.daos;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project1.model.Firebase_CallBack;
import com.example.project1.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DAO_User {
    DatabaseReference db_User;
    Context context;
    Fragment fragment;

    public DAO_User(Context context) {
        this.context = context;
        this.db_User = FirebaseDatabase.getInstance().getReference("Users");
    }

    public DAO_User(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.db_User = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void insert(User user){
        db_User.child(user.getId()).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null)
                    Toast.makeText(context, "Da them thanh cong", Toast.LENGTH_SHORT).show();
                else{
                    Toast.makeText(context, "Loi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public List<User> getData(final Firebase_CallBack firebase_callBack){
        final List<User> userList = new ArrayList<>();
        db_User.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    userList.add(user);
                }
                Collections.sort(userList, new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return  o1.getId().compareTo(o2.getId());
                    }
                });
                firebase_callBack.getDataUser(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return userList;
    }
    private void toast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}

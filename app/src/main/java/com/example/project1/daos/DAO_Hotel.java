package com.example.project1.daos;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project1.model.Firebase_CallBack;
import com.example.project1.model.Hotel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DAO_Hotel {
    Context context;
    DatabaseReference database;
    Fragment fragment;


    public DAO_Hotel(Context context) {
        this.context = context;
        this.database = FirebaseDatabase.getInstance().getReference("hotel");
    }

    public DAO_Hotel(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.database = FirebaseDatabase.getInstance().getReference("hotel");

    }

    public void insert(Hotel hotel) {
        hotel.setId_Hotel(database.push().getKey());
        database.child(hotel.getId_Hotel()).setValue(hotel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                } else Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void delete(String id) {
        database.child(id).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(context, "Thành Công", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void update(String id, Hotel hotel) {
        database.child(id).setValue(hotel);
    }

    public void getData(final Firebase_CallBack firebase_callBack) {
        final List<Hotel> hotelList = new ArrayList<>();
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                hotelList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Hotel hotel = ds.getValue(Hotel.class);
                    hotelList.add(hotel);
                }
                firebase_callBack.getDataHotel(hotelList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

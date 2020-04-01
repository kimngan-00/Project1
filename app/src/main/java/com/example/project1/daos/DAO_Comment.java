package com.example.project1.daos;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.project1.model.Comment;
import com.example.project1.model.Firebase_CallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DAO_Comment {
    Context context;
    Fragment fragment;
    DatabaseReference dbComment;

    public DAO_Comment(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dbComment = FirebaseDatabase.getInstance().getReference("comments");
    }

    public void insert(String idPost, Comment comment){
        String idComment = dbComment.push().getKey();
        comment.setIdComment(idComment);
        dbComment.child(idPost).child(idComment).setValue(comment);
    }

    public void getData(String idPost, final Firebase_CallBack firebase_callBack){
        final List<Comment> commentList = new ArrayList<>();
        dbComment.child(idPost).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commentList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Comment comment = ds.getValue(Comment.class);
                    commentList.add(comment);
                }
                firebase_callBack.getDataComment(commentList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}

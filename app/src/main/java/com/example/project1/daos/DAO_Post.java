package com.example.project1.daos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.project1.model.FirebaseCallback;
import com.example.project1.model.Post;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DAO_Post {
    private Context context;
    private Fragment fragment;
    private DatabaseReference dbAdmin, dbUser, dbPost;
    private String node;
    private StorageReference storagePost;


    public DAO_Post(Context context) {
        this.context = context;
        this.dbAdmin = FirebaseDatabase.getInstance().getReference("admin").child("category");
        this.dbUser = FirebaseDatabase.getInstance().getReference("category");
    }

    public DAO_Post(Context context, Fragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dbAdmin = FirebaseDatabase.getInstance().getReference("admin").child("category");
        this.dbUser = FirebaseDatabase.getInstance().getReference("category");
    }

    public void insertAdmin(String categoryNode, String placeNode, final Post post, ImageView imageView){
        dbPost = dbAdmin.child(categoryNode).child(placeNode);
        final String id = dbPost.push().getKey();
        final StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("Post/" + id);
        post.setId(id);
        post.setPlace(placeNode);
        post.setCategory(categoryNode);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bAOS);
        byte[] data = bAOS.toByteArray();
        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        post.setUrlImage(String.valueOf(uri));
                        dbPost.child(id).setValue(post);
                    }
                });
            }
        });
    }
    public void insertUser(final Post post, ImageView imageView){
        dbPost = dbUser.child(post.getCategory()).child(post.getPlace());
        final String id = post.getId();
        final StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("Post/" + id);
        post.setId(id);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bAOS);
        byte[] data = bAOS.toByteArray();
        UploadTask uploadTask = storageReference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        post.setUrlImage(String.valueOf(uri));
                        dbPost.child(id).setValue(post);
                        toast("Thêm thành công");
                    }
                });
            }
        });
    }

    public void deleteAdmin(String categoryNode, String placeNode,String postID){
        dbAdmin.child(categoryNode).child(placeNode).child(postID).removeValue();

    }

    public void getDataAdmin(final FirebaseCallback firebaseCallback){
        final List<Post> postList = new ArrayList<>();
        dbAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    for (DataSnapshot ds1 : ds.getChildren()){
                        for (DataSnapshot ds2 : ds1.getChildren()){
                            Post post = ds2.getValue(Post.class);
                            postList.add(post);
                        }
                    }
                }
                firebaseCallback.postListAdmin(postList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void getDataUser (String categoryNode, final FirebaseCallback firebaseCallback){
        final List<Post> postList = new ArrayList<>();
        dbUser.child(categoryNode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    for (DataSnapshot ds1 : ds.getChildren()){
                        Post post = ds1.getValue(Post.class);
                        postList.add(post);
                    }

                }
                firebaseCallback.postListUser(postList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void toast(String s){
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }
}

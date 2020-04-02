package com.example.project1.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.project1.R;
import com.example.project1.adapters.Blog_Adapter;
import com.example.project1.model.Blog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Blog extends Fragment {
    View view;
    FloatingActionButton btnAdd;
    ImageView dialog_imgUser, dialog_imgView, dialog_imgBtn;
    EditText dialog_edtTittle, dialog_edtDecription;
    ProgressBar dialog_ProgressBar;
    FirebaseUser currentUser;
    Uri pikedImgUri;
    static int FReqCode = 2;
    static int REQUESCODE = 2;
    ListView listView;
    Blog_Adapter post_adapter;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    List<Blog> postList;
    public Fragment_Blog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_blog, container, false);
        initView();
        // Inflate the layout for this fragment
        return view;
    }
    private void initView() {
        listView = (ListView)view.findViewById(R.id.fhome_ListView);
        postList = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("blog");
        btnAdd = (FloatingActionButton)view.findViewById(R.id.fBlog_FAB_Add_Post);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAdd();
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    for (DataSnapshot ds1: ds.getChildren()){
                        Blog post = ds1.getValue(Blog.class);
                        postList.add(post);
                    }
                }
                post_adapter = new Blog_Adapter(getActivity(),postList);
                listView.setAdapter(post_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public  void dialogAdd(){
        final Dialog dialog = new Dialog(getActivity());
        final DatabaseReference db = FirebaseDatabase.getInstance().getReference("blog").child("blog_image");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        dialog.setContentView(R.layout.dialog_addblog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT,Toolbar.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().getAttributes().gravity = Gravity.TOP;
        dialog_imgUser = dialog.findViewById(R.id.dialogAddBlog_ImageTitle);
        dialog_imgView = dialog.findViewById(R.id.dialogAddBlog_imageDescription);
        dialog_edtTittle = dialog.findViewById(R.id.dialogAddBlog_edt_Tittle);
        dialog_edtDecription = dialog.findViewById(R.id.dialogAddBlog_edt_Description);
        dialog_ProgressBar = dialog.findViewById(R.id.dialogAddBlog_Progress);
        dialog_imgBtn = dialog.findViewById(R.id.dialogAddBlog_edt_ImageView);
        dialog_imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRequestForPermission();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Choose picture"),REQUESCODE);
            }
        });
        dialog_imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tittle = dialog_edtTittle.getText().toString();
                String description = dialog_edtDecription.getText().toString();
                if (tittle.isEmpty() && description.isEmpty()){
                    toat("Vui lòng điền đầy đủ thông tin");
                }else {
                    final String id = db.push().getKey();
                    dialog_imgBtn.setVisibility(View.INVISIBLE);
                    dialog_ProgressBar.setVisibility(View.VISIBLE);
                    final StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Post/" + id);
                    final Blog post = new Blog();
                    post.setId_Blog(id);
                    post.setDescription(description);
                    post.setTittle(tittle);
                    post.setLongDate(timeStamp());
                    post.setUrlAvatarUser(String.valueOf(currentUser.getPhotoUrl()));
                    dialog_imgView.setDrawingCacheEnabled(true);
                    dialog_imgView.buildDrawingCache();
                    Bitmap bitmap = null;
                    try {
                        bitmap = ((BitmapDrawable)dialog_imgView.getDrawable()).getBitmap();

                    }catch (Exception e){
                        toat("Vui lòng chọn hình");
                    }

                    ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100, bAOS);
                    byte[] data = bAOS.toByteArray();
                    UploadTask uploadTask = storageReference.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            dialog_imgBtn.setVisibility(View.INVISIBLE);
                            dialog_ProgressBar.setVisibility(View.VISIBLE);
                            dialog.dismiss();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    post.setUrlImage(String.valueOf(uri));
                                    db.child(id).setValue(post);
                                    toat("Thành công");
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                }
            }

        });
        Picasso.get().load(currentUser.getPhotoUrl()).into(dialog_imgUser);
        dialog.show();
    }


    private void toat(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    private void checkAndRequestForPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(getActivity(), "Please accept for requited permission", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},FReqCode);
            }
        }
        else {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUESCODE && data != null){
            dialog_imgView.setImageURI(data.getData());
        }
    }
    private long timeStamp(){
        Calendar calendar = Calendar.getInstance();
        return  calendar.getTimeInMillis();
    }
}

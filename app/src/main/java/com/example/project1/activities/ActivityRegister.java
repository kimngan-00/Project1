package com.example.project1.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project1.R;
import com.example.project1.daos.DAO_User;
import com.example.project1.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ActivityRegister extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView tvLogin;
    private EditText edtUserName, edtEmail, edtPassword, edtPassword2;
    private ImageView imgAvatar, imgChangeAvatar;
    private Button btnRegister;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private DAO_User dao_user;
    private static int PICK_IMAGE_CODE = 1;
    private User insert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("REGISTRATION");
        initView();
    }

    private void initView() {
        dao_user = new DAO_User(this);
        firebaseAuth = FirebaseAuth.getInstance();
        edtUserName = (EditText) findViewById(R.id.edt_UserName);
        edtEmail = (EditText) findViewById(R.id.edt_Email);
        edtPassword = (EditText) findViewById(R.id.edt_Password);
        edtPassword2 = (EditText) findViewById(R.id.edt_Password2);
        imgAvatar = (ImageView) findViewById(R.id.fUser_ImgAvatar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnRegister = (Button) findViewById(R.id.btn_Register);
        tvLogin = (TextView) findViewById(R.id.tv_Login);

        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select picture"),PICK_IMAGE_CODE);
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ActivityLogin.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtUserName.getText().toString();
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String password2 = edtPassword2.getText().toString();
                if (userName.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()){
                    Toast.makeText(ActivityRegister.this, "Xin vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    createUser(userName,email,password);
                }
            }
        });
    }
    private void createUser(final String name, String email, String pass){
        insert.setPassword(pass);
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String uID = firebaseAuth.getCurrentUser().getUid();
                    storageReference = FirebaseStorage.getInstance().getReference().child("AvatarUser/" + uID);
                    imgAvatar.setDrawingCacheEnabled(true);
                    imgAvatar.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) imgAvatar.getDrawable()).getBitmap();
                    ByteArrayOutputStream bAOS = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100,bAOS);
                    byte[] data = bAOS.toByteArray();
                    UploadTask uploadTask = storageReference.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    updateUser(name, uri, firebaseAuth.getCurrentUser());
                                }
                            });
                        }
                    });
                    }else {
                    Toast.makeText(ActivityRegister.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateUser(final String name, final Uri uriImage, final FirebaseUser currentUser){
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder().setDisplayName(name).setPhotoUri(uriImage).build();
        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    insert = new User();
                    insert.setName(name);
                    insert.setUriAvatar(String.valueOf(uriImage));
                    insert.setEmail(currentUser.getEmail());
                    insert.setId(currentUser.getUid());
                    insert.setType("User");
                    dao_user.insert(insert);
                    Toast.makeText(ActivityRegister.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(ActivityRegister.this,MainActivity.class));
                }
            }
        });
    }
    private void log(String s){
        Log.d("log", s);
    }
    @Override
    protected void onActivityResult(int requestCODE, int resultCODE, @Nullable Intent data){
        if (requestCODE == PICK_IMAGE_CODE && data != null){
            imgAvatar.setImageURI(data.getData());

        }
        super.onActivityResult(requestCODE, resultCODE, data);
    }

}

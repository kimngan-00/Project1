package com.example.project1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.project1.R;
import com.example.project1.daos.DAO_User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Fragment_UserManagement extends Fragment {
    View view;
    TextView tvUserName, tvEmail, tvId, tvType;
    ImageView imageView;
    FirebaseUser firebaseUser;
    DAO_User dao_user;

    public Fragment_UserManagement(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_usermanagement, container, false);
        // Inflate the layout for this fragment
        initView();
        return view;
    }
    private void initView(){


    }
}

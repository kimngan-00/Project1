package com.example.project1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.daos.DAO_User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class Fragment_UserInfor extends Fragment {
    View view;
    TextView tvUserName, tvEmail, tvId, tvType;
    ImageView imageView;
    FirebaseUser firebaseUser;
    DAO_User dao_user;

    public Fragment_UserInfor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_userinfor, container, false);
        // Inflate the layout for this fragment
        initView();
        return view;
    }
    private void initView(){
        dao_user = new DAO_User(getActivity(),this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        tvUserName = (TextView) view.findViewById(R.id.tv_UserName);
        tvId = (TextView) view.findViewById(R.id.tv_UserID);
        tvEmail = (TextView) view.findViewById(R.id.tv_Email);
        tvType = (TextView) view.findViewById(R.id.tv_Type);
        imageView = (ImageView) view.findViewById(R.id.fUser_ImgAvatar);

        if (firebaseUser != null){
            tvUserName.setText(firebaseUser.getDisplayName());
            tvId.setText(firebaseUser.getUid());
            tvEmail.setText(firebaseUser.getEmail());
            Picasso.get().load(firebaseUser.getPhotoUrl()).into(imageView);
        }
    }


}

package com.example.project1.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.adapters.Adapter_lv_Hotel;
import com.example.project1.daos.DAO_Hotel;
import com.example.project1.daos.DAO_User;
import com.example.project1.model.Firebase_CallBack;
import com.example.project1.model.Hotel;
import com.example.project1.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_UserWall extends Fragment {
    View view;
    private CircleImageView civ_UserAvatar;
    private TextView tv_UserName;
    private TextView tv_UserEmail;
    private ListView lv_UserPost;
    private DAO_Hotel dao_hotel;
    private DAO_User dao_user;
    private Adapter_lv_Hotel adapter_lv_hotel;
    private FirebaseUser currentUser;


    public Fragment_UserWall() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_wall, container, false);
        init();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Bundle bundle = getArguments();
        final String id_UserWall = bundle.getString("Uid");

        dao_user = new DAO_User(getContext(), this);
        dao_user.getData(new Firebase_CallBack() {
            @Override
            public void getDataUser(List<User> userList) {

                for (User userWall : userList) {
                    if (id_UserWall.equalsIgnoreCase(userWall.getId())) {
                        tv_UserName.setText(userWall.getUserName());
                        tv_UserEmail.setText(userWall.getEmail());
                        Picasso.get().load(userWall.getUriAvatar()).into(civ_UserAvatar);

                    }
                }
                super.getDataUser(userList);
            }
        });

        dao_hotel = new DAO_Hotel(getContext(), this);
        dao_hotel.getData(new Firebase_CallBack() {
            @Override
            public void getDataHotel(final List<Hotel> hotelList) {
                super.getDataHotel(hotelList);
                List<Hotel> userPostList = new ArrayList<>();
                for (Hotel hotel : hotelList) {
                    if (id_UserWall.equalsIgnoreCase(hotel.getId_User())) {
                        userPostList.add(hotel);
                    }
                }
                adapter_lv_hotel = new Adapter_lv_Hotel(getActivity(), R.layout.raw_lv_all, userPostList);
                lv_UserPost.setAdapter(adapter_lv_hotel);


                lv_UserPost.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                        return true;
                    }
                });
            }
        });



        return view;
    }

    public void init() {
        civ_UserAvatar = (CircleImageView) view.findViewById(R.id.fUserWall_civUserAvatar);
        tv_UserEmail = (TextView) view.findViewById(R.id.fUserWall_tvUserEmail);
        tv_UserName = (TextView) view.findViewById(R.id.fUserWall_tvUserName);
        lv_UserPost = (ListView) view.findViewById(R.id.fUserWall_lvUserPost);

    }
}

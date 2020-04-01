package com.example.project1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.adapters.Adapter_LV_Comment;
import com.example.project1.daos.DAO_Comment;
import com.example.project1.daos.DAO_Hotel;
import com.example.project1.model.Comment;
import com.example.project1.model.Firebase_CallBack;
import com.example.project1.model.Hotel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_View_Post extends Fragment {
    private View view;
    private TextView txtUserName, txtPubDate, txtNameLocate, txtAddress, txtDescription;
    private ImageView imgLocation;
    private ListView lvComment;
    private EditText edComment;
    private Button btnPostComment;
    private Hotel hotel;
    private DAO_Hotel dao_hotel;
    private DAO_Comment dao_comment;
    private Adapter_LV_Comment adapterComment;
    private FirebaseUser currentUser;



    public Fragment_View_Post() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_post, container, false);
        init();
        Bundle bundle = getArguments();
        hotel =(Hotel) bundle.getSerializable("hotel");
        txtAddress.setText(hotel.getAddress_Hotel());
        txtNameLocate.setText(hotel.getName_Hotel());
        txtPubDate.setText(hotel.getPubDate_Hotel());
        txtUserName.setText(hotel.getId_User());
        txtDescription.setText(hotel.getDescription_Hotel());
        Picasso.get().load(hotel.getImage_Hotel()).into(imgLocation);
//        if (bundle != null) {
//            final int position = bundle.getInt("position");
//            dao_hotel = new DAO_Hotel(getContext(), this);
//            dao_hotel.getData(new Firebase_CallBack() {
//                @Override
//                public void getDataHotel(List<Hotel> hotelList) {
//                    hotel = hotelList.get(position);
//                    txtAddress.setText(hotel.getAddress_Hotel());
//                    txtNameLocate.setText(hotel.getName_Hotel());
//                    txtPubDate.setText(hotel.getPubDate_Hotel());
//                    txtUserName.setText(hotel.getId_User());
//                    txtDescription.setText(hotel.getDescription_Hotel());
//                    Picasso.get().load(hotel.getImage_Hotel()).into(imgLocation);
//                }
//            });
//
//        } else {
//            Toast.makeText(getContext(), "Không nhận được dữ liệu", Toast.LENGTH_SHORT).show();
//        }

        dao_comment.getData("-M3WlL6mGUWo_3mztW5o", new Firebase_CallBack() {
            @Override
            public void getDataComment(List<Comment> commentList) {
                adapterComment = new Adapter_LV_Comment(getActivity(), commentList);
                lvComment.setAdapter(adapterComment);
            }
        });

        btnPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
                String idPost = hotel.getId_Hotel();
                Toast.makeText(getActivity(), idPost, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void init() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        dao_comment = new DAO_Comment(getActivity(), this);
        txtAddress = (TextView) view.findViewById(R.id.fViewPost_tvAddress);
        txtNameLocate = (TextView) view.findViewById(R.id.fViewPost_tvNameLocate);
        txtPubDate = (TextView) view.findViewById(R.id.fViewPost_tvPubDate);
        txtUserName = (TextView) view.findViewById(R.id.fViewPost_tvUserName);
        txtDescription = (TextView) view.findViewById(R.id.fViewPost_tvDescription);
        edComment = (EditText) view.findViewById(R.id.fViewPost_edComment);
        btnPostComment = (Button) view.findViewById(R.id.fViewPost_btnPostComment);
        lvComment = (ListView) view.findViewById(R.id.fViewPost_lvComment);
        imgLocation = (ImageView) view.findViewById(R.id.fViewPost_imgLocation);

    }

    private void postComment() {
        String idPost = hotel.getId_Hotel();
        Comment comment = new Comment();
        comment.setContentComment(edComment.getText().toString());
        comment.setIdUser(currentUser.getUid());
        comment.setEmailUser(currentUser.getEmail());
        comment.setPubDate(stringPubDate());
        comment.setLongPubDate(longPubDate());
        comment.setUriAvatarUser(String.valueOf(currentUser.getPhotoUrl()));
        dao_comment.insert(idPost,comment);
    }

    private String stringPubDate() {
        String pubDate;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        pubDate = format.format(calendar.getTime());
        return pubDate;
    }

    private long longPubDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTimeInMillis();
    }
}

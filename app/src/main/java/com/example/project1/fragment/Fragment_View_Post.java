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
import com.example.project1.daos.DAO_Hotel;
import com.example.project1.model.Firebase_CallBack;
import com.example.project1.model.Hotel;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_View_Post extends Fragment {
    View view;
    TextView txtUserName, txtPubDate, txtNameLocate, txtAddress, txtDescription;
    ImageView imgLocation;
    ListView lvComment;
    EditText edComment;
    Button btnPostComment;
    Hotel hotel;
    DAO_Hotel dao_hotel;


    public Fragment_View_Post() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_view_post, container, false);
        init();
        Bundle bundle = getArguments();

        if (bundle != null) {
            final int position = bundle.getInt("position");
            dao_hotel = new DAO_Hotel(getContext(), this);
            dao_hotel.getData(new Firebase_CallBack() {
                @Override
                public void getDataHotel(List<Hotel> hotelList) {
                    hotel = hotelList.get(position);
                    txtAddress.setText(hotel.getAddress_Hotel());
                    txtNameLocate.setText(hotel.getName_Hotel());
                    txtPubDate.setText(hotel.getPubDate_Hotel());
                    txtUserName.setText(hotel.getId_User());
                    txtDescription.setText(hotel.getDescription_Hotel());
                    Picasso.get().load(hotel.getImage_Hotel()).into(imgLocation);
                }
            });

        } else {
            Toast.makeText(getContext(), "Không nhận được dữ liệu", Toast.LENGTH_SHORT).show();
        }
        return view;
    }

    public void init() {
        txtAddress = (TextView) view.findViewById(R.id.fViewPost_tvAddress);
        txtNameLocate = (TextView) view.findViewById(R.id.fViewPost_tvNameLocate);
        txtPubDate = (TextView) view.findViewById(R.id.fViewPost_tvPubDate);
        txtUserName = (TextView) view.findViewById(R.id.fViewPost_tvUserName);
        txtDescription = (TextView) view.findViewById(R.id.fViewPost_tvDescription);
        edComment = (EditText) view.findViewById(R.id.fViewPost_edComment);
        btnPostComment = (Button) view.findViewById(R.id.fAddPost_btnPost);
        lvComment = (ListView) view.findViewById(R.id.fViewPost_lvComment);
        imgLocation = (ImageView) view.findViewById(R.id.fViewPost_imgLocation);
    }
}

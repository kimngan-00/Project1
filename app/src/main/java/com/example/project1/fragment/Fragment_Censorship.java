package com.example.project1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.project1.R;
import com.example.project1.activities.MainActivity;
import com.example.project1.adapters.Adapter_LV_PostAdmin;
import com.example.project1.daos.DAO_Post;
import com.example.project1.model.FirebaseCallback;
import com.example.project1.model.Post;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Censorship extends Fragment {
    private View view;
    private Adapter_LV_PostAdmin adapterPost;
    private ListView lvPost;

    private TextView tvNumber;
    private DAO_Post dao_post;

    public Fragment_Censorship() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_censorship, container, false);
        initView();
        return view;
    }

    private void initView() {
        dao_post = new DAO_Post(getActivity(),this);
        lvPost = (ListView) view.findViewById(R.id.fCensorship_lvPost);
        tvNumber = (TextView) view.findViewById(R.id.fCensorship_tvNumber);

        dao_post.getDataAdmin(new FirebaseCallback(){
            @Override
            public void postListAdmin(List<Post> postList) {
                adapterPost = new Adapter_LV_PostAdmin(getActivity(),postList);
                lvPost.setAdapter(adapterPost);
                tvNumber.setText(String.valueOf(postList.size()));
            }
        });
    }
}

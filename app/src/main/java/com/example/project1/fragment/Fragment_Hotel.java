package com.example.project1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.project1.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Hotel extends Fragment {
    private View view;

    public Fragment_Hotel() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hotel, container, false);
        initView();
        return view;
    }

    public void initView() {

    }


}

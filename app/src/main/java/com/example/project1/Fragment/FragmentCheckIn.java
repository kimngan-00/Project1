package com.example.project1.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project1.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCheckIn extends Fragment {
View view;
    public FragmentCheckIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_check_in, container, false);
        // Inflate the layout for this fragment
        return view;
    }
}

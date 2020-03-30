package com.example.project1.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Food extends Fragment {
View view;
FloatingActionButton FAB;
    public Fragment_Food() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food, container, false);


        FAB = (FloatingActionButton) view.findViewById(R.id.fFood_FAB_add_post);

        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Add_Post fragment_add_post = new Fragment_Add_Post();
                Bundle bundle_add_post = new Bundle();
                bundle_add_post.putString("fragment","food");
                fragment_add_post.setArguments(bundle_add_post);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_frameLayout,fragment_add_post).commit();


            }
        });
        return view;
    }
}

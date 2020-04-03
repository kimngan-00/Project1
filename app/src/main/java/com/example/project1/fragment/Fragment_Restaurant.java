package com.example.project1.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.project1.R;
import com.example.project1.adapters.Adapter_LV_PostUser;
import com.example.project1.daos.DAO_Places;
import com.example.project1.daos.DAO_Post;
import com.example.project1.model.FirebaseCallback;
import com.example.project1.model.Places;
import com.example.project1.model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Restaurant extends Fragment {
    private View view;
    private DAO_Places dao_places;
    private DAO_Post dao_post;
    private List<String> placeNames;
    private TextView tvTitle;
    private ListView listView;
    private Adapter_LV_PostUser adapterPost;
    private FirebaseUser user;
    private FloatingActionButton fbaAdd;

    public Fragment_Restaurant() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        initView();
        return view;
    }

    private void initView() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        dao_post = new DAO_Post(getActivity(), this);
        dao_places = new DAO_Places(getActivity(), this);
        tvTitle = (TextView) view.findViewById(R.id.fRestaurant_tvTitle);
        fbaAdd = (FloatingActionButton) view.findViewById(R.id.fRestaurant_fabAddPost);
        listView = (ListView) view.findViewById(R.id.fRestaurant_lvPost);
        String categoryNode = "restaurants";
        dao_post.getDataUser(categoryNode, new FirebaseCallback() {
            @Override
            public void postListUser(List<Post> postList) {
                adapterPost = new Adapter_LV_PostUser(getActivity(), postList);
                listView.setAdapter(adapterPost);
            }
        });


        if (user == null) {
            fbaAdd.setVisibility(View.GONE);
        }

        fbaAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_AddPost addPost = new Fragment_AddPost();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frameLayout, addPost)
                        .commit();
            }
        });

        placeNames = new ArrayList<>();


    }

    private void log(String s) {
        Log.d("log", s);
    }

    private void toast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_ac_toolbar, menu);
        MenuItem search = menu.findItem(R.id.menu_sv_place);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setQueryHint("Vui lòng nhập tên địa điểm");
        final SearchView.SearchAutoComplete autoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        autoComplete.setBackgroundColor(Color.BLUE);
        autoComplete.setTextColor(Color.GREEN);
        autoComplete.setDropDownBackgroundResource(android.R.color.holo_blue_light);
        autoComplete.setThreshold(1);
        dao_places.getData(new FirebaseCallback() {
            @Override
            public void placesList(final List<String> placesList) {
                ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, placeNames);
                autoComplete.setAdapter(newsAdapter);
                autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                        String queryString = (String) adapterView.getItemAtPosition(itemIndex);
                        autoComplete.setText(queryString);
                        tvTitle.setText(queryString);
                    }
                });
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }
}

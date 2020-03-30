package com.example.project1.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.project1.R;
import com.example.project1.adapters.Adapter_lv_Hotel;
import com.example.project1.daos.DAO_Hotel;
import com.example.project1.model.Firebase_CallBack;
import com.example.project1.model.Hotel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Hotel extends Fragment {
    private View view;
    private ListView listView;
    private FloatingActionButton FAB_add_post;
    private ArrayAdapter autoCompleteAdapter;
    public List<String> placesList;
    DatabaseReference dbPlaces;
    List<Hotel> hotelList1;
    int PICK_IMAGE_REQUEST = 10;
    Adapter_lv_Hotel adapter_lv_hotel;
    DAO_Hotel dao_hotel = new DAO_Hotel(getContext(), this);

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
        FAB_add_post = (FloatingActionButton) view.findViewById(R.id.fHotel_FAB_add_post);
        listView = (ListView) view.findViewById(R.id.fHotel_lvHotel);


        FAB_add_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Add_Post fragment_add_post = new Fragment_Add_Post();
                Bundle bundle_add_post = new Bundle();
                bundle_add_post.putString("fragment","hotel");
                fragment_add_post.setArguments(bundle_add_post);


                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.main_frameLayout, fragment_add_post)
                        .addToBackStack(null)
                        .commit();
            }
        });


        dao_hotel.getData(new Firebase_CallBack() {
            @Override
            public void getDataHotel(List<Hotel> hotelList) {
                adapter_lv_hotel = new Adapter_lv_Hotel(getActivity(), R.layout.raw_lv_all, hotelList);
                listView.setAdapter(adapter_lv_hotel);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment_View_Post fragment_view_post = new Fragment_View_Post();
                Bundle bundle_view_post = new Bundle();
                bundle_view_post.putInt("position", position);
                fragment_view_post.setArguments(bundle_view_post);

                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.main_frameLayout, fragment_view_post)
                        .addToBackStack(null)
                        .commit();


            }
        });


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
        MenuItem menuItem = menu.findItem(R.id.menu_sv_place);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm");
        final SearchView.SearchAutoComplete autoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        autoComplete.setDropDownBackgroundResource(android.R.color.holo_blue_bright);
        autoComplete.setThreshold(1);

        // autocomplete TV
        placesList = new ArrayList<>();
        dbPlaces = FirebaseDatabase.getInstance().getReference("places");

        dbPlaces.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                placesList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String place = (String) ds.getValue();
                    placesList.add(place);
                }
                autoCompleteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        autoCompleteAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, placesList);
        autoComplete.setAdapter(autoCompleteAdapter);

        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String queryString = (String) parent.getItemAtPosition(position);
                autoComplete.setText(queryString);
                Toast.makeText(getActivity(), queryString, Toast.LENGTH_SHORT).show();
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}

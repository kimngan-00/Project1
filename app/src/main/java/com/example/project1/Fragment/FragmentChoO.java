package com.example.project1.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.project1.Adapter.Adapter_LV_ChoO;
import com.example.project1.DAO.DAO_SinhVien;
import com.example.project1.R;
import com.example.project1.model.SinhVien;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChoO extends Fragment {
    private View view;
    private List<SinhVien> danhSachSinhVien;
    private DAO_SinhVien dao_sinhVien;
    private Adapter_LV_ChoO adapterLvChoO;
    private ListView listView;

    public FragmentChoO() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cho_o, container, false);
        initView();
        return view;
    }

    public void initView() {
        dao_sinhVien = new DAO_SinhVien(getActivity(),this);
        listView = (ListView) view.findViewById(R.id.fChoO_lvChoO);
        danhSachSinhVien = dao_sinhVien.laydulieu();
        adapterLvChoO = new Adapter_LV_ChoO(getActivity(),R.layout.dong_cho_o, danhSachSinhVien);
        listView.setAdapter(adapterLvChoO);
    }

    @Override
    public void onResume() {
        lamMoiDanhSachSinhVien();
        super.onResume();
    }

    public void lamMoiDanhSachSinhVien(){
        if (adapterLvChoO!=null){
            adapterLvChoO.notifyDataSetChanged();
        }

    }
}

package com.example.project1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.model.SinhVien;

import java.util.List;

public class Adapter_LV_ChoO extends BaseAdapter {
    Context context;
    int layout;
    List<SinhVien> sinhVienList;
    int index;

    public Adapter_LV_ChoO(Context context, int layout, List<SinhVien> sinhVienList) {
        this.context = context;
        this.layout = layout;
        this.sinhVienList = sinhVienList;
    }

    @Override
    public int getCount() {
        return sinhVienList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null );
        TextView ten = convertView.findViewById(R.id.dongChoO_TvDiaChi);
        TextView ma = convertView.findViewById(R.id.dongChoO_TvTenKhachSan);
        TextView diem = convertView.findViewById(R.id.dongChoO_TvGiaTien);
        SinhVien sinhVien = sinhVienList.get(position);

       ten.setText(sinhVien.getTen());
       ma.setText(sinhVien.getMa());
       diem.setText(String.valueOf(sinhVien.getDiem()));

        return convertView;
    }

}

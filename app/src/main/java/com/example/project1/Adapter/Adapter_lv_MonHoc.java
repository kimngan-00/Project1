package com.example.project1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.model.MonHoc;
import com.example.project1.model.SinhVien;

import java.util.List;

public class Adapter_lv_MonHoc extends BaseAdapter {
    Context context;
    int layout;
    List<MonHoc> monHocList;
    int index;

    public Adapter_lv_MonHoc(Context context, int layout, List<MonHoc> monHocList) {
        this.context = context;
        this.layout = layout;
        this.monHocList = monHocList;
    }

    @Override
    public int getCount() {
        return monHocList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout,null);
        TextView ma = convertView.findViewById(R.id.activity2_TvMaMh);
        TextView ten= convertView.findViewById(R.id.activity2_TvTenMH);

MonHoc monHoc = monHocList.get(position);
        ten.setText(monHoc.getTenMH());
        ma.setText(monHoc.getMaMH());

        return convertView;
    }
}

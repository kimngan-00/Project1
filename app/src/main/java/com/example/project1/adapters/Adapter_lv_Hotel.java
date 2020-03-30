package com.example.project1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.model.Hotel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_lv_Hotel extends BaseAdapter {
    Context context;
    int layout;
    List<Hotel> hotelList;


    public Adapter_lv_Hotel(Context context, int layout, List<Hotel> hotelList) {
        this.context = context;
        this.layout = layout;
        this.hotelList = hotelList;
    }

    @Override
    public int getCount() {
        return hotelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ImageView iv_Hotel;
        TextView txt_name_Hotel;
        TextView txt_address_Hotel;
        TextView txt_id_User;
        TextView txt_pubDate_Hotel;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(layout, null);

        ImageView iv_Hotel = (ImageView) convertView.findViewById(R.id.raw_lv_all_imgContent);
        TextView txt_address_Hotel = (TextView) convertView.findViewById(R.id.raw_lv_all_tvAddress);
        TextView txt_id_User = (TextView) convertView.findViewById(R.id.raw_lv_all_tvUserName);
        TextView txt_name_Hotel = (TextView) convertView.findViewById(R.id.raw_lv_all_tvNameLocate);
        TextView txt_pubDate_Hotel = (TextView) convertView.findViewById(R.id.raw_lv_all_tvPubDate);

        Hotel hotel = hotelList.get(position);
        Picasso.get().load(hotel.getImage_Hotel()).into(iv_Hotel);
        txt_address_Hotel.setText(hotel.getAddress_Hotel());
        txt_id_User.setText(hotelList.get(position).getId_User());
        txt_name_Hotel.setText(hotel.getName_Hotel());
        txt_pubDate_Hotel.setText(hotelList.get(position).getPubDate_Hotel());

        return convertView;
    }
}

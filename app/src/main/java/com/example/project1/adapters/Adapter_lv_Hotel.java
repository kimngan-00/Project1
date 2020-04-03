package com.example.project1.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.activities.MainActivity;
import com.example.project1.daos.DAO_Hotel;
import com.example.project1.fragment.Fragment_View_Post;
import com.example.project1.model.Hotel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        CircleImageView civ_User;
        ImageView iv_Hotel;
        TextView txt_name_Hotel;
        TextView txt_address_Hotel;
        TextView txt_id_User;
        TextView txt_pubDate_Hotel;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.civ_User = (CircleImageView) convertView.findViewById(R.id.raw_lv_all_civUser);
            viewHolder.iv_Hotel = (ImageView) convertView.findViewById(R.id.raw_lv_all_imgContent);
            viewHolder.txt_address_Hotel = (TextView) convertView.findViewById(R.id.raw_lv_all_tvAddress);
            viewHolder.txt_id_User = (TextView) convertView.findViewById(R.id.raw_lv_all_tvUserName);
            viewHolder.txt_name_Hotel = (TextView) convertView.findViewById(R.id.raw_lv_all_tvNameLocate);
            viewHolder.txt_pubDate_Hotel = (TextView) convertView.findViewById(R.id.raw_lv_all_tvPubDate);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Hotel hotel = hotelList.get(position);
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Picasso.get().load(hotel.getImage_Hotel()).into(viewHolder.iv_Hotel);
        Picasso.get().load(hotel.getImage_User()).into(viewHolder.civ_User);
        viewHolder.txt_address_Hotel.setText(hotel.getAddress_Hotel());
        viewHolder.txt_id_User.setText(hotel.getUser_Name());
        viewHolder.txt_name_Hotel.setText(hotel.getName_Hotel());
        viewHolder.txt_pubDate_Hotel.setText(hotelList.get(position).getPubDate_Hotel());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_View_Post view_post = new Fragment_View_Post();
                Bundle bundle = new Bundle();
                bundle.putSerializable("hotel", hotel);
                view_post.setArguments(bundle);
                ((MainActivity) context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frameLayout, view_post)
                        .addToBackStack(null)
                        .commit();
                Toast.makeText(context, hotel.getId_User(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, currentUser.getUid(), Toast.LENGTH_SHORT).show();
            }
        });

        final DAO_Hotel dao_hotel = new DAO_Hotel(context);
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(hotel.getId_User().equalsIgnoreCase(currentUser.getUid())){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dao_hotel.delete(hotelList.get(position).getId_Hotel());
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(context, "You can't delete this post", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });


        return convertView;
    }
}

package com.example.project1.adapters;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.project1.R;
import com.example.project1.activities.MainActivity;
import com.example.project1.fragment.Fragment_Post_Detail;
import com.example.project1.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_LV_PostUser extends BaseAdapter {
    private Context context;
    private List<Post> postList;
    public static final String POST = "post";


    public Adapter_LV_PostUser(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public int getCount() {
        return postList.size();
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
        convertView = inflater.inflate(R.layout.raw_post,null);
        TextView tvPubDate = (TextView) convertView.findViewById(R.id.raw_post_tvPubDate);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.raw_post_tvTitle);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.raw_post_tvAddress);
        ImageView imgPost = (ImageView) convertView.findViewById(R.id.raw_post_imgPost);
        CircleImageView imgAvatar = (CircleImageView) convertView.findViewById(R.id.raw_post_imgAvatarUser);
        final Post post = postList.get(position);

        tvPubDate.setText(post.getPubDate());
        tvTitle.setText(post.getTittle());
        tvAddress.setText(post.getAddress());
        try {
            Picasso.get().load(Uri.parse(post.getUrlAvatarUser())).into(imgAvatar);
            Picasso.get().load(Uri.parse(post.getUrlImage())).into(imgPost);
        }catch (Exception e){

        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Post_Detail fragment_post_detail = new Fragment_Post_Detail();
                Bundle bundle = new Bundle();
                bundle.putSerializable(POST, post);
                fragment_post_detail.setArguments(bundle);
                ((MainActivity)context).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frameLayout, fragment_post_detail)
                        .addToBackStack(null)
                        .commit();

            }
        });

        return convertView;
    }
}

package com.example.project1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.model.User;

import java.util.List;

public class Adapter_User extends BaseAdapter {
    Context context;
    int layout;
    List<User> userList;

    public Adapter_User(Context context, int layout, List<User> userList) {
        this.context = context;
        this.layout = layout;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
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
        convertView = inflater.inflate(layout, null);

        TextView tv_UserName, tv_Uid, tv_Email, tv_UserType;

        tv_UserName = (TextView) convertView.findViewById(R.id.raw_UserName);

        tv_Uid = (TextView) convertView.findViewById(R.id.raw_UserID);
        tv_Email = (TextView) convertView.findViewById(R.id.raw_Email);
        tv_UserType = (TextView) convertView.findViewById(R.id.raw_Type);

        User user = userList.get(position);

        tv_UserName.setText(user.getUserName());
        tv_Uid.setText(user.getId());
        tv_Email.setText(user.getEmail());
        tv_UserType.setText(user.getType());

        return convertView;
    }
}

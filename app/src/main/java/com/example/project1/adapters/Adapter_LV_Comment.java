package com.example.project1.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.R;
import com.example.project1.model.Comment;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_LV_Comment extends BaseAdapter {
    Context context;
    List<Comment> commentList;

    public Adapter_LV_Comment(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return commentList.size();
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
        TextView tvUserName;
        TextView tvComment;
        TextView tvPubDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.raw_comment, null);
            viewHolder = new ViewHolder();

            //        TextView tvPubDate = (TextView) convertView.findViewById(R.id.raw_comment_tvPubDate)
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.raw_comment_tvUserName);
            viewHolder.tvComment = (TextView) convertView.findViewById(R.id.raw_comment_tvComment);
            viewHolder.civ_User = (CircleImageView) convertView.findViewById(R.id.raw_comment_civAvatar);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        Comment comment = commentList.get(position);

        viewHolder.tvUserName.setText(comment.getUserName());
//        tvPubDate.setText(comment.getPubDate());
        viewHolder.tvComment.setText(comment.getContentComment());
        Picasso.get().load(Uri.parse(comment.getUriAvatarUser())).into(viewHolder.civ_User);

        return convertView;
    }
}

package com.example.admin.prclosechat_android.adapter;

/**
 * Created by Admin on 1/10/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.admin.prclosechat_android.R;
import com.example.admin.prclosechat_android.model.User;
import com.example.admin.prclosechat_android.view.TextFitTextView;

import java.util.ArrayList;


public class UserAdapter extends BaseAdapter {

    Context context;
    ArrayList<User> listData;

    public UserAdapter(Context context, ArrayList<User> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        private TextFitTextView tvUserName;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.user_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvUserName = (TextFitTextView) view.findViewById(R.id.tvUserName);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        User user = listData.get(position);
        String userName = user.getName();
        viewHolder.tvUserName.setText(userName);
        return view;
    }
}
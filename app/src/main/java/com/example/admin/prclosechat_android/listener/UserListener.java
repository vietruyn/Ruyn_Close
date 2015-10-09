package com.example.admin.prclosechat_android.listener;

/**
 * Created by Admin on 1/10/2015.
 */


import com.example.admin.prclosechat_android.model.User;

import java.util.ArrayList;

/**
 * Created by PP on 6/14/2015.
 */
public interface UserListener {

    public void addUser(User user);

    public ArrayList<User> getAllUser();

    public int getUserCount();
}
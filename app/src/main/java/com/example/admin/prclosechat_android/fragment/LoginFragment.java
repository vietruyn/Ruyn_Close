package com.example.admin.prclosechat_android.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.admin.prclosechat_android.HomeActivity;
import com.example.admin.prclosechat_android.R;

/**
 * Created by Admin on 29/9/2015.
 */
public class LoginFragment extends Fragment {
    private Button btnLogin;
    private String mUserName , mPassword;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_login, container, false);

        btnLogin = (Button) view.findViewById(R.id.btnSignIn);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                LoginFragment.this.startActivity(intent);
            }
        });
        //Inflate the layout for this fragment
        return view;
    }

}

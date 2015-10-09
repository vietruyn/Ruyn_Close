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

//import com.example.admin.prclosechat_android.HomeActivity;

/**
 * Created by Admin on 29/9/2015.
 */
public class SignUpFragment extends Fragment {
    private Button btnJoinNow;
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_sign_up, container, false);
        btnJoinNow = (Button) view.findViewById(R.id.btnJoinNow);
        btnJoinNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                SignUpFragment.this.startActivity(intent);
            }
        });
        //Inflate the layout for this fragment
        return view;

    }

    private void init() {

    }
}

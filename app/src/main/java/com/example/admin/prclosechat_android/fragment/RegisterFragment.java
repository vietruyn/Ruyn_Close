package com.example.admin.prclosechat_android.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.prclosechat_android.R;


/**
 * Created by Admin on 29/9/2015.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private Button btnJoinNow;
    private EditText edtUserName, edtPassword, edtConfirmPassword;
    private String userName, passWord, confirmPassword;

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_register, container, false);

        init(view);
        return view;

    }


    private void init(View mView) {
        edtUserName = (EditText) mView.findViewById(R.id.edtUserName);
        edtPassword = (EditText) mView.findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) mView.findViewById(R.id.edtConfirmPassword);
        btnJoinNow = (Button) mView.findViewById(R.id.btnJoinNow);

        btnJoinNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnJoinNow:
                getData();
                if (userName.isEmpty() || passWord.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getActivity(), "Not be empty!", Toast.LENGTH_LONG).show();
                } else if (passWord.compareTo(confirmPassword) != 0) {
                    edtConfirmPassword.setError("Passwords don't match. Please re-enter Confirm Password");
                } else {
                    //Put the value
                    LoginFragment ldf = new LoginFragment();
                    getFragmentManager().beginTransaction().add(R.id.frm, ldf).commit();
                }

                break;
        }
    }

    private void getData() {
        userName = edtUserName.getText().toString();
        passWord = edtPassword.getText().toString();
        confirmPassword = edtConfirmPassword.getText().toString();
    }

}

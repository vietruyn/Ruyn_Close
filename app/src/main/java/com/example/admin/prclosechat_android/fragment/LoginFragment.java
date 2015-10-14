package com.example.admin.prclosechat_android.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.prclosechat_android.HomeActivity;
import com.example.admin.prclosechat_android.R;
import com.example.admin.prclosechat_android.register.ConnectWebservice;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Admin on 29/9/2015.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private Button btnLogin;
    private String mEmail, mPassword, emailPattern, check;
    private EditText edtEmail, edtPassword;
    private ProgressDialog mProgressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_login, container, false);

        init(view);
        hideKeyboard(view);

        return view;
    }

    /**
     * Khoi tao
     *
     * @param view
     */
    private void init(View view) {
        btnLogin = (Button) view.findViewById(R.id.btnSignIn);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(this);
    }

    /**
     * An ban phim
     *
     * @param view
     */
    private void hideKeyboard(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard();
                    return false;
                }
            });
        }
    }

    private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Su kien cho cac view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        getValue();
        switch (v.getId()) {
            case R.id.btnSignIn:
                new CheckLogin().execute();

        }

    }

    private void toastMessenger(String a) {
        Toast.makeText(getActivity(), a, Toast.LENGTH_SHORT).show();
    }

    private void getValue() {
        mEmail = edtEmail.getText().toString();
        mPassword = edtPassword.getText().toString();
        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    }

    class CheckLogin extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Please Waiting...");
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            String url = "http://closechat.com/closechatwebservice/index.php?route=webservice/users/register/login&password=" + mPassword + "&username=" + mEmail;
            ConnectWebservice connectWebservice = new ConnectWebservice(url);
            connectWebservice.fetchJSON();
            while (connectWebservice.parsingComplete) ;

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(connectWebservice.getData());
                check = jsonObject.getString("check");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return check;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressDialog.hide();
            switch (s) {
                    case "0":
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(intent);
                        break;
                    case "1":
                        toastMessenger("Username is not exist ");
                        break;
                    case "2":
                        toastMessenger("Password is not exist");
                        break;
                    case "3":
                        toastMessenger("User login erorr, check username and password again");
                        break;
                    default:
                        return;
                }

        }
    }


}

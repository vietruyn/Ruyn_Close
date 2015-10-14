package com.example.admin.prclosechat_android;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.prclosechat_android.adapter.UserAdapter;
import com.example.admin.prclosechat_android.handler.DBHandler;
import com.example.admin.prclosechat_android.model.User;
import com.example.admin.prclosechat_android.network.NetworkUtils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ListContactActivity extends Activity implements View.OnClickListener {

    private ListView listView;
    private UserAdapter adapter;
    private ArrayList<User> userArrayList;
    private DBHandler handler;
    private LinearLayout lnCallapp, lnCallphone, lnChat, lnSms;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);
        listView = (ListView) findViewById(R.id.listview);
        handler = new DBHandler(this);
        NetworkUtils utils = new NetworkUtils(ListContactActivity.this);
        callAsyncTask(utils);
    }

    /**
     * Check conditions and paser data
     *
     * @param utils
     */
    private void callAsyncTask(NetworkUtils utils) {
        if (handler.getUserCount() == 0 && utils.isConnectingToInternet()) {
            new DataFetcherTask().execute();
        } else {
            final ArrayList<User> userList = handler.getAllUser();
            adapter = new UserAdapter(ListContactActivity.this, userList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ArrayList<User> userList = handler.getAllUser();
                    Toast.makeText(ListContactActivity.this, " " + userList.get(position).getName(), Toast.LENGTH_LONG).show();
//                    showPopupWindow(view);
                    dialog = new Dialog(ListContactActivity.this, R.style.mydialogstyle);
                    dialog.setContentView(R.layout.custom_dialog);
                    initDialog(dialog);
                    dialog.show();
                }
            });
        }
    }

    /**
     * @param dialog
     */
    private void initDialog(Dialog dialog) {
        lnCallapp = (LinearLayout) dialog.findViewById(R.id.callApp);
        lnCallphone = (LinearLayout) dialog.findViewById(R.id.callPhone);
        lnChat = (LinearLayout) dialog.findViewById(R.id.chat);
        lnSms = (LinearLayout) dialog.findViewById(R.id.sms);

        lnCallapp.setOnClickListener(this);
        lnCallphone.setOnClickListener(this);
        lnChat.setOnClickListener(this);
        lnSms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.callApp:
                Intent mIntent = new Intent(ListContactActivity.this, Call1Activity.class);
                startActivity(mIntent);
                dialog.hide();
                break;
            case R.id.callPhone:
                Intent mIntent1 = new Intent(ListContactActivity.this, Call2Activity.class);
                startActivity(mIntent1);
                dialog.hide();
                break;
            case R.id.chat:
                Intent mIntent2 = new Intent(ListContactActivity.this, Call1Activity.class);
                startActivity(mIntent2);
                dialog.hide();
                break;
            case R.id.sms:
                Intent mIntent3 = new Intent(ListContactActivity.this, Call1Activity.class);
                startActivity(mIntent3);
                dialog.hide();
                break;
            default:
        }

    }

    /**
     * Thread paser data whit Asynctack
     */
    class DataFetcherTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String serverData = null;// String object to store fetched data from server

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://closechat.com/closechatwebservice/index.php?route=webservice/users/register/getall");
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                serverData = EntityUtils.toString(httpEntity);
                Log.e("response", serverData);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                userArrayList = new ArrayList<User>();
                JSONObject jsonObject = new JSONObject(serverData);
                JSONArray jsonArray = jsonObject.getJSONArray("users");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectCity = jsonArray.getJSONObject(i);
                    String username = jsonObjectCity.getString("username");
                    int customerId = jsonObjectCity.getInt("customer_id");
                    User user = new User();
                    user.setName(username);
                    user.setId(customerId);
                    Log.e("Name and Id: ", username + " " + customerId);
                    handler.addUser(user);// Inserting into DB
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayList<User> userList = handler.getAllUser();
            adapter = new UserAdapter(ListContactActivity.this, userList);
            listView.setAdapter(adapter);
        }
    }
    @Override
    public void onBackPressed() {

        finish();
        return;
    }
}

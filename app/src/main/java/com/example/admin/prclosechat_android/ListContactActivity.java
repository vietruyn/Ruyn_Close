package com.example.admin.prclosechat_android;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
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

public class ListContactActivity extends Activity {

    ListView listView;
    UserAdapter adapter;
    ArrayList<User> userArrayList;
    DBHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);
        listView = (ListView) findViewById(R.id.listview);
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1f1f1f")));
        actionBar.setTitle("List Contact");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
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
                    //Creating the instance of PopupMenu
                    PopupMenu popup = new PopupMenu(ListContactActivity.this, view);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            Toast.makeText(ListContactActivity.this, "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });

                    popup.show();//showing popup menu

                }
            });
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
}
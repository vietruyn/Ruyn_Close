package com.example.admin.prclosechat_android;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.prclosechat_android.fragment.LoginFragment;
import com.example.admin.prclosechat_android.fragment.RegisterFragment;

public class MainActivity extends FragmentActivity {

    private LinearLayout lnLogin, lnSignUp;
    private Fragment fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if(savedInstanceState==null ){
            fr = new LoginFragment();
            lnLogin.setBackgroundResource(R.color.click_button);
            lnSignUp.setBackgroundResource(R.color.unclick_button);
            callFragment(fr);
        }
        else return;
    }

    /**
     * Initialization
     **/
    private void init() {
        lnLogin = (LinearLayout) findViewById(R.id.lnLogin);
        lnSignUp = (LinearLayout) findViewById(R.id.lnSignUp);
    }

    /**
     * Event change fragment when click tabs
     * * @param view
     */
    public void selectFrag(View view) {


        if (view == findViewById(R.id.lnSignUp)) {
            fr = new RegisterFragment();
            lnSignUp.setBackgroundResource(R.color.click_button);
            lnLogin.setBackgroundResource(R.color.unclick_button);
            callFragment(fr);

        } else {
            fr = new LoginFragment();
            lnLogin.setBackgroundResource(R.color.click_button);
            lnSignUp.setBackgroundResource(R.color.unclick_button);
            callFragment(fr);
        }



    }

    private void callFragment(Fragment fr) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frm, fr);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.popup_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "Thanks for using application!!", Toast.LENGTH_LONG).show();
        finish();
        return;
    }
}

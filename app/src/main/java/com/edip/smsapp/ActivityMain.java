package com.edip.smsapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ActivityMain extends Activity {

    private BroadcastReceiver mIntentReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences getCode = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
         final String customCode = getCode.getString("code", "12345");



        IntentFilter intentFilter = new IntentFilter("SmsMessage.intent.MAIN");
        mIntentReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = intent.getStringExtra("get_msg");

                // Process the sms format and extract body &amp; phoneNumber
                msg = msg.replace("\n", "");
                String body = msg.substring(msg.lastIndexOf(":") + 1,
                        msg.length());
                String pNumber = msg.substring(0, msg.lastIndexOf(":"));

                // Add it to the list or do whatever you wish to
                Log.e("onResume", "" + msg + body + pNumber);

                //  Toast.makeText(getApplicationContext(), body, Toast.LENGTH_SHORT).show();

                // check body content with your validation code
                if (body.equalsIgnoreCase(customCode)) {

                    Toast.makeText(getApplicationContext(),
                            "3G is On", Toast.LENGTH_SHORT).show();

                    TurnOn();

                } else {

                    // if message contains invalid code
                    Toast.makeText(getApplicationContext(),
                            "New Message", Toast.LENGTH_SHORT).show();

                    //     SignInWaitingActivity.this.finish();

                }

            }
        };
        this.registerReceiver(mIntentReceiver, intentFilter);
    }

    private void TurnOn() {

        ConnectivityManager dataManager;
        dataManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Method dataMtd = null;
        try {
            dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        dataMtd.setAccessible(true);
        try {
            dataMtd.invoke(dataManager, true);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){

            case R.id.action_settings:
                    Intent OpenSettings = new Intent(this, Prefs.class);
                startActivity(OpenSettings);
                break;
        }

        return false;
    }
}

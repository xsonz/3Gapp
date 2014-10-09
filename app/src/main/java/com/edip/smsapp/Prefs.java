package com.edip.smsapp;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Eddy on 10/9/2014.
 */
public class Prefs extends PreferenceActivity {

    @Override
    @SuppressWarnings("deprecation")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
    }
}

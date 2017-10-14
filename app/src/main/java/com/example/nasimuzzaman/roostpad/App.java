package com.example.nasimuzzaman.roostpad;

import android.app.Application;
import android.content.Context;

import com.binjar.prefsdroid.Preference;

/**
 * Created by nasimuzzaman on 10/14/17.
 */

public class App extends Application {
    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Preference.load().using(this).with(PrefKeys.PREF_NAME).prepare();
    }

    public static Context getContext() {
        return context;
    }
}

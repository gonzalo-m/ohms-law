package com.ilogic.ohmslaw;

import android.app.Application;

/**
 * Created by G on 8/7/16.
 */
public class App extends Application {

    private static App sAppInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppInstance = this;
    }

    public static App getAppInstance() {
        return sAppInstance;
    }
}

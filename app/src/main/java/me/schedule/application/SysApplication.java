package me.schedule.application;

import android.app.Application;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class SysApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ECrashHandler eCrashHandler = new ECrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(eCrashHandler);
    }
}

package me.schedule.application;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class SysApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        ECrashHandler eCrashHandler = new ECrashHandler(this);
//        Thread.setDefaultUncaughtExceptionHandler(eCrashHandler);
    }

    private class RecentScheduleRunnable implements Runnable {
        @Override
        public void run() {
            //TODO
//            handler.postDelayed(this, 2000);
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}

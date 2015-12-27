package me.schedule.application;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;

import me.schedule.R;
import me.schedule.activity.MainActivity;
import me.schedule.bean.ScheduleBean;
import me.schedule.dao.ScheduleDAO;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class SysApplication extends Application {

    private NotificationManager notificationManager;
    private final int NOTIFICATION_ID = 49;
    private ScheduleBean scheduleBean;
//    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        prepareRecent();

//        ECrashHandler eCrashHandler = new ECrashHandler(this);
//        Thread.setDefaultUncaughtExceptionHandler(eCrashHandler);
    }

    private void prepareRecent(){
        ScheduleDAO dao = ScheduleDAO.getInstance(this);
        scheduleBean = dao.getRecent();
        if(scheduleBean != null){
//            Log.e("recenttime","RecentTime:" +  scheduleBean.getRecentTime());
            handler.postDelayed(new RecentScheduleRunnable(), scheduleBean.getRecentTime());
        }
    }

    private class RecentScheduleRunnable implements Runnable {
        @Override
        public void run() {
            sendNotification();
            prepareRecent();
        }
    }

//    private void playRing(){
//        if(mediaPlayer == null){
//            mediaPlayer = new MediaPlayer();
//        }
//        Uri uri = Uri.parse("android.resource://" + getPackageName() +"/"+ R.raw.ringer);
//        try {
//            mediaPlayer.setDataSource(this, uri);
//            mediaPlayer.prepare();
//            mediaPlayer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void sendNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(
                getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentText(scheduleBean.getName() + "\n" + scheduleBean.getRemark());
        builder.setContentTitle("你有一个新代办事项")
                .setSmallIcon(getApplicationInfo().icon)
                .setContentIntent(pendingIntent)
//                .setDefaults(Notification.DEFAULT_ALL)
                .setShowWhen(true)
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ringer))
                .setAutoCancel(true);
        builder.setTicker("新代办事项提示");

        Notification notification = builder.build();
        // notification.flags = Notification.FLAG_AUTO_CANCEL;

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    private Handler handler = new Handler();
}

package me.schedule.application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.schedule.activity.AddScheduleActivity;
import me.schedule.activity.MainActivity;

/**
 * Created by caowenhua on 2015/11/16.
 */
public class ECrashHandler implements Thread.UncaughtExceptionHandler {

    private Context context;

    public ECrashHandler(Context context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        createExceptionReport(ex);

//        try {
//            Thread.sleep(300);
//        }
//        catch (Exception e){
//            Log.e("thread ex", e.getMessage());
//        }

        Log.e("thread id", "thread id : " + thread.getId());
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, "应用闪退", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();

        try {
            Thread.sleep(300);
        }
        catch (Exception e){
            Log.e("thread ex", e.getMessage());
        }

        handleException();

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);

//        handleException();
    }

    private void createExceptionReport(Throwable ex){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(getDateByTime() + "\n" + ex.getMessage() + "\n");

        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            stringBuffer.append(elements[i].toString() + "\n");
        }

        stringBuffer.append("\n\n");
        stringBuffer.append("device:" + Build.DEVICE + "\n");
        stringBuffer.append("sdk:" + Build.VERSION.SDK_INT  + "\n");
        stringBuffer.append("MODEL:" + Build.MODEL + "\n");
        stringBuffer.append("RELEASE:" + Build.VERSION.RELEASE + "\n");
        stringBuffer.append("radio:" + Build.getRadioVersion() + "\n");
        stringBuffer.append("CPU_ABI:" + Build.CPU_ABI + "\n");
        stringBuffer.append("CPU_ABI2:" + Build.CPU_ABI2 + "\n");
        stringBuffer.append("DISPLAY:" + Build.DISPLAY + "\n");


        Log.e("eExpetion", stringBuffer.toString());

        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/eclog.txt");
        try {
            FileOutputStream stream = new FileOutputStream(file, true);
            stream.write("\n\n\n".getBytes());
            stream.write(stringBuffer.toString().getBytes());
            stream.flush();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        //TODO 处理string
    }

    private void handleException(){
        Intent i = new Intent(context, MainActivity.class);
        context.startActivity(i);
    }

    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null"
                        : pi.versionName;
                String versionCode = pi.versionCode + "";
//                infos.put("versionName", versionName);
//                infos.put("versionCode", versionCode);
//                infos.put("crashTime", formatter.format(new Date()));
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
//        Field[] fields = Build.class.getDeclaredFields();
//        for (Field field: fields) {
//            try {
//                field.setAccessible(true);
//                infos.put(field.getName(), field.get(null).toString());
//            } catch (Exception e) {
//            }
//        }
    }

    private void restart(){
        try{
            Thread.sleep(300);
        }catch (InterruptedException e){
        }
        Intent intent = new Intent(context.getApplicationContext(), AddScheduleActivity.class);
        PendingIntent restartIntent = PendingIntent.getActivity(
                context.getApplicationContext(), 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
//                Intent.FLAG_ACTIVITY_NEW_TASK);
        //退出程序
        AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 500,
                restartIntent); // 0.5秒钟后重启应用
    }

    private String getDateByTime() {
        String temp = "";
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.getDefault());
        temp = sdf.format(dt);
        return temp;
    }
}

package me.schedule.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import me.schedule.R;
import me.schedule.util.ScreenUtils;
import me.schedule.widget.MouthCalenderView;

/**
 * Created by caowenhua on 2015/11/5.
 */
public class TestActivity extends Activity {

    MouthCalenderView view_mouth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouth_schedule);
        view_mouth = (MouthCalenderView) findViewById(R.id.view_mouth);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.instance(this).getScreenWidth(),
                ScreenUtils.instance(this).getScreenHeight()*4/5);
        view_mouth.setLayoutParams(params);
    }
}

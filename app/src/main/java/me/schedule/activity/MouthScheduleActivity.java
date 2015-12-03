package me.schedule.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import me.schedule.R;
import me.schedule.listener.OnMouthCalenderListener;
import me.schedule.widget.MouthCalenderView;

/**
 * Created by caowenhua on 2015/11/5.
 */
public class MouthScheduleActivity extends Activity implements View.OnClickListener, OnMouthCalenderListener{

    private ImageView imgBack;
    private TextView tvDate;
    private MouthCalenderView viewMouth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouth_schedule);

        assignViews();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int mouth = calendar.get(Calendar.MONTH);
        tvDate.setText(year + "." + mouth);
    }

    private void assignViews() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        tvDate = (TextView) findViewById(R.id.tv_date);
        viewMouth = (MouthCalenderView) findViewById(R.id.view_mouth);

        imgBack.setOnClickListener(this);
        viewMouth.setOnMouthCalenderListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == imgBack){
            finish();
        }
    }

    @Override
    public void onDayClick(int year, int mouth, int day) {

    }

    @Override
    public void onMouthChange(int year, int mouth) {
        tvDate.setText(year + "." + mouth);
    }
}

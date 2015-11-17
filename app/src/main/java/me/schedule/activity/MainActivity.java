package me.schedule.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import me.schedule.R;
import me.schedule.adapter.MainListAdapter;
import me.schedule.dao.ScheduleTimeDAO;

/**
 * Created by caowenhua on 2015/11/5.
 */
//当天待办
public class MainActivity extends Activity implements View.OnClickListener {

    private ImageView imgSetting;
    private ImageView imgTick;
    private ListView lvList;
    private ImageView imgSchedule;
    private ImageView imgAdd;
    private ImageView imgCourse;

    private MainListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assignViews();

        ScheduleTimeDAO dao = ScheduleTimeDAO.getInstance(this);
        adapter = new MainListAdapter(dao.getCurrent(), this);
        lvList.setAdapter(adapter);
    }


    private void assignViews() {
        imgSetting = (ImageView) findViewById(R.id.img_setting);
        imgTick = (ImageView) findViewById(R.id.img_tick);
        lvList = (ListView) findViewById(R.id.lv_list);
        imgSchedule = (ImageView) findViewById(R.id.img_schedule);
        imgAdd = (ImageView) findViewById(R.id.img_add);
        imgCourse = (ImageView) findViewById(R.id.img_course);

        imgAdd.setOnClickListener(this);
        imgTick.setOnClickListener(this);
        imgSetting.setOnClickListener(this);
        imgSchedule.setOnClickListener(this);
        imgCourse.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_setting:
                Intent setting = new Intent(this, SettingActivity.class);
                startActivity(setting);
                break;
            case R.id.img_tick:
                break;
            case R.id.img_schedule:
                Intent schedule = new Intent(this, ScheduleActivity.class);
                startActivity(schedule);
                break;
            case R.id.img_add:
                Intent add = new Intent(this, AddScheduleActivity.class);
                startActivity(add);
                break;
            case R.id.img_course:
                Intent course = new Intent(this, CourseActivity.class);
                startActivity(course);
                break;
        }
    }
}

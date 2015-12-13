package me.schedule.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import me.schedule.R;
import me.schedule.adapter.MainListAdapter;
import me.schedule.bean.ScheduleBean;
import me.schedule.dao.ScheduleDAO;
import me.schedule.listener.OnButtonClickListener;
import me.schedule.listener.OnDateChangeListener;
import me.schedule.widget.dialog.ChooseDayDialog;
import me.schedule.widget.dialog.ScheduleMoreDialog;

/**
 * Created by caowenhua on 2015/11/4.
 */

public class ScheduleActivity extends Activity implements View.OnClickListener{

    private ImageView imgBack;
    private TextView tvDate;
    private ImageView imgMore;
    private ListView lvList;

    private MainListAdapter adapter;
    private List<ScheduleBean> list;

    private int year;
    private int mouth;
    private int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        assignViews();
        ScheduleDAO dao = ScheduleDAO.getInstance(this);

        if(getIntent().getIntExtra("year", -1) == -1){
            list = dao.getToday();
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            mouth = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);
        }
        else{
            year = getIntent().getIntExtra("year", 2015);
            mouth = getIntent().getIntExtra("mouth", 12);
            day = getIntent().getIntExtra("day", 1);
            list = dao.getDay(year, mouth, day);
        }

        tvDate.setText(year + "." + mouth + "." + day);
        adapter = new MainListAdapter(list, this);
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent add = new Intent(ScheduleActivity.this, AddScheduleActivity.class);
                add.putExtra("scheduleBean", list.get(position));
                startActivity(add);
            }
        });
    }



    private void assignViews() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        tvDate = (TextView) findViewById(R.id.tv_date);
        imgMore = (ImageView) findViewById(R.id.img_more);
        lvList = (ListView) findViewById(R.id.lv_list);

        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
        tvDate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(imgBack == v){
            finish();
        }
        else if(v == imgMore){
            ScheduleMoreDialog scheduleMoreDialog = new ScheduleMoreDialog(this, new OnButtonClickListener() {
                @Override
                public void onButtonClick() {
                    Intent intent = new Intent(ScheduleActivity.this, MouthScheduleActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, new OnButtonClickListener() {
                @Override
                public void onButtonClick() {
                    Intent intent = new Intent(ScheduleActivity.this, WeekScheduleActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, new OnButtonClickListener() {
                @Override
                public void onButtonClick() {
                    Intent intent = new Intent(ScheduleActivity.this, AddScheduleActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
        else if(v == tvDate){
            ChooseDayDialog chooseDayDialog = new ChooseDayDialog(this, year, mouth, day);
            chooseDayDialog.setOnDateChangeListener(new OnDateChangeListener() {
                @Override
                public void OnDateChange(int year, int mouth, int day) {
                    ScheduleActivity.this.year = year;
                    ScheduleActivity.this.mouth = mouth;
                    ScheduleActivity.this.day = day;
                    tvDate.setText(year + "." + mouth + "." + day);
                    list.clear();
                    ScheduleDAO dao = ScheduleDAO.getInstance(ScheduleActivity.this);
                    list.addAll(dao.getDay(year, mouth, day));
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}

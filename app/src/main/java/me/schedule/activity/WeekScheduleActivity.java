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
import me.schedule.listener.OnTimeCommitListener;
import me.schedule.listener.OnWeekChangeListener;
import me.schedule.widget.dialog.ChooseWeekDialog;
import me.schedule.widget.dialog.MainClickDialog;
import me.schedule.widget.dialog.ScheduleMoreDialog;

/**
 * Created by caowenhua on 2015/11/4.
 */

public class WeekScheduleActivity extends Activity implements View.OnClickListener{

    private ImageView imgBack;
    private TextView tvDate;
    private ImageView imgMore;
    private ListView lvList;
    private TextView tvTip;

    private MainListAdapter adapter;
    private List<ScheduleBean> list;

    private int year;
    private int mouth;
    private int firstDay;
    private int lastDay;
    private ScheduleDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_schedule);

        assignViews();
        dao = ScheduleDAO.getInstance(this);

        Calendar calendar = Calendar.getInstance();

        firstDay = calendar.get(Calendar.DAY_OF_MONTH);
        mouth = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);
        lastDay = firstDay + 7 > getMaxDay(year, mouth)? getMaxDay(year, mouth) : firstDay + 7;

        tvDate.setText(year + "." + mouth + "." + firstDay + "-" + lastDay);
        list = dao.getWeek(year,mouth,firstDay,lastDay);
        adapter = new MainListAdapter(list, this);
        if (list.size() > 0){
            tvTip.setVisibility(View.GONE);
        }
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                MainClickDialog mainClickDialog = new MainClickDialog(WeekScheduleActivity.this,
                        new OnButtonClickListener() {
                            @Override
                            public void onButtonClick() {
                                Intent add = new Intent(WeekScheduleActivity.this, AddScheduleActivity.class);
                                add.putExtra("scheduleBean", list.get(position));
                                startActivity(add);
                            }
                        },
                        new OnButtonClickListener() {
                            @Override
                            public void onButtonClick() {
                                dao.delete(list.get(position));
                                list.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        });
            }
        });
    }



    private void assignViews() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        tvDate = (TextView) findViewById(R.id.tv_date);
        imgMore = (ImageView) findViewById(R.id.img_more);
        lvList = (ListView) findViewById(R.id.lv_list);
        tvTip = (TextView) findViewById(R.id.tv_tip);

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
                    Intent intent = new Intent(WeekScheduleActivity.this, MouthScheduleActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, new OnButtonClickListener() {
                @Override
                public void onButtonClick() {
                    Intent intent = new Intent(WeekScheduleActivity.this, ScheduleActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, new OnButtonClickListener() {
                @Override
                public void onButtonClick() {
                    Intent intent = new Intent(WeekScheduleActivity.this, AddScheduleActivity.class);
                    startActivity(intent);
                    finish();
                }
            },true);
        }
        else if(v == tvDate){
            ChooseWeekDialog chooseWeekDialog = new ChooseWeekDialog(this, year, mouth, firstDay, lastDay);
            chooseWeekDialog.setOnWeekChangeListener(new OnWeekChangeListener() {
                @Override
                public void onWeekChange(int year, int mouth, int firstday, int lastday) {
                    WeekScheduleActivity.this.year = year;
                    WeekScheduleActivity.this.mouth = mouth;
                    firstDay = firstday;
                    lastDay = lastday;
                    tvDate.setText(year + "." + mouth + "." + firstDay + "-" + lastDay);
                }
            });
            chooseWeekDialog.setOnTimeCommitListener(new OnTimeCommitListener() {
                @Override
                public void onCommitTime() {
                    list.clear();
                    ScheduleDAO dao = ScheduleDAO.getInstance(WeekScheduleActivity.this);
                    list.addAll(dao.getWeek(year, mouth, firstDay, lastDay));
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    public int getMaxDay(int year, int mouth){
        switch (mouth){
            case 1:
                return 31;
            case 2:
                if(year % 4 == 0){
                    return 28;
                }
                else{
                    return 29;
                }
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
        }
        return 31;
    }
}

package me.schedule.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import me.schedule.R;
import me.schedule.adapter.MainListAdapter;
import me.schedule.bean.ScheduleBean;
import me.schedule.dao.ScheduleDAO;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        assignViews();

        ScheduleDAO dao = ScheduleDAO.getInstance(this);
        list = dao.getToday();
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

        }
        else if(v == tvDate){

        }
    }
}

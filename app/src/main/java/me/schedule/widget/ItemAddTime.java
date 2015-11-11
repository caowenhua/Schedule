package me.schedule.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.schedule.R;
import me.schedule.bean.ScheduleTimeBean;
import me.schedule.listener.OnTimeClickListener;
import me.schedule.util.DateUtil;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class ItemAddTime extends RelativeLayout implements View.OnClickListener {
    private ImageView imgDel;
    private TextView tvDay;
    private TextView tvTime;

    private int id;
    private OnTimeClickListener onTimeClickListener;
    private ScheduleTimeBean scheduleTimeBean;

    public ItemAddTime(Context context, int id) {
        super(context);
        this.id = id;
        init();
    }

    public ItemAddTime(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemAddTime(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_add_time, this);
        assignViews();

        scheduleTimeBean = new ScheduleTimeBean();
        scheduleTimeBean.setIsCycle(false);

        long time = System.currentTimeMillis() / 1000;
        scheduleTimeBean.setYear(Integer.parseInt(DateUtil.getYearByTime(time)));
        scheduleTimeBean.setMouth(Integer.parseInt(DateUtil.getMouthByTime(time)));
        scheduleTimeBean.setDay(Integer.parseInt(DateUtil.getDayByTime(time)));
        scheduleTimeBean.setHour(Integer.parseInt(DateUtil.getHourByTime(time)));
        scheduleTimeBean.setMinute(Integer.parseInt(DateUtil.getMinuteByTime(time)));
        scheduleTimeBean.setDays(new boolean[]{true,true,true,true,true,false,false});
        scheduleTimeBean.setCycle("0,1,2,3,4");
    }

    private void assignViews() {
        imgDel = (ImageView) findViewById(R.id.img_del);
        tvDay = (TextView) findViewById(R.id.tv_day);
        tvTime = (TextView) findViewById(R.id.tv_time);

        imgDel.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        tvDay.setOnClickListener(this);
    }

    public void setData(String day, String time) {
        tvDay.setText(day);
        tvTime.setText(time);
    }

    public void setOnTimeClickListener(OnTimeClickListener onTimeClickListener) {
        this.onTimeClickListener = onTimeClickListener;
    }

    public int getItemId(){
        return id;
    }

    public ScheduleTimeBean getScheduleTimeBean() {
        return scheduleTimeBean;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_del:
                if(onTimeClickListener != null){
                    onTimeClickListener.onDelClick(id, scheduleTimeBean);
                }
                break;
            case R.id.tv_day:
                if(onTimeClickListener != null){
                    onTimeClickListener.onDayClick(id, scheduleTimeBean);
                }
                break;
            case R.id.tv_time:
                if(onTimeClickListener != null){
                    onTimeClickListener.onTimeClick(id, scheduleTimeBean);
                }
                break;
        }
    }
}

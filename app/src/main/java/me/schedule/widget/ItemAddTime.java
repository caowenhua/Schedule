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

    public ItemAddTime(Context context, int id, ScheduleTimeBean scheduleTimeBean) {
        super(context);
        this.id = id;
        this.scheduleTimeBean = scheduleTimeBean;

        LayoutInflater.from(getContext()).inflate(R.layout.item_add_time, this);
        assignViews();

        if(!scheduleTimeBean.isCycle()){
            setData(scheduleTimeBean.getYear() + "." + scheduleTimeBean.getMouth() + "." +scheduleTimeBean.getDay(),
                    scheduleTimeBean.getHour() + ":" + scheduleTimeBean.getMinute());
        }
        else{
            setData(getCycleString(scheduleTimeBean.getDays()), scheduleTimeBean.getHour() + ":" + scheduleTimeBean.getMinute());
        }
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
        scheduleTimeBean.setDays(new boolean[]{true, true, true, true, true, false, false});
        scheduleTimeBean.setCycle("0,1,2,3,4");

        if(scheduleTimeBean.isCycle()){
            setData(scheduleTimeBean.getYear() + "." + scheduleTimeBean.getMouth() + "." +scheduleTimeBean.getDay(),
                    scheduleTimeBean.getHour() + ":" + scheduleTimeBean.getMinute());
        }
        else{
            setData(getCycleString(scheduleTimeBean.getDays()), scheduleTimeBean.getHour() + ":" + scheduleTimeBean.getMinute());
        }

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

    private String getCycleString(boolean[] cycle){
        if(cycle[0] && cycle[1] && cycle[2] && cycle[3] && cycle[4] && cycle[5] && cycle[6]){
            return "每天";
        }
        else if(cycle[5] && cycle[1] && cycle[2] && cycle[3] && cycle[4] && !cycle[0] && !cycle[6]){
            return "工作日";
        }
        else if(!cycle[5] && !cycle[1] && !cycle[2] && !cycle[3] && !cycle[4] && cycle[0] && cycle[6]){
            return "周末";
        }
        else {
            String cycleString = "";
            boolean isFirst = true;
            for (int i = 0; i < cycle.length; i++) {
                if(cycle[i]){
                    if(isFirst){
                        cycleString = "每周" + getDayByIndex(i);
                        isFirst = false;
                    }
                    else{
                        cycleString = cycleString + "," + getDayByIndex(i);
                    }
                }
            }
            return cycleString;
        }
    }

    private String getDayByIndex(int index){
        switch (index){
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 0:
                return "日";
        }
        return "";
    }
}

package me.schedule.listener;

import me.schedule.bean.ScheduleTimeBean;

/**
 * Created by caowenhua on 2015/11/4.
 */
public interface OnTimeCreateListener {
    void onTimeCreate(int id, ScheduleTimeBean bean);
}

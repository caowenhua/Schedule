package me.schedule.listener;

import me.schedule.bean.ScheduleTimeBean;

/**
 * Created by caowenhua on 2015/11/5.
 */
public interface OnTimeClickListener {
    void onTimeClick(int id, ScheduleTimeBean bean);
    void onDelClick(int id, ScheduleTimeBean bean);
    void onDayClick(int id, ScheduleTimeBean bean);
}

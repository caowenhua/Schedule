package me.schedule.listener;

/**
 * Created by caowenhua on 2015/11/17.
 */
public interface OnMouthCalenderListener {
    void onDayClick(int year, int mouth, int day);
    void onMouthChange(int year, int mouth);
}

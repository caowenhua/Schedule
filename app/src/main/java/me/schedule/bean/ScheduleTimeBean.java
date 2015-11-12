package me.schedule.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by caowenhua on 2015/11/5.
 */
@DatabaseTable(tableName = "tb_ScheduleTime")
public class ScheduleTimeBean {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "isCycle")
    private boolean isCycle;
    @DatabaseField(columnName = "hour")
    private int hour;
    @DatabaseField(columnName = "minute")
    private int minute;
    @DatabaseField(columnName = "year")
    private int year;
    @DatabaseField(columnName = "mouth")
    private int mouth;
    @DatabaseField(columnName = "day")
    private int day;
    @DatabaseField(columnName = "cycle")
    private String cycle;

    @DatabaseField(foreign=true,foreignAutoRefresh=true)
    private ScheduleBean scheduleBean;

    private boolean[] days;

    public boolean isCycle() {
        return isCycle;
    }

    public void setIsCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMouth() {
        return mouth;
    }

    public void setMouth(int mouth) {
        this.mouth = mouth;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public boolean[] getDays() {
        if(days != null){
            return days;
        }
        days = new boolean[7];
        String[] tmp = cycle.split(",");
        for (int i = 0; i < tmp.length; i++) {
            days[Integer.valueOf(tmp[i])] = true;
        }
        return days;
    }

    public void setDays(boolean[] days) {
        this.days = days;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < days.length; i++) {
            if(days[i]){
                if(builder.length() == 0){
                    builder.append(i);
                }
                else{
                    builder.append("," + i);
                }
            }
        }
        setCycle(builder.toString());
    }

    @Override
    public boolean equals(Object o) {
        ScheduleTimeBean bean;
        try {
            bean = (ScheduleTimeBean) o;
        }
        catch (Exception e){
            return false;
        }
        if(isCycle == bean.isCycle() && cycle.equals(bean.getCycle()) && hour == bean.getHour() && minute == bean.getMinute() &&
                day == bean.getDay() && mouth == bean.getMouth() && year == bean.getYear()){
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ScheduleBean getScheduleBean() {
        return scheduleBean;
    }

    public void setScheduleBean(ScheduleBean scheduleBean) {
        this.scheduleBean = scheduleBean;
    }
}

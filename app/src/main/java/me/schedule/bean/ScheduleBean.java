package me.schedule.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by caowenhua on 2015/11/4.
 */
@DatabaseTable(tableName = "tb_Schedule")
public class ScheduleBean {
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "remark")
    private String remark;
    @DatabaseField(columnName = "detail")
    private String detail;
    @DatabaseField(columnName = "event")
    private int event;
    @DatabaseField(columnName = "isAlarm")
    private boolean isAlarm;
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

    private boolean[] days;

//    @ForeignCollectionField
//    private Collection<ScheduleTimeBean> times;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public boolean isAlarm() {
        return isAlarm;
    }

    public void setIsAlarm(boolean isAlarm) {
        this.isAlarm = isAlarm;
    }

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

    //    public Collection<ScheduleTimeBean> getTimes() {
//        return times;
//    }
//
//    public void setTimes(Collection<ScheduleTimeBean> times) {
//        this.times = times;
//    }
}

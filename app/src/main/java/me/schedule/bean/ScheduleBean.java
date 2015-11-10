package me.schedule.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Collection;

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

    @ForeignCollectionField
    private Collection<ScheduleTimeBean> times;



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

    public Collection<ScheduleTimeBean> getTimes() {
        return times;
    }

    public void setTimes(Collection<ScheduleTimeBean> times) {
        this.times = times;
    }

//    public Collection<ScheduleTimeBean> getTimes() {
//        return times;
//    }
//
//    public void setTimes(Collection<ScheduleTimeBean> times) {
//        this.times = times;
//    }
}

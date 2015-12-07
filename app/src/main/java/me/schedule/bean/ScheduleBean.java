package me.schedule.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by caowenhua on 2015/11/4.
 */
@DatabaseTable(tableName = "tb_Schedule")
public class ScheduleBean implements Serializable{
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

//    @ForeignCollectionField
//    private Collection<ScheduleTimeBean> times;

    @ForeignCollectionField
    private ForeignCollection<ScheduleTimeBean> times;

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

    public ForeignCollection<ScheduleTimeBean> getTimes() {
        return times;
    }

    public void setTimes(ForeignCollection<ScheduleTimeBean> times) {
        this.times = times;
    }


    //    public Collection<ScheduleTimeBean> getTimes() {
//        return times;
//    }
//
//    public void setTimes(Collection<ScheduleTimeBean> times) {
//        this.times = times;
//    }


    @Override
    public boolean equals(Object o) {
        ScheduleBean bean;
        try {
            bean = (ScheduleBean) o;
        }
        catch (Exception e){
            return false;
        }
        if(isAlarm == bean.isAlarm() && event == bean.getEvent() && detail.equals(bean.getDetail()) && remark.equals(bean.getRemark()) &&
                name.equals(bean.getName()) && times.equals(bean.getTimes())){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "id:" + id + ",event:" + event + ",name:" + name + ",isalarm:" + isAlarm +
                ",remark:" + remark + ",detail" + detail;
    }

    public long getRecentTime(){
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int min = calendar.get(Calendar.MINUTE);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        long time = System.currentTimeMillis();
        long minValue = 0;
        for (ScheduleTimeBean bean : times){
            if(bean.isCycle()){
                Date date = new Date();
                date.setHours(bean.getHour());
                date.setYear(bean.getYear());
                date.setMonth(bean.getMouth());
                date.setDate(bean.getDay());
                date.setMinutes(bean.getMinute());
                long t = date.getTime() - time;
                if(minValue == 0 && t > 0){
                    minValue = t;
                }
                else if(t > 0 && t < minValue){
                    minValue = t;
                }
            }
            else{
                long value = 0;
                boolean[] days = bean.getDays();
                for (int j = 0; j < 7; j++) {
                    if(week + j > 6){
                        if(days[week + j - 7]){
                            if(j == 0){
                                if(hour < bean.getHour()){
                                    value += (bean.getHour() - hour) * 60 * 60 * 1000 + (bean.getMinute() - min) * 60 * 1000;
                                    break;
                                }
                                if(hour == bean.getHour()){
                                    if(min < bean.getMinute()){
                                        value += (bean.getMinute() - min) * 60 * 1000;
                                        break;
                                    }
                                }
                            }
                            else{
                                value += j * 24 * 60 * 60 * 1000;
                                break;
                            }
                        }
                    }
                    else{
                        if(days[week + j]){
                            if(j == 0){
                                if(hour < bean.getHour()){
                                    value += (bean.getHour() - hour) * 60 * 60 * 1000 + (bean.getMinute() - min) * 60 * 1000;
                                    break;
                                }
                                if(hour == bean.getHour()){
                                    if(min < bean.getMinute()){
                                        value += (bean.getMinute() - min) * 60 * 1000;
                                        break;
                                    }
                                }
                            }
                            else{
                                value += j * 24 * 60 * 1000;
                                break;
                            }
                        }
                    }
                }
                if(minValue == 0 && value > 0){
                    minValue = value;
                }
                else if(minValue > 0 && value < minValue){
                    minValue = value;
                }
            }
        }
        return minValue;
    }
}

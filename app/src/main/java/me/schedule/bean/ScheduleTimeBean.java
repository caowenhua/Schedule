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
    @DatabaseField(columnName = "hour")
    private int hour;
    @DatabaseField(columnName = "minute")
    private int minute;
    @DatabaseField(columnName = "dutarion")
    private int dutarion;
    @DatabaseField(columnName = "cycle")
    private String cycle;

    private boolean[] day;


}

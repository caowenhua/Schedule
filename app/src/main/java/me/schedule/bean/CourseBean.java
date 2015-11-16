package me.schedule.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by caowenhua on 2015/11/5.
 */
@DatabaseTable(tableName = "tb_course")
public class CourseBean implements Serializable{
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "day")
    private int day;
    @DatabaseField(columnName = "startStamp")
    private int startStamp;
    @DatabaseField(columnName = "durationStamp")
    private int durationStamp;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "classroom")
    private String classroom;
    @DatabaseField(columnName = "teacher")
    private String teacher;
    @DatabaseField(columnName = "remark")
    private String remark;



    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStartStamp() {
        return startStamp;
    }

    public void setStartStamp(int startStamp) {
        this.startStamp = startStamp;
    }

    public int getDurationStamp() {
        return durationStamp;
    }

    public void setDurationStamp(int durationStamp) {
        this.durationStamp = durationStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

package me.schedule.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import me.schedule.bean.ScheduleBean;
import me.schedule.bean.ScheduleTimeBean;

/**
 * Created by caowenhua on 2015/11/10.
 */
public class ScheduleTimeDAO {
    private Dao<ScheduleTimeBean, Integer> dao;
    private DatabaseHelper helper;

    private static ScheduleTimeDAO scheduleDAO;

    public static ScheduleTimeDAO getInstance(Context context){
        if(scheduleDAO == null){
            scheduleDAO = new ScheduleTimeDAO(context);
        }
        return scheduleDAO;
    }

    private ScheduleTimeDAO(Context context) {
        try
        {
            helper = DatabaseHelper.getHelper(context);
            dao = helper.getDao(ScheduleBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public long getLength(){
        try {
            return dao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void add(ScheduleTimeBean bean){
        try {
            dao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(){

    }
}

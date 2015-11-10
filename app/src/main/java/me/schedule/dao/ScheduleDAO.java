package me.schedule.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import me.schedule.bean.ScheduleBean;

/**
 * Created by caowenhua on 2015/11/10.
 */
public class ScheduleDAO {
    private Dao<ScheduleBean, Integer> dao;
    private DatabaseHelper helper;

    private static ScheduleDAO scheduleDAO;

    public static ScheduleDAO getInstance(Context context){
        if(scheduleDAO == null){
            scheduleDAO = new ScheduleDAO(context);
        }
        return scheduleDAO;
    }

    private ScheduleDAO(Context context) {
        try
        {
            helper = DatabaseHelper.getHelper(context);
            dao = helper.getDao(ScheduleBean.class);
        } catch (java.sql.SQLException e) {
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

    public void add(ScheduleBean bean){
        try {
            dao.create(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(){

    }
}

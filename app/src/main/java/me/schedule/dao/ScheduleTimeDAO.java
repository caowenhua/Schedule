package me.schedule.dao;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
            dao = helper.getDao(ScheduleTimeBean.class);
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

    public void delete(ScheduleTimeBean bean){
        try {
            dao.delete(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ScheduleBean> getCurrent(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int mouth = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        Log.e("getCurrentPranms -", "day:" + day + "----mouth:" + mouth + "---year:" + year + "----week:" + week);
        List<ScheduleBean> list = new ArrayList<>();
        try {
            QueryBuilder queryBuilder = dao.queryBuilder();
            List<ScheduleTimeBean> l = queryBuilder.where().eq("isCycle", false).and().eq("day", day).and().eq("mouth", mouth).and().eq("year", year).query();
            for (int i = 0; i < l.size(); i++) {
                if(!list.contains(l.get(i).getScheduleBean())){
                    list.add(l.get(i).getScheduleBean());
                }
            }

            QueryBuilder builder = dao.queryBuilder();
            List<ScheduleTimeBean> l2 = builder.where().eq("isCycle", true).and().like("cycle", week+"").query();
            for (int i = 0; i < l2.size(); i++) {
                if(!list.contains(l2.get(i).getScheduleBean())){
                    list.add(l2.get(i).getScheduleBean());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

package me.schedule.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

//    public List<ScheduleBean> getCurrent(){
//        Calendar calendar = Calendar.getInstance();
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        int mouth = calendar.get(Calendar.MONTH) + 1;
//        int year = calendar.get(Calendar.YEAR);
//        int week = calendar.get(Calendar.DAY_OF_WEEK);
//        Log.e("getCurrentPranms -", "day:" + day + "----mouth:" + mouth + "---year:" + year + "----week:" + week);
//        List<ScheduleBean> list = new ArrayList<>();
//        try {
//            QueryBuilder queryBuilder = dao.queryBuilder();
//            List<ScheduleTimeBean> l = queryBuilder.where().eq("isCycle", false).and().eq("day", day).and().eq("mouth", mouth).and().eq("year", year).query();
//            for (int i = 0; i < l.size(); i++) {
//                if(!list.contains(l.get(i).getScheduleBean())){
//                    list.add(l.get(i).getScheduleBean());
//                }
//            }
//
//            QueryBuilder builder = dao.queryBuilder();
//            List<ScheduleTimeBean> l2 = builder.where().eq("isCycle", true).and().like("cycle", week+"").query();
//            for (int i = 0; i < l2.size(); i++) {
//                if(!list.contains(l2.get(i).getScheduleBean())){
//                    list.add(l2.get(i).getScheduleBean());
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public List<ScheduleBean> getMouth(int mouth){
//        List<ScheduleBean> list = new ArrayList<>();
//        try {
//            QueryBuilder queryBuilder = dao.queryBuilder();
//            List<ScheduleTimeBean> l = queryBuilder.where().eq("isCycle", false).and().eq("mouth", mouth).query();
//            for (int i = 0; i < l.size(); i++) {
//                if(!list.contains(l.get(i).getScheduleBean())){
//                    list.add(l.get(i).getScheduleBean());
//                }
//            }
//
//            QueryBuilder builder = dao.queryBuilder();
//            List<ScheduleTimeBean> l2 = builder.where().eq("isCycle", true).and().query();
//            for (int i = 0; i < l2.size(); i++) {
//                if(!list.contains(l2.get(i).getScheduleBean())){
//                    list.add(l2.get(i).getScheduleBean());
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public List<ScheduleBean> getWeek(int mouth, int firstDay, int lastDay){
//        List<ScheduleBean> list = new ArrayList<>();
//        try {
//            QueryBuilder queryBuilder = dao.queryBuilder();
//            List<ScheduleTimeBean> l = queryBuilder.where().eq("isCycle", false).and().eq("mouth", mouth).and().between("day", firstDay, lastDay).query();
//            for (int i = 0; i < l.size(); i++) {
//                if(!list.contains(l.get(i).getScheduleBean())){
//                    list.add(l.get(i).getScheduleBean());
//                }
//            }
//
//            QueryBuilder builder = dao.queryBuilder();
//            List<ScheduleTimeBean> l2 = builder.where().eq("isCycle", true).and().query();
//            for (int i = 0; i < l2.size(); i++) {
//                if(!list.contains(l2.get(i).getScheduleBean())){
//                    list.add(l2.get(i).getScheduleBean());
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    public List<ScheduleBean> getDay(int year, int mouth, int day){
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(year, mouth, day);
//        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
//        List<ScheduleBean> list = new ArrayList<>();
//        try {
//            QueryBuilder queryBuilder = dao.queryBuilder();
//            List<ScheduleTimeBean> l = queryBuilder.where().eq("isCycle", false).and()
//                    .eq("mouth", mouth).and().eq("year", year).and().eq("day", day).query();
//            for (int i = 0; i < l.size(); i++) {
//                if(!list.contains(l.get(i).getScheduleBean())){
//                    list.add(l.get(i).getScheduleBean());
//                }
//            }
//
//            QueryBuilder builder = dao.queryBuilder();
//            List<ScheduleTimeBean> l2 = builder.where().eq("isCycle", true).and().query();
//            for (int i = 0; i < l2.size(); i++) {
//                if(!list.contains(l2.get(i).getScheduleBean()) && l2.get(i).isInCycle(dayofweek-1)){
//                    list.add(l2.get(i).getScheduleBean());
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }


    public List<ScheduleTimeBean> getAllChild(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}

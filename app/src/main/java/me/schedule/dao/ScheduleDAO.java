package me.schedule.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.schedule.bean.Mouth;
import me.schedule.bean.ScheduleBean;
import me.schedule.bean.ScheduleTimeBean;

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

    public void delete(ScheduleBean bean){
        try {
            dao.delete(bean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ScheduleBean> getAll(){
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<ScheduleBean> getToday(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int mouth = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        List<ScheduleBean> list = new ArrayList<>();
        try {
            List<ScheduleBean> tmp = dao.queryForAll();
            for (int i = 0; i < tmp.size(); i++) {
                for (ScheduleTimeBean bean : tmp.get(i).getTimes()){
                    if(!bean.isCycle()){
                        if(day == bean.getDay() && year == bean.getYear() && mouth == bean.getMouth()){
                            list.add(tmp.get(i));
                            break;
                        }
                    }
                    else{
                        if(bean.getCycle().indexOf(week+"") != -1){
                            list.add(tmp.get(i));
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ScheduleBean> getMouth(int year, int mouth){
        List<ScheduleBean> list = new ArrayList<>();
        try {
            List<ScheduleBean> tmp = dao.queryForAll();
            for (int i = 0; i < tmp.size(); i++) {
                for (ScheduleTimeBean bean : tmp.get(i).getTimes()){
                    if(!bean.isCycle()){
                        if(year == bean.getYear() && mouth == bean.getMouth()){
                            list.add(tmp.get(i));
                            break;
                        }
                    }
                    else{
                        list.add(tmp.get(i));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ScheduleBean> getDay(int year, int mouth, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, mouth, day);
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        List<ScheduleBean> list = new ArrayList<>();
        try {
            List<ScheduleBean> tmp = dao.queryForAll();
            for (int i = 0; i < tmp.size(); i++) {
                for (ScheduleTimeBean bean : tmp.get(i).getTimes()){
                    if(!bean.isCycle()){
                        if(day == bean.getDay() && year == bean.getYear() && mouth == bean.getMouth()){
                            list.add(tmp.get(i));
                            break;
                        }
                    }
                    else{
                        if(bean.getCycle().indexOf(dayofweek+"") != -1){
                            list.add(tmp.get(i));
                            break;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ScheduleBean> getWeek(int year, int mouth, int firstDay, int lastDay){
        List<ScheduleBean> list = new ArrayList<>();
        try {
            List<ScheduleBean> tmp = dao.queryForAll();
            for (int i = 0; i < tmp.size(); i++) {
                for (ScheduleTimeBean bean : tmp.get(i).getTimes()){
                    if(!bean.isCycle()){
                        if(year == bean.getYear() && mouth == bean.getMouth()){
                            if(firstDay <= bean.getDay() && lastDay >= bean.getDay()){
                                list.add(tmp.get(i));
                                break;
                            }
                        }
                    }
                    else{
                        list.add(tmp.get(i));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Mouth> getMouthEvent(int year, int mouth){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, mouth, 1);
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        List<ScheduleBean> list = getMouth(year, mouth);
        List<Mouth> event = new ArrayList<>();
        for (int i = 0; i < getDayByYearMouth(year, mouth); i++) {
            Mouth m = new Mouth();
            m.setDay(i+1);
            boolean isIn = false;
            int max = 0;
            for (int j = 0; j < list.size(); j++) {
                for (ScheduleTimeBean bean : list.get(i).getTimes()){
                    if(!bean.isCycle()){
                        if(i+1 == bean.getDay() && year == bean.getYear() && mouth == bean.getMouth()){
                            isIn = true;
                            if(list.get(j).getEvent() > max){
                                max = list.get(j).getEvent();
                            }
                            break;
                        }
                    }
                    else{
                        if(bean.getCycle().indexOf(dayofweek+"") != -1){
                            isIn = true;
                            if(list.get(j).getEvent() > max){
                                max = list.get(j).getEvent();
                            }
                            break;
                        }
                    }
                }
                if(max == 5){
                    break;
                }
            }
            m.setIsIn(isIn);
            m.setMaxevent(max);
            event.add(m);
        }
        return event;
    }

    public int getDayByYearMouth(int year, int mouth){
        switch (mouth){
            case 1:
                return 31;
            case 2:
                if(year % 4 == 0){
                    return 28;
                }
                else{
                    return 29;
                }
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;
        }
        return 30;
    }
}

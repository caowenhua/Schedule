package me.schedule.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.schedule.R;
import me.schedule.bean.ScheduleBean;
import me.schedule.bean.ScheduleTimeBean;
import me.schedule.dao.ScheduleDAO;
import me.schedule.dao.ScheduleTimeDAO;

/**
 * Created by caowenhua on 2015/12/13.
 */
public class TestDAOActivity extends Activity implements View.OnClickListener {
    private Button add;
    private Button showall;
    private Button ebevt;
    private Button showtoday;
    private Button showday;
    private Button showmouth;
    private Button recent;
    private ListView listView;

    private ScheduleTimeDAO scheduleTimeDAO;
    private ScheduleDAO scheduleDAO;
    Calendar calender;
    ArrayAdapter adapter;

    private List<String> list;

    private void assignViews() {
        add = (Button) findViewById(R.id.add);
        showall = (Button) findViewById(R.id.showall);
        showtoday = (Button) findViewById(R.id.showtoday);
        showday = (Button) findViewById(R.id.showday);
        showmouth = (Button) findViewById(R.id.showmouth);
        recent = (Button) findViewById(R.id.recent);
        ebevt = (Button) findViewById(R.id.ebevt);
        listView = (ListView) findViewById(R.id.list);

        add.setOnClickListener(this);
        showall.setOnClickListener(this);
        showtoday.setOnClickListener(this);
        showday.setOnClickListener(this);
        showmouth.setOnClickListener(this);
        ebevt.setOnClickListener(this);
        recent.setOnClickListener(this);

        list = new ArrayList<>();
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_dao);

        scheduleTimeDAO = ScheduleTimeDAO.getInstance(this);
        scheduleDAO = ScheduleDAO.getInstance(this);
        calender = Calendar.getInstance();

        assignViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                addBean();
                break;
            case R.id.showall:
                List<ScheduleBean> l = scheduleDAO.getAll();
                for (int i = 0; i < l.size(); i++) {
                    StringBuffer s = new StringBuffer();
                    s.append("\n" + l.get(i).getName() + "    ->have times:" + l.get(i).getTimes().size() + "\n");
                    for(ScheduleTimeBean b : l.get(i).getTimes()){
                        s.append(b.toString() + "\n");
                    }
                    list.add(s.toString());
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.showday:
                list.clear();
                List<ScheduleBean> ld = scheduleDAO.getDay(2015, 12, 18);
                for (int i = 0; i < ld.size(); i++) {
                    StringBuffer s = new StringBuffer();
                    s.append("\n" + ld.get(i).getName() + "    ->have times:" + ld.get(i).getTimes().size() + "\n");
                    for(ScheduleTimeBean b : ld.get(i).getTimes()){
                        s.append(b.toString() + "\n");
                    }
                    list.add(s.toString());
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.showmouth:
                list.clear();
                List<ScheduleBean> lm = scheduleDAO.getMouth(2015, 12);
                for (int i = 0; i < lm.size(); i++) {
                    StringBuffer s = new StringBuffer();
                    s.append("\n" + lm.get(i).getName() + "    ->have times:" + lm.get(i).getTimes().size() + "\n");
                    for(ScheduleTimeBean b : lm.get(i).getTimes()){
                        s.append(b.toString() + "\n");
                    }
                    list.add(s.toString());
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.showtoday:
                list.clear();
                List<ScheduleBean> lt = scheduleDAO.getToday();
                for (int i = 0; i < lt.size(); i++) {
                    StringBuffer s = new StringBuffer();
                    s.append("\n" + lt.get(i).getName() + "    ->have times:" + lt.get(i).getTimes().size() + "\n");
                    for(ScheduleTimeBean b : lt.get(i).getTimes()){
                        s.append(b.toString() + "\n");
                    }
                    list.add(s.toString());
                }
                adapter.notifyDataSetChanged();
                break;
            case R.id.ebevt:
                scheduleDAO.getMouthEvent(2015, 12);
                break;
            case R.id.recent:
                ScheduleBean sb = scheduleDAO.getRecent();
                StringBuffer s = new StringBuffer();
                s.append("\n" + sb.getName() + "    ->have times:" + sb.getTimes().size() + "\n");
                for(ScheduleTimeBean b : sb.getTimes()){
                    s.append(b.toString() + "\n");
                }
                Log.e("recent", sb.toString());
                Log.e("recent", sb.getRecentTime() + "**");
                break;
        }
    }

    private void addBean() {
        //TODO 第一个周期-------20:10
        ScheduleBean bean = new ScheduleBean();
        bean.setEvent(4);
        bean.setIsAlarm(true);
        bean.setName("First 食饭,周期性");
        bean.setRemark("1st remark");
        bean.setDetail("1st detail");
        scheduleDAO.add(bean);

        ScheduleTimeBean ssbean1 = new ScheduleTimeBean();
        ssbean1.setIsCycle(true);
        ssbean1.setHour(11);
        ssbean1.setMinute(30);
        ssbean1.setDays(new boolean[]{true, true, false, false, true, false, true});
        ssbean1.setScheduleBean(bean);
        scheduleTimeDAO.add(ssbean1);

        ScheduleTimeBean ssbean2 = new ScheduleTimeBean();
        ssbean2.setIsCycle(true);
        ssbean2.setHour(20);
        ssbean2.setMinute(10);
        ssbean2.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean2.setScheduleBean(bean);
        scheduleTimeDAO.add(ssbean2);

        ScheduleTimeBean ssbean3 = new ScheduleTimeBean();
        ssbean3.setIsCycle(true);
        ssbean3.setHour(8);
        ssbean3.setMinute(00);
        ssbean3.setDays(new boolean[]{true, true, true, true, true, false, true});
        ssbean3.setScheduleBean(bean);
        scheduleTimeDAO.add(ssbean3);

        //TODO 第一个单独-----15...11:00
        ScheduleBean bean2 = new ScheduleBean();
        bean2.setEvent(3);
        bean2.setIsAlarm(true);
        bean2.setName("Second Lol,fei周期性");
        bean2.setRemark("2nd remark");
        bean2.setDetail("2nd detail");
        scheduleDAO.add(bean2);

        ScheduleTimeBean ssbean21 = new ScheduleTimeBean();
        ssbean21.setIsCycle(false);
        ssbean21.setYear(2015);
        ssbean21.setMouth(12);
        ssbean21.setDay(15);
        ssbean21.setHour(11);
        ssbean21.setMinute(00);
        ssbean21.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean21.setScheduleBean(bean2);
        scheduleTimeDAO.add(ssbean21);

        //TODO 第二个单独----17....15:00
        ScheduleBean bean3 = new ScheduleBean();
        bean3.setEvent(3);
        bean3.setIsAlarm(true);
        bean3.setName("S3 Lolii,fei周期性");
        bean3.setRemark("3nd remark");
        bean3.setDetail("3nd detail");
        scheduleDAO.add(bean3);

        ScheduleTimeBean ssbean31 = new ScheduleTimeBean();
        ssbean31.setIsCycle(false);
        ssbean31.setYear(2015);
        ssbean31.setMouth(12);
        ssbean31.setDay(17);
        ssbean31.setHour(15);
        ssbean31.setMinute(00);
        ssbean31.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean31.setScheduleBean(bean3);
        scheduleTimeDAO.add(ssbean31);

        ScheduleTimeBean ssbean32 = new ScheduleTimeBean();
        ssbean32.setIsCycle(false);
        ssbean32.setYear(2015);
        ssbean32.setMouth(12);
        ssbean32.setDay(20);
        ssbean32.setHour(7);
        ssbean32.setMinute(59);
        ssbean32.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean32.setScheduleBean(bean3);
        scheduleTimeDAO.add(ssbean32);

        //TODO 第三个单独----19:00...21:29
        ScheduleBean bean4 = new ScheduleBean();
        bean4.setEvent(5);
        bean4.setIsAlarm(true);
        bean4.setName("Ss4 ssslii,fei周期性");
        bean4.setRemark("4d remark");
        bean4.setDetail("4d detail");
        scheduleDAO.add(bean4);

        ScheduleTimeBean ssbean41 = new ScheduleTimeBean();
        ssbean41.setIsCycle(false);
        ssbean41.setYear(2015);
        ssbean41.setMouth(12);
        ssbean41.setDay(13);
        ssbean41.setHour(19);
        ssbean41.setMinute(00);
        ssbean41.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean41.setScheduleBean(bean4);
        scheduleTimeDAO.add(ssbean41);

        ScheduleTimeBean ssbean42 = new ScheduleTimeBean();
        ssbean42.setIsCycle(false);
        ssbean42.setYear(2015);
        ssbean42.setMouth(12);
        ssbean42.setDay(13);
        ssbean42.setHour(21);
        ssbean42.setMinute(29);
        ssbean42.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean42.setScheduleBean(bean4);
        scheduleTimeDAO.add(ssbean42);

        //TODO 第四个单独
        ScheduleBean bean5 = new ScheduleBean();
        bean5.setEvent(3);
        bean5.setIsAlarm(true);
        bean5.setName("5555555ol,fei周期性");
        bean5.setRemark("2nd remark");
        bean5.setDetail("2nd detail");
        scheduleDAO.add(bean5);

        ScheduleTimeBean ssbean51 = new ScheduleTimeBean();
        ssbean51.setIsCycle(false);
        ssbean51.setYear(2016);
        ssbean51.setMouth(1);
        ssbean51.setDay(12);
        ssbean51.setHour(11);
        ssbean51.setMinute(00);
        ssbean51.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean51.setScheduleBean(bean5);
        scheduleTimeDAO.add(ssbean51);

        ScheduleTimeBean ssbean52 = new ScheduleTimeBean();
        ssbean52.setIsCycle(false);
        ssbean52.setYear(2016);
        ssbean52.setMouth(2);
        ssbean52.setDay(19);
        ssbean52.setHour(10);
        ssbean52.setMinute(50);
        ssbean52.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean52.setScheduleBean(bean5);
        scheduleTimeDAO.add(ssbean52);

        ScheduleTimeBean ssbean53 = new ScheduleTimeBean();
        ssbean53.setIsCycle(false);
        ssbean53.setYear(2016);
        ssbean53.setMouth(8);
        ssbean53.setDay(1);
        ssbean53.setHour(1);
        ssbean53.setMinute(0);
        ssbean53.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean53.setScheduleBean(bean5);
        scheduleTimeDAO.add(ssbean53);

        ScheduleTimeBean ssbean54 = new ScheduleTimeBean();
        ssbean54.setIsCycle(false);
        ssbean54.setYear(2016);
        ssbean54.setMouth(12);
        ssbean54.setDay(20);
        ssbean54.setHour(6);
        ssbean54.setMinute(10);
        ssbean54.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean54.setScheduleBean(bean5);
        scheduleTimeDAO.add(ssbean54);

        //TODO 第一个混合----19:30
        ScheduleBean bean6 = new ScheduleBean();
        bean6.setEvent(4);
        bean6.setIsAlarm(true);
        bean6.setName("混合！！！！！");
        bean6.setRemark("1st remark");
        bean6.setDetail("1st detail");
        scheduleDAO.add(bean6);

        ScheduleTimeBean ssbean61 = new ScheduleTimeBean();
        ssbean61.setIsCycle(true);
        ssbean61.setHour(19);
        ssbean61.setMinute(30);
        ssbean61.setDays(new boolean[]{true, true, false, false, true, false, true});
        ssbean61.setScheduleBean(bean6);
        scheduleTimeDAO.add(ssbean61);

        ScheduleTimeBean ssbean62 = new ScheduleTimeBean();
        ssbean62.setIsCycle(true);
        ssbean62.setHour(18);
        ssbean62.setMinute(15);
        ssbean62.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean62.setScheduleBean(bean6);
        scheduleTimeDAO.add(ssbean62);

        ScheduleTimeBean ssbean63 = new ScheduleTimeBean();
        ssbean63.setIsCycle(false);
        ssbean63.setYear(2015);
        ssbean63.setMouth(12);
        ssbean63.setDay(20);
        ssbean63.setHour(20);
        ssbean63.setMinute(20);
        ssbean63.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean63.setScheduleBean(bean6);
        scheduleTimeDAO.add(ssbean63);

        //TODO 六点半
        ScheduleBean bean7 = new ScheduleBean();
        bean7.setEvent(5);
        bean7.setIsAlarm(true);
        bean7.setName("六点半");
        bean7.setRemark("4d remark");
        bean7.setDetail("4d detail");
        scheduleDAO.add(bean7);

        ScheduleTimeBean ssbean71 = new ScheduleTimeBean();
        ssbean71.setIsCycle(false);
        ssbean71.setYear(2015);
        ssbean71.setMouth(12);
        ssbean71.setDay(13);
        ssbean71.setHour(18);
        ssbean71.setMinute(30);
        ssbean71.setDays(new boolean[]{false, true, true, true, true, false, true});
        ssbean71.setScheduleBean(bean7);
        scheduleTimeDAO.add(ssbean71);
    }
}

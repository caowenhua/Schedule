package me.schedule.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import me.schedule.R;
import me.schedule.bean.ScheduleBean;
import me.schedule.bean.ScheduleTimeBean;
import me.schedule.dao.ScheduleDAO;
import me.schedule.dao.ScheduleTimeDAO;

/**
 * Created by caowenhua on 2015/11/5.
 */
public class TestActivity extends Activity implements View.OnClickListener{

    private Button add;
    private Button show;
    private TextView tv;
    ScheduleDAO dao;
    ScheduleTimeDAO sdao;
    Calendar calender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        assignViews();
    }


    private void assignViews() {
        add = (Button) findViewById(R.id.add);
        show = (Button) findViewById(R.id.show);
        tv = (TextView) findViewById(R.id.tv);

        add.setOnClickListener(this);
        show.setOnClickListener(this);

        dao = ScheduleDAO.getInstance(this);
        sdao = ScheduleTimeDAO.getInstance(this);
        calender = Calendar.getInstance();
    }

    @Override
    public void onClick(View v) {
        if(v == add){
//            ScheduleBean bean = new ScheduleBean();
//            bean.setName("testt-->" + calender.getTime().toLocaleString());
//            bean.setEvent(1);
//
//            ScheduleTimeBean ssbean = new ScheduleTimeBean();
//            ssbean.setIsCycle(true);
//            ssbean.setHour(calender.get(Calendar.HOUR));
//            ssbean.setMinute(calender.get(Calendar.MINUTE));
//            ssbean.setDays(new boolean[]{true, true, false, false, true, true, true});
//            ssbean.setScheduleBean(bean);
//
//            ScheduleTimeBean ssbean2 = new ScheduleTimeBean();
//            ssbean2.setIsCycle(true);
//            ssbean2.setHour(calender.get(Calendar.HOUR) + 2);
//            ssbean2.setMinute(calender.get(Calendar.MINUTE));
//            ssbean2.setDays(new boolean[]{false, true, true, true, true, false, true});
//            ssbean2.setScheduleBean(bean);

            ScheduleBean bean = new ScheduleBean();
            bean.setName("testt-->" + calender.getTime().toLocaleString());
            bean.setEvent(5);

            ScheduleTimeBean ssbean = new ScheduleTimeBean();
            ssbean.setIsCycle(false);
            ssbean.setYear(2015);
            ssbean.setMouth(11);
            ssbean.setDay(30);
            ssbean.setHour(calender.get(Calendar.HOUR));
            ssbean.setMinute(calender.get(Calendar.MINUTE));
            ssbean.setDays(new boolean[]{true, true, false, false, false, true, true});
            ssbean.setScheduleBean(bean);

            dao.add(bean);

            sdao.add(ssbean);
//            sdao.add(ssbean2);

        }
        else if(v == show){
            StringBuffer s = new StringBuffer();
            List<ScheduleBean> l = dao.getAll();
            s.append("--all--");
            for (int i = 0; i < l.size(); i++) {
                s.append("\n\n\n\n" + l.get(i).getName() + "\n");
                for(ScheduleTimeBean b : l.get(i).getTimes()){
                    s.append(b.toString() + "\n");
                }
            }

            s.append("\n\n--ai--\n\n");
            l = dao.getMouth(2015, 11);
            for (int i = 0; i < l.size(); i++) {
                s.append("\n\n\n\n" + l.get(i).getName() + "\n");
                for(ScheduleTimeBean b : l.get(i).getTimes()){
                    s.append(b.toString() + "\n");
                }
            }

//            List<ScheduleTimeBean> ll = sdao.getAllChild();
//            for (int i = 0; i < ll.size(); i++) {
//                s.append("\n\n" + ll.get(i).toString() + "\n" + ll.get(i).getScheduleBean().toString() + "\n");
//            }

            Log.e("data", s.toString() + "--");
            tv.setText(s.toString());
        }
    }
}

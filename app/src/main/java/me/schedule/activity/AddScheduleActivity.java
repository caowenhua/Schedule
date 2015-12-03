package me.schedule.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import me.schedule.R;
import me.schedule.bean.ScheduleBean;
import me.schedule.bean.ScheduleTimeBean;
import me.schedule.dao.ScheduleDAO;
import me.schedule.listener.OnMatterListener;
import me.schedule.listener.OnTimeChooseListener;
import me.schedule.listener.OnTimeClickListener;
import me.schedule.listener.OnTimeCreateListener;
import me.schedule.widget.MatterEventView;
import me.schedule.widget.TimeManageView;
import me.schedule.widget.dialog.ChooseTimeDialog;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class AddScheduleActivity extends Activity implements View.OnClickListener,OnTimeClickListener,
        OnTimeCreateListener, OnMatterListener{

    private ImageView imgBack;
    private ImageView imgTick;
    private EditText edtName;
    private ImageView imgAlarm;
    private TimeManageView lltTime;
    private MatterEventView rltMatter;
    private EditText edtRemark;
    private EditText edtDetail;

    private boolean isAlarm;
    private int event;
    private ScheduleBean scheduleBean;
//    private List<ScheduleTimeBean> scheduleTimeBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        assignViews();

        if(getIntent().getSerializableExtra("scheduleBean") != null){
            scheduleBean = (ScheduleBean) getIntent().getSerializableExtra("scheduleBean");
            isAlarm = scheduleBean.isAlarm();
            lltTime.setScheduleTimeList(scheduleBean.getTimes());
            rltMatter.setMatter(scheduleBean.getEvent());
        }
        else{
            isAlarm = true;
            scheduleBean = new ScheduleBean();
            scheduleBean.setIsAlarm(isAlarm);
        }
    }

    private void assignViews() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgTick = (ImageView) findViewById(R.id.img_tick);
        edtName = (EditText) findViewById(R.id.edt_name);
        imgAlarm = (ImageView) findViewById(R.id.img_alarm);
        lltTime = (TimeManageView) findViewById(R.id.llt_time);
        rltMatter = (MatterEventView) findViewById(R.id.rlt_matter);
        edtRemark = (EditText) findViewById(R.id.edt_remark);
        edtDetail = (EditText) findViewById(R.id.edt_detail);

        imgBack.setOnClickListener(this);
        imgTick.setOnClickListener(this);
        rltMatter.setOnMatterListener(this);
        lltTime.setOnTimeCreateListener(this);
        imgAlarm.setOnClickListener(this);
    }

    private void refreshSwitchButton(){
        if(isAlarm){
            imgAlarm.setImageResource(R.drawable.button_open);
        }
        else{
            imgAlarm.setImageResource(R.drawable.button_off);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_tick:
                if(edtName.getText().length() == 0){
                    Toast.makeText(this, "名称不能为空", Toast.LENGTH_SHORT).show();
                }
                else if(lltTime.getScheduleTimeList().size() == 0){
                    Toast.makeText(this, "请先设定时间", Toast.LENGTH_SHORT).show();
                }
                else {
                    List<ScheduleTimeBean> scheduleTimeBeanList = lltTime.getScheduleTimeList();
                    for (int i = 0; i < scheduleTimeBeanList.size(); i++) {
                        scheduleTimeBeanList.get(i).setScheduleBean(scheduleBean);
                    }
                    scheduleBean.setEvent(event);
//                    scheduleBean.setTimes(lltTime.getScheduleTimeList());
                    scheduleBean.setDetail(edtDetail.getText().length() == 0 ? "" : edtDetail.getText().toString());
                    scheduleBean.setName(edtName.getText().toString());
                    scheduleBean.setRemark(edtRemark.getText().length() == 0 ? "" : edtRemark.getText().toString());
                    ScheduleDAO dao = ScheduleDAO.getInstance(this);
                    dao.add(scheduleBean);
                    Toast.makeText(this, "添加时间成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.img_alarm:
                isAlarm = !isAlarm;
                refreshSwitchButton();
                break;
        }
    }

    @Override
    public void onTimeClick(int id, ScheduleTimeBean bean) {
        ChooseTimeDialog dialog = new ChooseTimeDialog(this, lltTime.getItemById(id).getScheduleTimeBean());
    }

    @Override
    public void onDelClick(int id, ScheduleTimeBean bean) {
        lltTime.delItem(id);
    }

    @Override
    public void onDayClick(int id, ScheduleTimeBean bean) {

    }

    @Override
    public void onTimeCreate(final int id, ScheduleTimeBean bean) {
        ChooseTimeDialog dialog = new ChooseTimeDialog(this, bean);
        dialog.setOnTimeChooseListener(new OnTimeChooseListener() {
            @Override
            public void onChoose(String cycle, int hour, int min) {
                if(lltTime.getItemById(id) != null)
                    lltTime.getItemById(id).setData(cycle, hour + ":" + min);
            }
        });
    }

    @Override
    public void onMatterChange(int event) {
        this.event = event;
    }
}

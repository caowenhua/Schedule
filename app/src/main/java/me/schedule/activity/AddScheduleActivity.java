package me.schedule.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.schedule.R;
import me.schedule.listener.OnTimeClickListener;
import me.schedule.listener.OnTimeCreateListener;
import me.schedule.widget.MatterEventView;
import me.schedule.widget.TimeManageView;
import me.schedule.widget.dialog.ChooseSingleTimeDialog;
import me.schedule.widget.dialog.ChooseTimeDialog;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class AddScheduleActivity extends Activity implements View.OnClickListener,OnTimeClickListener,
        OnTimeCreateListener{

    private RelativeLayout rltTop;
    private ImageView imgBack;
    private ImageView imgTick;
    private TextView tvName;
    private EditText edtName;
    private TextView tvAlarm;
    private ImageView imgAlarm;
    private TimeManageView lltTime;
    private MatterEventView rltMatter;
    private TextView tvRemark;
    private EditText edtRemark;
    private TextView tvDetail;
    private EditText edtDetail;

    private boolean isAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
        assignViews();

        isAlarm = true;
    }


    private void assignViews() {
        rltTop = (RelativeLayout) findViewById(R.id.rlt_top);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgTick = (ImageView) findViewById(R.id.img_tick);
        tvName = (TextView) findViewById(R.id.tv_name);
        edtName = (EditText) findViewById(R.id.edt_name);
        tvAlarm = (TextView) findViewById(R.id.tv_alarm);
        imgAlarm = (ImageView) findViewById(R.id.img_alarm);
        lltTime = (TimeManageView) findViewById(R.id.llt_time);
        rltMatter = (MatterEventView) findViewById(R.id.rlt_matter);
        tvRemark = (TextView) findViewById(R.id.tv_remark);
        edtRemark = (EditText) findViewById(R.id.edt_remark);
        tvDetail = (TextView) findViewById(R.id.tv_detail);
        edtDetail = (EditText) findViewById(R.id.edt_detail);

        imgBack.setOnClickListener(this);
        imgTick.setOnClickListener(this);
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
//                finish();
                ChooseSingleTimeDialog chooseSingleTimeDialog = new ChooseSingleTimeDialog(this);
                break;
            case R.id.img_tick:
                ChooseTimeDialog dialog = new ChooseTimeDialog(this);
                break;
            case R.id.img_alarm:
                isAlarm = !isAlarm;
                refreshSwitchButton();
                break;
        }
    }

    @Override
    public void onTimeClick(int id) {

    }

    @Override
    public void onDelClick(int id) {
        lltTime.delItem(id);
    }

    @Override
    public void onDayClick(int id) {

    }

    @Override
    public void onTimeCreate() {

    }
}

package me.schedule.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import me.schedule.R;
import me.schedule.base.BaseDialog;
import me.schedule.bean.ScheduleTimeBean;
import me.schedule.listener.OnTimeChooseListener;
import me.schedule.util.ScreenUtils;
import me.schedule.widget.wheel.DayWheelAdapter;
import me.schedule.widget.wheel.IntegerWheelAdapter;
import me.schedule.widget.wheel.OnWheelChangedListener;
import me.schedule.widget.wheel.WheelView;

/**
 * Created by caowenhua on 2015/11/9.
 */
public class ChooseTimeDialog extends BaseDialog implements View.OnClickListener{

    private ImageView img_x;
    private WheelView wheel_hour;
    private WheelView wheel_year;
    private WheelView wheel_min;
    private WheelView wheel_mouth;
    private WheelView wheel_day;
    private Button btn_commit;

    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;

    private IntegerWheelAdapter hourAdapter;
    private IntegerWheelAdapter minAdapter;
    private IntegerWheelAdapter yearAdapter;
    private DayWheelAdapter dayAdapter;
    private IntegerWheelAdapter mouthAdapter;

    private ImageView img_cycle;
    private ImageView img_single;
    private View view_mask;
    private Button[] buttons;
    private int[] days;
    private boolean[] cycle;

    private ScheduleTimeBean scheduleTimeBean;

    private OnTimeChooseListener onTimeChooseListener;

    public ChooseTimeDialog(Context context, ScheduleTimeBean bean) {
        super(context, R.style.DialogUpDown);
        scheduleTimeBean = bean;
        init();
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_choose_time;
    }

    @Override
    protected void findView() {
        wheel_hour = findViewByID(R.id.wheel_hour);
        wheel_min = findViewByID(R.id.wheel_min);
        wheel_day = findViewByID(R.id.wheel_day);
        wheel_year = findViewByID(R.id.wheel_year);
        wheel_mouth = findViewByID(R.id.wheel_mouth);
        img_x = findViewByID(R.id.img_x);
        btn_commit = findViewByID(R.id.btn_commit);
        btn_1 = findViewByID(R.id.btn_1);
        btn_2 = findViewByID(R.id.btn_2);
        btn_3 = findViewByID(R.id.btn_3);
        btn_4 = findViewByID(R.id.btn_4);
        btn_5 = findViewByID(R.id.btn_5);
        btn_6 = findViewByID(R.id.btn_6);
        btn_7 = findViewByID(R.id.btn_7);
        img_cycle = findViewByID(R.id.img_cycle);
        img_single = findViewByID(R.id.img_single);

//        check_cycle = findViewByID(R.id.check_cycle);
//        check_single = findViewByID(R.id.check_single);

        view_mask = findViewByID(R.id.view_mask);

        img_x.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        img_cycle.setOnClickListener(this);
        img_single.setOnClickListener(this);

//        check_cycle.setOnCheckListener(this);
//        check_single.setOnCheckListener(this);
    }

    @Override
    protected void initData() {

    }

    private void init() {
        cycle = scheduleTimeBean.getDays();
        buttons = new Button[]{btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7};
        days = new int[]{};
//        check_single.setIsChecked(true);

        hourAdapter = new IntegerWheelAdapter(0, 23);
        wheel_hour.setAdapter(hourAdapter);
        wheel_hour.setLabel("时");
        wheel_hour.setCyclic(true);
        wheel_hour.setVisibleItems(5);
        wheel_hour.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_hour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                scheduleTimeBean.setHour(hourAdapter.getIndex(newValue));
                callBackChoose();
            }
        });

        minAdapter = new IntegerWheelAdapter(0, 59);
        wheel_min.setAdapter(minAdapter);
        wheel_min.setLabel("分");
        wheel_min.setCyclic(true);
        wheel_min.setVisibleItems(5);
        wheel_min.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_min.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                scheduleTimeBean.setMinute(minAdapter.getIndex(newValue));
                callBackChoose();
            }
        });

        yearAdapter = new IntegerWheelAdapter(2015, 2099);
        wheel_year.setAdapter(yearAdapter);
        wheel_year.setLabel("年");
        wheel_year.setCyclic(true);
        wheel_year.setVisibleItems(5);
        wheel_year.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_year.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                scheduleTimeBean.setYear(yearAdapter.getIndex(newValue));
                dayAdapter.refreshData(scheduleTimeBean.getYear(), scheduleTimeBean.getMouth());
                wheel_day.invalidateLayouts();
                wheel_day.invalidate();
                callBackChoose();
            }
        });

        mouthAdapter = new IntegerWheelAdapter(1, 12);
        wheel_mouth.setAdapter(mouthAdapter);
        wheel_mouth.setLabel("月");
        wheel_mouth.setCyclic(true);
        wheel_mouth.setVisibleItems(5);
        wheel_mouth.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_mouth.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                scheduleTimeBean.setMouth(mouthAdapter.getIndex(newValue));
                dayAdapter.refreshData(scheduleTimeBean.getYear(), scheduleTimeBean.getMouth());
                wheel_day.invalidateLayouts();
                wheel_day.invalidate();
                callBackChoose();
            }
        });

        dayAdapter = new DayWheelAdapter(scheduleTimeBean.getYear(), scheduleTimeBean.getMouth());
        wheel_day.setAdapter(dayAdapter);
        wheel_day.setLabel("日");
        wheel_day.setCyclic(true);
        wheel_day.setVisibleItems(5);
        wheel_day.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_day.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                scheduleTimeBean.setDay(dayAdapter.getIndex(newValue));
                callBackChoose();
            }
        });

        refreshStatus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_x:
                dismiss();
                break;
            case R.id.btn_commit:
                dismiss();
                break;
            case R.id.btn_1:
                refreshButton(1);
                break;
            case R.id.btn_2:
                refreshButton(2);
                break;
            case R.id.btn_3:
                refreshButton(3);
                break;
            case R.id.btn_4:
                refreshButton(4);
                break;
            case R.id.btn_5:
                refreshButton(5);
                break;
            case R.id.btn_6:
                refreshButton(6);
                break;
            case R.id.btn_7:
                refreshButton(0);
                break;
            case R.id.img_cycle:
                scheduleTimeBean.setIsCycle(!scheduleTimeBean.isCycle());
                refreshStatus();
                break;
            case R.id.img_single:
                scheduleTimeBean.setIsCycle(!scheduleTimeBean.isCycle());
                refreshStatus();
                break;
        }
    }

//    @Override
//    public void onCheck(View v, boolean isCheck) {
//        if(check_cycle == v){
//            if(isCheck && !scheduleTimeBean.isCycle()){
//                scheduleTimeBean.setIsCycle(true);
//                refreshStatus();
//                check_single.setIsChecked(false);
//            }
//        }
//        else{
//            if(isCheck && scheduleTimeBean.isCycle()){
//                scheduleTimeBean.setIsCycle(false);
//                refreshStatus();
//                check_cycle.setIsChecked(false);
//            }
//        }
//    }

    private void refreshStatus(){
        if(!scheduleTimeBean.isCycle()){
            view_mask.setVisibility(View.VISIBLE);
            wheel_year.setAvailable(true);
            wheel_mouth.setAvailable(true);
            wheel_day.setAvailable(true);
            btn_1.setOnClickListener(null);
            btn_2.setOnClickListener(null);
            btn_3.setOnClickListener(null);
            btn_4.setOnClickListener(null);
            btn_5.setOnClickListener(null);
            btn_6.setOnClickListener(null);
            btn_7.setOnClickListener(null);
            img_single.setImageResource(R.drawable.ic_check);
            img_cycle.setImageResource(R.drawable.ic_uncheck);
        }
        else{
            view_mask.setVisibility(View.GONE);
            wheel_year.setAvailable(false);
            wheel_mouth.setAvailable(false);
            wheel_day.setAvailable(false);
            btn_1.setOnClickListener(this);
            btn_2.setOnClickListener(this);
            btn_3.setOnClickListener(this);
            btn_4.setOnClickListener(this);
            btn_5.setOnClickListener(this);
            btn_6.setOnClickListener(this);
            btn_7.setOnClickListener(this);
            img_single.setImageResource(R.drawable.ic_uncheck);
            img_cycle.setImageResource(R.drawable.ic_check);
        }
    }

    private void refreshButton(int position){
        cycle[position] = !cycle[position];
        for (int i = 0; i < cycle.length; i++) {
            if(cycle[i]){
                buttons[i].setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.bg_main_circle));
            }
            else{
                buttons[i].setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.bg_a1_circle));
            }
        }
        scheduleTimeBean.setDays(cycle);
    }

    public void setOnTimeChooseListener(OnTimeChooseListener onTimeChooseListener) {
        this.onTimeChooseListener = onTimeChooseListener;
    }

    private void callBackChoose(){
        if(onTimeChooseListener != null){
            if(scheduleTimeBean.isCycle()){
                onTimeChooseListener.onChoose(getCycleString(), scheduleTimeBean.getHour(), scheduleTimeBean.getMinute());
            }
            else{
                onTimeChooseListener.onChoose(scheduleTimeBean.getYear() + "." + scheduleTimeBean.getMouth() + "." + scheduleTimeBean.getDay()
                        , scheduleTimeBean.getHour(), scheduleTimeBean.getMinute());
            }
        }
    }


    private String getCycleString(){
        if(cycle[0] && cycle[1] && cycle[2] && cycle[3] && cycle[4] && cycle[5] && cycle[6]){
            return "每天";
        }
        else if(cycle[5] && cycle[1] && cycle[2] && cycle[3] && cycle[4] && !cycle[0] && !cycle[6]){
            return "工作日";
        }
        else if(!cycle[5] && !cycle[1] && !cycle[2] && !cycle[3] && !cycle[4] && cycle[0] && cycle[6]){
            return "周末";
        }
        else {
            String cycleString = "";
            boolean isFirst = true;
            for (int i = 0; i < cycle.length; i++) {
                if(cycle[i]){
                    if(isFirst){
                        cycleString = "每周" + getDayByIndex(i);
                    }
                    else{
                        cycleString = "," + getDayByIndex(i);
                    }
                }
            }
            return cycleString;
        }
    }

    private String getDayByIndex(int index){
        switch (index){
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 0:
                return "日";
        }
        return "";
    }
}

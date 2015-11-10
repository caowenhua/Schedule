package me.schedule.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import me.schedule.R;
import me.schedule.base.BaseDialog;
import me.schedule.bean.ScheduleBean;
import me.schedule.listener.OnTimeChooseListener;
import me.schedule.util.ScreenUtils;
import me.schedule.widget.CheckBox;
import me.schedule.widget.wheel.DayWheelAdapter;
import me.schedule.widget.wheel.IntegerWheelAdapter;
import me.schedule.widget.wheel.OnWheelChangedListener;
import me.schedule.widget.wheel.WheelView;

/**
 * Created by caowenhua on 2015/11/9.
 */
public class ChooseTimeDialog extends BaseDialog implements View.OnClickListener, CheckBox.OnCheckListener {

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

    private CheckBox check_cycle;
    private CheckBox check_single;
    private View view_mask;
    private Button[] buttons;
    private int[] days;
    private boolean[] cycle;
    private boolean isSingle;
    private int year;
    private int mouth;
    private int day;
    private int hour;
    private int min;

    private OnTimeChooseListener onTimeChooseListener;

    public ChooseTimeDialog(Context context, ScheduleBean bean) {
        super(context, R.style.DialogUpDown);

    }

    public ChooseTimeDialog(Context context) {
        super(context, R.style.DialogUpDown);
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
        check_cycle = findViewByID(R.id.check_cycle);
        check_single = findViewByID(R.id.check_single);

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

        check_cycle.setOnCheckListener(this);
        check_single.setOnCheckListener(this);
    }

    @Override
    protected void initData() {
        cycle = new boolean[]{true, true, true, true, true, false, false};
        buttons = new Button[]{btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7};
        days = new int[]{};
        isSingle = true;
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
                hour = newValue;
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
                min = newValue;
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
                year = newValue;
                dayAdapter.refreshData(year, mouth);
                wheel_day.invalidate();
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
                mouth = newValue;
                dayAdapter.refreshData(year, mouth);
                wheel_day.invalidate();
            }
        });

        year = 2015;
        mouth = 11;
        dayAdapter = new DayWheelAdapter(year, mouth);
        wheel_day.setAdapter(dayAdapter);
        wheel_day.setLabel("日");
        wheel_day.setCyclic(true);
        wheel_day.setVisibleItems(5);
        wheel_day.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_day.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

            }
        });

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
                refreshButton(0);
                break;
            case R.id.btn_2:
                refreshButton(1);
                break;
            case R.id.btn_3:
                refreshButton(2);
                break;
            case R.id.btn_4:
                refreshButton(3);
                break;
            case R.id.btn_5:
                refreshButton(4);
                break;
            case R.id.btn_6:
                refreshButton(5);
                break;
            case R.id.btn_7:
                refreshButton(6);
                break;
        }
    }

    @Override
    public void onCheck(View v, boolean isCheck) {
        if(check_cycle == v){
            if(isCheck && isSingle){
                isSingle = false;
                refreshStatus();
                check_single.setIsChecked(false);
            }
        }
        else{
            if(isCheck && !isSingle){
                isSingle = true;
                refreshStatus();
                check_cycle.setIsChecked(false);
            }
        }
    }

    private void refreshStatus(){
        if(isSingle){
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
    }

    public void setOnTimeChooseListener(OnTimeChooseListener onTimeChooseListener) {
        this.onTimeChooseListener = onTimeChooseListener;
    }
}

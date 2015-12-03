package me.schedule.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Calendar;

import me.schedule.R;
import me.schedule.base.BaseDialog;
import me.schedule.listener.OnDateChangeListener;
import me.schedule.util.ScreenUtils;
import me.schedule.widget.wheel.DayWheelAdapter;
import me.schedule.widget.wheel.IntegerWheelAdapter;
import me.schedule.widget.wheel.OnWheelChangedListener;
import me.schedule.widget.wheel.WheelView;

/**
 * Created by caowenhua on 2015/12/3.
 */
public class ChooseDayDialog extends BaseDialog implements View.OnClickListener {

    private ImageView img_x;
    private WheelView wheel_year;
    private WheelView wheel_mouth;
    private WheelView wheel_day;
    private Button btn_commit;

    private IntegerWheelAdapter yearAdapter;
    private DayWheelAdapter dayAdapter;
    private IntegerWheelAdapter mouthAdapter;

    private OnDateChangeListener onDateChangeListener;

    private int year;
    private int mouth;
    private int day;

    public ChooseDayDialog(Context context, int year, int mouth, int day) {
        super(context);
        this.year = year;
        this.mouth = mouth;
        this.day = day;

        setupAdapter();
    }

    public ChooseDayDialog(Context context) {
        super(context);
        init();
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_choose_day;
    }

    @Override
    protected void findView() {
        wheel_day = findViewByID(R.id.wheel_day);
        wheel_year = findViewByID(R.id.wheel_year);
        wheel_mouth = findViewByID(R.id.wheel_mouth);
        img_x = findViewByID(R.id.img_x);
        btn_commit = findViewByID(R.id.btn_commit);
    }

    @Override
    protected void initData() {
        img_x.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
    }

    private void init(){
        Calendar calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        mouth = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);

        setupAdapter();

    }

    private void setupAdapter() {
        yearAdapter = new IntegerWheelAdapter(2015, 2099);
        wheel_year.setAdapter(yearAdapter);
        if(year >= 2015){
            wheel_year.setCurrentItem(year - 2015);
        }
        wheel_year.setLabel("年");
        wheel_year.setCyclic(true);
        wheel_year.setVisibleItems(5);
        wheel_year.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_year.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                year = yearAdapter.getIndex(newValue);
                dayAdapter.refreshData(year, mouth);
                wheel_day.invalidateLayouts();
                wheel_day.invalidate();
                callBackChoose();
            }
        });

        mouthAdapter = new IntegerWheelAdapter(1, 12);
        wheel_mouth.setAdapter(mouthAdapter);
        wheel_mouth.setCurrentItem(mouth - 1);
        wheel_mouth.setLabel("月");
        wheel_mouth.setCyclic(true);
        wheel_mouth.setVisibleItems(5);
        wheel_mouth.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_mouth.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                mouth = mouthAdapter.getIndex(newValue);
                dayAdapter.refreshData(year, mouth);
                wheel_day.invalidateLayouts();
                wheel_day.invalidate();
                callBackChoose();
            }
        });

        dayAdapter = new DayWheelAdapter(year, mouth);
        wheel_day.setAdapter(dayAdapter);
        wheel_day.setLabel("日");
        wheel_day.setCyclic(true);
        wheel_day.setVisibleItems(5);
        wheel_day.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_day.setCurrentItem(day - 1);
        wheel_day.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                day = dayAdapter.getIndex(newValue);
                callBackChoose();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_x:
                dismiss();
                break;
            case R.id.btn_commit:
                dismiss();
                break;
        }
    }

    private void callBackChoose(){
        if(onDateChangeListener != null){
            onDateChangeListener.OnDateChange(year, mouth, day);
        }
    }

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        this.onDateChangeListener = onDateChangeListener;
    }
}

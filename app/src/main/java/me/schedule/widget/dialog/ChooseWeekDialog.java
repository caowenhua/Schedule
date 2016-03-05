package me.schedule.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import me.schedule.R;
import me.schedule.base.BaseDialog;
import me.schedule.listener.OnTimeCommitListener;
import me.schedule.listener.OnWeekChangeListener;
import me.schedule.util.ScreenUtils;
import me.schedule.widget.wheel.DayWheelAdapter;
import me.schedule.widget.wheel.IntegerWheelAdapter;
import me.schedule.widget.wheel.OnWheelChangedListener;
import me.schedule.widget.wheel.WheelView;

/**
 * Created by caowenhua on 2016/2/6.
 */
public class ChooseWeekDialog extends BaseDialog implements View.OnClickListener{
    private ImageView img_x;
    private WheelView wheel_year;
    private WheelView wheel_mouth;
    private WheelView wheel_firstday;
    private WheelView wheel_lastday;
    private Button btn_commit;

    private IntegerWheelAdapter yearAdapter;
    private DayWheelAdapter dayAdapter;
    private DayWheelAdapter dayAdapter2;
    private IntegerWheelAdapter mouthAdapter;

    private OnWeekChangeListener onWeekChangeListener;
    private OnTimeCommitListener onTimeCommitListener;

    private int year;
    private int mouth;
    private int firstday;
    private int lastday;

    public ChooseWeekDialog(Context context, int year, int mouth, int firstday, int lastday) {
        super(context);
        this.year = year;
        this.mouth = mouth;
        this.firstday = firstday;
        this.lastday = lastday;

        setupAdapter();
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_choose_week;
    }

    @Override
    protected void findView() {
        wheel_firstday = findViewByID(R.id.wheel_firstday);
        wheel_lastday = findViewByID(R.id.wheel_lastday);
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
                wheel_firstday.invalidateLayouts();
                wheel_firstday.invalidate();
                wheel_lastday.invalidateLayouts();
                wheel_lastday.invalidate();
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
                wheel_firstday.invalidateLayouts();
                wheel_firstday.invalidate();
                wheel_lastday.invalidateLayouts();
                wheel_lastday.invalidate();
                callBackChoose();
            }
        });

        dayAdapter = new DayWheelAdapter(year, mouth);
        wheel_firstday.setAdapter(dayAdapter);
        wheel_firstday.setLabel("日");
        wheel_firstday.setCyclic(true);
        wheel_firstday.setVisibleItems(5);
        wheel_firstday.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_firstday.setCurrentItem(firstday - 1);
        wheel_firstday.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                firstday = dayAdapter.getIndex(newValue);
                callBackChoose();
            }
        });

        dayAdapter2 = new DayWheelAdapter(year, mouth);
        wheel_lastday.setAdapter(dayAdapter2);
        wheel_lastday.setLabel("日");
        wheel_lastday.setCyclic(true);
        wheel_lastday.setVisibleItems(5);
        wheel_lastday.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_lastday.setCurrentItem(lastday - 1);
        wheel_lastday.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                lastday = dayAdapter.getIndex(newValue);
                callBackChoose();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (onTimeCommitListener != null) {
            onTimeCommitListener.onCommitTime();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_x:
                dismiss();
//                if (onTimeCommitListener != null) {
//                    onTimeCommitListener.onCommitTime();
//                }
                break;
            case R.id.btn_commit:
                dismiss();
//                if (onTimeCommitListener != null) {
//                    onTimeCommitListener.onCommitTime();
//                }
                break;
        }
    }

    private void callBackChoose(){
        if(onWeekChangeListener != null){
            int f,l;
            l = firstday > lastday ? firstday : lastday;
            f = firstday > lastday ? lastday : firstday;
            onWeekChangeListener.onWeekChange(year, mouth, f, l);
        }
    }

    public void setOnWeekChangeListener(OnWeekChangeListener onWeekChangeListener) {
        this.onWeekChangeListener = onWeekChangeListener;
    }

    public void setOnTimeCommitListener(OnTimeCommitListener onTimeCommitListener) {
        this.onTimeCommitListener = onTimeCommitListener;
    }
}

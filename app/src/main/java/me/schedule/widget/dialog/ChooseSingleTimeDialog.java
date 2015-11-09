package me.schedule.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import me.schedule.R;
import me.schedule.base.BaseDialog;
import me.schedule.util.ScreenUtils;
import me.schedule.widget.wheel.IntegerWheelAdapter;
import me.schedule.widget.wheel.OnWheelChangedListener;
import me.schedule.widget.wheel.WheelView;

/**
 * Created by caowenhua on 2015/11/6.
 */

public class ChooseSingleTimeDialog extends BaseDialog implements View.OnClickListener{


    private ImageView img_x;
    private WheelView wheel_hour;
    private WheelView wheel_year;
    private WheelView wheel_min;
    private WheelView wheel_mouth;
    private WheelView wheel_day;
    private Button btn_commit;


    private IntegerWheelAdapter hourAdapter;
    private IntegerWheelAdapter minAdapter;
    private IntegerWheelAdapter yearAdapter;
    private IntegerWheelAdapter dayAdapter;
    private IntegerWheelAdapter mouthAdapter;

    private int[] days;

    public ChooseSingleTimeDialog(Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_choose_single_time;
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

        img_x.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        days = new int[]{};

        hourAdapter = new IntegerWheelAdapter(0, 23);
        wheel_hour.setAdapter(hourAdapter);
        wheel_hour.setLabel("时");
        wheel_hour.setCyclic(true);
        wheel_hour.setVisibleItems(5);
        wheel_hour.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_hour.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                Toast.makeText(getContext(), hourAdapter.getIndex(newValue) + "---", Toast.LENGTH_SHORT).show();
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

            }
        });

        yearAdapter = new IntegerWheelAdapter(2015, 2020);
        wheel_year.setAdapter(yearAdapter);
        wheel_year.setLabel("年");
        wheel_year.setCyclic(true);
        wheel_year.setVisibleItems(5);
        wheel_year.TEXT_SIZE = ScreenUtils.instance(getContext()).dip2px(100)/5;
        wheel_year.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {

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

            }
        });

        dayAdapter = new IntegerWheelAdapter(0, 30);
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
        }
    }
}

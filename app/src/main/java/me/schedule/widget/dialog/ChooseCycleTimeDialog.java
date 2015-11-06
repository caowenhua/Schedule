package me.schedule.widget.dialog;

import android.content.Context;

import me.schedule.R;
import me.schedule.base.BaseDialog;
import me.schedule.widget.wheel.NumericWheelAdapter;
import me.schedule.widget.wheel.WheelView;

/**
 * Created by caowenhua on 2015/11/5.
 */
public class ChooseTimeDialog extends BaseDialog {

    private WheelView wheel_hour;

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
    }

    @Override
    protected void initData() {
        wheel_hour.setAdapter(new NumericWheelAdapter(0, 23, "%02d"));
        wheel_hour.setLabel("时");
        wheel_hour.setCyclic(true);
        wheel_hour.setVisibleItems(5);
        wheel_hour.TEXT_SIZE = 60;
    }
}

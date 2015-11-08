package me.schedule.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import me.schedule.R;
import me.schedule.base.BaseDialog;
import me.schedule.widget.wheel.IntegerWheelAdapter;
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

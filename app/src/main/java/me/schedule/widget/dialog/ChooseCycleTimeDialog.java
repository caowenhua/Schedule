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
 * Created by caowenhua on 2015/11/5.
 */
public class ChooseCycleTimeDialog extends BaseDialog implements View.OnClickListener {

    private WheelView wheel_hour;
//    private WheelView wheel_cycle;
    private WheelView wheel_min;
    private Button btn_commit;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private Button btn_4;
    private Button btn_5;
    private Button btn_6;
    private Button btn_7;
    private ImageView img_x;

    private int[] cycle;
    private IntegerWheelAdapter hourAdapter;
    private IntegerWheelAdapter minAdapter;
    private int hour;
    private int min;

    public ChooseCycleTimeDialog(Context context) {
        super(context, R.style.DialogUpDown);
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_choose_cycle_time;
    }

    @Override
    protected void findView() {
        wheel_hour = findViewByID(R.id.wheel_hour);
        wheel_min = findViewByID(R.id.wheel_min);
//        wheel_cycle = findViewByID(R.id.wheel_cycle);
        img_x = findViewByID(R.id.img_x);
        btn_commit = findViewByID(R.id.btn_commit);
        btn_1 = findViewByID(R.id.btn_1);
        btn_2 = findViewByID(R.id.btn_2);
        btn_3 = findViewByID(R.id.btn_3);
        btn_4 = findViewByID(R.id.btn_4);
        btn_5 = findViewByID(R.id.btn_5);
        btn_6 = findViewByID(R.id.btn_6);
        btn_7 = findViewByID(R.id.btn_7);

        img_x.setOnClickListener(this);
        btn_commit.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        cycle = new int[]{1,1,1,1,1,0,0};

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


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_x:
                dismiss();
                break;
            case R.id.btn_1:
                break;
            case R.id.btn_2:
                break;
            case R.id.btn_3:
                break;
            case R.id.btn_4:
                break;
            case R.id.btn_5:
                break;
            case R.id.btn_6:
                break;
            case R.id.btn_7:
                break;
            case R.id.btn_commit:
                dismiss();
                break;
        }
    }
}

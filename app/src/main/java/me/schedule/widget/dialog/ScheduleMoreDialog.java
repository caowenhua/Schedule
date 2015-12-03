package me.schedule.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import me.schedule.R;
import me.schedule.base.BaseDialog;
import me.schedule.listener.OnButtonClickListener;

/**
 * Created by caowenhua on 2015/11/17.
 */
public class ScheduleMoreDialog extends BaseDialog implements View.OnClickListener{

    private OnButtonClickListener listener, delListener, addListener;
    private TextView tvMouth;
    private TextView tvWeek;
    private TextView tvAdd;

    public ScheduleMoreDialog(Context context, OnButtonClickListener listener, OnButtonClickListener delListener
            , OnButtonClickListener addListener) {
        super(context);

        this.listener = listener;
        this.delListener = delListener;
        this.addListener = addListener;
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_schedule_more;
    }

    @Override
    protected void findView() {
        tvMouth = (TextView) findViewById(R.id.tv_mouth);
        tvWeek = (TextView) findViewById(R.id.tv_week);
        tvAdd = (TextView) findViewById(R.id.tv_add);
    }

    @Override
    protected void initData() {
        tvMouth.setOnClickListener(this);
        tvWeek.setOnClickListener(this);
        tvAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tvMouth){
            if(listener != null){
                listener.onButtonClick();
            }
            dismiss();
        }
        else if(v == tvWeek){
            if(delListener != null){
                delListener.onButtonClick();
            }
            dismiss();
        }
        else{
            if(addListener != null){
                addListener.onButtonClick();
            }
            dismiss();
        }
    }
}

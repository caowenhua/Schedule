package me.schedule.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.schedule.util.ExplosionUtils;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class TimeManageView extends LinearLayout implements View.OnClickListener{

    private TextView tvAdd;

    public TimeManageView(Context context) {
        super(context);
        init();
    }

    public TimeManageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimeManageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        tvAdd = new TextView(getContext());
//        tvAdd.setCompoundDrawables(getResources().getDrawable(), null, null, null);
        tvAdd.setCompoundDrawablePadding(ExplosionUtils.dp2Px(20));
        tvAdd.setText("点击增加事件时间");
        tvAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}

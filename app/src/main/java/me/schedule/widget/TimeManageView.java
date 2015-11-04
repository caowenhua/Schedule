package me.schedule.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import me.schedule.R;
import me.schedule.util.ExplosionUtils;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class TimeManageView extends LinearLayout implements View.OnClickListener{

    private LinearLayout llt;
    private int count;

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
        llt = new LinearLayout(getContext());
        llt.setOrientation(HORIZONTAL);
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ExplosionUtils.dp2Px(40), ExplosionUtils.dp2Px(40));
//        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(layoutParams);
//        marginLayoutParams.leftMargin = ExplosionUtils.dp2Px(20);
//        marginLayoutParams.topMargin = ExplosionUtils.dp2Px(10);
//        marginLayoutParams.bottomMargin = ExplosionUtils.dp2Px(10);
//        imageView.setLayoutParams(marginLayoutParams);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(R.drawable.add);
        imageView.setPadding(ExplosionUtils.dp2Px(5), ExplosionUtils.dp2Px(5), ExplosionUtils.dp2Px(5), ExplosionUtils.dp2Px(5));
        TextView tvAdd = new TextView(getContext());
//        tvAdd.setCompoundDrawables(getResources().getDrawable(R.drawable.del), null, null, null);
//        tvAdd.setCompoundDrawablePadding(ExplosionUtils.dp2Px(20));
//        tvAdd.setCompoundDrawablesWithIntrinsicBounds(R.drawable.add, 0, 0, 0);
        tvAdd.setText("点击增加事件时间");
        tvAdd.setOnClickListener(this);
        tvAdd.setTextColor(getResources().getColor(R.color.text_deep));
        tvAdd.setGravity(Gravity.CENTER_VERTICAL);
        tvAdd.setPadding(ExplosionUtils.dp2Px(20), ExplosionUtils.dp2Px(10), 0, ExplosionUtils.dp2Px(10));
        llt.addView(imageView);
        llt.addView(tvAdd);

        addView(llt);
        llt.setOnClickListener(this);

        count = 0;
    }

    @Override
    public void onClick(View v) {

    }
}

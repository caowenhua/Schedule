package me.schedule.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import me.schedule.R;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class ItemAddTime extends RelativeLayout {

    @Bind(R.id.img_del)
    ImageView imgDel;
    @Bind(R.id.tv_day)
    TextView tvDay;
    @Bind(R.id.tv_time)
    TextView tvTime;

    private int id;

    public ItemAddTime(Context context, int id) {
        super(context);
        this.id = id;
        init();
    }

    public ItemAddTime(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ItemAddTime(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_add_time, this);
    }

    @OnClick({R.id.img_del, R.id.tv_day, R.id.tv_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_del:
                break;
            case R.id.tv_day:
                break;
            case R.id.tv_time:
                break;
        }
    }

    public void setData(String day, String time){
        tvDay.setText(day);
        tvTime.setText(time);
    }
}

package me.schedule.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by caowenhua on 2015/11/18.
 */
public class TableFlowLayout extends ViewGroup {

    public TableFlowLayout(Context context) {
        super(context);
    }

    public TableFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TableFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}

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
public class CourseMoreDialog extends BaseDialog implements View.OnClickListener{

    private TextView tv_change;
    private TextView tv_del;
    private OnButtonClickListener listener, delListener;

    public CourseMoreDialog(Context context, OnButtonClickListener listener, OnButtonClickListener delListener) {
        super(context);

        this.listener = listener;
        this.delListener = delListener;
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_course_more;
    }

    @Override
    protected void findView() {
        tv_change = findViewByID(R.id.tv_change);
        tv_del = findViewByID(R.id.tv_del);
    }

    @Override
    protected void initData() {
        tv_del.setOnClickListener(this);
        tv_change.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tv_change){
            if(listener != null){
                listener.onButtonClick();
            }
            dismiss();
        }
        else{
            if(delListener != null){
                delListener.onButtonClick();
            }
            dismiss();
        }
    }
}

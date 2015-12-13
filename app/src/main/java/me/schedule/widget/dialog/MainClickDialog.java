package me.schedule.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import me.schedule.R;
import me.schedule.base.BaseDialog;
import me.schedule.listener.OnButtonClickListener;

/**
 * Created by caowenhua on 2015/12/13.
 */
public class MainClickDialog extends BaseDialog implements View.OnClickListener{

    private TextView tv_detail;
    private TextView tv_del;
    private OnButtonClickListener listener, delListener;

    public MainClickDialog(Context context, OnButtonClickListener listener, OnButtonClickListener delListener) {
        super(context);
        this.listener = listener;
        this.delListener = delListener;
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_main_click;
    }

    @Override
    protected void findView() {
        tv_detail = findViewByID(R.id.tv_detail);
        tv_del = findViewByID(R.id.tv_del);
    }

    @Override
    protected void initData() {
        tv_del.setOnClickListener(this);
        tv_detail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == tv_detail){
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

package me.schedule.widget.dialog;

import android.content.Context;

import me.schedule.R;
import me.schedule.base.BaseDialog;

/**
 * Created by caowenhua on 2015/11/5.
 */
public class ChooseTimeDialog extends BaseDialog {


    public ChooseTimeDialog(Context context) {
        super(context, R.style.DialogUpDown);
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_choose_time;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void initData() {
    }
}

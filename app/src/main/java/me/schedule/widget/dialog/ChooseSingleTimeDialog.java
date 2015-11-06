package me.schedule.widget.dialog;

import android.content.Context;

import me.schedule.R;
import me.schedule.base.BaseDialog;

/**
 * Created by caowenhua on 2015/11/6.
 */
public class ChooseSingleTimeDialog extends BaseDialog{

    public ChooseSingleTimeDialog(Context context) {
        super(context);
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_choose_single_time;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void initData() {

    }
}

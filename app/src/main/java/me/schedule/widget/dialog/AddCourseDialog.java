package me.schedule.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.schedule.R;
import me.schedule.base.BaseDialog;
import me.schedule.bean.CourseBean;
import me.schedule.dao.CourseDAO;
import me.schedule.listener.OnAddSuccessListener;

/**
 * Created by caowenhua on 2015/11/16.
 */
public class AddCourseDialog extends BaseDialog implements View.OnClickListener{

    private int day;
    private int startStamp;
    private int durationStamp;
    private EditText edtName;
    private EditText edtClassroom;
    private EditText edtRemark;
    private EditText edtTeacher;
    private TextView tvTime;
    private TextView tvCount;
    private TextView tvStart;
    private Button btnAdd;
    private Button btnDel;
    private Button btnCommit;

    private OnAddSuccessListener listener;

    public AddCourseDialog(Context context, int day, int startStamp) {
        super(context);
        this.day = day;
        this.startStamp = startStamp;
        durationStamp = 2;

        tvTime.setText(getDayByIndex(day));
        tvStart.setText("第" + (startStamp + 1) + "节");
        tvCount.setText(durationStamp + "");
    }

    @Override
    protected int setLayout() {
        return R.layout.dialog_add_course;
    }

    @Override
    protected void findView() {
        edtName = (EditText) findViewById(R.id.edt_name);
        edtClassroom = (EditText) findViewById(R.id.edt_classroom);
        edtRemark = (EditText) findViewById(R.id.edt_remark);
        tvTime = (TextView) findViewById(R.id.tv_time);
        btnAdd = (Button) findViewById(R.id.btn_add);
        tvCount = (TextView) findViewById(R.id.tv_count);
        btnDel = (Button) findViewById(R.id.btn_del);
        btnCommit = (Button) findViewById(R.id.btn_commit);
        tvStart = (TextView) findViewById(R.id.tv_start);
        edtTeacher = findViewByID(R.id.edt_teacher);

        btnAdd.setOnClickListener(this);
        btnDel.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                if(startStamp + durationStamp < 12){
                    durationStamp++;
                    tvCount.setText(durationStamp + "");
                }
                break;
            case R.id.btn_del:
                if(durationStamp > 1){
                    durationStamp--;
                    tvCount.setText(durationStamp + "");
                }
                break;
            case R.id.btn_commit:
                if(edtName.getText().length() == 0){
                    showToast("课程名不能为空");
                }
                else{
                    CourseBean bean = new CourseBean();
                    bean.setDay(day);
                    bean.setClassroom(edtClassroom.getText().length() == 0 ? "" : edtClassroom.getText().toString());
                    bean.setDurationStamp(durationStamp);
                    bean.setName(edtName.getText().toString());
                    bean.setStartStamp(startStamp);
                    bean.setTeacher(edtTeacher.getText().length() == 0 ? "" : edtTeacher.getText().toString());
                    bean.setId(day * 13 + startStamp);
                    bean.setRemark(edtRemark.getText().length() == 0 ? "" : edtRemark.getText().toString());
                    CourseDAO dao = new CourseDAO(getContext());
                    dao.add(bean);
                    if(listener != null){
                        listener.success();
                    }
                    dismiss();
                }
                break;
        }
    }

    private String getDayByIndex(int index){
        switch (index){
            case 0:
                return "星期一";
            case 1:
                return "星期二";
            case 2:
                return "星期三";
            case 3:
                return "星期四";
            case 4:
                return "星期五";
            case 5:
                return "星期六";
            case 6:
                return "星期日";
        }
        return "";
    }

    public void setAddSuccessListener(OnAddSuccessListener listener) {
        this.listener = listener;
    }
}

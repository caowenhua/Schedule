package me.schedule.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.schedule.R;
import me.schedule.bean.CourseBean;
import me.schedule.dao.CourseDAO;
import me.schedule.listener.OnButtonClickListener;
import me.schedule.widget.dialog.AddCourseDialog;
import me.schedule.widget.dialog.CourseMoreDialog;

/**
 * Created by caowenhua on 2015/11/5.
 */
public class CourseDetailActivity extends Activity implements View.OnClickListener{

    private ImageView imgBack;
    private ImageView imgMore;
    private TextView tvName;
    private TextView tvClassroom;
    private TextView tvDay;
    private TextView tvTeacher;
    private TextView tvStamp;
    private TextView tvRemark;

    private CourseBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        assignViews();

        bean = (CourseBean) getIntent().getSerializableExtra("bean");
        if(bean != null){
            tvClassroom.setText(bean.getClassroom());
            tvDay.setText(getDay());
            tvName.setText(bean.getName());
            tvRemark.setText(bean.getRemark());
            tvTeacher.setText(bean.getTeacher());
            tvStamp.setText(getStamp());
        }
        else{
            Toast.makeText(this, "参数缺失，请重试", Toast.LENGTH_SHORT).show();
            finish();
        }

    }


    private void assignViews() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgMore = (ImageView) findViewById(R.id.img_more);
        tvName = (TextView) findViewById(R.id.tv_name);
        tvClassroom = (TextView) findViewById(R.id.tv_classroom);
        tvDay = (TextView) findViewById(R.id.tv_day);
        tvTeacher = (TextView) findViewById(R.id.tv_teacher);
        tvStamp = (TextView) findViewById(R.id.tv_stamp);
        tvRemark = (TextView) findViewById(R.id.tv_remark);

        imgBack.setOnClickListener(this);
        imgMore.setOnClickListener(this);
    }

    private String getDay(){
        switch (bean.getDay()){
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

    private String getStamp(){
        boolean first = true;
        String s = "";
        for (int i = 0; i < bean.getDurationStamp(); i++) {
            if(first){
                s = "第" + (bean.getStartStamp()+1) + "节";
            }
            else{
                s = s + "、第" + (bean.getStartStamp()+1+i) + "节";
            }
        }
        return s;
    }

    @Override
    public void onClick(View v) {
        if(imgBack == v){
            finish();
        }
        else{
            CourseMoreDialog dialog = new CourseMoreDialog(this, new OnButtonClickListener() {
                @Override
                public void onButtonClick() {
                    AddCourseDialog addCourseDialog = new AddCourseDialog(CourseDetailActivity.this, bean);
                }
            }, new OnButtonClickListener() {
                @Override
                public void onButtonClick() {
                    CourseDAO dao = new CourseDAO(CourseDetailActivity.this);
                    dao.delete(bean);
                    finish();
                }
            });

        }
    }
}

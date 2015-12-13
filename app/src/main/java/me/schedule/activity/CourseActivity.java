package me.schedule.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import me.schedule.R;
import me.schedule.bean.CourseBean;
import me.schedule.dao.CourseDAO;
import me.schedule.listener.OnAddSuccessListener;
import me.schedule.listener.OnCourseClickListener;
import me.schedule.util.ScreenUtils;
import me.schedule.widget.CourseTableView;
import me.schedule.widget.dialog.AddCourseDialog;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class CourseActivity extends Activity implements View.OnClickListener, OnCourseClickListener {

    private ImageView imgBack;
    private ImageView imgSetting;
    private CourseTableView viewCoursetable;

    private List<CourseBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        assignViews();
    }

    private void assignViews() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgSetting = (ImageView) findViewById(R.id.img_setting);
        viewCoursetable = (CourseTableView) findViewById(R.id.view_coursetable);

        imgBack.setOnClickListener(this);
        imgSetting.setOnClickListener(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.instance(this).getScreenWidth(),
                ScreenUtils.instance(this).getScreenHeight() * 2);
        viewCoursetable.setLayoutParams(params);
        viewCoursetable.setOnCourseClickListener(this);

        CourseDAO dao = new CourseDAO(this);
        list = dao.getAll();
        viewCoursetable.setCourseBeanList(list);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_setting:
                break;
        }
    }

    @Override
    public void onCourseClick(int row, int column, boolean isIn) {
        if(isIn){
            CourseBean bean = null;
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).getDay() == row){
                    if(list.get(i).getStartStamp() <= column && list.get(i).getStartStamp() + list.get(i).getDurationStamp() - 1 >= column){
                        bean = list.get(i);
                        break;
                    }
                }
            }
            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra("bean", bean);
            startActivityForResult(intent, 10);
        }
        else{
            AddCourseDialog addCourseDialog = new AddCourseDialog(this, row, column);
            addCourseDialog.setAddSuccessListener(new OnAddSuccessListener() {
                @Override
                public void success() {
                    CourseDAO dao = new CourseDAO(CourseActivity.this);
                    list = dao.getAll();
                    viewCoursetable.setCourseBeanList(list);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            CourseDAO dao = new CourseDAO(this);
            list = dao.getAll();
            viewCoursetable.setCourseBeanList(list);
        }
    }
}

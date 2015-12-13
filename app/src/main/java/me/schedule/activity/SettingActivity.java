package me.schedule.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import me.schedule.R;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class SettingActivity extends Activity implements View.OnClickListener{

    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        assignViews();
    }

    private void assignViews() {
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == imgBack){
            finish();
        }
    }
}

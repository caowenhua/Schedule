package me.schedule.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import me.schedule.R;
import me.schedule.application.ECrashHandler;

/**
 * Created by caowenhua on 2015/11/5.
 */
public class TestActivity extends Activity {

    TextView c;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ECrashHandler handler = new ECrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(handler);
        setContentView(R.layout.activity_test);

        c = (TextView) findViewById(R.id.tv);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b = (Button) findViewById(R.id.btn_1);
                b.setText("Asdasdas");
            }
        });
    }
}

package me.schedule.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import me.schedule.R;
import me.schedule.listener.OnMatterListener;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class MatterEventView extends RelativeLayout implements View.OnClickListener{

    LinearLayout lltLv1;
    LinearLayout lltLv2;
    LinearLayout lltLv3;
    LinearLayout lltLv4;
    LinearLayout lltLv5;

    private OnMatterListener onMatterListener;
    private int matter;
    private List<LinearLayout> llts;

    public MatterEventView(Context context) {
        super(context);
        init();
    }

    public MatterEventView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MatterEventView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_matter_event, this);
        matter = 3;
        lltLv1 = (LinearLayout) findViewById(R.id.llt_lv1);
        lltLv2 = (LinearLayout) findViewById(R.id.llt_lv2);
        lltLv3 = (LinearLayout) findViewById(R.id.llt_lv3);
        lltLv4 = (LinearLayout) findViewById(R.id.llt_lv4);
        lltLv5 = (LinearLayout) findViewById(R.id.llt_lv5);
        lltLv1.setOnClickListener(this);
        lltLv2.setOnClickListener(this);
        lltLv3.setOnClickListener(this);
        lltLv4.setOnClickListener(this);
        lltLv5.setOnClickListener(this);
        llts = new ArrayList<>();
        llts.add(lltLv1);
        llts.add(lltLv2);
        llts.add(lltLv3);
        llts.add(lltLv4);
        llts.add(lltLv5);

        refresh();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llt_lv1:
                if(onMatterListener != null){
                    onMatterListener.onMatterChange(1);
                }
                matter = 1;
                break;
            case R.id.llt_lv2:
                if(onMatterListener != null){
                    onMatterListener.onMatterChange(2);
                }
                matter = 2;
                break;
            case R.id.llt_lv3:
                if(onMatterListener != null){
                    onMatterListener.onMatterChange(3);
                }
                matter = 3;
                break;
            case R.id.llt_lv4:
                if(onMatterListener != null){
                    onMatterListener.onMatterChange(4);
                }
                matter = 4;
                break;
            case R.id.llt_lv5:
                if(onMatterListener != null){
                    onMatterListener.onMatterChange(5);
                }
                matter = 5;
                break;
        }
        refresh();
    }

    private void refresh(){
        for (int i = 0; i < llts.size(); i++) {
            if(i == matter-1){
                llts.get(i).setBackgroundColor(Color.parseColor("#20dd191d"));
            }
            else{
                llts.get(i).setBackgroundColor(Color.parseColor("#00000000"));
            }
        }
    }

    public void setOnMatterListener(OnMatterListener onMatterListener){
        this.onMatterListener = onMatterListener;
    }

    public int getMatter() {
        return matter;
    }

    public void setMatter(int matter) {
        this.matter = matter;
        refresh();
    }
}

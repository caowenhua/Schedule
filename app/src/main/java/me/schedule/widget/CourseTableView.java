package me.schedule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by caowenhua on 2015/11/4.
 */
public class CourseTableView extends View {

    private int width;
    private int height;

    private int courseTextSize;
    private int classroomTextSize;

    private Paint paint;


    private int dayOfWeek;

    public CourseTableView(Context context) {
        super(context);
        init();
    }

    public CourseTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CourseTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        width = w;
        height = h;

        courseTextSize = h / 60 - 2;
        classroomTextSize = courseTextSize - 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }

    private void drawBackground(Canvas canvas){
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, width, height, paint);

        paint.setColor(Color.parseColor("#f4f4f4"));
        canvas.drawRect(width*(2*dayOfWeek-1) / 15, 0, width*(2*dayOfWeek+1) / 15, height, paint);

        paint.setColor(Color.parseColor("#c9cac6"));
        paint.setStrokeWidth(1);
        paint.setTextSize(21);
        canvas.drawLine(0, 0, 0, height, paint);
        for(int i = 0 ; i <= 7 ; i++){
            canvas.drawLine((1 + 2*i)*width/15, 0, (1 + 2*i)*width/15, height, paint);
        }
        for(int i = 0 ; i <= 13 ; i++){
            canvas.drawLine(0, height * i / 13, width, height * i / 13, paint);
        }
        for(int i = 1 ; i <= 13 ; i++){
            //canvas.drawText(Integer.toString(i), width/70, height * (i - 1) / 13 + height / 26, paint);
            canvas.drawText(Integer.toString(i), width/70, height * (i - 1) / 13 + height / 23, paint);
        }
    }

    private void drawClass(int day,int when,int span,String className,String classroom,Canvas canvas){
        int top,left,bottom,right,baseline,cutRectHeight,cutButtomHeight;

        RectF targetRect;
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        Paint.FontMetricsInt fontMetrics;

        left = (2*day - 1 )*width / 15;
        right = (2*day + 1 )*width / 15;
        top = (when - 1)*height / 13;
        bottom = (when + span - 1)*height / 13;

        cutButtomHeight = bottom - height / 13 / 15;
        cutRectHeight = bottom - height / 13 / 3;

        if(className.length() > 24){
            className = className.substring(0, 23); //+ "...";
        }

        if(day == dayOfWeek){
            paint.setColor(Color.parseColor("#F4F4F4"));
        }
        else{
            paint.setColor(Color.parseColor("#FFFFFF"));
        }
        canvas.drawRect(left + 2, top + 5, right, cutRectHeight, paint);
        TextPaint tp = new TextPaint();
        tp.setAntiAlias(true);
        tp.setDither(true);
        tp.setColor(Color.parseColor("#666666"));
        tp.setStyle(Paint.Style.FILL);
        tp.setTextSize(courseTextSize);
        StaticLayout slayout = new StaticLayout(className, tp, right-left - 3,
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        int h = (cutRectHeight-slayout.getHeight()-top) / 2;
        canvas.translate(left + 3, top + 5 + h);
        slayout.draw(canvas);
        canvas.translate(-left - 3, -top - 5 - h);



        RectF surround = new RectF(left + 3, cutRectHeight , right - 3, cutButtomHeight);
        paint.setColor(Color.parseColor("#20c2a9"));
        canvas.drawRoundRect(surround, 15, 15, paint);

        targetRect = new RectF(left + 6, cutRectHeight + 3, right - 6, cutButtomHeight - 3);

        if(day == dayOfWeek){
            paint.setColor(Color.parseColor("#20c2a9"));
            canvas.drawRoundRect(targetRect, 15, 15, paint);
            paint.setColor(Color.parseColor("#ffffff"));
        }
        else{
            paint.setColor(Color.parseColor("#ffffff"));
            canvas.drawRoundRect(targetRect, 15, 15, paint);
            paint.setColor(Color.parseColor("#20c2a9"));
        }

        paint.setTextSize(classroomTextSize);
        fontMetrics = paint.getFontMetricsInt();
        baseline = (int) (targetRect.top +
                (targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top) / 2 -
                fontMetrics.top);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(classroom , targetRect.centerX(), baseline, paint);
    }

    private void getDayOfWeek(){
        Calendar calender = Calendar.getInstance();
        calender.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        dayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == 1){
            dayOfWeek = 7;
        }
        else{
            dayOfWeek -- ;
        }
    }
}

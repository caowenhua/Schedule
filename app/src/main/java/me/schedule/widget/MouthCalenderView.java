package me.schedule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;

import me.schedule.R;
import me.schedule.listener.OnMouthCalenderListener;

/**
 * Created by caowenhua on 2015/11/6.
 */
public class MouthCalenderView extends View {

//    private int defaultSize;
//    private float density;
    private Paint paint;

    private int eachWidth;
    private Calendar calendar;
    private int year;
    private int mouth;
    private int dayOfWeek;
    private int dayCount;

    private int clickRow;
    private int clickColumn;

    private int red;
    private int orange;
    private int blue;
    private int green;
    private int gray;

    private OnMouthCalenderListener onMouthCalenderListener;

    public MouthCalenderView(Context context) {
        super(context);
        init();
    }

    public MouthCalenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MouthCalenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
//        density = Resources.getSystem().getDisplayMetrics().density;
//        defaultSize = Math.round(200 * density);

        paint = new Paint();
        paint.setAntiAlias(true);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        mouth = calendar.get(Calendar.MONTH);
        getDayCount();

        red = getResources().getColor(R.color.red_deep);
        orange = getResources().getColor(R.color.orange_deep);
        blue = getResources().getColor(R.color.blue_deep);
        green = getResources().getColor(R.color.green);
        gray = getResources().getColor(R.color.text_deep);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int width  = measureSize(widthMeasureSpec);
//        int height = measureSize(heightMeasureSpec);
//        setMeasuredDimension(width, height);
//    }
//
//    private int measureSize(int measureSpec){
//        int result;
//        int specMode = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//        if (specMode == MeasureSpec.EXACTLY) {
//            result = specSize;
//        } else if (specMode == MeasureSpec.AT_MOST) {
//            result = Math.min(defaultSize, specSize);
//        } else {
//            result = defaultSize;
//        }
//        return result;
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        eachWidth = w / 8;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                clickRow = (int) ((event.getX() * 16 / getWidth()) + 1 ) / 2;
                clickColumn = (int) (event.getY() / eachWidth);
//                Log.e("aaaa", clickRow + "--" + clickRow);
                return true;
            case MotionEvent.ACTION_UP:
                int row = (int) ((event.getX() * 16 / getWidth()) + 1 ) / 2;
                int column = (int) (event.getY() / eachWidth);
                if(clickRow == row && clickColumn == column){
                    if(onMouthCalenderListener != null){
                        onMouthCalenderListener.onDayClick(year, mouth, getDayBySize(row, column, dayOfWeek));
                    }
                }
                if(row - clickRow >= 2 || column - clickColumn >= 2){
                    if(mouth <= 0){
                        mouth = 11;
                        year --;
                    }
                    else{
                        mouth --;
                    }
                    if(onMouthCalenderListener != null){
                        onMouthCalenderListener.onMouthChange(year, mouth);
                    }
                    getDayCount();
                    invalidate();
                }
                else if(clickRow - row >= 2 || clickColumn - column >= 2){
                    if(mouth >= 11){
                        mouth = 0;
                        year ++;
                    }
                    else{
                        mouth++;
                    }
                    if(onMouthCalenderListener != null){
                        onMouthCalenderListener.onMouthChange(year, mouth);
                    }
                    getDayCount();
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        calendar.set(year, mouth, 1);
        dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        Log.e("day", "day:" + day + "----dayCount：" + dayCount + "--year:" + year + "--mouth:" + mouth);
        int columnCount = (dayCount - (8 - dayOfWeek)) / 7 + 1;
        if((dayCount - (8 - dayOfWeek)) % 7 != 0){
            columnCount ++;
        }
//        else{
//            columnCount --;
//        }
//        Log.e("columnCount","columnCount:"+columnCount);

        drawBackground(columnCount, canvas);
        drawText(dayOfWeek, canvas);

    }

    private void drawBackground(int columnCount, Canvas canvas){

        paint.setColor(Color.parseColor("#e5e5e5"));
        canvas.drawRect(eachWidth/2, 3 * eachWidth, 7.5f * eachWidth, (3 + columnCount) * eachWidth, paint);

        paint.setColor(Color.parseColor("#a0a0a0"));
        for (int i = 0; i < columnCount + 1; i++) {
            canvas.drawLine(eachWidth/2, (3+i)*eachWidth, 7.5f*eachWidth, (3+i)*eachWidth, paint);
        }
        for (int i = 0; i < 8; i++) {
            canvas.drawLine((i+0.5f)*eachWidth, 3*eachWidth , (i+0.5f)*eachWidth, (3+columnCount)*eachWidth, paint);
        }



    }

    private void drawText(int day, Canvas canvas){
        paint.setColor(Color.parseColor("#333333"));
        paint.setTextSize(eachWidth / 2);
        paint.setTextAlign(Paint.Align.CENTER);
        int row = 0;
        for (int i = 1; i <= dayCount; i++) {
            canvas.drawText(i+"", (day)*eachWidth, (3.7f+row) * eachWidth, paint);
            day++;
            if(day > 7){
                day = 1;
                row ++;
            }
        }

        canvas.drawText(year + "-" + (mouth+1), 4*eachWidth , eachWidth, paint);
    }

    public void getDayCount(){
        switch (mouth+1){
            case 1:
                dayCount = 31;
                break;
            case 2:
                if(year % 4 == 0){
                    dayCount = 28;
                }
                else{
                    dayCount = 29;
                }
                break;
            case 3:
                dayCount = 31;
                break;
            case 4:
                dayCount = 30;
                break;
            case 5:
                dayCount = 31;
                break;
            case 6:
                dayCount = 30;
                break;
            case 7:
                dayCount = 31;
                break;
            case 8:
                dayCount = 31;
                break;
            case 9:
                dayCount = 30;
                break;
            case 10:
                dayCount = 31;
                break;
            case 11:
                dayCount = 30;
                break;
            case 12:
                dayCount = 31;
                break;
        }
    }

    private int getDayBySize(int row,int column, int dayOfWeek){
        return row * 7 + column - dayOfWeek + 1;
    }

    public void setOnMouthCalenderListener(OnMouthCalenderListener onMouthCalenderListener) {
        this.onMouthCalenderListener = onMouthCalenderListener;
    }
}

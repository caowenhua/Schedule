package me.schedule.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.TimeZone;

public class ChartLine extends SurfaceView implements SurfaceHolder.Callback{

	private int width;
	private int height;
	private SurfaceHolder holder;
	private int dayOfWeek;
	private int aTextSize;
	private int bTextSize;
	
	private String[] classname;
	private String[] classroom;
	private int[] span;
	private int[] days;
	private int[] when;

	public void changeJSON(JSONObject json) throws JSONException{
		JSONArray array = (JSONArray) json.get("classes");
		classname = new String[array.length()];
		classroom = new String[array.length()];
		span = new int[array.length()];
		days = new int[array.length()];
		when = new int[array.length()];
		for(int i=0; i<array.length() ;i++){
			classname[i] = array.getJSONObject(i).getString("classname");
			classroom[i] = array.getJSONObject(i).getString("location");
			days[i] = getDayOfWeek(array.getJSONObject(i).getString("day"));
			
			String node = array.getJSONObject(i).getString("node");
			
			when[i] = Integer.parseInt(node.substring(0, node.indexOf(",")));
			//when[i] = array.getJSONObject(i).getString("node").charAt(0);
			span[i] = getSpan(array.getJSONObject(i).getString("node"));
		}
		if(holder != null ){
			drawAll();
		}
		else{
			Log.i("vvv", "null holder");
		}
			
	}
	
	private int getSpan(String string) {
		int count = 1;
		//char[] c = string.toCharArray();
		for(int i=0; i<string.length(); i++){
			if(string.charAt(i) == ','){
				count++;
			}
		}
		return count;
	}

	private int getDayOfWeek(String s) {
		if(s.equals("һ")){
			return 1;
		}
		else if(s.equals("��")){
			return 2;
		}
		else if(s.equals("��")){
			return 3;	
		}
		else if(s.equals("��")){
			return 4;
		}
		else if(s.equals("��")){
			return 5;
		}
		else if(s.equals("��")){
			return 6;
		}
		else{
			return 7;
		}
	}

	public ChartLine(Context context) {
		super(context);
		getHolder().addCallback(this);
		getDayOfWeek();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("vvvvvvvv", "surfaceCreated");
		this.holder = holder;
		drawLine(holder);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		//this.holder = holder;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.i("vvvvvvvv", "w-------h");
		width = w;
		height = h;
		
		aTextSize = h / 60 - 2;
		bTextSize = aTextSize - 3;
	}
	
	private void drawAll(){
		Canvas canvas = null;
		try {
			canvas = holder.lockCanvas();
			Paint paint = new Paint();
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
				canvas.drawText(Integer.toString(i), width/70, height * (i - 1) / 13 + height / 23, paint);
			}
			
			for(int i=0;i<days.length;i++){
				addClass(days[i], when[i], span[i], classname[i], classroom[i], canvas);
				Log.i("vvv", "addClass" + i + "--" + days[i]+ "--" + when[i]+ "--" + span[i]+ "--" + classname[i]+ "--" + classroom[i]);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if(canvas != null){
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	private void drawLine(SurfaceHolder holder){
		Canvas canvas = null;
		try {
			Log.i("vvvvvvvv", "draw");
			canvas = holder.lockCanvas();
			Paint paint = new Paint();
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
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			Log.i("vvvvvvvv", "drawfinally");
			if(canvas != null){
				holder.unlockCanvasAndPost(canvas);
			}
		}
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
	
	public void addClass(int day,int when,int span,String className,String classroom,Canvas canvas){	
		int top,left,bottom,right,baseline,cutRectHeight,cutButtomHeight;
		
		RectF targetRect;
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setDither(true);
		FontMetricsInt fontMetrics;

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
		//canvas.translate(left + 3, top + 5);
		TextPaint tp = new TextPaint();
		tp.setAntiAlias(true);
		tp.setDither(true);
		tp.setColor(Color.parseColor("#666666"));
		tp.setStyle(Style.FILL);
		tp.setTextSize(aTextSize);
		StaticLayout slayout = new StaticLayout(className, tp, right-left - 3, 
				Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
		int h = (cutRectHeight-slayout.getHeight()-top) / 2;
		//Log.i("ggggghhhhhhhh", h + "");
		canvas.translate(left + 3, top + 5 + h);
		//Log.i("ggggghhhhhhhh", h + "--" + top);
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
		
		paint.setTextSize(bTextSize);
		fontMetrics = paint.getFontMetricsInt();
		baseline = (int) (targetRect.top + 
				(targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top) / 2 -
				fontMetrics.top);  
		paint.setTextAlign(Paint.Align.CENTER);  
	    canvas.drawText(classroom , targetRect.centerX(), baseline, paint); 
	}
	/*
	public void addClass(int day,int when,int span,String className,String classroom,Canvas canvas){	
		int top,left,bottom,right,cutHeight,baseline;
		RectF targetRect;
		Paint paint = new Paint();
		FontMetricsInt fontMetrics;
		
		left = (2*day - 1 )*width / 15;
		right = (2*day + 1 )*width / 15;
		top = (when - 1)*height / 13;
		bottom = (when + span - 1)*height / 13;
		cutHeight = top + 4 * span * height / 65;
		
		if(className.length() > 28){
			className = className.substring(0, 27) + "...";
		}
		paint.setColor(Color.parseColor("#FFFFFF"));//F4DD00
		canvas.drawRect(left, top, right, cutHeight, paint);
		canvas.translate(left + 3, top + 3);
		TextPaint tp = new TextPaint();
		tp.setAntiAlias(true);
		tp.setDither(true);
		tp.setColor(Color.BLACK);
		tp.setStyle(Style.FILL);
		tp.setTextSize(aTextSize);
		StaticLayout slayout = new StaticLayout(className, tp, right-left - 3, 
				Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
		slayout.draw(canvas);
		canvas.translate(-left - 3, -top - 3);
		
	    
	    targetRect = new RectF(left, cutHeight, right, bottom);
	    paint.setColor(Color.parseColor("#20c2a9"));//00aacc
	    canvas.drawRoundRect(targetRect, 3, 3, paint);
	    paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setColor(Color.BLACK);
		paint.setTextSize(bTextSize);
		fontMetrics = paint.getFontMetricsInt();
		baseline = (int) (targetRect.top + 
				(targetRect.bottom - targetRect.top - fontMetrics.bottom + fontMetrics.top) / 2 -
				fontMetrics.top);  
		paint.setTextAlign(Paint.Align.CENTER);  
	    canvas.drawText(classroom , targetRect.centerX(), baseline, paint); 
	}*/
}

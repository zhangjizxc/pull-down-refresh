package com.example.demosliding;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyLinearlayout extends LinearLayout {

	float ratio;
	float scale;
	TextView tranceView;
	LinearLayout linearContent;
	float topCenterY;
	float minTranceY;
	float screenWidth;

	public MyLinearlayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {

			topCenterY = getResources().getDimension(R.dimen.top_height);
			minTranceY = getResources().getDimension(R.dimen.top_height);
			WindowManager manager = ((Activity) context).getWindowManager();
			Display display = manager.getDefaultDisplay();
			screenWidth = display.getWidth();

		}
	}

	public void setRatio(float ratio, float scale) {
		this.ratio = ratio;
		this.scale = scale;
		invalidate();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		tranceView = (TextView) findViewById(R.id.trance_view);
		linearContent = (LinearLayout) findViewById(R.id.linear_content);
	}

	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		if (child == tranceView) {
//			Log.d("pengpeng", "scale = "+scale+" screenWidth = "+screenWidth+" topCenterY = "+topCenterY);
			canvas.save();
//			canvas.scale(1, scale, 0, 0);
			boolean result = super.drawChild(canvas, child, drawingTime);
			canvas.restore();
			return result;
		}else if(child == linearContent){
			canvas.save();
			canvas.translate(0, ratio * minTranceY);
			boolean result = super.drawChild(canvas, child, drawingTime);
			canvas.restore();
			return result;
		}else{
			boolean result = super.drawChild(canvas, child, drawingTime);
			return result;
		}
	}

}

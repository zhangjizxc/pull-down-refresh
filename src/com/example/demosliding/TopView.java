package com.example.demosliding;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TopView extends FrameLayout{

	float ratio;
	float scale = 1;
	float minTranceY;
	float topCenterY;
	float screenWidth;
	LinearLayout linearTop;
	LinearLayout linearTopReal;
	public TopView(Context context, AttributeSet attrs) {
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
		linearTop = (LinearLayout)findViewById(R.id.linear_top);
		linearTopReal = (LinearLayout)findViewById(R.id.linear_top_real);
	}
	
	@Override
	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
		if(!isInEditMode()){
			if(child == linearTop){
				canvas.save();
				canvas.scale(1, scale, 0, 0);
//				canvas.translate(0, minTranceY * ratio);
				boolean result = super.drawChild(canvas, child, drawingTime);
				canvas.restore();
				return result;
			}else if(child == linearTopReal){
				canvas.save();
				canvas.translate(0, (minTranceY/2) * ratio);
				boolean result = super.drawChild(canvas, child, drawingTime);
				canvas.restore();
				return result;
			}else{
				boolean result = super.drawChild(canvas, child, drawingTime);
				canvas.restore();
				return result;
			}
		}else{
			boolean result = super.drawChild(canvas, child, drawingTime);
			canvas.restore();
			return result;
		}
	}

}

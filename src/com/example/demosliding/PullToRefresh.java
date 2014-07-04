package com.example.demosliding;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.example.demosliding.EntryScrollView.ScrollViewListener;
import com.example.demosliding.MyAnimator.MyAnimatorListener;

public class PullToRefresh extends FrameLayout implements OnTouchListener{

	private float startY;
	private float curTempY;
	private float oldTempY;
	private float distance;
	private float tempDistance;
	private float temp;
	private float ratio;
	private float scale;
	private float topHeight;
	private float minTranceY;
	private boolean isOpen = false;//topview处于拉伸状态
	private boolean isInitOneTimeUp = true;
	private boolean isInitOneTimeDown = true;
	private boolean isStartAnimation = false;//正在动画
	private boolean onTouchState = false;
	private TopView topView;
	private FrameLayout topReal;
	private MyLinearlayout mylinear;
	private EntryScrollView myScrollView;
	private Handler handler;
	private MyScrollListener scrollViewListener;
	
	@SuppressLint("NewApi")
	public PullToRefresh(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(R.layout.pull_refresh_view, this);
		topHeight = getResources().getDimension(R.dimen.top_height);
		minTranceY = getResources().getDimension(R.dimen.top_height);
		handler = new Handler();
		myScrollView = (EntryScrollView) view.findViewById(R.id.entry_scrollView);
		topView = (TopView) view.findViewById(R.id.topview);
		topReal = (FrameLayout) view.findViewById(R.id.linear_top_real);
		mylinear = (MyLinearlayout) view.findViewById(R.id.mylinear);
		myScrollView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
		myScrollView.setOnTouchListener(this);
		myScrollView.setScrollViewListener(new ScrollViewListener() {
			
			@Override
			public void onTouchEvent(MotionEvent ev) {
				if(ev.getAction() == MotionEvent.ACTION_DOWN){
//					Log.d("pengpeng", "MotionEvent.ACTION_DOWN");
//					myScrollView.setOnTouchListener(PullToRefresh.this);
				}
			}
			
			@Override
			public void onScrollChanged(EntryScrollView scrollView, int x, int y, int oldx, int oldy) {
				scrollViewListener.onScrollChanged(scrollView, x, y, oldx, oldy);
			}
			
			@Override
			public void onInterceptTouchEvent(MotionEvent ev) {
				
			}
		});
	}
	
	public MyScrollListener getScrollViewListener() {
		return scrollViewListener;
	}

	public void setScrollViewListener(MyScrollListener scrollViewListener) {
		this.scrollViewListener = scrollViewListener;
	}



	public static float clamp(float value, float min, float max) {
		return Math.max(Math.min(value, max), min);
	}
	
	public void setTopView(View topView) {
		if(topView != null){
			this.topReal.addView(topView);
		}
	}
	
	public void setContentView(View view) {
		if(view != null){
			this.mylinear.addView(view);
		}

	}
	
	public EntryScrollView getMyScrollView() {
		return myScrollView;
	}

	public void setMyScrollView(EntryScrollView myScrollView) {
		this.myScrollView = myScrollView;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = event.getY();
			isStartAnimation = false;
			Log.e("pengpeng", "--------------------down");
			return true;
		case MotionEvent.ACTION_MOVE:
			
			
			
			
			curTempY = event.getY();
			if (myScrollView.getScrollY() == 0) {
				// ratio会不断的变化，当为1时就不会再变了，顶部的区域的高度也固定为最小值了
//				Log.d("pengpeng", "1111 distance = "+distance);
				
				if(curTempY - oldTempY > 0){
					if(isInitOneTimeDown){
						startY = curTempY;
						tempDistance = distance;
						isInitOneTimeDown = false;
					}
					distance = tempDistance + Math.abs((curTempY - startY));
					if(distance >= topHeight){
						distance = topHeight;
					}
//					Log.d("pengpeng", "distance = "+distance+" tempDistance = "+tempDistance+" 111 = "+(curTempY - startY)+" curTempY = "+curTempY+" startY = "+startY);
					ratio = clamp(distance / topHeight, 0.0f, 1.0f);
					if(ratio > 0.1){
//						Log.e("pengpeng", "----------------onTouchState = "+onTouchState);
						onTouchState = true;
					}
					isInitOneTimeUp = true;
					
				}else if(curTempY - oldTempY < 0){
					isInitOneTimeDown = true;
					if(ratio > 0.05){
						onTouchState = true;
//						Log.e("pengpeng", "+++++++++++++++++++onTouchState = "+onTouchState+" ratio = "+ratio);
					}
					if(isInitOneTimeUp){
						startY = curTempY;
						tempDistance = distance;
						isInitOneTimeUp = false;
					}
					
					distance = tempDistance - Math.abs((curTempY - startY));
					
					ratio = clamp(distance / topHeight, 0.0f, 1.0f);
				}
				if(distance == topHeight){//ritio大于1以后tempDistance就固定大小了
					tempDistance = topHeight;
				}
//				Log.d("pengpeng", "ratio = "+ratio+" onTouchState = "+onTouchState+" 111 = "+(curTempY - startY)+" curTempY = "+curTempY+" startY = "+startY);
				scale = 1.0f + ratio * ((minTranceY + topHeight) / topHeight - 1.0f);
				topView.setRatio(ratio, scale);
				mylinear.setRatio(ratio, scale);
				oldTempY = curTempY;
				
//				if(ratio == 0.0f){
//					Log.e("pengpeng", "------------------------------");
//					 return false;
//				 }
				 return onTouchState;
				
				
				
				
//			Log.d("pengpeng", "tempY = " + curTempY + " startY = " + startY + " distance = " + distance);
//			Log.d("pengpeng", "myScrollView.getScrollY() = "+myScrollView.getScrollY());
//				curTempY = event.getY();
//				distance = curTempY - startY;
//				ratio = clamp(distance / topHeight, 0.0f, 1.0f);
//				scale = 1.0f + ratio * ((minTranceY + topHeight) / topHeight - 1.0f);
//				topView.setRatio(ratio, scale);
//				mylinear.setRatio(ratio, scale);
//				 Log.d("pengpeng", "ratio = " + ratio+" distance = "+distance+" curTempY = "+curTempY);
////				 tempDistance = distance;
//				 if(ratio == 0.0f){
//					 return false;
//				 }
//				 return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			// startY = 0;
			onTouchState = false;
//			tempDistance = distance;
//			myScrollView.setOnTouchListener(null);
			
			MyAnimator animator = new MyAnimator();
			animator.setDuration(150);
			animator.setListener(new MyAnimatorListener() {

				public void onAnimationUpdate(float fraction) {

					if(isStartAnimation){
						tempDistance = temp * (1 - fraction);
						distance = temp * (1 - fraction);
						ratio = clamp(distance / topHeight, 0.0f, 1.0f);
						topView.setRatio(ratio, 1.0f + (1 - fraction) * (scale - 1.0f));
						mylinear.setRatio(ratio, 1.0f + (1 - fraction) * (scale - 1.0f));
//						Log.d("pengpeng", "-------------onAnimationEnd fraction = "+fraction);
					}
					
						
				}

				public void onAnimationStart() {
					isStartAnimation = true;
					temp = distance;
				}

				public void onAnimationEnd() {
					isStartAnimation = false;
				}
			});
			animator.start();
			break;
		}
//		Log.i("pengpeng", "--------------------true = "+onTouchState);
		return false;
	}
	
	interface MyScrollListener{
		public void onScrollChanged(EntryScrollView scrollView, int x, int y, int oldx, int oldy);
	}
	
}

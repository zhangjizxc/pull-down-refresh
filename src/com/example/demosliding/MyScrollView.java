package com.example.demosliding;

import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView{
    private boolean mShouldScroll = true;

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
	    Log.v("533","!!!scroll view on touch event.."+ev.getAction() +"  "+ev.getY());
	    return super.onTouchEvent(ev);
	}

	public void setShouldScroll(boolean shouldScroll) {
	    mShouldScroll = shouldScroll;
	}
	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
	        int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY,
	        boolean isTouchEvent) {
	    Log.v("533","overScrollBy  deltaY="+deltaY +"  scrollY="+scrollY);
	    if (!mShouldScroll) {
	        deltaY = 0;
	        mShouldScroll = true;
	    }
	    return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY,
	            maxOverScrollX, maxOverScrollY, isTouchEvent);
	}
}

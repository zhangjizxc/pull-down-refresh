package com.example.demosliding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class EntryScrollView extends ScrollView {
    private boolean pauseFlag = false;
    private ScrollViewListener scrollViewListener = null;

    public EntryScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public EntryScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EntryScrollView(Context context) {
        super(context);
    }

    public void setPauseTouchEvent(boolean pauseFlag) {
        this.pauseFlag = pauseFlag;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(scrollViewListener != null) {
            scrollViewListener.onInterceptTouchEvent(ev);
        }
        if (pauseFlag) {
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(scrollViewListener != null) {
            scrollViewListener.onTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }
    
    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }
    
    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
        }
    }
    
    public interface ScrollViewListener {
        void onScrollChanged(EntryScrollView scrollView, int x, int y, int oldx, int oldy);
        void onTouchEvent(MotionEvent ev);
        void onInterceptTouchEvent(MotionEvent ev);
    }
}

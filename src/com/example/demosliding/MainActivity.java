
package com.example.demosliding;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ScrollView;

import com.example.demosliding.MyAnimator.MyAnimatorListener;

public class MainActivity extends Activity implements OnTouchListener {

    float startY;
    float curTempY;
    float oldTempY;
    float distance;
    float tempDistance;
    float temp;
    float ratio;
    float scale;
    float topHeight;
    float minTranceY;
    boolean isOpen = false;// topview��������״̬
    boolean isInitOneTimeUp = true;
    boolean isInitOneTimeDown = true;
    boolean isStartAnimation = false;// ���ڶ���
    TopView topView;
    MyLinearlayout mylinear;
    MyScrollView myScrollView;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        topHeight = getResources().getDimension(R.dimen.top_height);
        minTranceY = getResources().getDimension(R.dimen.top_height);
        handler = new Handler();

        myScrollView = (MyScrollView) findViewById(R.id.scroll);
        myScrollView.setOverScrollMode(ScrollView.OVER_SCROLL_NEVER);
        topView = (TopView) findViewById(R.id.topview);
        mylinear = (MyLinearlayout) findViewById(R.id.mylinear);
        myScrollView.setOnTouchListener(this);
        System.err.println("hello git");
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(Math.min(value, max), min);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                isStartAnimation = false;
                if (ratio > 0) {
                    myScrollView.setShouldScroll(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                curTempY = event.getY();
                Log.v("533", " myScrollView.getScrollY()="+myScrollView.getScrollY() +" event.getY()="+(event.getY()));
                if (myScrollView.getScrollY() == 0) {

                    if (curTempY - oldTempY > 0) {
                        isInitOneTimeUp = true;
                        if (isInitOneTimeDown) {
                            startY = curTempY;
                            tempDistance = distance;
                            isInitOneTimeDown = false;
                        }
                        distance = tempDistance + Math.abs((curTempY - startY));
                        if (distance >= topHeight) {
                            distance = topHeight;
                        }
//                        Log.d("pengpeng", "distance = " + distance + " tempDistance = "
//                                + tempDistance + " 111 = " + (curTempY - startY) + " curTempY = "
//                                + curTempY + " startY = " + startY);
                        ratio = clamp(distance / topHeight, 0.0f, 1.0f);

                    } else if (curTempY - oldTempY < 0) {
                        isInitOneTimeDown = true;
                        if (isInitOneTimeUp) {
                            startY = curTempY;
                            tempDistance = distance;
                            isInitOneTimeUp = false;
                        }

                        distance = tempDistance - Math.abs((curTempY - startY));

                        ratio = clamp(distance / topHeight, 0.0f, 1.0f);
                    }
                    if (distance == topHeight) {// ritio����1�Ժ�tempDistance�͹̶���С��
                        tempDistance = topHeight;
                    }
//                    Log.d("pengpeng", "distance = " + distance + " tempDistance = " + tempDistance
//                            + " 111 = " + (curTempY
//                            - startY) + " curTempY = " + curTempY + " startY = " + startY);
                    scale = 1.0f + ratio * ((minTranceY + topHeight) / topHeight - 1.0f);
                    topView.setRatio(ratio, scale);
                    mylinear.setRatio(ratio, scale);
                    oldTempY = curTempY;
                    Log.v("533"," scale="+scale +"  ratio="+ratio);
                    if (ratio == 0.0f) {
                        return false;
                    }

                    return true;

                    // Log.d("pengpeng", "tempY = " + curTempY + " startY = " +
                    // startY + " distance = " + distance);
                    // Log.d("pengpeng",
                    // "myScrollView.getScrollY() = "+myScrollView.getScrollY());
                    // curTempY = event.getY();
                    // distance = curTempY - startY;
                    // ratio = clamp(distance / topHeight, 0.0f, 1.0f);
                    // scale = 1.0f + ratio * ((minTranceY + topHeight) /
                    // topHeight - 1.0f);
                    // topView.setRatio(ratio, scale);
                    // mylinear.setRatio(ratio, scale);
                    // // Log.d("pengpeng", "ratio = " +
                    // ratio+" distance = "+distance+" curTempY = "+curTempY);
                    // // tempDistance = distance;
                    // if(ratio == 0.0f){
                    // return false;
                    // }
                    // return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                // startY = 0;
                // tempDistance = distance;
                myScrollView.setShouldScroll(true);

                MyAnimator animator = new MyAnimator();
                animator.setDuration(150);
                animator.setListener(new MyAnimatorListener() {

                    public void onAnimationUpdate(float fraction) {

                        if (isStartAnimation) {
                            tempDistance = temp * (1 - fraction);
                            distance = temp * (1 - fraction);
                            topView.setRatio((1 - fraction) * ratio, 1.0f + (1 - fraction)
                                    * (scale - 1.0f));
                            mylinear.setRatio((1 - fraction) * ratio, 1.0f + (1 - fraction)
                                    * (scale - 1.0f));
                            // Log.d("pengpeng",
                            // "-------------onAnimationEnd fraction = "+fraction);
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
        return false;
    }

}

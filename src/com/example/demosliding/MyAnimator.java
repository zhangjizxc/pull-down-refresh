package com.example.demosliding;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class MyAnimator {

    // ��ת����duration
    public static final int ANIM_DURATION_ROTATE_500 = 500;
    
    static final int ANIMATION_START = 0;
    static final int ANIMATION_FRAME = 1;

    private static final long DEFAULT_FRAME_DELAY = 10;
    private long sFrameDelay = DEFAULT_FRAME_DELAY;

    private long mDuration = 0;

    private long mStartTime;
    private boolean isRunning;

    private MyAnimatorListener mListener;
    private Interpolator mInterpolator;
    private float mStart = 0.0f;
    private float mEnd = 1.0f;
    
    public interface MyAnimatorListener {
        public void onAnimationStart();

        public void onAnimationEnd();

        public void onAnimationUpdate(float fraction);
    }

    public Interpolator getInterpolator() {
        if (mInterpolator == null) {
            return new AccelerateDecelerateInterpolator();
        }
        
        return mInterpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.mInterpolator = interpolator;
    }

    public MyAnimatorListener getListener() {
        return mListener;
    }

    public void setListener(MyAnimatorListener mListener) {
        this.mListener = mListener;
    }

    public void setDuration(long mDuration) {
        this.mDuration = mDuration;
    }

    public void setValue(float start, float end) {
        mStart = start;
        mEnd = end;
    }

    public void start() {
        AnimationHandler hanlder = new AnimationHandler();
        hanlder.sendEmptyMessage(ANIMATION_START);
    }

    public void cancel() {
        isRunning = false;
    }

    private void animationFrame(long currentTime) {
        float fraction = (float) (currentTime - mStartTime) / mDuration;
        if (fraction > 1.0f) {
            isRunning = false;
            animateValue(mEnd);
            animationEnd();
        } else {
            Interpolator interpolator = getInterpolator();
            fraction = interpolator.getInterpolation(fraction);
            fraction = (mEnd - mStart) * fraction + mStart;
            animateValue(fraction);
        }
    }

    private void animateValue(float fraction) {
        if (mListener != null) {
            mListener.onAnimationUpdate(fraction);
        }
    }

    private void animationStart() {
        mStartTime = AnimationUtils.currentAnimationTimeMillis();
        if (mListener != null) {
            mListener.onAnimationStart();
        }
    }

    private void animationEnd() {
        if (mListener != null) {
            mListener.onAnimationEnd();
        }
    }

    private class AnimationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case ANIMATION_START:
                animationStart();
                isRunning = true;
            case ANIMATION_FRAME:
                long currentTime = AnimationUtils.currentAnimationTimeMillis();
                animationFrame(currentTime);
                if (isRunning) {
                    sendEmptyMessageDelayed(ANIMATION_FRAME,
                            Math.max(0, sFrameDelay - (AnimationUtils.currentAnimationTimeMillis() - currentTime)));
                }
                break;
            }
        }
    }

    ///////////////////////��ݷ���?////////////////////////////////////////////////////////////////////////////////

    /**
     * ��������һ������
     * @param mAnimListener
     * @param animDuration
     */
    public static void startAnimation(MyAnimatorListener mAnimListener, int animDuration){
        MyAnimator animator = new MyAnimator();
        animator.setDuration(animDuration);
        animator.setListener(mAnimListener);
        animator.start();
    }
    
    /**
     * Ϊһ��Refresh View����һ����ת����
     * @param targetView
     */
    public static void startRefreshIconRotateAnimation(View targetView){
        startRotateAnimation(targetView, ANIM_DURATION_ROTATE_500);
    }
    
    /**
     * Ϊһ��View����һ����ת����
     * @param targetView
     * @param animDuration
     */
    public static void startRotateAnimation(View targetView, int animDuration){
        if(targetView != null){
            targetView.clearAnimation();
            Animation anim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                    0.5f);
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(animDuration);
            anim.setInterpolator(new LinearInterpolator());
            anim.setFillAfter(false);
            targetView.setAnimation(anim);
            anim.start();
        }
    }
    
    /**
     * ֹͣһ��View�Ķ���
     * @param targetView
     */
    public static void stopAnimation(View targetView){
        if(targetView != null){
            targetView.clearAnimation();
        }
    }
}

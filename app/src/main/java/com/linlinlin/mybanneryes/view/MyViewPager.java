package com.linlinlin.mybanneryes.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {

    private int delayTime = 3000;
    private boolean isClick;
    private float downX;
    private float downY;
    private long downTime;

    private OnPagerItemClickListener mOnPagerItemClickListener;

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public MyViewPager(@NonNull Context context) {
        this(context, null);
    }

    public MyViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startLooper();
    }

    private void startLooper() {
        removeCallbacks(mRunnable);
        postDelayed(mRunnable, delayTime);
    }

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            int position = getCurrentItem() + 1;
            setCurrentItem(position);
            postDelayed(this, delayTime);
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopLooper();
    }

    private void stopLooper() {
        removeCallbacks(mRunnable);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                downTime = System.currentTimeMillis();
                stopLooper();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                float dx = Math.abs(ev.getX() - downX);
                float dy = Math.abs(ev.getY() - downY);
                long dTime = System.currentTimeMillis() - downTime;
                isClick = dx <= 5 && dy <= 5 && dTime <= 1000;
                if (isClick && mOnPagerItemClickListener != null){

                        mOnPagerItemClickListener.onItemClick(getCurrentItem());

                }
                startLooper();
                break;
        }
        performClick();
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public void setOnPagerItemClickListener(OnPagerItemClickListener onPagerItemClickListener) {
        mOnPagerItemClickListener = onPagerItemClickListener;
    }

    public interface OnPagerItemClickListener {
        void onItemClick(int position);
    }


}

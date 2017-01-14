package com.project.tangyifeng.ex_recyclerview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import butterknife.ButterKnife;

/**
 * Author: Alexander
 * Email: yifengtang@hustunique.com
 * Since: 17/1/13.
 */

public class EX_ScrollBaseView extends RelativeLayout {

    private static final String TAG = "EX_ScrollBaseView";

    private float lastX;
    private float lastDelta = 0;
    private float slop;
    private View viewAbove;
    private View viewBelow;
    private boolean isScrolled = false;

    private EX_ScrollBaseView(Context context) {
        super(context);
    }

    public EX_ScrollBaseView(Context context, ViewGroup parent, int viewAboveId, int viewBelowId) {
        super(context);
        slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        viewAbove = LayoutInflater.from(parent.getContext()).inflate(viewAboveId, parent, false);
        viewBelow = LayoutInflater.from(parent.getContext()).inflate(viewBelowId, parent, false);
        LayoutParams viewBelowLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        viewBelowLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addView(viewBelow, viewBelowLayoutParams);
        addView(viewAbove);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        float curX = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercept = !(isScrolled && event.getX() >= getWidth() - viewBelow.getWidth());
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if(Math.abs(lastX - curX) > slop) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                intercept = false;
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercept = false;
                break;
            }
        }
        lastX = curX;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "Touched!");
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                Log.d(TAG, "ACTION_DOWN");
                if(isScrolled) {
                    if(event.getX() < getWidth() - viewBelow.getWidth()) {
                        startAnimation(lastDelta, 0, 100);
                        lastDelta = 0;
                        isScrolled = false;
                    }
                    break;
                }
            }
            case MotionEvent.ACTION_MOVE: {
                Log.d(TAG, "ACTION_MOVE");
                float curX = event.getX();
                float preDelta = lastDelta, afterDelta = (lastDelta + curX - lastX);
                if(-(lastDelta + curX - lastX) > viewBelow.getWidth()) {
                    afterDelta = -viewBelow.getWidth();
                    isScrolled = true;
                }
                if(lastDelta + curX - lastX > 0) {
                    afterDelta = 0;
                    isScrolled = false;
                }
                if(-lastDelta > viewBelow.getWidth()) {
                    preDelta = -viewBelow.getWidth();
                    isScrolled = true;
                }
                if(lastDelta > 0) {
                    preDelta = 0;
                    isScrolled = false;
                }
                startAnimation(preDelta, afterDelta, 10);
                lastDelta = afterDelta;
                lastX = curX;
                return true;
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                if(!isScrolled) {
                    if (-lastDelta > viewBelow.getWidth() / 4) {
                        startAnimation(lastDelta, -viewBelow.getWidth(), 200);
                        lastDelta = -viewBelow.getWidth();
                        isScrolled = true;
                    } else {
                        startAnimation(lastDelta, 0, 200);
                        lastDelta = 0;
                        isScrolled = false;
                    }
                } else {
                    startAnimation(lastDelta, 0, 100);
                    lastDelta = 0;
                    isScrolled = false;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void startAnimation(float start, float end, int time){
        ObjectAnimator animator;
        animator = ObjectAnimator.ofFloat(viewAbove, "translationX", start, end);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(time);
        animator.start();
    }

    public View getViewAbove(){
        return viewAbove;
    }

    public View getViewBelow(){
        return viewBelow;
    }

}

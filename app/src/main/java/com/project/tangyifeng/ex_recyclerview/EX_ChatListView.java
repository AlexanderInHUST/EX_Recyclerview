package com.project.tangyifeng.ex_recyclerview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Author: Alexander
 * Email: yifengtang@hustunique.com
 * Since: 17/1/14.
 */

public class EX_ChatListView<T> extends RelativeLayout {

    private static final String TAG = "EX_ChatListView";

    private float slop;
    private float lastXIntercept = -1;
    private float lastYIntercept = -1;
    private float lastY = -1;
    private float lastDelta = 0;
    private float pix;
    private int waitTime;
    private boolean isFlashing = false;
    private EX_Adapter<T> adapter;
    private EX_RecyclerView recyclerView;
    private FrameLayout backgroundImage;
    private View backgroundView;
    private boolean canFlash;

    private OnFlashListener flashListener;

    public interface OnFlashListener{
        public boolean flash();
        public void flashSuccessfully();
        public void flashUnsuccessfully();
    }

    public EX_ChatListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        recyclerView = new EX_RecyclerView(context);
        recyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        addView(recyclerView);
    }

    public EX_ChatListView(Context context) {
        super(context);
    }

    public EX_Adapter<T> getAdapter() {
        return adapter;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if(canFlash) {
            float curY = event.getY();
            float curX = event.getX();
            //Log.d(TAG, "Offset is " + recyclerView.computeVerticalScrollOffset());
            if (recyclerView.computeVerticalScrollOffset() == 0) {
                if (lastYIntercept != -1 && curY - lastYIntercept > slop && lastXIntercept != -1 && Math.abs(curX - lastXIntercept) < slop) {
                    //Log.d(TAG, "Intercept!" + " " + lastXIntercept + " " + lastYIntercept);
                    if (lastY == -1)
                        lastY = curY;
                    return true;
                } else if ((lastYIntercept != 1 && lastXIntercept != 1) || (lastYIntercept - curY > slop && Math.abs(curX - lastXIntercept) < slop)) {
                    //Log.d(TAG, "Write down position!");
                    lastYIntercept = curY;
                    lastXIntercept = curX;
                }
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                lastXIntercept = -1;
                lastYIntercept = -1;
                lastY = -1;
                //Log.d(TAG, "Refresh!");
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float curY = event.getY();
        if(canFlash && recyclerView.computeVerticalScrollOffset() == 0) {
            //Log.d(TAG, "1:" + curY + " " + lastY);
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE: {
                    float start = lastDelta, end = lastDelta + curY - lastY;
                    if(end < 0) {
                        end = 0;
                    }
                    startAnimation(start, end, 10, 0);
                    lastDelta = end;
                    lastY = curY;
                    return true;
                }
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP: {
                    if(lastDelta < pix) {
                        startAnimation(lastDelta, 0, 300, 0);
                        lastDelta = 0;
                    } else {
                        startAnimation(lastDelta, pix, 300, 0);
                        lastDelta = pix;
                        isFlashing = true;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(isFlashing) {
                                    competeFlash();
                                }
                            }
                        }, waitTime);
                        if(flashListener.flash()) {
                            flashListener.flashSuccessfully();
                        } else {
                            flashListener.flashUnsuccessfully();
                        }
                        competeFlash();
                    }
                    lastXIntercept = -1;
                    lastYIntercept = -1;
                    lastY = -1;
                    //Log.d(TAG, "Refresh!");
                    break;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void setAdapter(EX_Adapter<T> adapter) {
        this.adapter = adapter;
        recyclerView.setAdapter(adapter);
    }

    public void setBackgroundImageByBitmap(@NonNull Bitmap image) {
        setBackground(new BitmapDrawable(image));
    }

    public void setBackgroundImageByDrawable(@Nullable Drawable image) {
        backgroundImage = new FrameLayout(getContext());
        if(image == null) {
            backgroundImage.setBackgroundColor(Color.RED);
        } else {
            backgroundImage.setBackground(image);
        }
        backgroundImage.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(backgroundImage, 0);
    }

    public void setCanFlash(boolean canFlash, @Nullable Integer layoutId, @Nullable Float pix, @Nullable Integer waitTime) {
        if(canFlash) {
            backgroundView = LayoutInflater.from(getContext()).inflate(layoutId, (ViewGroup) getParent(), false);
            backgroundView.measure(getMeasuredWidth(), getMeasuredHeight());
            ((ViewGroup) getParent()).addView(backgroundView, 0);
            this.pix = pix;
            this.canFlash = true;
            this.waitTime = waitTime;
        } else {
            this.canFlash = false;
        }
    }

    private void competeFlash() {
        if(isFlashing) {
            startAnimation(lastDelta, 0, 300, 0);
            lastDelta = 0;
            isFlashing = false;
        }
    }

    public void setOnFlashListener(OnFlashListener onFlashListener) {
        flashListener = onFlashListener;
    }

    public EX_RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private void startAnimation(float start, float end, int time, int delay){
        ObjectAnimator animatorAbove;
        ObjectAnimator animatorBelow;
        animatorAbove = ObjectAnimator.ofFloat(recyclerView, "translationY", start, end);
        animatorAbove.setInterpolator(new AccelerateInterpolator());
        animatorAbove.setDuration(time);
        animatorBelow = ObjectAnimator.ofFloat(backgroundImage, "translationY", start, end);
        animatorBelow.setInterpolator(new AccelerateInterpolator());
        animatorBelow.setDuration(time);
        animatorAbove.setStartDelay(delay);
        animatorBelow.setStartDelay(delay);
        animatorAbove.start();
        animatorBelow.start();
    }
}

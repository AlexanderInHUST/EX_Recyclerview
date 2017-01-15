package com.project.tangyifeng.ex_recyclerview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

/**
 * Author: Alexander
 * Email: yifengtang@hustunique.com
 * Since: 17/1/13.
 */

public class EX_RecyclerView extends RecyclerView {

    private static final String TAG = "EX_RecyclerView";

    public EX_RecyclerView(Context context) {
        this(context, null);
    }

    public EX_RecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialRecyclerView();
    }

    private void initialRecyclerView(){

        setLayoutManager(new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollHorizontally(){
                return false;
            }
        });
    }
}

package com.project.tangyifeng.ex_recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Author: Alexander
 * Email: yifengtang@hustunique.com
 * Since: 17/1/13.
 */

public class EX_RecyclerView extends RecyclerView {

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

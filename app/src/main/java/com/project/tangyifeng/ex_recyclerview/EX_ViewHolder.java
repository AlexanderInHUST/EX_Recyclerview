package com.project.tangyifeng.ex_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Author: Alexander
 * Email: yifengtang@hustunique.com
 * Since: 17/1/13.
 */

public abstract class EX_ViewHolder<T> extends RecyclerView.ViewHolder {

    private static final String TAG = "EX_ViewHolder";

    private View viewAbove;
    private View viewBelow;

    public EX_ViewHolder(View itemView) {
        super(itemView);
    }

    public EX_ViewHolder(ViewGroup parent, int viewAboveId, int viewBelowId) {
        super(fromResLayout(parent, viewAboveId, viewBelowId));
        viewAbove = ((EX_ScrollBaseView) itemView).getViewAbove();
        viewBelow = ((EX_ScrollBaseView) itemView).getViewBelow();
    }

    private static View fromResLayout(ViewGroup parent, int viewAboveId, int viewBelowId) {
        return new EX_ScrollBaseView(parent.getContext(), parent, viewAboveId, viewBelowId);
    }

    public abstract void bindData(T data);
    public abstract void bindButtonBelow();

    public View getAboveView(){
        return viewAbove;
    }

    public View getBelowView(){
        return viewBelow;
    }
}

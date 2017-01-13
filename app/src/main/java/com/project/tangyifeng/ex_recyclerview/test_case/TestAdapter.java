package com.project.tangyifeng.ex_recyclerview.test_case;

import android.view.ViewGroup;

import com.project.tangyifeng.ex_recyclerview.EX_Adapter;
import com.project.tangyifeng.ex_recyclerview.EX_ViewHolder;
import com.project.tangyifeng.ex_recyclerview.R;

/**
 * Author: Alexander
 * Email: yifengtang@hustunique.com
 * Since: 17/1/13.
 */

public class TestAdapter extends EX_Adapter<Integer> {
    @Override
    public EX_ViewHolder<Integer> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TestViewHolder(parent, R.layout.view_above_layout, R.layout.view_below_layout);
    }
}

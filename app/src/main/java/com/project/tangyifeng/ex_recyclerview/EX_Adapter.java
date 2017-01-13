package com.project.tangyifeng.ex_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Alexander
 * Email: yifengtang@hustunique.com
 * Since: 17/1/13.
 */

public abstract class EX_Adapter<T> extends RecyclerView.Adapter<EX_ViewHolder<T>> {

    private List<T> dataList;

    @Override
    public void onBindViewHolder(EX_ViewHolder<T> holder, int position) {
        holder.bindData(dataList.get(position));
        holder.bindButtonBelow();
    }

    @Override
    public int getItemCount() {
        return (dataList == null) ? 0 : dataList.size();
    }

    public void addData(List<T> data) {
        if(dataList == null) {
            setData(data);
        }
        int pos = dataList.size();
        dataList.addAll(data);
        notifyItemInserted(pos);
    }

    public void setData(List<T> data) {
        dataList = data;
        notifyDataSetChanged();
    }

    public List<T> getDataList(){
        return dataList;
    }

}

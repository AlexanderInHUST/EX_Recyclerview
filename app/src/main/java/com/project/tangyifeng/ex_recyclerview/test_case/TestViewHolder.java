package com.project.tangyifeng.ex_recyclerview.test_case;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tangyifeng.ex_recyclerview.EX_ViewHolder;
import com.project.tangyifeng.ex_recyclerview.R;

import butterknife.BindView;

/**
 * Author: Alexander
 * Email: yifengtang@hustunique.com
 * Since: 17/1/13.
 */

public class TestViewHolder extends EX_ViewHolder<Integer> {

    private static final String TAG = "TestViewHolder";

    public TestViewHolder(View itemView) {
        super(itemView);
    }

    public TestViewHolder(ViewGroup parent, int viewAboveId, int viewBelowId) {
        super(parent, viewAboveId, viewBelowId);
    }

    @Override
    public void bindData(Integer data) {
        TextView textView = (TextView)getAboveView().findViewById(R.id.test_text_view);

        textView.setText(data.toString());
    }

    @Override
    public void bindButtonBelow() {
        ImageView buttonOne = (ImageView)getBelowView().findViewById(R.id.button_one);
        ImageView buttonTwo = (ImageView)getBelowView().findViewById(R.id.button_two);

        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click one!");
            }
        });

        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Click two!");
            }
        });
    }
}

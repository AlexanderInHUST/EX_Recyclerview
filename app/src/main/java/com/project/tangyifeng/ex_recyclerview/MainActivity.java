package com.project.tangyifeng.ex_recyclerview;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.tangyifeng.ex_recyclerview.test_case.TestAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ex_recycler_view)
    EX_ChatListView<Integer> recyclerView;

    private TestAdapter adapter;
    private ArrayList<Integer> testArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new TestAdapter();
        initialView();
    }

    private void initialView(){
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundImageByDrawable(null);
        recyclerView.setCanFlash(true, R.layout.view_background_layout, 200f, 1000);
        recyclerView.setOnFlashListener(new EX_ChatListView.OnFlashListener() {
            @Override
            public boolean flash() {
                return (Math.random() * 100 > 50);
            }

            @Override
            public void flashSuccessfully() {
                Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void flashUnsuccessfully() {
                Toast.makeText(MainActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
            }
        });
        testArray = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            testArray.add(i);
        }
        adapter.addData(testArray);
    }
}

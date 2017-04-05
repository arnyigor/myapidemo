package com.arny.myapidemo.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.RecyclerAdapter;
import com.arny.myapidemo.models.GoodItem;
import com.arny.myapidemo.utils.BaseUtils;
import com.arny.myapidemo.utils.Generator;

import java.util.ArrayList;

public class RecyclerTestActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<GoodItem> goodItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_test);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        goodItems = getGoodItems();
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        // создаем адаптер
        mAdapter = new RecyclerAdapter(goodItems,this);
        mRecyclerView.setAdapter(mAdapter);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodItems.add(getNewGoodItem());
                mAdapter.dataSetChanged(goodItems);
            }
        });
    }

    private GoodItem getNewGoodItem() {
            GoodItem goodItem = new GoodItem();
            goodItem.setId(String.valueOf(goodItems.size())+1);
            goodItem.setName(Generator.getString(10));
            goodItem.setImgUrl(Generator.getImageUrl(50, 50));
            goodItem.setPrice(BaseUtils.randDouble(15.05,55.5));
        return goodItem;
    }

    private ArrayList<GoodItem> getGoodItems() {
        ArrayList<GoodItem> goodItems = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            GoodItem goodItem = new GoodItem();
            goodItem.setId(String.valueOf(i));
            goodItem.setName(Generator.getString(10));
            goodItem.setImgUrl(Generator.getImageUrl(50, 50));
            goodItem.setPrice(BaseUtils.randDouble(15.05,55.5));
            goodItems.add(goodItem);
        }
        return goodItems;
    }
}

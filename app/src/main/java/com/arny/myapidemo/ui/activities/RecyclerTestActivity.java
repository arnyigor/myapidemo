package com.arny.myapidemo.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.arny.arnylib.utils.generators.Generator;
import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.RecyclerAdapter;
import com.arny.myapidemo.models.GoodItem;

import java.util.ArrayList;

public class RecyclerTestActivity extends AppCompatActivity  implements RecyclerAdapter.OnItemClickListener  {
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
        mAdapter = new RecyclerAdapter(goodItems,this,this);
        mRecyclerView.setAdapter(mAdapter);
//        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                mAdapter.remove(viewHolder.getAdapterPosition());
//            }
//        };
//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
//        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goodItems.add(getNewGoodItem());
                mAdapter.dataSetChanged(goodItems);
                mRecyclerView.scrollToPosition(goodItems.size()-1);
            }
        });
    }

    private GoodItem getNewGoodItem() {
            GoodItem goodItem = new GoodItem();
            goodItem.setId(String.valueOf(goodItems.size()));
            goodItem.setTitle(Generator.getString(10));
            goodItem.setImage(Generator.getImageUrl(1600, 1200,Generator.GENERATOR_TYPE_IMG_PLACEIMG));
            goodItem.setPrice(MathUtils.randDouble(15.05,55.5));
        return goodItem;
    }

    private ArrayList<GoodItem> getGoodItems() {
        ArrayList<GoodItem> goodItems = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            GoodItem goodItem = new GoodItem();
            goodItem.setId(String.valueOf(i));
            goodItem.setTitle(Generator.getString(10));
            goodItem.setImage(Generator.getImageUrl(1600, 1200,Generator.GENERATOR_TYPE_IMG_PLACEIMG));
            goodItem.setPrice(MathUtils.randDouble(15.05,55.5));
            goodItems.add(goodItem);
        }
        return goodItems;
    }

    @Override
    public void onItemClick(int position) {
        ToastMaker.toast(this,goodItems.get(position).getId());
    }
}

package com.arny.myapidemo.adapters;

import android.content.Context;
import android.view.View;
import com.arny.arnylib.adapters.FilterBindableAdapter;
import com.arny.myapidemo.models.TestObject;
public class FilterExampleAdapter extends FilterBindableAdapter<TestObject, SimpleViewHolder> {
    private SimpleViewHolder.SimpleActionListener simpleActionListener;
    private int layout;

    public FilterExampleAdapter(Context context, int layout, SimpleViewHolder.SimpleActionListener simpleActionListener){
        this.context = context;
        this.simpleActionListener = simpleActionListener;
        this.layout = layout;
    }

    @Override
    protected int layoutId(int type) {
        return layout;
    }

    @Override
    protected void onBindItemViewHolder(SimpleViewHolder viewHolder, final int position, int type) {
        viewHolder.bindView(context,position,getItem(position),simpleActionListener);
    }

    @Override
    protected SimpleViewHolder viewHolder(View view, int type) {
        return new SimpleViewHolder(view);
    }

    @Override
    protected String itemToString(TestObject item) {
        return item.getName();
    }
}
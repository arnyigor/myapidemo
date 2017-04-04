package com.arny.myapidemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arny.myapidemo.R;
import com.arny.myapidemo.models.GoodItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<GoodItem> goodItems;
    private Context context;

    public RecyclerAdapter(ArrayList<GoodItem> goodItems, Context context) {
        this.goodItems = goodItems;
        this.context = context;
    }

    public void dataSetChanged(ArrayList<GoodItem> goodItems){
        this.goodItems = goodItems;
        notifyDataSetChanged();
    }
    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView itemName;
         ImageView itemImage;
        TextView itemPrice;
        public ViewHolder(View v) {
            super(v);
            itemName = (TextView) v.findViewById(R.id.tvGoodName);
            itemImage = (ImageView) v.findViewById(R.id.imgGoodPhoto);
            itemPrice = (TextView) v.findViewById(R.id.tvGoodPrice);
        }
    }

    // Создает новые views (вызывается layout manager-ом)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        return new ViewHolder(v);
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemName.setText(goodItems.get(position).getName());
        holder.itemPrice.setText(String.valueOf(goodItems.get(position).getPrice()));
        Picasso.with(context).load(goodItems.get(position).getImgUrl()).into(holder.itemImage);
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return goodItems.size();
    }
}
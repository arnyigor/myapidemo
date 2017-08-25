package com.arny.myapidemo.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.TouchImageView;
import com.arny.myapidemo.R;
import com.arny.myapidemo.models.GoodItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {


    private ArrayList<GoodItem> goodItems;
    private Context context;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    public RecyclerAdapter(ArrayList<GoodItem> goodItems, Context context, OnItemClickListener listener) {
        this.goodItems = goodItems;
        this.context = context;
        this.listener = listener;
    }

    public void remove(int position) {
        goodItems.remove(position);
        notifyItemRemoved(position);
    }

    public void dataSetChanged(ArrayList<GoodItem> goodItems) {
        this.goodItems = goodItems;
        notifyDataSetChanged();
    }

    // класс view holder-а с помощью которого мы получаем ссылку на каждый элемент
    // отдельного пункта списка
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,OnItemClickListener {
        private TextView itemName;
        private TextView itemPrice;
        private ImageView itemImage;
        private ArrayList<GoodItem> goodItems;
        private Context context;

        public ViewHolder(View v, ArrayList<GoodItem> goodItems, Context context) {
            super(v);
            this.context = context;
            itemView.setOnTouchListener(itemTouchListener);
            this.goodItems = goodItems;
            itemName = (TextView) v.findViewById(R.id.tvGoodName);
            itemImage = (ImageView) v.findViewById(R.id.imgGoodPhoto);
            itemPrice = (TextView) v.findViewById(R.id.tvGoodPrice);
            itemImage.setOnClickListener(this);
        }

        public void bind(final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            try {
                Bitmap bitmap;
                Drawable d = itemImage.getDrawable();
                bitmap = Bitmap.createBitmap(d.getIntrinsicWidth(), d.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                d.draw(canvas);
                Dialog dialog=new Dialog(context,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.dialog_image_view_layout);
                TouchImageView imageDialog = (TouchImageView) dialog.findViewById(R.id.imageDialog);
                imageDialog.setImageBitmap(bitmap);
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onItemClick(int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
        }
    }

    private View.OnTouchListener itemTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.setBackgroundResource(R.drawable.background_item_pressed);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    // CANCEL triggers when you press the view for too long
                    // It prevents UP to trigger which makes the 'pressed' background permanent which isn't what we want
                case MotionEvent.ACTION_OUTSIDE:
                    // OUTSIDE triggers when the user's finger moves out of the view
                case MotionEvent.ACTION_UP:
                    v.setBackgroundResource(R.drawable.background_item);
                    break;
                default:
                    break;
            }

            return true;
        }
    };


    // Создает новые views (вызывается layout manager-ом)
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        // тут можно программно менять атрибуты лэйаута (size, margins, paddings и др.)
        return new ViewHolder(v, goodItems, context);
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(listener);
        holder.itemName.setText("Id:" + goodItems.get(position).getId() + " Name:" + goodItems.get(position).getName());
        String priceText = "Price:" + String.valueOf(MathUtils.round(goodItems.get(position).getPrice(), 2));
        holder.itemPrice.setText(priceText);
        Glide.with(context)
                .load(goodItems.get(position).getImgUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.drawable.ic_splash)
                .into(holder.itemImage);
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return goodItems.size();
    }
}
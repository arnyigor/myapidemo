package com.arny.myapidemo.adapters;

import android.view.View;
import com.arny.arnylib.adapters.RecyclerBindableAdapter;
import com.arny.myapidemo.R;
public class LinearExampleAdapter extends RecyclerBindableAdapter<Integer, LinearViewHolder> {
	private LinearViewHolder.ActionListener actionListener;

	//задаем layout id для нашего элемента
	@Override
	protected int layoutId(int type) {
		return R.layout.linear_example_item;
	}

	//Создаем ViewHolder
	@Override
	protected LinearViewHolder viewHolder(View view, int type) {
		return new LinearViewHolder(view);
	}

	//Изменяем данные внутри элемента
	@Override
	protected void onBindItemViewHolder(LinearViewHolder viewHolder, final int position, int type) {
		viewHolder.bindView(getItem(position), position, actionListener);
	}

	//интерфейс для обработки событий
	public void setActionListener(LinearViewHolder.ActionListener actionListener) {
		this.actionListener = actionListener;
	}
}
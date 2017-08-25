package com.arny.myapidemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.arny.myapidemo.R;
import com.arny.myapidemo.models.Test;
import com.arny.myapidemo.models.TestObject;

import java.util.ArrayList;

public class LinearViewHolder extends RecyclerView.ViewHolder {

	@BindView(R.id.linear_example_item_text)
	TextView text;

	private int position;
	private ActionListener actionListener;

	public LinearViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);

	}


	public void bindView(TestObject object, int position, ActionListener listener) {
		actionListener = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.OnItemClick(getAdapterPosition());
            }
        });
		this.position = position;
		text.setText(String.valueOf(object));
	}

	@OnClick(R.id.linear_example_item_move_to_top)
	protected void OnMoveToTopClick() {
		if (actionListener != null) {
			actionListener.onMoveToTop(position);
		}
	}

	@OnClick(R.id.linear_example_item_remove)
	protected void OnRemoveClick() {
		if (actionListener != null) {
			actionListener.OnRemove(position);
		}
	}

	@OnClick(R.id.linear_example_item_up)
	protected void OnUpClick() {
		if (actionListener != null) {
			actionListener.OnUp(position);
		}
	}

	@OnClick(R.id.linear_example_item_down)
	protected void OnDownClick() {
		if (actionListener != null) {
			actionListener.OnDown(position);
		}
	}

	public interface ActionListener {
		void onMoveToTop(int position);

		void OnRemove(int position);

		void OnUp(int position);

		void OnDown(int position);

		void OnItemClick(int position);
	}
}
package com.arny.myapidemo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.arny.arnylib.adapters.BindableViewHolder;
import com.arny.myapidemo.R;
import com.arny.myapidemo.models.TestObject;
public class SimpleViewHolder extends BindableViewHolder<TestObject> implements View.OnClickListener {

	private int position;
    private SimpleActionListener simpleActionListener;

    public SimpleViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void bindView(Context context, int position, TestObject item, ActionListener actionListener) {
        super.bindView(context,position, item, actionListener);
        this.position = position;
        simpleActionListener = (SimpleActionListener) actionListener;
	    itemView.findViewById(R.id.simple_example_item_move_to_top).setOnClickListener(this);
	    itemView.findViewById(R.id.simple_example_item_remove).setOnClickListener(this);
	    itemView.findViewById(R.id.simple_example_item_up).setOnClickListener(this);
	    itemView.findViewById(R.id.simple_example_item_down).setOnClickListener(this);
	    TextView simpleExampleItemTittle = itemView.findViewById(R.id.simple_example_item_title);
	    simpleExampleItemTittle.setText(String.format("ID:%s Pos:%d Name:%s", item.getDbId(),position, item.getName()));
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.simple_example_item_move_to_top:
				if (simpleActionListener != null) {
					simpleActionListener.onMoveToTop(position);
				}
				break;
			case R.id.simple_example_item_remove:
				if (simpleActionListener != null) {
					simpleActionListener.OnRemove(position);
				}
				break;
			case R.id.simple_example_item_up:
				if (simpleActionListener != null) {
					simpleActionListener.OnUp(position);
				}
				break;
			case R.id.simple_example_item_down:
				if (simpleActionListener != null) {
					simpleActionListener.OnDown(position);
				}
				break;
		}
	}

	public interface SimpleActionListener extends ActionListener {
        void onMoveToTop(int position);

        void OnRemove(int position);

        void OnUp(int position);

        void OnDown(int position);
    }
}
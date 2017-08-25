package com.arny.myapidemo.adapters;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.arny.arnylib.adapters.BindableViewHolder;
import com.arny.myapidemo.R;
import com.arny.myapidemo.models.TestObject;
public class SimpleRecyclerViewHolder extends BindableViewHolder<TestObject> {


    @BindView(R.id.simple_example_item_tittle)
    TextView tittle;

    private int position;
    private SimpleActionListener simpleActionListener;

    public SimpleRecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bindView(int position, TestObject item, ActionListener actionListener) {
        super.bindView(position, item, actionListener);
        this.position = position;
        simpleActionListener = (SimpleActionListener) actionListener;
        tittle.setText(String.format("ID:%s Name:%s", item.getId(), item.getName()));
    }

    @OnClick(R.id.simple_example_item_move_to_top)
    protected void OnMoveToTopClick() {
        if (simpleActionListener != null) {
            simpleActionListener.onMoveToTop(position);
        }
    }

    @OnClick(R.id.simple_example_item_remove)
    protected void OnRemoveClick() {
        if (simpleActionListener != null) {
            simpleActionListener.OnRemove(position);
        }
    }

    @OnClick(R.id.simple_example_item_up)
    protected void OnUpClick() {
        if (simpleActionListener != null) {
            simpleActionListener.OnUp(position);
        }
    }

    @OnClick(R.id.simple_example_item_down)
    protected void OnDownClick() {
        if (simpleActionListener != null) {
            simpleActionListener.OnDown(position);
        }
    }

    public interface SimpleActionListener extends ActionListener {
        void onMoveToTop(int position);

        void OnRemove(int position);

        void OnUp(int position);

        void OnDown(int position);
    }
}
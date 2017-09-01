package com.arny.myapidemo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.arny.arnylib.adapters.BindableViewHolder;
import com.arny.myapidemo.R;
import com.arny.myapidemo.api.jsonplaceholder.Post;
public class JsonPostViewHolder extends BindableViewHolder<Post> implements View.OnClickListener {

	private int position;
    private SimpleActionListener simpleActionListener;

    public JsonPostViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void bindView(Context context, int position, Post item, ActionListener actionListener) {
        super.bindView(context,position, item, actionListener);
        this.position = position;
        simpleActionListener = (SimpleActionListener) actionListener;
	    TextView postTitle = (TextView) itemView.findViewById(R.id.tv_post_title);
	    TextView postBody = (TextView) itemView.findViewById(R.id.tv_body);
	    postTitle.setText(item.getTitle());
        postBody.setText(item.getBody());
    }


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		}
	}

	public interface SimpleActionListener extends ActionListener {
        void OnRemove(int position);
    }
}
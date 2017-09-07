package com.arny.myapidemo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.arny.arnylib.adapters.BindableViewHolder;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.myapidemo.R;
import com.arny.myapidemo.api.jsonplaceholder.Post;
import com.arny.myapidemo.api.umorili.PostModel;
import com.arny.myapidemo.models.TestObject;
public class NetworkListViewHolder extends BindableViewHolder<PostModel> implements View.OnClickListener {

	private int position;
    private SimpleActionListener simpleActionListener;

    public NetworkListViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void bindView(Context context, int position, PostModel item, ActionListener actionListener) {
        super.bindView(context,position, item, actionListener);
        this.position = position;
        simpleActionListener = (SimpleActionListener) actionListener;
	    TextView tvName = (TextView) itemView.findViewById(R.id.tv_name);
	    tvName.setText(String.format("Name:%s", item.getName()));
	    TextView tvContent = (TextView) itemView.findViewById(R.id.tv_content);
	    tvContent.setText(DroidUtils.fromHtml(item.getElementPureHtml()));
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		}
	}

	public interface SimpleActionListener extends ActionListener {
    }
}
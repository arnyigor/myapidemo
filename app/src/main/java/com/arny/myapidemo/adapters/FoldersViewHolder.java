package com.arny.myapidemo.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.arny.arnylib.adapters.BindableViewHolder;
import com.arny.arnylib.files.FileUtils;
import com.arny.arnylib.files.MediaFileInfo;
import com.arny.myapidemo.R;
import com.arny.myapidemo.models.FolderFile;
import com.arny.myapidemo.models.TestObject;
public class FoldersViewHolder extends BindableViewHolder<FolderFile> implements View.OnClickListener {

	private int position;
    private SimpleActionListener simpleActionListener;

    public FoldersViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void bindView(Context context, int position, FolderFile item, ActionListener actionListener) {
        super.bindView(context,position, item, actionListener);
        this.position = position;
        simpleActionListener = (SimpleActionListener) actionListener;
	    itemView.findViewById(R.id.simple_example_item_move_to_top).setVisibility(View.GONE);
	    itemView.findViewById(R.id.simple_example_item_remove).setOnClickListener(this);
	    itemView.findViewById(R.id.simple_example_item_up).setVisibility(View.GONE);
	    itemView.findViewById(R.id.simple_example_item_down).setVisibility(View.GONE);
	    TextView simpleExampleItemTittle = (TextView) itemView.findViewById(R.id.simple_example_item_title);
	    simpleExampleItemTittle.setText(String.format("%s %s", item.getFileName(), FileUtils.formatFileSize(item.getSize(),3)));
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.simple_example_item_remove:
				if (simpleActionListener != null) {
					simpleActionListener.OnRemove(position);
				}
				break;
		}
	}

	public interface SimpleActionListener extends ActionListener {
        void OnRemove(int position);
    }
}
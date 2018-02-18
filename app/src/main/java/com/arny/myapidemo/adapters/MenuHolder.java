package com.arny.myapidemo.adapters;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import com.arny.myapidemo.R;
import com.arny.myapidemo.models.IconMenuElement;
import com.burakeregar.easiestgenericrecycleradapter.base.GenericViewHolder;
public class MenuHolder extends GenericViewHolder {
	private IconMenuElement mItem;
	public MenuHolder(View itemView) {
			super(itemView);
	}

	@Override
	public void bindData(Object o) {
		mItem = (IconMenuElement) o;
		ImageView viewById = itemView.findViewById(R.id.ivIcon);
		Resources resources = itemView.getContext().getResources();
		int icon = mItem.getIcon();
		viewById.setImageDrawable(resources.getDrawable(icon));
	}
}

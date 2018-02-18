package com.arny.myapidemo.adapters

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.arny.arnylib.adapters.BindableViewHolder
import com.arny.myapidemo.R
import com.arny.myapidemo.models.IconMenuElement

class IconsViewHolder(itemView: View) : BindableViewHolder<IconMenuElement>(itemView), View.OnClickListener {

    private var position: Int? = 0
    private var iconActionListener: IconActionListener? = null

    override fun bindView(context: Context, position: Int, item: IconMenuElement, actionListener: BindableViewHolder.ActionListener) {
        super.bindView(context, position, item, actionListener)
        this.position = position
        iconActionListener = actionListener as IconActionListener
        val icon = itemView.findViewById<ImageView>(R.id.ivIcon)
        icon.setImageDrawable(context.resources.getDrawable(item.icon))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivIcon -> {
            }
        }
    }

    interface IconActionListener : BindableViewHolder.ActionListener
}
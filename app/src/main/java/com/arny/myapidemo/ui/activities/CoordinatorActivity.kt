package com.arny.myapidemo.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MotionEvent
import com.arny.arnylib.adapters.SimpleBindableAdapter
import com.arny.arnylib.utils.ToastMaker
import com.arny.myapidemo.R
import com.arny.myapidemo.adapters.IconsViewHolder
import com.arny.myapidemo.models.IconMenuElement
import kotlinx.android.synthetic.main.coordinator_activity.*


class CoordinatorActivity : AppCompatActivity() {

    lateinit var adapter: SimpleBindableAdapter<IconMenuElement, IconsViewHolder>

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coordinator_activity)
        initUI()
        initAdapter()
    }

    private fun initAdapter() {
        adapter = SimpleBindableAdapter<IconMenuElement, IconsViewHolder>(this, R.layout.icons_list_item, IconsViewHolder::class.java)
        val menu = arrayListOf<IconMenuElement>(IconMenuElement(R.drawable.ic_menu_delete), IconMenuElement(R.drawable.ic_splash))
        rvIconScroll.adapter = adapter
        adapter.addAll(menu)
        adapter.setActionListener(object : IconsViewHolder.IconActionListener {
            override fun OnItemClickListener(position: Int, Item: Any?) {
                when (position) {
                    0 -> {
                        ToastMaker.toastInfo(this@CoordinatorActivity, "Первое меню")
                    }
                }
            }
        })
    }

    private fun initUI() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar?.title = "Coordinator layout"
        rvIconScroll.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvIconScroll.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val action = e.action
                when (action) {
                    MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })
    }

}
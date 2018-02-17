package com.arny.myapidemo.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.arny.myapidemo.R
import kotlinx.android.synthetic.main.coordinator_activity.*
import android.view.MotionEvent
import android.support.v7.widget.RecyclerView
import android.widget.SimpleAdapter
import com.arny.myapidemo.R.id.listView







class CoordinatorActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coordinator_activity)
        rvIconScroll.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        val adapter = SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                arrayOf("Name", "Tel"),
                intArrayOf(android.R.id.text1, android.R.id.text2))
        rvIconScroll.setAdapter(adapter)
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
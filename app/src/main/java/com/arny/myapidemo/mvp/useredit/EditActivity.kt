package com.arny.myapidemo.mvp.useredit

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.arny.myapidemo.R

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        initToolbar()
        viewFragment()
    }

    private fun viewFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val bundle = Bundle()
        bundle.putString("login", "admin")
        val userEditFragment = UserEditFragment()
        userEditFragment.arguments = bundle
        fragmentTransaction.replace(R.id.container_edit, userEditFragment)
        fragmentTransaction.commit()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            title = "Редактирование"
        }
    }
}

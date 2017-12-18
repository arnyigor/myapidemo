package com.arny.myapidemo.mvp.auth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.afollestad.materialdialogs.folderselector.FileChooserDialog
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arny.myapidemo.R
import com.arny.myapidemo.mvp.useredit.UserEditFragment
import com.arny.myapidemo.mvp.useredit.UserEditPresenter
import java.io.File

class AuthActivity : MvpAppCompatActivity(), FileChooserDialog.FileCallback {
    @InjectPresenter
    var userEditPresenter: UserEditPresenter? = null

    override fun onFileChooserDismissed(dialog: FileChooserDialog) {

    }

    override fun onFileSelection(dialog: FileChooserDialog, file: File) {
        when (dialog.getTag()) {
            "select_image" -> {
                userEditPresenter?.viewImage(file,this)
            }
        }
    }

    val FRAGMENT_AUTH = 0
    val FRAGMENT_LOGIN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        initToolbar()
        viewFragment(FRAGMENT_AUTH)
    }


    fun viewFragment(select: Int) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment: Fragment
        when (select) {
            FRAGMENT_AUTH -> {
                fragment = AuthFragment()
                fragmentTransaction.replace(R.id.container_auth, fragment)
            }
            FRAGMENT_LOGIN -> {
                val bundle = Bundle()
                bundle.putString("login", null)
                fragment = UserEditFragment()
                fragment.setArguments(bundle)
                fragmentTransaction.replace(R.id.container_auth, fragment)
                fragmentTransaction.addToBackStack("edit")
            }
        }//        fragmentTransaction.addToBackStack("edit");
        fragmentTransaction.commit()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            title = "Авторизация"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }


}

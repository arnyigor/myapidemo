package com.arny.myapidemo.mvp.useredit

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.arny.myapidemo.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_user_edit.*
import com.afollestad.materialdialogs.folderselector.FileChooserDialog


class UserEditFragment : MvpAppCompatFragment(), UserEditView, View.OnClickListener {
    override fun viewImage(uri: String?) {
        Log.i("UserEditFragment","uri:" + uri)
        val loaded = Glide.with(context).asBitmap().load(uri)
        Log.i("UserEditFragment","loaded:" + loaded)
        Glide.with(context)
                .load(uri)
                .into(imgAvatar)
    }

    @InjectPresenter
    var userEditPresenter: UserEditPresenter? = null
    private var materialDialog: MaterialDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabEdit.setOnClickListener(this)
        imgBtnEditAvatar.setOnClickListener(this)
        if (arguments != null) {
            val login = arguments!!.getString("login")
            context?.let { userEditPresenter?.initState(it, login) }
        }
    }

    override fun initUI(login: String, email: String, avatar: String?) {
        edtLogin.setText(login)
        edtEmail.setText(email)
        if (avatar != null) {
            Glide.with(context).load(avatar).into(imgAvatar!!)
        }
    }

    override fun hideErrors() {

    }

    override fun setToolbarTitle(title: String) {
        if (activity != null) {
            activity!!.title = title
        }
    }

    override fun updateProgress(progress: String) {

    }

    override fun showAlert(message: String) {
        if (materialDialog == null || !materialDialog!!.isShowing) {
            materialDialog = MaterialDialog.Builder(context!!)
                    .title(R.string.app_name)
                    .content(message)
                    .positiveText(android.R.string.ok)
                    .onPositive { dialog, which -> userEditPresenter!!.alertRead() }
                    .cancelable(false)
                    .show()
        }
    }

    override fun hideMessage() {
        if (materialDialog != null && materialDialog!!.isShowing) {
            materialDialog!!.dismiss()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnSignIn -> context?.let { userEditPresenter?.register(edtLogin.text.toString(), edtPass.text.toString(), it) }
            R.id.imgBtnEditAvatar -> {
                context?.let {
                    FileChooserDialog.Builder(it)
                            .initialPath(Environment.getExternalStorageDirectory().getPath())
                            .mimeType("image/*")
                            .extensionsFilter(".png", ".jpg") // Optional extension filter, will override mimeType()
                            .tag("select_image")
                            .goUpLabel("Up") // custom go up label, default label is "..."
                            .show(activity)
                }
            }
        }
    }
}

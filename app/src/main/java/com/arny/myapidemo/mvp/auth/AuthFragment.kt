package com.arny.myapidemo.mvp.auth

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arny.arnylib.utils.ToastMaker
import com.arny.myapidemo.R
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : MvpAppCompatFragment(), AuthView, View.OnClickListener {
    @InjectPresenter
    var authPresenter: AuthPresenter? = null
    private var materialDialog: MaterialDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignIn.setOnClickListener(this)
        btnSignUp.setOnClickListener(this)
        ll_progress.visibility = View.GONE
        edt_login.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                hideErrors()
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        edt_pass.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                hideErrors()
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

            }

            override fun afterTextChanged(editable: Editable) {

            }
        })
        setToolbarTitle("Авторизация")

    }

    override fun signUp() {
        if (activity != null) {
            activity!!.onBackPressed()
        }
    }

    override fun signIn() {}

    override fun showLoginError() {
        til_login.error = "Empty login"
    }

    override fun showPassError() {
        til_pass.error = "Empty pass"
    }

    override fun showAuthError() {
        ToastMaker.toastError(context, "Wrong login or pass")
    }

    override fun hideErrors() {
        til_login.error = null
        til_pass.error = null
    }

    override fun showProgress() {
        btnSignUp.visibility = View.GONE
        btnSignIn.visibility = View.GONE
        ll_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        btnSignUp.visibility = View.VISIBLE
        btnSignIn.visibility = View.VISIBLE
        ll_progress.visibility = View.GONE
    }

    override fun setToolbarTitle(title: String) {
        activity!!.title = title
    }

    override fun updateProgress(progress: String) {
        tv_progress.text = progress
    }

    override fun showAlert(message: String) {
        Log.i(AuthActivity::class.java.simpleName, "onCreate: isRestore = " + (authPresenter?.isInRestoreState(this)))
        if (materialDialog?.isShowing!!) {
            materialDialog = MaterialDialog.Builder(context!!)
                    .title(R.string.app_name)
                    .content(message)
                    .positiveText(android.R.string.ok)
                    .onPositive { _, _ -> authPresenter?.alertRead() }
                    .cancelable(false)
                    .show()
        }

    }

    override fun hideMessage() {
        if (materialDialog != null && materialDialog!!.isShowing) {
            materialDialog!!.dismiss()
        }
    }

    override fun onPause() {
        super.onPause()
        hideMessage()
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnSignIn -> authPresenter?.auth(edt_login.text.toString(), edt_pass.text.toString(), context!!)
            R.id.btnSignUp -> if (activity != null) {
                (activity as AuthActivity).viewFragment(1)
            }
        }
    }
}

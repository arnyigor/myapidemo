package com.arny.myapidemo.ui.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.myapidemo.R;
import com.arny.myapidemo.mvp.presenter.AuthPresenter;
import com.arny.myapidemo.mvp.view.AuthView;

public class AuthActivity extends MvpAppCompatActivity implements AuthView, View.OnClickListener {
	@InjectPresenter
    AuthPresenter mPresenter;
	private EditText editLogin;
	private EditText editPass;
	private TextInputLayout tilLogin;
	private TextInputLayout tilPass;
	private LinearLayout llLogin;
	private LinearLayout llProgress;
	private TextView tvProgress;
	private AlertDialog mDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initUI();
	}

	private void initUI() {
		findViewById(R.id.btn_log_in).setOnClickListener(this);
		editLogin = findViewById(R.id.et_login);
		editPass = findViewById(R.id.et_pass);
		tilLogin = findViewById(R.id.til_login);
		tilPass = findViewById(R.id.til_pass);
		llLogin = findViewById(R.id.ll_login);
		llProgress = findViewById(R.id.ll_progress);
		tvProgress = findViewById(R.id.tv_progress);
		llProgress.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_log_in:
				mPresenter.checkLoginPass(editLogin.getText().toString(), editPass.getText().toString(), this);
				break;
		}
	}

	@Override
	public void loggedIn() {
		startActivity(new Intent(getBaseContext(), GitHubActivity.class));
	}

	@Override
	public void showLoginError() {
		tilLogin.setError("Empty login");
	}

	@Override
	public void showPassError() {
		tilPass.setError("Empty pass");
	}

	@Override
	public void showAuthError() {
		ToastMaker.toastError(this, "Wrong login or pass");
	}

	@Override
	public void hideErrors() {
		tilLogin.setError(null);
		tilPass.setError(null);
	}

	@Override
	public void showProgress() {
		llLogin.setVisibility(View.GONE);
		llProgress.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideProgress() {
		llLogin.setVisibility(View.VISIBLE);
		llProgress.setVisibility(View.GONE);
	}

	@Override
	public void updateProgress(String progress) {
		tvProgress.setText(progress);
	}

	@Override
	public void showAlert(String message) {
		Log.i(AuthActivity.class.getSimpleName(), "showAlert: isRestore = " + mPresenter.isInRestoreState(this));
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			if (mDialog == null || !mDialog.isShowing()) {
				AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder((new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light_Dialog)))
						.setTitle(R.string.app_name)
						.setMessage(message)
						.setOnDismissListener(dialog -> {
							mPresenter.onHideMessage();
						})
						.setPositiveButton(android.R.string.ok, null);
				mDialog = mDialogBuilder.create();
				mDialog.setCancelable(false);
				mDialog.show();
			}

		}
	}

	@Override
	public void hideMessage() {
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
	}

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}

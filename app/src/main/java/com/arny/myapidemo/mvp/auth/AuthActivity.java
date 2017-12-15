package com.arny.myapidemo.mvp.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.myapidemo.R;
import com.arny.myapidemo.ui.activities.GitHubActivity;

public class AuthActivity extends MvpAppCompatActivity implements AuthView, View.OnClickListener {
    @InjectPresenter
    AuthPresenter authPresenter;
    private EditText editLogin;
    private EditText editPass;
    private TextInputLayout tilLogin;
    private TextInputLayout tilPass;
    private ConstraintLayout llProgress;
    private TextView tvProgress;
    private MaterialDialog materialDialog;
    private Button btnLogin;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initUI();
        initListeners();
    }

    private void initListeners() {
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        editLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hideErrors();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        editPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                hideErrors();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initUI() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setToolbarTitle("Авторизация");
        }
        btnLogin = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        editLogin = findViewById(R.id.edt_login);
        editPass = findViewById(R.id.edt_pass);
        tilLogin = findViewById(R.id.til_login);
        tilPass = findViewById(R.id.til_pass);
        llProgress = findViewById(R.id.ll_progress);
        tvProgress = findViewById(R.id.tv_progress);
        llProgress.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                authPresenter.auth(editLogin.getText().toString(), editPass.getText().toString(), this);
                break;
            case R.id.btnSignUp:
                authPresenter.register(editLogin.getText().toString(), editPass.getText().toString(), this);
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
        btnSignUp.setVisibility(View.GONE);
        btnLogin.setVisibility(View.GONE);
        llProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        btnSignUp.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.VISIBLE);
        llProgress.setVisibility(View.GONE);
    }

    @Override
    public void setToolbarTitle(String title) {
        setTitle(title);
    }

    @Override
    public void updateProgress(String progress) {
        tvProgress.setText(progress);
    }

    @Override
    public void showAlert(String message) {
        Log.i(AuthActivity.class.getSimpleName(), "onCreate: isRestore = " + (authPresenter != null && authPresenter.isInRestoreState(this)));
        if (materialDialog == null || !materialDialog.isShowing()) {
            materialDialog = new MaterialDialog.Builder(this)
                    .title(R.string.app_name)
                    .content(message)
                    .positiveText(android.R.string.ok)
                    .onPositive((dialog, which) -> authPresenter.alertRead())
                    .cancelable(false)
                    .show();
        }

    }

    @Override
    public void hideMessage() {
        if (materialDialog != null && materialDialog.isShowing()) {
            materialDialog.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideMessage();
    }
}

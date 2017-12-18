package com.arny.myapidemo.mvp.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.myapidemo.R;
import com.arny.myapidemo.ui.activities.GitHubActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthFragment extends MvpAppCompatFragment implements AuthView, View.OnClickListener {
    @InjectPresenter
    AuthPresenter authPresenter;
    private TextInputLayout tilLogin;
    private EditText edtLogin;
    private TextInputLayout tilPass;
    private EditText edtPass;
    private Button btnSignIn;
    private ConstraintLayout llProgress;
    private ProgressBar progressbar;
    private TextView tvProgress;
    private Button btnSignUp;
    private Button btnLogin;
    private Context context;
    private MaterialDialog materialDialog;

    public AuthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.context = container.getContext();
        return inflater.inflate(R.layout.fragment_auth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin = view.findViewById(R.id.btnSignIn);
        btnLogin.setOnClickListener(this);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        edtLogin = view.findViewById(R.id.edt_login);
        edtPass = view.findViewById(R.id.edt_pass);
        tilLogin = view.findViewById(R.id.til_login);
        tilPass = view.findViewById(R.id.til_pass);
        llProgress = view.findViewById(R.id.ll_progress);
        tvProgress = view.findViewById(R.id.tv_progress);
        llProgress.setVisibility(View.GONE);
        edtLogin.addTextChangedListener(new TextWatcher() {
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
        edtPass.addTextChangedListener(new TextWatcher() {
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
        setToolbarTitle("Авторизация");

    }

    @Override
    public void signUp() {
        if (getActivity() != null) {
            getActivity().onBackPressed();
        }
    }

    @Override
    public void signIn() {
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
        ToastMaker.toastError(context, "Wrong login or pass");
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
        if (getActivity() != null) {
            getActivity().setTitle(title);
        }
    }

    @Override
    public void updateProgress(String progress) {
        tvProgress.setText(progress);
    }

    @Override
    public void showAlert(String message) {
        Log.i(AuthActivity.class.getSimpleName(), "onCreate: isRestore = " + (authPresenter != null && authPresenter.isInRestoreState(this)));
        if (materialDialog == null || !materialDialog.isShowing()) {
            materialDialog = new MaterialDialog.Builder(context)
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
    public void onPause() {
        super.onPause();
        hideMessage();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                authPresenter.auth(edtLogin.getText().toString(), edtPass.getText().toString(), context);
                break;
            case R.id.btnSignUp:
                if (getActivity() != null) {
                    ((AuthActivity)getActivity()).viewFragment(AuthActivity.FRAGMENT_LOGIN);
                }
                break;
        }
    }
}

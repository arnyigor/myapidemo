package com.arny.myapidemo.mvp.useredit;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arny.myapidemo.R;
import com.bumptech.glide.Glide;

public class UserEditFragment extends MvpAppCompatFragment implements UserEditView {
    @InjectPresenter
    UserEditPresenter userEditPresenter;
    private ImageView imgAvatar;
    private EditText edtLogin;
    private EditText editEmail;
    private EditText edtPass;
    private EditText edtPassConfirm;
    private ImageButton imgBtnEditAvatar;
    private ImageButton imgBtnDeleteAvatar;
    private FloatingActionButton fabEdit;
    private Context context;
    private MaterialDialog materialDialog;

    public UserEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = container.getContext();
        return inflater.inflate(R.layout.fragment_user_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        edtLogin = view.findViewById(R.id.edtLogin);
        editEmail = view.findViewById(R.id.edtEmail);
        edtPass = view.findViewById(R.id.edtPass);
        edtPassConfirm = view.findViewById(R.id.edtPassConfirm);
        imgBtnEditAvatar = view.findViewById(R.id.imgBtnEditAvatar);
        imgBtnDeleteAvatar = view.findViewById(R.id.imgBtnDeleteAvatar);
        fabEdit = view.findViewById(R.id.fabEdit);
        if (getArguments() != null) {
            String login = getArguments().getString("login");
            userEditPresenter.initState(context, login);
        }
    }

    @Override
    public void initUI(String login, String email, String avatar) {
        edtLogin.setText(login);
        editEmail.setText(email);
        if (avatar != null) {
            Glide.with(context).load(avatar).into(imgAvatar);
        }
    }

    @Override
    public void hideErrors() {

    }

    @Override
    public void setToolbarTitle(String title) {

    }

    @Override
    public void updateProgress(String progress) {

    }

    @Override
    public void showAlert(String message) {
        if (materialDialog == null || !materialDialog.isShowing()) {
            materialDialog = new MaterialDialog.Builder(context)
                    .title(R.string.app_name)
                    .content(message)
                    .positiveText(android.R.string.ok)
                    .onPositive((dialog, which) -> userEditPresenter.alertRead())
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
}

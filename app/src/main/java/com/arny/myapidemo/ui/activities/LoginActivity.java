package com.arny.myapidemo.ui.activities;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.arny.myapidemo.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editLogin;
    private EditText editPass;
    private TextInputLayout tilLogin;
    private TextInputLayout tilPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI() {
        findViewById(R.id.btn_log_in).setOnClickListener(this);
        editLogin= (EditText) findViewById(R.id.et_login);
        editPass= (EditText) findViewById(R.id.et_pass);
        tilLogin = (TextInputLayout) findViewById(R.id.til_login);
        tilPass = (TextInputLayout) findViewById(R.id.til_pass);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_log_in:
                startActivity(new Intent(getBaseContext(), GitHubActivity.class));
                break;
        }
    }
}

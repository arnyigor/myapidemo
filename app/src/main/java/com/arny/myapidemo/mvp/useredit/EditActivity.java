package com.arny.myapidemo.mvp.useredit;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.arny.myapidemo.R;
import com.arny.myapidemo.ui.fragments.FirstFragment;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        initToolbar();
        viewFragment();
    }

    private void viewFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("login", "admin");
        UserEditFragment userEditFragment = new UserEditFragment();
        userEditFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container_edit, userEditFragment);
//        fragmentTransaction.addToBackStack("edit");
        fragmentTransaction.commit();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Редактирование");
        }
    }
}

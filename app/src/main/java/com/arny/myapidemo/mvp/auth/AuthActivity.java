package com.arny.myapidemo.mvp.auth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arny.myapidemo.R;
import com.arny.myapidemo.mvp.useredit.UserEditFragment;

public class AuthActivity extends MvpAppCompatActivity  {

    public static final int FRAGMENT_AUTH = 0;
    public static final int FRAGMENT_LOGIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        initToolbar();
        viewFragment(FRAGMENT_AUTH);
    }


    public void viewFragment(int select) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment;
        switch (select) {
            case FRAGMENT_AUTH:
                fragment = new AuthFragment();
                fragmentTransaction.replace(R.id.container_auth, fragment);
//        fragmentTransaction.addToBackStack("edit");
                break;
            case FRAGMENT_LOGIN:
                Bundle bundle = new Bundle();
                bundle.putString("login", null);
                fragment = new UserEditFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container_auth, fragment);
                fragmentTransaction.addToBackStack("edit");
                break;
        }
        fragmentTransaction.commit();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Авторизация");
        }
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


}

package com.arny.myapidemo.net;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import com.arny.myapidemo.R;
import com.arny.myapidemo.fragments.RetrofitFragment;

public class NetActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        Fragment retrofitFragment = new RetrofitFragment();
        Bundle bundle = new Bundle();
        bundle.putString("message", "Alo Elena!");
        retrofitFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.netFragment, retrofitFragment);
        fragmentTransaction.commit();
    }
}

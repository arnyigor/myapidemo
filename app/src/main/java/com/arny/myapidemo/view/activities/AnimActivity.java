package com.arny.myapidemo.view.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.arny.myapidemo.R;

public class AnimActivity extends Activity
{
    final int MENU_COMBO_ID = 5;
    TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anim);
        tv = (TextView) findViewById(R.id.tv);
        registerForContextMenu(tv);
        Animation anim = null;
        anim = AnimationUtils.loadAnimation(this, R.anim.mycombo);
        tv.startAnimation(anim);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.tv:
                menu.add(0, MENU_COMBO_ID, 0, "repeat");
                break;
        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Animation anim = null;
        switch (item.getItemId()) {
            case MENU_COMBO_ID:
                anim = AnimationUtils.loadAnimation(this, R.anim.mycombo);
                break;
        }
        tv.startAnimation(anim);
        return super.onContextItemSelected(item);
    }
}
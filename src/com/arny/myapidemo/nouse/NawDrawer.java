package com.arny.myapidemo.nouse;/*
package com.arny.ui.uis;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.arny.ui.R;
import com.arny.ui.vars.Consts;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class NawDrawer extends AppCompatActivity {
	private Drawer nawDrawer;
	private ActionBar actionBar;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.naw_drawer_activity);

		try {
			actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		nawDrawer = new DrawerBuilder()
				.withActivity(this)
				.withTranslucentStatusBar(false)
				.withActionBarDrawerToggle(true)
				.withActionBarDrawerToggleAnimated(true)
				.addDrawerItems(
						new PrimaryDrawerItem().withName("Test1"),
						new PrimaryDrawerItem().withName("Test2")
				).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
					@Override
					public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
						Log.i(Consts.TAG, "onItemClick position = " + position);
						return false;
					}
				})
				.build();
	}
}*/

package com.arny.myapidemo.ui.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.adapters.SnappingLinearLayoutManager;
import com.arny.arnylib.utils.DroidUtils;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.NetworkListViewHolder;
import com.arny.myapidemo.api.API;
import com.arny.arnylib.network.ApiFactory;
import com.arny.myapidemo.api.retrofit.PlaceholderApi;
import com.arny.myapidemo.api.umorili.PostModel;
import com.arny.myapidemo.api.umorili.UmoriliApi;
import com.arny.myapidemo.services.NetworkStateService;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class NetworkActivity extends AppCompatActivity implements View.OnClickListener {

	private JSONObject params, headers;
	private Intent intent;
	private RecyclerView mRecyclerView;
	private SimpleBindableAdapter adapter;
	private List<PostModel> posts = new ArrayList<>();
	private PlaceholderApi placeholderService;
	private UmoriliApi umoriliApi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.netwok_activity);
		intent = new Intent(this, NetworkStateService.class);
		placeholderService = ApiFactory.getInstance().createService(PlaceholderApi.class, API.JSON_PLASEHOLDER_BASE_URL);
		umoriliApi = ApiFactory.getInstance().createService(UmoriliApi.class, API.BASE_URL_UMORILI);
		Log.i(NetworkActivity.class.getSimpleName(), "onCreate: intent = " + intent);
		initUI();
	}

	@Override
	public void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter(NetworkStateService.ACTION_UPDATE);
		filter.addCategory(Intent.CATEGORY_DEFAULT);
		LocalBroadcastManager.getInstance(this).registerReceiver(updateReciever, filter);
	}

	@Override
	public void onPause() {
		super.onPause();
		LocalBroadcastManager.getInstance(this).unregisterReceiver(updateReciever);
	}

	private BroadcastReceiver updateReciever = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(NetworkActivity.class.getSimpleName(), "onReceive: intent = " + intent);
			Log.i(NetworkActivity.class.getSimpleName(), "onReceive: intent = " + DroidUtils.dumpIntent(intent));
		}
	};

	private void initAdapter() {
		adapter = new SimpleBindableAdapter<>(NetworkActivity.this, R.layout.umorili_list_item, NetworkListViewHolder.class);
		adapter.setActionListener(
				(NetworkListViewHolder.SimpleActionListener) (position, Item) -> Log.i(NetworkActivity.class.getSimpleName(), "initAdapter: item = " + position));
		mRecyclerView.setAdapter(adapter);
	}

	private void initUI() {
		initToolbar();
		mRecyclerView = (RecyclerView) findViewById(R.id.rv_network_list);
		mRecyclerView.setLayoutManager(new SnappingLinearLayoutManager(this));
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		initAdapter();
		findViewById(R.id.btn_start_network_service).setOnClickListener(this);
		findViewById(R.id.btn_stop_network_service).setOnClickListener(this);
		findViewById(R.id.btn_get_umorili).setOnClickListener(this);
		try {
			params = new JSONObject();
			headers = new JSONObject();
			params.put("email", "test@test.ru");
			params.put("password", "123456");
			headers.put("Accept", "application/json");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		viewNetInfo();
	}

	private void viewNetInfo() {
		Context context = this;
		String text = DroidUtils.getNetworkInfo(context);
	}

	private void initToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		if (getSupportActionBar() != null) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			setTitle(getString(R.string.network));
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.btn_start_network_service:
				startService(intent);
				viewNetInfo();
				break;
			case R.id.btn_stop_network_service:
				stopService(intent);
				viewNetInfo();
				break;
			case R.id.btn_get_umorili:
				getUmoriliList();
				break;
		}
	}

	private void getUmoriliList() {
		umoriliApi.getPosts(10).enqueue(new Callback<List<PostModel>>() {
			@Override
			public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
				Log.i(NetworkActivity.class.getSimpleName(), "onResponse: call = " + call);
				Log.i(NetworkActivity.class.getSimpleName(), "onResponse: body = " + response);
				if (NetworkActivity.this.onResponse(response)) return;
				List<PostModel> body = response.body();
				if (body != null) {
					NetworkActivity.this.posts = body;
					setAdapterItems(body);
				}
			}

			@Override
			public void onFailure(Call<List<PostModel>> call, Throwable t) {
				t.printStackTrace();
				ToastMaker.toastError(NetworkActivity.this, "Error:" + t.getMessage());
			}
		});
	}

	private boolean onResponse(Response<?> response) {
		if (response == null) {
			ToastMaker.toastError(NetworkActivity.this, "response is null");
			return true;
		}
		if (response.code() != 200) {
			ToastMaker.toastError(NetworkActivity.this, "Error:" + response.message());
			return true;
		}
		return false;
	}

	private void setAdapterItems(List<PostModel> body) {
		adapter.clear();
		adapter.addAll(body);
	}

}

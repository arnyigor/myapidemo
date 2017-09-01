package com.arny.myapidemo.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.arny.arnylib.adapters.SimpleBindableAdapter;
import com.arny.arnylib.adapters.SnappingLinearLayoutManager;
import com.arny.arnylib.database.DBProvider;
import com.arny.arnylib.utils.ToastMaker;
import com.arny.myapidemo.R;
import com.arny.myapidemo.adapters.JsonPostViewHolder;
import com.arny.myapidemo.adapters.SimpleViewHolder;
import com.arny.myapidemo.api.jsonplaceholder.JsonPlaceholderApi;
import com.arny.myapidemo.api.jsonplaceholder.Post;

import java.util.ArrayList;

public class JsonPlaceholderApiActivity extends AppCompatActivity {

    private ArrayList<Post> posts = new ArrayList<>();
    private RecyclerView recyclerViewPosts;
    private SimpleBindableAdapter<Post, JsonPostViewHolder> adapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_placeholder_api);
        context = JsonPlaceholderApiActivity.this;
        initToolbar();
        initUI();
        initAdapter();
        loadPosts();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadPosts() {
        JsonPlaceholderApi.getApiPosts(context, new JsonPlaceholderApi.OnGetPostsResponse() {
            @Override
            public void onResultSuccess(ArrayList<Post> posts) {
                Log.i(JsonPlaceholderApiActivity.class.getSimpleName(), "onResultSuccess: posts = " + posts);
                JsonPlaceholderApiActivity.this.posts = posts;
                adapter.clear();
                adapter.addAll(JsonPlaceholderApiActivity.this.posts);
            }

            @Override
            public void onError(String error) {
                ToastMaker.toastError(context,error);
            }
        });
    }

    private void initUI() {
        recyclerViewPosts = (RecyclerView) findViewById(R.id.rv_posts);
        recyclerViewPosts.setLayoutManager(new SnappingLinearLayoutManager(this));
        recyclerViewPosts.setItemAnimator(new DefaultItemAnimator());
    }

    private void initAdapter() {
        adapter = new SimpleBindableAdapter<>(JsonPlaceholderApiActivity.this, R.layout.json_placeholder_posts_item, JsonPostViewHolder.class);
        adapter.setActionListener(new JsonPostViewHolder.SimpleActionListener() {

            @Override
            public void OnRemove(int position) {

            }

            @Override
            public void OnItemClickListener(int position, Object Item) {
                Log.i(DBHelperActivity.class.getSimpleName(), "OnItemClickListener: position = " + position);
            }
        });
        recyclerViewPosts.setAdapter(adapter);
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("JsonPlaceholder API");
        }
    }
}

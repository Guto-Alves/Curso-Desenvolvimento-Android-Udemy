package com.gutotech.youtube.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gutotech.youtube.R;
import com.gutotech.youtube.adapter.VideosAdapter;
import com.gutotech.youtube.api.YoutubeService;
import com.gutotech.youtube.helper.RetrofitConfig;
import com.gutotech.youtube.helper.YoutubeConfig;
import com.gutotech.youtube.model.Item;
import com.gutotech.youtube.model.Result;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private MaterialSearchView searchView;

    private VideosAdapter videosAdapter;

    private Result result;
    private List<Item> items = new ArrayList<>();

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Youtube");
        setSupportActionBar(toolbar);

        retrofit = RetrofitConfig.getRetrofit();

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getVideos(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
                getVideos("");
            }
        });

        RecyclerView videosRecyclerView = findViewById(R.id.videosRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        videosRecyclerView.setLayoutManager(layoutManager);
        videosRecyclerView.setHasFixedSize(true);
        videosAdapter = new VideosAdapter(items, videoClickListener);
        videosRecyclerView.setAdapter(videosAdapter);
        getVideos("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView.setMenuItem(menuItem);
        return true;
    }

    private void getVideos(String search) {
        search = search.replaceAll(" ", "+");

        YoutubeService youtubeService = retrofit.create(YoutubeService.class);

        youtubeService.recuperarVideos(
                "snippet", "date", "20", YoutubeConfig.API_KEY, YoutubeConfig.CHANNEL_ID, search
        ).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.isSuccessful()) {
                    result = response.body();
                    items = result.items;
                    videosAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
            }
        });
    }

    private final VideosAdapter.VideoClickListener videoClickListener = new VideosAdapter.VideoClickListener() {
        @Override
        public void onClick(View view, int position) {
            Item video = items.get(position);

            Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
            intent.putExtra("videoId", video.id.videoId);
            startActivity(intent);
        }
    };
}

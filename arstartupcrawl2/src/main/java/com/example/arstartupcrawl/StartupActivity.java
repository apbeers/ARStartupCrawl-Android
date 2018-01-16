package com.example.arstartupcrawl;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StartupActivity extends WearableActivity {

    static RecyclerView recyclerView;
    static MyStartupRecyclerViewAdapter startupRecyclerViewAdapter;
    static List<Startup> startupList = new ArrayList<>();
    static boolean hasSetup = false;
    static Retrofit retrofit;
    static StartupClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_startups_list);

        recyclerView = (RecyclerView) findViewById(R.id.list);
        startupRecyclerViewAdapter = new MyStartupRecyclerViewAdapter(startupList, this);
        recyclerView.setAdapter(startupRecyclerViewAdapter);

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public void onResume(){
        super.onResume();
        refreshStartups();

    }

    public void refreshStartups(){
        if(!hasSetup){
            setupRetrofit();
        }

        Call<List<Startup>> call = client.getAllStartupsNoPics();

        Log.e("STARTUPMANAGER:", "Trying to get Startups");

        call.enqueue(new Callback<List<Startup>>() {
            @Override
            public void onResponse(Call<List<Startup>> call, Response<List<Startup>> response) {
                startupList.clear();
                startupList.addAll(response.body());
                recyclerView.getAdapter().notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Startup>> call, Throwable t) {
                Log.e("STARTUPMANAGER:", "Did not receive Startups from database");
            }
        });
    }

    public void setupRetrofit(){
        if(!hasSetup){
            retrofit = new Retrofit.Builder()
                    .baseUrl(StartupClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            client = retrofit.create(StartupClient.class);

            hasSetup = true;
        }
    }
}

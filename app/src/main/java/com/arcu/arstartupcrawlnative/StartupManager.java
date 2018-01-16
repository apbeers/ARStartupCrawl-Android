package com.arcu.arstartupcrawlnative;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andrew on 11/27/17.
 */

public class StartupManager {

    private boolean hasSetup = false;

    private Retrofit retrofit;

    private static StartupClient client;

    private ArrayList<Startup> startups = new ArrayList<>();

    public ArrayList<Startup> getStartups() {
        return startups;
    }

    public void setStartups(ArrayList<Startup> s) {
        this.startups = s;
    }

    public void addStartup(Startup s) {
        startups.add(s);
    }

    public int getCount() {
        return startups.size();
    }

    private static final StartupManager manager = new StartupManager();
    public static StartupManager getManager() {
        return manager;
    }

    public void refreshStartups(StartupMapFragment mapFragIn){
        if(!hasSetup){
            setupRetrofit();
        }
        final StartupMapFragment mapFrag = mapFragIn;

        Call<List<Startup>> call = client.getAllStartups();

        Log.e("STARTUPMANAGER:", "Trying to get Startups");

        call.enqueue(new Callback<List<Startup>>() {
            @Override
            public void onResponse(Call<List<Startup>> call, Response<List<Startup>> response) {
                startups.clear();
                startups.addAll(response.body());
                //recyclerView.getAdapter().notifyDataSetChanged();
                mapFrag.refreshStartups();

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

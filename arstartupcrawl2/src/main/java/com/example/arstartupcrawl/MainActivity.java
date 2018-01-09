package com.example.arstartupcrawl;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends WearableActivity {

    private TextView mTextView;
    private Button mButton;
    private boolean hasSetup = false;
    static Retrofit retrofit;
    static StartupClient client;
    static List<Startup> startups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        mButton = (Button) findViewById(R.id.button);

        // Enables Always-on
        setAmbientEnabled();
        refreshStartups();
    }

    public void refreshStartups(){
        if(!hasSetup){
            setupRetrofit();
        }

        Call<List<Startup>> call = client.getAllStartups();

        Log.e("STARTUPMANAGER:", "Trying to get Startups");

        call.enqueue(new Callback<List<Startup>>() {
            @Override
            public void onResponse(Call<List<Startup>> call, Response<List<Startup>> response) {
                startups.clear();
                startups.addAll(response.body());
                mTextView.setText(startups.get(0).getTitle());

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

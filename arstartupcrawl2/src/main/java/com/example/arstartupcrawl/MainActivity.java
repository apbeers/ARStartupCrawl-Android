package com.example.arstartupcrawl;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends WearableActivity implements View.OnClickListener {

    private TextView mTextView;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text);
        mButton = (Button) findViewById(R.id.button);

        mButton.setOnClickListener(this);

        // Enables Always-on
        setAmbientEnabled();
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this, StartupActivity.class);
        startActivity(i);
    }
}

package com.arcu.arstartupcrawlnative;

import android.content.Context;
import android.widget.Toast;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by shawn on 1/5/2018.
 */

public class PushNotification {
    String datetime, title, body, username; //username is the user who created the notification

    public PushNotification(String title, String body, String username) {
        this.datetime = new Date().toString();
        this.title = title;
        this.body = body;
        this.username = username;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void createStartupNotification(final Context context, StartupClient client){
        Call<PushNotification> call = client.addStartupNotification(new PushNotification(this.title, this.body, this.username));

        call.enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                if(response.body().getTitle() == "SUCCESS") {
                    Toast.makeText(context, "Startup Notification " + response.body().getDatetime() + " created Successfully.", Toast.LENGTH_LONG).show();
                }else if(response.body().getTitle() == "FAILED"){
                    Toast.makeText(context, "Startup Notification " + response.body().getDatetime() + " failed to create.", Toast.LENGTH_LONG).show();
                }else if(response.body().getTitle() == "PASSCODE_INCORRECT"){
                    Toast.makeText(context, "Startup Notification " + response.body().getDatetime() + " not created. INCORRECT PASSCODE", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Startup Notification " + response.body().getDatetime() + " not created.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(context, "ERROR: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createGuestNotification(final Context context, StartupClient client){
        Call<PushNotification> call = client.addGuestNotification(new PushNotification(this.title, this.body, this.username));

        call.enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                if(response.body().getTitle() == "SUCCESS") {
                    Toast.makeText(context, "Startup Notification " + response.body().getDatetime() + " created Successfully.", Toast.LENGTH_LONG).show();
                }else if(response.body().getTitle() == "FAILED"){
                    Toast.makeText(context, "Startup Notification " + response.body().getDatetime() + " failed to create.", Toast.LENGTH_LONG).show();
                }else if(response.body().getTitle() == "PASSCODE_INCORRECT"){
                    Toast.makeText(context, "Startup Notification " + response.body().getDatetime() + " not created. INCORRECT PASSCODE", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "Startup Notification " + response.body().getDatetime() + " not created.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(context, "ERROR: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

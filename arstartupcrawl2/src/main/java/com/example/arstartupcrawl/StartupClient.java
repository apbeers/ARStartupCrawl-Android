package com.example.arstartupcrawl;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by shawn on 1/7/2018.
 */

public interface StartupClient {
    String BASE_URL = "https://ar-startup-crawl.herokuapp.com/";

    @GET("notifications")
    Call<List<PushNotification>> getAllNotifications();

    @GET("notifications/guest")
    Call<List<PushNotification>> getGuestNotifications();

    @GET("notifications/startup")
    Call<List<PushNotification>> getStartupNotifications();

    @GET("startups/nopics")
    Call<List<Startup>> getAllStartupsNoPics();

    @PUT("notifications/guest")
    Call<PushNotification> addGuestNotification(@Body PushNotification notification);

    @PUT("notifications/startup")
    Call<PushNotification> addStartupNotification(@Body PushNotification notification);

    @PUT("startups")
    Call<Startup> addStartup(@Body Startup startup);

    @POST("startups")
    Call<Startup> updateStartup(@Body Startup startup);

    @DELETE("startups")
    Call<Startup> deleteStartup(@Body Startup startup);

}

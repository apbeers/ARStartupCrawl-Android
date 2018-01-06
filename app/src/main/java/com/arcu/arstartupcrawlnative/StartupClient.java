package com.arcu.arstartupcrawlnative;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by shawn on 1/5/2018.
 */

public interface StartupClient {
    String BASE_URL = "https://ar-startup-crawl.herokuapp.com/";

    @GET("notifications")
    Call<List<PushNotification>> getAllNotifications();

    @GET("notifications/guest")
    Call<List<PushNotification>> getGuestNotifications();

    @GET("notifications/startup")
    Call<List<PushNotification>> getStartupNotifications();

    @POST("notifications/guest/add")
    Call<PushNotification> addGuestNotification(@Body PushNotification notification);

    @POST("notifications/startup/add")
    Call<PushNotification> addStartupNotification(@Body PushNotification notification);

    //@GET("/users/{user}")
    //Call<List<Trip>> tripsForUser(@Path("user") String user);

    //Function calls
}

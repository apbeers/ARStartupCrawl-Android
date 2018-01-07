package com.arcu.arstartupcrawlnative;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.arcu.arstartupcrawlnative.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AnnouncementFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    static List<PushNotification> notificationList = new ArrayList<>();
    private Retrofit retrofit;
    static StartupClient client;
    boolean hasSetup = false;
    static View view;
    static RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AnnouncementFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AnnouncementFragment newInstance(int columnCount) {
        AnnouncementFragment fragment = new AnnouncementFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notificationList.add(new PushNotification("New", "new", "new"));

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_announcement_list, container, false);

        //notificationList = new List<PushNotification>();
        //notificationList.add(new PushNotification("New", "new", "new"));
        //getGuestNotifications();

        // Set the adapter
        if (view instanceof RecyclerView) {
            Log.e("AF, onCreateView:", "Inside: instance of recyclerview");
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                Log.e("AF, onCreateView:", "Inside: instance of recyclerview, if");
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                Log.e("AF, onCreateView:", "Inside: instance of recyclerview, else");
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyAnnouncementRecyclerViewAdapter(notificationList, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(PushNotification item);
    }

    public void getGuestNotifications(){
        Call<List<PushNotification>> call = client.getGuestNotifications();

        Log.e("GUESTNOTIFICATION:", "Trying to get GNotifications");

        call.enqueue(new Callback<List<PushNotification>>() {
            @Override
            public void onResponse(Call<List<PushNotification>> call, Response<List<PushNotification>> response) {
                notificationList.clear();
                notificationList.addAll(response.body());
                Log.e("GUESTNOTIFICATION:", "Received " + notificationList.size() + " Notifications. " + notificationList.get(0).getTitle());
                Log.e("GUESTNOTIFICATION:", "Received " + notificationList.size() + " Notifications. " + notificationList.get(1).getTitle());
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<PushNotification>> call, Throwable t) {
                Log.e("GUESTNOTIFICATION:", "Did not receive Guest Notifications from database");
            }
        });
    }

    public void refreshFragment(){
        getGuestNotifications();
    }

    public void setupRetrofit(){
        if(!hasSetup){
            retrofit = new Retrofit.Builder()
                    .baseUrl(StartupClient.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            client = retrofit.create(StartupClient.class);

            getGuestNotifications();

            hasSetup = true;
        }
    }
}

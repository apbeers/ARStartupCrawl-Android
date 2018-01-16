package com.arcu.arstartupcrawlnative;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        StartupMapFragment.OnFragmentInteractionListener,
        StartupsFragment.OnListFragmentInteractionListener,
        AnnouncementFragment.OnListFragmentInteractionListener,
        DashboardFragment.OnListFragmentInteractionListener {

    int lastMenuItemId = 10;
    private boolean view_startup, has_startup_permission, hasSetup = false;
    static List<Verification> verificationList = new ArrayList<>();
    static Verification verification;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    static MenuItem startupDash;
    private NavigationView navigationView;
    private StartupMapFragment startupMapFragment;
    private StartupsFragment startupsFragment;
    private AnnouncementFragment announcementFragment;
    private DashboardFragment dashboardFragment;
    private final FragmentManager fragmentManager = getFragmentManager();
    private StartupManager startupManager = StartupManager.getManager();
    static Retrofit retrofit;
    static StartupClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("STARTUP_PREF", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        view_startup = sharedPreferences.getBoolean("view_startup", false);
        has_startup_permission = sharedPreferences.getBoolean("has_startup_permission", false);

        Log.e("MA: ", "'view_startup' is " + view_startup);

        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        startupMapFragment = new StartupMapFragment();
        startupsFragment = new StartupsFragment();
        announcementFragment = new AnnouncementFragment();
        dashboardFragment = new DashboardFragment();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, startupMapFragment);
        fragmentTransaction.add(R.id.container, startupsFragment);
        fragmentTransaction.add(R.id.container, announcementFragment);
        fragmentTransaction.add(R.id.container, dashboardFragment);
        fragmentTransaction.commit();


        int menuItemIndex = 0;

        if (getIntent().getBooleanExtra("announcementFragment", false)) {
            menuItemIndex = 2;
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem menuItem = navigationView.getMenu().getItem(menuItemIndex);
        onNavigationItemSelected(menuItem);
        startupDash = navigationView.getMenu().getItem(3);
        startupDash.setVisible(view_startup);



        navigationView.getMenu().getItem(menuItemIndex).setChecked(true);

        startupManager.refreshStartups(startupMapFragment);
        startupMapFragment.refreshStartups();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if(has_startup_permission) {
                if (!view_startup) {
                    editor.putBoolean("view_startup", true);
                    editor.commit();
                    view_startup = true;
                    startupDash.setVisible(view_startup);
                    Toast.makeText(getApplicationContext(), "Startup Announcements ON", Toast.LENGTH_LONG).show();
                } else {
                    editor.putBoolean("view_startup", false);
                    editor.commit();
                    view_startup = false;
                    startupDash.setVisible(view_startup);
                    MenuItem menuItem = navigationView.getMenu().getItem(0);
                    onNavigationItemSelected(menuItem);
                    Toast.makeText(getApplicationContext(), "Startup Announcements OFF", Toast.LENGTH_LONG).show();
                }
            }else{
                showInputDialog();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (lastMenuItemId == id) {
            if (lastMenuItemId == navigationView.getMenu().getItem(1).getItemId() || lastMenuItemId == navigationView.getMenu().getItem(2).getItemId()) {
                lastMenuItemId = id;
            } else {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        } else {
            lastMenuItemId = id;
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (id == R.id.startup_map) {

            fragmentTransaction.show(startupMapFragment);
            fragmentTransaction.hide(startupsFragment);
            fragmentTransaction.hide(announcementFragment);
            fragmentTransaction.hide(dashboardFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.startup_list) {

            fragmentTransaction.hide(startupMapFragment);
            fragmentTransaction.show(startupsFragment);
            fragmentTransaction.hide(announcementFragment);
            fragmentTransaction.hide(dashboardFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.event_updates) {

            announcementFragment.refreshFragment();
            fragmentTransaction.hide(startupMapFragment);
            fragmentTransaction.hide(startupsFragment);
            fragmentTransaction.show(announcementFragment);
            fragmentTransaction.hide(dashboardFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.startup_dash) {

            dashboardFragment.refreshFragment();
            fragmentTransaction.hide(startupMapFragment);
            fragmentTransaction.hide(startupsFragment);
            fragmentTransaction.hide(announcementFragment);
            fragmentTransaction.show(dashboardFragment);
            fragmentTransaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onListFragmentInteraction(Startup item) {

    }


    @Override
    public void onListFragmentInteraction(PushNotification item) {

    }

    public SharedPreferences getLocalSharedPreferences(){
        return sharedPreferences;
    }

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.pin_input_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        final TextView textView = (TextView) promptView.findViewById(R.id.input_dialog_textview);
        final EditText editText = (EditText) promptView.findViewById(R.id.input_dialog_edittext);

        textView.setText("Input PIN for Startup Announcement Access");
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        verification = new Verification(editText.getText().toString(), "FAILED");
                        getPinVerification();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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

    public void getPinVerification(){
        if(!hasSetup){
            setupRetrofit();
        }
        Call<List<Verification>> call = client.verifyPin(verification);

        call.enqueue(new Callback<List<Verification>>() {
            @Override
            public void onResponse(Call<List<Verification>> call, Response<List<Verification>> response) {
                verificationList.clear();
                verificationList.addAll(response.body());
                Log.e("VERIFICATION:", verificationList.get(0).getUsername());
                if(verificationList.get(0).getUsername().equals("SUCCESS")){
                    editor.putBoolean("has_startup_permission", true);
                    editor.putBoolean("view_startup", true);
                    editor.commit();
                    has_startup_permission = true;
                    view_startup = true;
                    startupDash.setVisible(true);
                    Toast.makeText(getApplicationContext(), "Startup Announcements ON", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Failed to turn Startup Announcements ON", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Verification>> call, Throwable t) {
                Log.e("VERIFYPIN:", "Did not receive Verification from database");
            }
        });
    }

}

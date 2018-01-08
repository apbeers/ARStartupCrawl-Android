package com.arcu.arstartupcrawlnative;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Context;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.arcu.arstartupcrawlnative.dummy.DummyContent;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.view.View.VISIBLE;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        StartupMapFragment.OnFragmentInteractionListener,
        StartupsFragment.OnListFragmentInteractionListener,
        AnnouncementFragment.OnListFragmentInteractionListener,
        DashboardFragment.OnListFragmentInteractionListener {

    int lastMenuItemId = 10;
    private boolean view_startup;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    static MenuItem startupDash, optionsViewStartup;
    private NavigationView navigationView;
    private StartupMapFragment startupMapFragment;
    private StartupsFragment startupsFragment;
    private AnnouncementFragment announcementFragment;
    private DashboardFragment dashboardFragment;
    private final FragmentManager fragmentManager = getFragmentManager();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private StartupManager startupManager = StartupManager.getManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("STARTUP_PREF", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        view_startup = sharedPreferences.getBoolean("view_startup", false);

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
            if(!view_startup){
                editor.putBoolean("view_startup", true);
                editor.commit();
                view_startup = true;
                startupDash.setVisible(view_startup);
                Toast.makeText(getApplicationContext(), "Startup Notifications ON", Toast.LENGTH_LONG).show();
            }else{
                editor.putBoolean("view_startup", false);
                editor.commit();
                view_startup = false;
                startupDash.setVisible(view_startup);
                MenuItem menuItem = navigationView.getMenu().getItem(0);
                onNavigationItemSelected(menuItem);
                Toast.makeText(getApplicationContext(), "Startup Notifications OFF", Toast.LENGTH_LONG).show();
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

    public void refreshMaps(){

    }
}

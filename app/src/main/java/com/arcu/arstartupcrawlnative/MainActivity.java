package com.arcu.arstartupcrawlnative;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        StartupMapFragment.OnFragmentInteractionListener,
        StartupsFragment.OnListFragmentInteractionListener,
        SponsorsFragment.OnFragmentInteractionListener {

    int lastMenuItemId = 10;
    private NavigationView navigationView;
    private StartupMapFragment startupMapFragment;
    private StartupsFragment startupsFragment;
    private SponsorsFragment sponsorsFragment;
    private final FragmentManager fragmentManager = getFragmentManager();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private StartupManager startupManager = StartupManager.getManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.container, startupMapFragment);
        fragmentTransaction.add(R.id.container, startupsFragment);
        fragmentTransaction.commit();


        int menuItemIndex = 0;

        if (getIntent().getBooleanExtra("announcementFragment", false)) {
            menuItemIndex = 2;
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        MenuItem menuItem = navigationView.getMenu().getItem(menuItemIndex);
        onNavigationItemSelected(menuItem);
        navigationView.getMenu().getItem(menuItemIndex).setChecked(true);


        databaseReference.child("startups").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                startupManager.addStartup(dataSnapshot.getValue(Startup.class));
                startupMapFragment.refreshStartups();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

            if (sponsorsFragment != null) {
                fragmentTransaction.hide(sponsorsFragment);
            }

            fragmentTransaction.show(startupMapFragment);
            fragmentTransaction.hide(startupsFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.startup_list) {

            if (sponsorsFragment != null) {
                fragmentTransaction.hide(sponsorsFragment);
            }

            fragmentTransaction.hide(startupMapFragment);
            fragmentTransaction.show(startupsFragment);
            fragmentTransaction.commit();

        } else if (id == R.id.sponsors) {

            if (sponsorsFragment != null) {
                fragmentTransaction.show(sponsorsFragment);
            } else {
                sponsorsFragment = new SponsorsFragment();
                fragmentTransaction.add(R.id.container, sponsorsFragment);
            }

            fragmentTransaction.hide(startupMapFragment);
            fragmentTransaction.hide(startupsFragment);
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
}

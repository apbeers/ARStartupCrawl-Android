package com.arcu.arstartupcrawlnative;

import java.util.ArrayList;

/**
 * Created by andrew on 11/27/17.
 */

public class StartupManager {

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

}

package com.arcu.arstartupcrawlnative;

import java.util.ArrayList;

/**
 * Created by andrew on 11/28/17.
 */

public class AnnouncementManager {

    private ArrayList<Announcement> announcements = new ArrayList<>();

    public ArrayList<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setStartups(ArrayList<Announcement> s) {
        this.announcements = s;
    }

    public void addStartup(Announcement s) {
        announcements.add(s);
    }

    public int getCount() {
        return announcements.size();
    }

    private AnnouncementManager() {
        System.out.print("announcement constructor");
    }

    private static final AnnouncementManager manager = new AnnouncementManager();
    public static AnnouncementManager getManager() {
        return manager;
    }
}

package com.arcu.arstartupcrawlnative;

/**
 * Created by shawn on 1/8/2018.
 */

public class Verification {
    private String passcode, username;

    public Verification(String passcode, String username){
        this.passcode = passcode;
        this.username = username;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

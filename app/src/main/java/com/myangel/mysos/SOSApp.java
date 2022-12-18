package com.myangel.mysos;

import android.app.Application;
import android.location.Location;

public class SOSApp extends Application {
    private static SOSApp instance;

    public Location currentLocation;

    public static SOSApp getInstance(){
        return instance;
    }

    public SOSApp(){
        instance = this;
    }

}

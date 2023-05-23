package com.androbohij;

public class Preferences {
    static double lat = 0;
    static double lon = 0;
    static boolean autoLoc = true;
    static int theme = 0;
    static int unit = 0;
    static int refreshTime = 0;

    public static void setLat(double lat) {
        Preferences.lat = lat;
    }
    public static void setLon(double lon) {
        Preferences.lon = lon;
    }
    public static void setAutoLoc(boolean autoLoc) {
        Preferences.autoLoc = autoLoc;
    }
    public static void setTheme(int theme) {
        Preferences.theme = theme;
    }
    public static void setUnit(int unit) {
        Preferences.unit = unit;
    }
    public static void setRefreshTime(int refreshTime) {
        Preferences.refreshTime = refreshTime;
    }
    
    public static double getLat() {
        return lat;
    }
    public static double getLon() {
        return lon;
    }
    public static boolean getAutoLoc() {
        return autoLoc;
    }
    public static int getTheme() {
        return theme;
    }
    public static int getUnit() {
        return unit;
    }
}

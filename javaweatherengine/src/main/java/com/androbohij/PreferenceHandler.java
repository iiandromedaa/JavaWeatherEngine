package com.androbohij;

public class PreferenceHandler {
    static double lat = getLat();
    static double lon = getLon();
    static boolean autoLoc = getAutoLoc();
    static int theme = getTheme();
    static int unit = getUnit();
    static int refreshTime = getRefreshTime();

    public static void setLat(double lat) {
        App.prefs.putDouble("lat", lat);
    }
    public static void setLon(double lon) {
        App.prefs.putDouble("lon", lon);
    }
    public static void setAutoLoc(boolean autoLoc) {
        App.prefs.putBoolean("autoLoc", autoLoc);
    }
    public static void setTheme(int theme) {
        App.prefs.putInt("theme", theme);
    }
    public static void setUnit(int unit) {
        App.prefs.putInt("unit", unit);
    }
    public static void setRefreshTime(int refreshTime) {
        App.prefs.putInt("refreshTime", refreshTime);
    }
    
    public static double getLat() {
        return App.prefs.getDouble("lat", 0);
    }
    public static double getLon() {
        return App.prefs.getDouble("lon", 0);
    }
    public static boolean getAutoLoc() {
        return App.prefs.getBoolean("autoLoc", true);
    }
    public static int getTheme() {
        return App.prefs.getInt("theme", 0);
    }
    public static int getUnit() {
        return App.prefs.getInt("unit", 0);
    }
    public static int getRefreshTime() {
        return App.prefs.getInt("refreshTime", 0);
    }
}

package com.androbohij.javaweatherengine;

/**
 * Hello cruel world!
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Program Entrance");
        new SplashScreen();
        String lat = "44.986";
        String lon = "-93.258";
        Steve steve = new Steve();
        // System.out.println(steve.getDates()[0]);
        new Thread(() -> {
            System.out.println(steve.getThirty(lat, lon, steve.getDates()[1], steve.getDates()[0]));
        }).start();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                MainWindow winder = new MainWindow();
                System.out.println("Window Setup");
                winder.createWindow();
            }
        });
    }
}

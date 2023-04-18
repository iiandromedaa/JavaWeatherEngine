package com.androbohij.javaweatherengine;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Program Entrance");
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                mainWindow winder = new mainWindow();
                System.out.println("Window Setup");
                winder.createWindow();
            }
        });
    }
}

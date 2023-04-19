package com.androbohij.javaweatherengine;

import javax.swing.JFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("Program Entrance\n\n\n\n\n\n\n\n\n\n\n\n");
        JFrame jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setBounds(200, 200, 620, 300);
        SplashScreen splishsplash = new SplashScreen();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run(){
                mainWindow winder = new mainWindow();
                System.out.println("Window Setup");
                winder.createWindow();
            }
        });
    }
}

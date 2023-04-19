package com.androbohij.javaweatherengine;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.util.TimerTask;

public class SplashScreen extends JFrame {

    private JPanel contentPane;

    public SplashScreen() {
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	setUndecorated(true);
    	setResizable(false);
        setBounds(100,100, 620, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel splashImage = new JLabel("");
        splashImage.setIcon(new ImageIcon(SplashScreen.class.getResource("/com/androbohij/javaweatherengine/images/splash.png")));
        splashImage.setBounds(0, 0, 620, 300);
        contentPane.add(splashImage);

        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setVisible(true);
        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                dispose();
            }
        }, 5000);*/
        new Timer(3_000, (e) -> { dispose(); }).start();
    }
}

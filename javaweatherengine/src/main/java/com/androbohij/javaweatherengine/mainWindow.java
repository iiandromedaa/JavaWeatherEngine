package com.androbohij.javaweatherengine;

import java.awt.*;
import javax.swing.*;
public class mainWindow {
    public void createWindow() {
        JFrame window = new JFrame("Java Weather Engine");
        //window.setIconImage(new ImageIcon(iconhere).getImage());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(600, 400));
        window.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        window.pack();
        window.setVisible(true);
    }
}

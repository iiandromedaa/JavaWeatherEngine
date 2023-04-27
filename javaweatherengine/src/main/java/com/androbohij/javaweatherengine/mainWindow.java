package com.androbohij.javaweatherengine;

import java.awt.*;
import javax.swing.*;
public class MainWindow {
    /**
     * @wbp.parser.entryPoint
     */
    public void createWindow() {
        JFrame window = new JFrame("Java Weather Engine");
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/com/androbohij/javaweatherengine/images/icon.png")));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emptyLabel = new JLabel("");
        emptyLabel.setPreferredSize(new Dimension(1280, 720));
        window.getContentPane().add(emptyLabel, BorderLayout.CENTER);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        window.setVisible(false);
        new Timer(3_000, (e) -> { window.setVisible(true); }).start();
    }
}

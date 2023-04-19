package com.androbohij.javaweatherengine;
import java.util.TimerTask;

import javax.swing.JFrame;

abstract class MyTimerTask extends TimerTask  {
    JFrame myparam;

    public MyTimerTask(JFrame param) {
        myparam = param;
    }

    @Override
    public void run() {
        myparam.setVisible(true);
    }
}

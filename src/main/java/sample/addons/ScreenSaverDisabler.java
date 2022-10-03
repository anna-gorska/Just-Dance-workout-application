package sample.addons;

import javafx.scene.Cursor;
import javafx.scene.Scene;

import java.applet.Applet;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Moves the mouse cursor once in a minute to prevent the screen saver from
 * kicking in.
 * Source of the code: https://stackoverflow.com/questions/12341921/disable-screen-saver-sleep-mode-through-a-website,
 * Author: Petr Janeek
 * */
public class ScreenSaverDisabler extends Applet {

    private static final int PERIOD = 5;
    private Timer screenSaverDisabler;
    private Scene scene;

    @Override
    public void start() {
        screenSaverDisabler = new Timer();
        screenSaverDisabler.scheduleAtFixedRate(new TimerTask() {
            Robot r = null;
            {
                try {
                    r = new Robot();
                } catch (AWTException headlessEnvironmentException) {
                    screenSaverDisabler.cancel();
                }
            }
            @Override
            public void run() {
                Point loc = MouseInfo.getPointerInfo().getLocation();
                    r.mouseMove(loc.x + 1, loc.y);
                    r.mouseMove(loc.x, loc.y);
                if (scene!=null) {
                    scene.setCursor(Cursor.NONE);
                }
            }
        }, 0, PERIOD*1000);
    }


    @Override
    public void stop() {
        screenSaverDisabler.cancel();
    }

    public ScreenSaverDisabler(Scene scene){
        this.scene=scene;
    }
    public ScreenSaverDisabler(){}


}

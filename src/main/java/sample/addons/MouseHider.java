package sample.addons;

import javafx.animation.PauseTransition;
import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.awt.*;

/*
Class that hides mouse after certain time of inactivity
 */

public class MouseHider {
    private Scene scene;
    private int duration;

    public MouseHider(Scene scene, int duration) {
        this.scene = scene;
        this.duration = duration;
    }

    public void hideMouse() {
        PauseTransition idle = new PauseTransition(Duration.seconds(duration));
        Point loc = MouseInfo.getPointerInfo().getLocation();
        idle.setOnFinished(e -> scene.setCursor(Cursor.NONE));
        scene.addEventHandler(Event.ANY, e -> {
            Point loc2 = MouseInfo.getPointerInfo().getLocation();
            double mouseDelta = Math.abs(loc.x-loc2.x);
            if (mouseDelta>1) {
                idle.playFromStart();
                scene.setCursor(Cursor.DEFAULT);
            }
            loc.x=loc2.x;
        });
    }

}

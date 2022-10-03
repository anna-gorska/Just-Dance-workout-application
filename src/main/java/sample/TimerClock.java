package sample;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

class TimerClock extends BorderPane {
    // Time shown by the timer
    private int hour;
    private int minute;
    private int second;
    private Text text = new Text();
    private Timeline animation;
    boolean shouldIPlay = true; // make false if "pause" button is clicked

    /**
     * Constructor method for a timer
     */
    TimerClock() {
        setPadding(new Insets(5, 15, 5, 15));
        clear();

        Font Tungsten =
                Font.loadFont(getClass()
                        .getResourceAsStream("/Graphics/Tungsten-Bold.ttf"), 70);
        text.setFont(Tungsten);
        text.setFill(Color.WHITESMOKE);
        text.setOpacity(0.7);
        text.setTextAlignment(TextAlignment.LEFT);
        setBottom(text);

        animation = new Timeline(
                new KeyFrame(Duration.millis(1000), e -> run()));
        animation.setCycleCount(Timeline.INDEFINITE);

        this.play();
    }

    /**
     * Play animation
     */
    private void play() {
        if (shouldIPlay)
            animation.play();
    }

    /**
     * Animate stopwatch
     */
    private void run() {
        if (shouldIPlay) {
            if (minute == 59)
                hour = hour + 1;

            if (second == 59)
                minute = minute + 1;

            second = second < 59 ? second + 1 : 0;

            text.setText(getTime());
        }
    }

    /**
     * Reset stopwatch
     */
    private void clear() {
        hour = 0;
        minute = 0;
        second = 0;
        text.setText(getTime());
    }

    /**
     * Return time as a string
     */
    private String getTime() {
        return pad(hour) + ":" + pad(minute) + ":" + pad(second);
    }

    /**
     * Return n as padded string
     */
    private String pad(int n) {
        return n < 10 ? "0" + n : n + "";
    }

}
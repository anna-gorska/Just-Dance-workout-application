package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import sample.addons.ScreenSaverDisabler;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {


            GUI app = new GUI();
            Scene scene = new Scene(app, 1280, 700);

            // Add a screen saver disabler that will prevent screen from going into sleep mode
            ScreenSaverDisabler screenSaverDisabler = new ScreenSaverDisabler();
            screenSaverDisabler.start();

            primaryStage.setScene(scene);
            primaryStage.setFullScreenExitHint("");
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            primaryStage.setTitle("JUST DANCE");


            primaryStage.setFullScreen(true);

            primaryStage.show();
        }
        catch (Exception e) {
            // prints line numbers + call stack
            e.printStackTrace();

        }
    }
    @Override
    public void stop() {
        System.exit(0);
    }



    public static void main(String[] args) {
        launch(args);
    }
}
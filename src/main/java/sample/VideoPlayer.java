package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import sample.addons.MouseHider;
import sample.addons.ScreenSaverDisabler;
import sample.playlistUtils.SongStats;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

class VideoPlayer {
    private Stage stage;
    private TimerClock timer;
    private MediaView mediaView;
    private BorderPane root;
    private MediaPlayer mediaPlayer;
    private ArrayList<File> fullPlaylist;
    private ArrayList<SongStats> songStatsSongs;
    private int i;
    private int numberSongsPlayed = 0;
    private GUI gui;


    /**
     * Constructor for video player
     *
     * @param songs    - list of songs
     */
    VideoPlayer(ArrayList<File> songs, ArrayList<SongStats> songStatsSongs, GUI gui) {
        this.fullPlaylist = songs;
        for (File song : songs) {
            System.out.println(song.getName() + " Video Player fullPlaylist");
        }
        this.gui = gui;
        this.i = gui.getSongs_i();
        timer = new TimerClock();
        this.songStatsSongs = songStatsSongs;

    }

    private MediaPlayer error_player(Media media) {
        MediaPlayer mediaPlayer1 = new MediaPlayer(media);
        mediaPlayer1.setOnError(() -> {
            System.out.println("here we handle error <44444");

            if (stage != null) {
                stage.close();
                stage = null;
            }
            if (root != null) {
                root = null;
                root = new BorderPane();
            }
            setStage();
            try {
                initMediaPlayer(mediaView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return mediaPlayer1;
    }

    void startVideoPlayer() throws IOException {
        mediaView = createMediaView();
        DoubleProperty mvw = mediaView.fitWidthProperty();
        DoubleProperty mvh = mediaView.fitHeightProperty();
        mvw.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        mvh.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
        mediaView.setPreserveRatio(true);

        // Setting the pane with media player, timer, and stop and pause buttons
        root = new BorderPane();
        root.getChildren().add(mediaView);

        root.setBottom(timer);
        Button pause = pauseButton();

        Button exit = exitButton();

        VBox pauseBox = new VBox();
        pauseBox.getChildren().add(pause);
        pauseBox.setAlignment(Pos.CENTER);
        VBox exitBox = new VBox();
        exitBox.getChildren().add(exit);
        exitBox.setAlignment(Pos.CENTER);
        root.setRight(pauseBox);
        root.setLeft(exitBox);

        VBox nextSongBox = new VBox();
        Button nextSong = nextSong();
        nextSongBox.getChildren().add(nextSong);
        nextSongBox.setAlignment(Pos.CENTER);


        VBox previousSongBox = new VBox();
        Button previousSong = previousSong();
        previousSongBox.getChildren().add(previousSong);
        previousSongBox.setAlignment(Pos.CENTER);

        HBox playlistControl = new HBox();
        playlistControl.getChildren().add(previousSongBox);
        playlistControl.getChildren().add(nextSongBox);
        playlistControl.setAlignment(Pos.TOP_CENTER);
        root.setTop(playlistControl);


        setStage();


    }

    private void setStage() {
        // Setting up the scene and popup stage
        stage = new Stage();
        Scene scene = new Scene(root, 1280, 700);
        // Add a functionality where mouse is hidden after certain time of inactivity
        MouseHider mouseHider = new MouseHider(scene, 3);
        mouseHider.hideMouse();
        // Add a screen saver disabler that will prevent screen from going into sleep mode
        ScreenSaverDisabler screenSaverDisabler = new ScreenSaverDisabler(scene);
        screenSaverDisabler.start();
        // MouseHiderAndScreenSaverDisabler mouseHiderAndScreenSaverDisabler = new MouseHiderAndScreenSaverDisabler(scene, 3);
        //   mouseHiderAndScreenSaverDisabler.apply();
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    /**
     * Method that creates a Media View object with songs to play
     *
     * @return a media view object
     */
    private MediaView createMediaView() {
        MediaView mediaView = new MediaView();
        while (true) {
            try {
                initMediaPlayer(mediaView);
                break;
            } catch (Exception e) {
                System.out.println("Media View exception"); // This ugly solution is due to bugged JavaFX implementation
            }
        }

        return mediaView;
    }

    private MediaPlayer createMediaPlayer(Media media) {
        MediaPlayer media_player;
        while (true) {
            try {
                System.out.println("trying to play media"); // This ugly solution is due to bugged JavaFX implementation
                media_player = new MediaPlayer(media);
                media_player.setAutoPlay(true);
                break;
            } catch (Exception e) {
                System.out.println("media_player error"); // This ugly solution is due to bugged JavaFX implementation
            }
        }
        return media_player;
    }

    /**
     * Method that controls how long to play which songs in a media player
     *
     * @param mediaView a media view object
     */
    private void initMediaPlayer(final MediaView mediaView) throws Exception {

        if (i < fullPlaylist.size()) {
            System.out.println("Now playing: " + fullPlaylist.get(i).toURI().toString());
            System.out.println(fullPlaylist.get(i));
            Media media = new Media(fullPlaylist.get(i).toURI().toString());
            mediaPlayer = createMediaPlayer(media); //new MediaPlayer(new Media(songs.get(i++).toURI().toString()));}

            mediaPlayer.setAutoPlay(true);

            mediaPlayer.setOnError(() -> {
                try {

                    mediaPlayer = error_player(media);
                    initMediaPlayer(mediaView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            mediaPlayer.setOnEndOfMedia(() -> {
                try {
                    if (!fullPlaylist.get(i).getName().equals("0.mp4") && !fullPlaylist.get(i).getName().equals("1.mp4") && !fullPlaylist.get(i).getName().equals("2.mp4") && !fullPlaylist.get(i).getName().equals("3.mp4") && !fullPlaylist.get(i).getName().equals("4.mp4")) {
                        numberSongsPlayed++;
                    }
                    i++;
                    gui.setSongs_i(i);
                    initMediaPlayer(mediaView);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Error here :(");
                }
            });

            System.out.println(mediaPlayer.getStatus());
            mediaView.setMediaPlayer(mediaPlayer);
            System.out.println(mediaPlayer.getStatus());
        } else {
            updatePlaylist();
            stage.close();

        }


    }

    /**
     * Method that creates a pause button that pauses both video and timer
     *
     * @return a pause button
     * @throws FileNotFoundException - if button image hasn't been found
     */
    private Button pauseButton() throws FileNotFoundException {
        Image imageButton = new Image(new FileInputStream("src/main/resources/Graphics/pause.png"));
        Button button1 = new Button();

        BackgroundImage backgroundImage = new BackgroundImage(imageButton, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        Image imageButton1 = new Image(new FileInputStream("src/main/resources/Graphics/transparent.png"));
        BackgroundImage backgroundImage1 = new BackgroundImage(imageButton1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background1 = new Background(backgroundImage1);

        button1.setBackground(background1);
        button1.setPrefSize(imageButton.getWidth(), imageButton.getHeight());
        button1.setOnAction(e -> {
            if (timer.shouldIPlay) {
                timer.shouldIPlay = false;
                mediaPlayer.pause();
            } else {
                timer.shouldIPlay = true;
                mediaPlayer.play();

            }

        });
        button1.setOnMouseEntered(e -> button1.setBackground(background));
        button1.setOnMouseExited(e -> button1.setBackground(background1));
        return button1;
    }

    private Button nextSong() throws FileNotFoundException {
        Image imageButton = new Image(new FileInputStream("src/main/resources/Graphics/next.png"));
        Button button1 = new Button();

        BackgroundImage backgroundImage = new BackgroundImage(imageButton, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        Image imageButton1 = new Image(new FileInputStream("src/main/resources/Graphics/transparent.png"));
        BackgroundImage backgroundImage1 = new BackgroundImage(imageButton1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background1 = new Background(backgroundImage1);

        button1.setBackground(background1);
        button1.setPrefSize(imageButton.getWidth(), imageButton.getHeight());
        button1.setOnAction(e -> {
            if (i < fullPlaylist.size() - 1 && i >= 0) {
                mediaPlayer.stop();
                mediaPlayer.pause();
                stage.close();
                stage.show();
                i++;
                try {
                    initMediaPlayer(mediaView);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        button1.setOnMouseEntered(e -> button1.setBackground(background));
        button1.setOnMouseExited(e -> button1.setBackground(background1));
        return button1;
    }

    private Button previousSong() throws FileNotFoundException {
        Image imageButton = new Image(new FileInputStream("src/main/resources/Graphics/previous.png"));
        Button button1 = new Button();

        BackgroundImage backgroundImage = new BackgroundImage(imageButton, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        Image imageButton1 = new Image(new FileInputStream("src/main/resources/Graphics/transparent.png"));
        BackgroundImage backgroundImage1 = new BackgroundImage(imageButton1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background1 = new Background(backgroundImage1);


        button1.setBackground(background1);
        button1.setPrefSize(imageButton.getWidth(), imageButton.getHeight());
        button1.setOnAction(e -> {
            if (i < fullPlaylist.size() && i >= 0) {
                mediaPlayer.stop();
                mediaPlayer.pause();
                stage.close();
                stage.show();
                if (i > 0) {
                    i--;
                }
                try {
                    initMediaPlayer(mediaView);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        button1.setOnMouseEntered(e -> button1.setBackground(background));
        button1.setOnMouseExited(e -> button1.setBackground(background1));
        return button1;
    }

    /**
     * A method that creates an exit button that exists the stage
     *
     * @return exit button
     * @throws FileNotFoundException e
     */
    private Button exitButton() throws FileNotFoundException {
        Image imageButton = new Image(new FileInputStream("src/main/resources/Graphics/exit.png"));
        Button button1 = new Button();

        BackgroundImage backgroundImage = new BackgroundImage(imageButton, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        Image imageButton1 = new Image(new FileInputStream("src/main/resources/Graphics/transparent.png"));
        BackgroundImage backgroundImage1 = new BackgroundImage(imageButton1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background1 = new Background(backgroundImage1);

        button1.setBackground(background1);
        button1.setPrefSize(imageButton.getWidth(), imageButton.getHeight());
        button1.setOnAction(e -> {
            mediaPlayer.stop();
            mediaPlayer.pause();
            stage.close();
            try {
                updatePlaylist();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });

        button1.setOnMouseEntered(e -> button1.setBackground(background));
        button1.setOnMouseExited(e -> button1.setBackground(background1));
        return button1;
    }

    private void updatePlaylist() throws IOException {
        //for (int j = 0; j < songStatsSongs.size(); j++) {
        for (int j = 0; j < numberSongsPlayed; j++) {
            String oldSongInfo = songStatsSongs.get(j).saveSongToString();
            songStatsSongs.get(j).increaseNumberPlayed();
            songStatsSongs.get(j).updateTextFileWithNewNumberPlayed(oldSongInfo);
        }
    }


}


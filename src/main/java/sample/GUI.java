package sample;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.*;

import sample.playlistUtils.*;
import sample.workoutDetails.WorkoutDuration;
import sample.workoutDetails.WorkoutType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

class GUI extends BorderPane {
    private WorkoutType workoutType;
    private WorkoutDuration workoutDuration;
    private HBox vBox;
    private GridPane pane;
    private TextField playlistSeed;
    private ArrayList<SongStats> playlistSongs;
    private int songs_i = 0;
    private ArrayList<SongStats> allSongs;
    private String seed;
    private ArrayList<File> finalPlaylist;


    GUI() throws IOException {

        Image image = new Image("Graphics/Background2.png", true);
        BackgroundImage bgImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, true, true)
        );
        this.setBackground(new Background(bgImage));
        VBox hBox = new VBox();
        vBox = new HBox();

        vBox.getChildren().add(handButton());
        vBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(vBox);

        hBox.setAlignment(Pos.CENTER);
        this.setCenter(hBox);

    }



    private Button handButton() throws FileNotFoundException {

        Image imageButton = new Image(new FileInputStream("src\\main\\resources\\Graphics\\ButtonArea.png"));
        final Button button1 = new Button();

        BackgroundImage backgroundImage = new BackgroundImage(imageButton, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        button1.setBackground(background);
        button1.setPrefSize(imageButton.getHeight(), imageButton.getWidth());

        button1.setOnAction(e -> {
            vBox.getChildren().remove(button1);
            // Read all songs
            SongStatsArray songStatsArray = new SongStatsArray();
            try {
                allSongs = songStatsArray.readAllSongs();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            try {
                for (Button button : GUI.this.workoutButtons()) {
                    vBox.getChildren().add(button);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });

        return button1;
    }

    private ArrayList<Button> workoutButtons() throws FileNotFoundException {
        Image imageButton1 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\aero.png"));
        Image imageButton2 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\kick.png"));
        Image imageButton3 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\nowork.png"));
        Image imageButton4 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\aero_text.png"));
        Image imageButton5 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\kick_text.png"));
        Image imageButton6 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\nowork_text.png"));
        Image imageButton7 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\sixties.png"));
        Image imageButton8 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\sixties_text.png"));
        Image imageButton9 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\latin.png"));
        Image imageButton10 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\latin_text.png"));
        final Button button1 = new Button();
        final Button button2 = new Button();
        final Button button3 = new Button();
        final Button button4 = new Button();
        final Button button5 = new Button();

        BackgroundImage backgroundImage1 = new BackgroundImage(imageButton1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        final Background background1 = new Background(backgroundImage1);
        BackgroundImage backgroundImage2 = new BackgroundImage(imageButton2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        final Background background2 = new Background(backgroundImage2);
        BackgroundImage backgroundImage3 = new BackgroundImage(imageButton3, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        final Background background3 = new Background(backgroundImage3);
        BackgroundImage backgroundImage4 = new BackgroundImage(imageButton4, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        final Background background4 = new Background(backgroundImage4);
        BackgroundImage backgroundImage5 = new BackgroundImage(imageButton5, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        final Background background5 = new Background(backgroundImage5);
        BackgroundImage backgroundImage6 = new BackgroundImage(imageButton6, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        final Background background6 = new Background(backgroundImage6);
        BackgroundImage backgroundImage7 = new BackgroundImage(imageButton7, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        final Background background7 = new Background(backgroundImage7);
        BackgroundImage backgroundImage8 = new BackgroundImage(imageButton8, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        final Background background8 = new Background(backgroundImage8);
        BackgroundImage backgroundImage9 = new BackgroundImage(imageButton9, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        final Background background9 = new Background(backgroundImage9);
        BackgroundImage backgroundImage10 = new BackgroundImage(imageButton10, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        final Background background10 = new Background(backgroundImage10);
        button1.setBackground(background1);
        button1.setPrefSize(imageButton1.getHeight(), imageButton1.getWidth());
        button2.setBackground(background2);
        button2.setPrefSize(imageButton2.getHeight(), imageButton2.getWidth());
        button3.setBackground(background3);
        button3.setPrefSize(imageButton3.getHeight(), imageButton3.getWidth());
        button4.setBackground(background7);
        button4.setPrefSize(imageButton7.getHeight(), imageButton7.getWidth());
        button5.setBackground(background9);
        button5.setPrefSize(imageButton9.getHeight(), imageButton9.getWidth());


        button1.setOnAction(e -> {
            vBox.getChildren().remove(button1);
            vBox.getChildren().remove(button2);
            vBox.getChildren().remove(button3);
            vBox.getChildren().remove(button4);
            vBox.getChildren().remove(button5);
            pane = new GridPane();
            workoutType = WorkoutType.AEROBICS;
            try {
                for (int i = 0; i < GUI.this.timeButtons().size() - 1; i++) {
                    pane.add(GUI.this.timeButtons().get(i), i, 0);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            try {
                pane.add(GUI.this.timeButtons().get(GUI.this.timeButtons().size() - 1), 0, GUI.this.timeButtons().size() - 1);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            vBox.getChildren().add(pane);
        });
        button1.setOnMouseEntered(e -> button1.setBackground(background4));
        button1.setOnMouseExited(e -> button1.setBackground(background1));
        button2.setOnAction(e -> {
            vBox.getChildren().remove(button1);
            vBox.getChildren().remove(button2);
            vBox.getChildren().remove(button3);
            vBox.getChildren().remove(button4);
            vBox.getChildren().remove(button5);
            pane = new GridPane();
            workoutType = WorkoutType.KICKBOXING;
            try {
                for (int i = 0; i < GUI.this.timeButtons().size() - 1; i++) {
                    pane.add(GUI.this.timeButtons().get(i), i, 0);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            try {
                pane.add(GUI.this.timeButtons().get(GUI.this.timeButtons().size() - 1), 0, GUI.this.timeButtons().size() - 1);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            vBox.getChildren().add(pane);
        });
        button2.setOnMouseEntered(e -> button2.setBackground(background5));
        button2.setOnMouseExited(e -> button2.setBackground(background2));
        button4.setOnAction(e -> {
            vBox.getChildren().remove(button1);
            vBox.getChildren().remove(button2);
            vBox.getChildren().remove(button3);
            vBox.getChildren().remove(button4);
            vBox.getChildren().remove(button5);
            pane = new GridPane();
            workoutType = WorkoutType.SIXTIES;
            try {
                for (int i = 0; i < GUI.this.timeButtons().size() - 1; i++) {
                    pane.add(GUI.this.timeButtons().get(i), i, 0);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            try {
                pane.add(GUI.this.timeButtons().get(GUI.this.timeButtons().size() - 1), 0, GUI.this.timeButtons().size() - 1);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            vBox.getChildren().add(pane);
        });
        button4.setOnMouseEntered(e -> button4.setBackground(background8));
        button4.setOnMouseExited(e -> button4.setBackground(background7));
        button5.setOnAction(e -> {
            vBox.getChildren().remove(button1);
            vBox.getChildren().remove(button2);
            vBox.getChildren().remove(button3);
            vBox.getChildren().remove(button4);
            vBox.getChildren().remove(button5);
            pane = new GridPane();
            workoutType = WorkoutType.LATIN;
            try {
                for (int i = 0; i < GUI.this.timeButtons().size() - 1; i++) {
                    pane.add(GUI.this.timeButtons().get(i), i, 0);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            try {
                pane.add(GUI.this.timeButtons().get(GUI.this.timeButtons().size() - 1), 0, GUI.this.timeButtons().size() - 1);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            vBox.getChildren().add(pane);
        });
        button5.setOnMouseEntered(e -> button5.setBackground(background10));
        button5.setOnMouseExited(e -> button5.setBackground(background9));
        button3.setOnAction(e -> {
            vBox.getChildren().remove(button1);
            vBox.getChildren().remove(button2);
            vBox.getChildren().remove(button3);
            vBox.getChildren().remove(button4);
            vBox.getChildren().remove(button5);
            pane = new GridPane();
            workoutType = WorkoutType.JUST_SONGS;

            try {
                for (int i = 0; i < GUI.this.timeButtons().size() - 1; i++) {
                    pane.add(GUI.this.timeButtons().get(i), i, 0);
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            try {
                pane.add(GUI.this.timeButtons().get(GUI.this.timeButtons().size() - 1), 0, GUI.this.timeButtons().size() - 1);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            vBox.getChildren().add(pane);
        });
        button3.setOnMouseEntered(e -> button3.setBackground(background6));
        button3.setOnMouseExited(e -> button3.setBackground(background3));
        ArrayList<Button> buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button5);
        buttons.add(button4);
        buttons.add(button3);

        return buttons;

    }


    private ArrayList<Button> timeButtons() throws FileNotFoundException {
        Image imageButton1 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\10min.png"));
        Image imageButton2 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\25min.png"));
        Image imageButton3 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\45min.png"));
        Image imageButton4 = new Image(new FileInputStream("src\\main\\resources\\Graphics\\infinity.png"));

        Button button1 = new Button();
        Button button2 = new Button();
        Button button3 = new Button();
        Button button4 = new Button();

        BackgroundImage backgroundImage1 = new BackgroundImage(imageButton1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background1 = new Background(backgroundImage1);
        BackgroundImage backgroundImage2 = new BackgroundImage(imageButton2, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background2 = new Background(backgroundImage2);
        BackgroundImage backgroundImage3 = new BackgroundImage(imageButton3, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background3 = new Background(backgroundImage3);
        BackgroundImage backgroundImage4 = new BackgroundImage(imageButton4, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background4 = new Background(backgroundImage4);

        button1.setBackground(background1);
        button1.setPrefSize(imageButton1.getHeight(), imageButton1.getWidth());
        button2.setBackground(background2);
        button2.setPrefSize(imageButton2.getHeight(), imageButton2.getWidth());
        button3.setBackground(background3);
        button3.setPrefSize(imageButton3.getHeight(), imageButton3.getWidth());
        button4.setBackground(background4);
        button4.setPrefSize(imageButton4.getHeight(), imageButton3.getWidth());

        button1.setOnAction(e -> {
            workoutDuration = WorkoutDuration.TEN;
            vBox.getChildren().remove(pane);
            pane = new GridPane();
            VBox seedButtonBox = new VBox();

            SongStats[] allSongsArr = new SongStats[allSongs.size()];
            allSongsArr = allSongs.toArray(allSongsArr);
            IntegerLinearProgramming ilp = new IntegerLinearProgramming(allSongsArr, workoutDurationToSeconds(), workoutType);
            int[] songsIdx = ilp.ILPModel();
            SongStats[] playlistSongs = ilp.chosenSongs(songsIdx);
            ArrayList<SongStats> playlistArray = new ArrayList<>(Arrays.asList(playlistSongs));
            for (SongStats songStats : playlistArray){
                System.out.println(songStats.saveSongToString() + " <3 playlist arr");
            }
            PlaylistCreator creator = new PlaylistCreator(workoutType, playlistArray);
            finalPlaylist = creator.createFullPlaylist();
            for (File file : finalPlaylist){
                System.out.println(file + " <-- FinalPlaylist GUI");
            }
            // Find seed
            SeedFinder seedFinder = new SeedFinder(allSongs, playlistArray, workoutDuration);
            try {
                seed = seedFinder.findSeed();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            playlistSeed = new TextField(seed);

            try {
                seedButtonBox.getChildren().add(startButton());
                seedButtonBox.getChildren().add(playlistSeed);
                seedButtonBox.getChildren().add(backButton(timeButtons()));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            pane.add(seedButtonBox,0,0);
            vBox.getChildren().add(pane);



        });
        button2.setOnAction(e -> {
            workoutDuration = WorkoutDuration.TWENTY_FIVE;
            vBox.getChildren().remove(pane);
            pane = new GridPane();
            VBox seedButtonBox = new VBox();

            SongStats[] allSongsArr = new SongStats[allSongs.size()];
            allSongsArr = allSongs.toArray(allSongsArr);
            IntegerLinearProgramming ilp = new IntegerLinearProgramming(allSongsArr, workoutDurationToSeconds(), workoutType);
            int[] songsIdx = ilp.ILPModel();
            SongStats[] playlistSongs = ilp.chosenSongs(songsIdx);
            ArrayList<SongStats> playlistArray = new ArrayList<>(Arrays.asList(playlistSongs));
            PlaylistCreator creator = new PlaylistCreator(workoutType, playlistArray);
            finalPlaylist = creator.createFullPlaylist();
            // Find seed
            SeedFinder seedFinder = new SeedFinder(allSongs, playlistArray, workoutDuration);
            try {
                seed = seedFinder.findSeed();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            playlistSeed = new TextField(seed);

            try {
                seedButtonBox.getChildren().add(startButton());
                seedButtonBox.getChildren().add(playlistSeed);
                seedButtonBox.getChildren().add(backButton(timeButtons()));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            pane.add(seedButtonBox,0,0);
            vBox.getChildren().add(pane);



        });

        button3.setOnAction(e -> {
            workoutDuration = WorkoutDuration.FORTY_FIVE;
            vBox.getChildren().remove(pane);
            pane = new GridPane();
            VBox seedButtonBox = new VBox();

            SongStats[] allSongsArr = new SongStats[allSongs.size()];
            allSongsArr = allSongs.toArray(allSongsArr);
            IntegerLinearProgramming ilp = new IntegerLinearProgramming(allSongsArr, workoutDurationToSeconds(), workoutType);
            int[] songsIdx = ilp.ILPModel();
            SongStats[] playlistSongs = ilp.chosenSongs(songsIdx);
            ArrayList<SongStats> playlistArray = new ArrayList<>(Arrays.asList(playlistSongs));
            PlaylistCreator creator = new PlaylistCreator(workoutType, playlistArray);
            finalPlaylist = creator.createFullPlaylist();
            // Find seed
            SeedFinder seedFinder = new SeedFinder(allSongs, playlistArray, workoutDuration);
            try {
                seed = seedFinder.findSeed();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            playlistSeed = new TextField(seed);

            try {
                seedButtonBox.getChildren().add(startButton());
                seedButtonBox.getChildren().add(playlistSeed);
                seedButtonBox.getChildren().add(backButton(timeButtons()));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            pane.add(seedButtonBox,0,0);
            vBox.getChildren().add(pane);



        });
        button4.setOnAction(e -> {
            workoutDuration = WorkoutDuration.INFINITY;
            vBox.getChildren().remove(pane);
            pane = new GridPane();
            VBox seedButtonBox = new VBox();

            SongStats[] allSongsArr = new SongStats[allSongs.size()];
            allSongsArr = allSongs.toArray(allSongsArr);
            IntegerLinearProgramming ilp = new IntegerLinearProgramming(allSongsArr, workoutDurationToSeconds(), workoutType);
            int[] songsIdx = ilp.ILPModel();
            SongStats[] playlistSongs = ilp.chosenSongs(songsIdx);
            ArrayList<SongStats> playlistArray = new ArrayList<>(Arrays.asList(playlistSongs));
            PlaylistCreator creator = new PlaylistCreator(workoutType, playlistArray);
            finalPlaylist = creator.createFullPlaylist();
            // Find seed
            SeedFinder seedFinder = new SeedFinder(allSongs, playlistArray, workoutDuration);
            try {
                seed = seedFinder.findSeed();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            playlistSeed = new TextField(seed);

            try {
                seedButtonBox.getChildren().add(startButton());
                seedButtonBox.getChildren().add(playlistSeed);
                seedButtonBox.getChildren().add(backButton(timeButtons()));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            pane.add(seedButtonBox,0,0);
            vBox.getChildren().add(pane);



        });
        ArrayList<Button> buttons = new ArrayList<>();
        switch (workoutType) {
            case JUST_SONGS:
                buttons.add(button2);
                buttons.add(button3);
                buttons.add(button4);
                break;
            case KICKBOXING:
            case AEROBICS:
            case SIXTIES:
            case LATIN:
                buttons.add(button1);
                buttons.add(button2);
                buttons.add(button3);
                break;
        }
        Button button7 = backButton(GUI.this.workoutButtons());
        buttons.add(button7);

        return buttons;

    }

    private Button backButton(ArrayList<Button> buttonsToAdd) throws FileNotFoundException {
        Image imageButton = new Image(new FileInputStream("src\\main\\resources\\Graphics\\back.png"));
        Button buttonBack = new Button();

        BackgroundImage backgroundImage = new BackgroundImage(imageButton, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        buttonBack.setBackground(background);
        buttonBack.setPrefSize(imageButton.getWidth(), imageButton.getHeight());

        buttonBack.setOnAction(e -> {
            vBox.getChildren().clear();
            for (Button button : buttonsToAdd) {
                vBox.getChildren().add(button);
            }
        });
        return buttonBack;
    }



    private void createPlaylist() {
        VideoPlayer player = new VideoPlayer(finalPlaylist, playlistSongs, this);
        int i = 0;
        try {
            i++;
            if (i>10){
                throw new Exception();
            }
            player.startVideoPlayer();
            System.out.println("Trying");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Button startButton() throws FileNotFoundException {

        Image imageButton = new Image(new FileInputStream("src\\main\\resources\\Graphics\\PlayButton.png"));
        final Button button1 = new Button();

        BackgroundImage backgroundImage = new BackgroundImage(imageButton, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);

        button1.setBackground(background);
        button1.setPrefSize(imageButton.getHeight(), imageButton.getWidth());

        button1.setOnAction(e -> {

            String seedDecided = playlistSeed.getText();
            System.out.println(seedDecided + " seedDecided GUI");
            SeedDecipher seedDecipher = new SeedDecipher(allSongs,seedDecided, workoutDuration);
            seedDecipher.decipher();
            long shuffleSeed = seedDecipher.getShuffleSeed();
            playlistSongs = seedDecipher.getDecipheredSongs();
            Collections.shuffle(playlistSongs, new Random(shuffleSeed));
            PlaylistCreator creator = new PlaylistCreator(workoutType, playlistSongs);
            finalPlaylist = creator.createFullPlaylist();
            GUI.this.createPlaylist();
        });

        return button1;
    }

    int getSongs_i() {
        return songs_i;
    }

    void setSongs_i(int songs_i) {
        this.songs_i = songs_i;
    }

    private int workoutDurationToSeconds(){
        int workoutDur = -1;
        switch (workoutDuration){
            case TEN:
                workoutDur = 10;
                break;
            case TWENTY_FIVE:
                workoutDur = 25;
                break;
            case FORTY_FIVE:
                workoutDur = 45;
                break;
        }
        return workoutDur*60;
    }
}
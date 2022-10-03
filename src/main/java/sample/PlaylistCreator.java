package sample;

import sample.playlistUtils.SongStats;
import sample.workoutDetails.WorkoutType;

import java.io.*;
import java.util.ArrayList;

class PlaylistCreator {
    private WorkoutType workoutType;
    private ArrayList<SongStats> songsForPlaylist;


    PlaylistCreator(WorkoutType workoutType, ArrayList<SongStats> songsForPlaylist) {
        this.workoutType = workoutType;
        this.songsForPlaylist = songsForPlaylist;
    }

    private ArrayList<File> createWorkout() {
        ArrayList<File> ourWorkout = new ArrayList<>();
        String warmup = "";
        String work1 = "";
        String work2 = "";
        String work3 = "";
        String cool = "";
        switch (workoutType) {
            case AEROBICS:
                warmup = ("src/Songs/Workouts/Aerobics/0.mp4");
                work1 =  ("src/Songs/Workouts/Aerobics/1.mp4");
                work2 =  ("src/Songs/Workouts/Aerobics/2.mp4");
                work3 =  ("src/Songs/Workouts/Aerobics/3.mp4");
                cool =   ("src/Songs/Workouts/Aerobics/4.mp4");
                break;
            case KICKBOXING:
                warmup = ("src/Songs/Workouts/Kickboxing/0.mp4");
                work1 =  ("src/Songs/Workouts/Kickboxing/1.mp4");
                work2 =  ("src/Songs/Workouts/Kickboxing/2.mp4");
                work3 =  ("src/Songs/Workouts/Kickboxing/3.mp4");
                cool =   ("src/Songs/Workouts/Kickboxing/4.mp4");

                break;
            case SIXTIES:
                warmup = ("src/Songs/Workouts/Sixties/0.mp4");
                work1 =  ("src/Songs/Workouts/Sixties/1.mp4");
                work2 =  ("src/Songs/Workouts/Sixties/2.mp4");
                work3 =  ("src/Songs/Workouts/Sixties/3.mp4");
                cool =   ("src/Songs/Workouts/Sixties/4.mp4");

                break;

            case LATIN:
                warmup = ("src/Songs/Workouts/Latin/0.mp4");
                work1 =  ("src/Songs/Workouts/Latin/1.mp4");
                work2 =  ("src/Songs/Workouts/Latin/2.mp4");
                work3 =  ("src/Songs/Workouts/Latin/3.mp4");
                cool =   ("src/Songs/Workouts/Latin/4.mp4");

                break;
        }
        ourWorkout.add(new File(warmup));
        ourWorkout.add(new File(work1));
        ourWorkout.add(new File(work2));
        ourWorkout.add(new File(work3));
        ourWorkout.add(new File(cool));

        return ourWorkout;
    }

    ArrayList<File> createFullPlaylist() {
        ArrayList<File> fullPlaylist = new ArrayList<>();
        if (workoutType != WorkoutType.JUST_SONGS) {
            ArrayList<File> workoutPlaylist = createWorkout();
            fullPlaylist.add(workoutPlaylist.get(0)); // Adding warmup
            int j = 0;
            for (int i = 0; i < this.songsForPlaylist.size() - 1; i++) {
                fullPlaylist.add(new File(this.songsForPlaylist.get(i).getPathToSong()));
                fullPlaylist.add(workoutPlaylist.get((i % 3) + 1));
                j = i;
            }
            fullPlaylist.add(new File(this.songsForPlaylist.get(++j).getPathToSong()));
            fullPlaylist.add(workoutPlaylist.get(4)); // Adding cooldown
        } else {
            for (SongStats songStats : this.songsForPlaylist) {
                fullPlaylist.add(new File(songStats.getPathToSong()));
            }
        }
        return fullPlaylist;
    }

}

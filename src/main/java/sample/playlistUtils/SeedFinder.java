package sample.playlistUtils;

import sample.workoutDetails.WorkoutDuration;

import java.util.*;

/**
 * Class that finds the workout seed from the chosen by ILP songs
 */

public class SeedFinder {
    private ArrayList<SongStats> songsChosen;
    private ArrayList<SongStats> allSongs;
    private WorkoutDuration workoutDuration;

    public SeedFinder(ArrayList<SongStats> allSongs, ArrayList<SongStats> songsChosen, WorkoutDuration workoutDuration){
        this.allSongs = allSongs;
        this.songsChosen = songsChosen;
        this.workoutDuration = workoutDuration;
    }

    private static String longToBase36OneDigit(long integ) throws Exception {
        if (integ<36 && integ>=0){
            return Long.toString(integ, 36);
        }
        throw new Exception("Wrong number longToBase36OneDigit " + integ);
    }

    private static String longToBase36(long integ){
        String result = Long.toString(integ, 36);
        if (result.length() == 1){
            return "0"+result;
        }
        assert result.length()==2;
        return result;
    }


    private long randomNumberPlaylistShuffle(){
        return (long)  (Math.random()*36);
    }


    public String findSeed() throws Exception {
        if (this.workoutDuration == WorkoutDuration.INFINITY) {
            return (int) (Math.random() * 9999) + "";
        }
        // Shuffle playlist according to generated 1-digit 36 base seed
        long seedPlaylist = randomNumberPlaylistShuffle();
        StringBuilder seed = new StringBuilder(longToBase36OneDigit(seedPlaylist));
        assert seed.length() == 1;

        // Get IDs for songs based on shuffled order and mark ids of chosen songs, add the ids to seed
        for (int i = 0; i < this.allSongs.size(); i++) {
            for (SongStats chosen : this.songsChosen) {
                if (chosen.getPathToSong().equals(this.allSongs.get(i).getPathToSong())) {
                    String chosenID = longToBase36(i);
                    seed.append(chosenID);
                }
            }
        }
        return seed.toString();
    }

}


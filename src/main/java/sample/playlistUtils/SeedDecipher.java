package sample.playlistUtils;

import sample.workoutDetails.WorkoutDuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Class that deciphers the seed to create workout
 */
public class SeedDecipher {
    private String seed;
    private ArrayList<SongStats> allSongs;
    private ArrayList<SongStats> decipheredSongs;
    private WorkoutDuration workoutDuration;
    private long shuffleSeed;


    public SeedDecipher(ArrayList<SongStats> allSongs, String seed, WorkoutDuration workoutDuration){
        this.allSongs=allSongs;
        this.seed=seed;
        this.decipheredSongs = new ArrayList<>();
        this.workoutDuration = workoutDuration;
    }

    private static long base36ToLong(String base){
        if (base.charAt(0) == '0'){
            base = base.substring(1);
        }
        return Long.valueOf(base, 36);
    }
    private static long base36ToLongOneDigit(String base){
        return Long.valueOf(base, 36);
    }

    public void decipher(){
        ArrayList<SongStats> allSongsCopy = new ArrayList<>();
        for (SongStats song : this.allSongs) {
            allSongsCopy.add(song);
        }
        if (workoutDuration == WorkoutDuration.INFINITY) {
            int seedInt = Integer.parseInt(seed);
            Collections.shuffle(allSongsCopy, new Random(seedInt));
            this.decipheredSongs = allSongsCopy;
        } else {
            shuffleSeed = base36ToLongOneDigit(this.seed.charAt(0) + "");
            String remainingSeed = this.seed.substring(1);
            for (int i = 0; i < remainingSeed.length() - 1; i += 2) {
                String substring = remainingSeed.substring(i, i + 2);
                assert substring.length() == 2;
                int decipheredID = (int) base36ToLong(substring);
                this.decipheredSongs.add(allSongsCopy.get(decipheredID));
            }
        }
    }

    public long getShuffleSeed(){
        return shuffleSeed;
    }

    public ArrayList<SongStats> getDecipheredSongs(){
        return decipheredSongs;
    }

}

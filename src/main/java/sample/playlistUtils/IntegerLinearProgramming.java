package sample.playlistUtils;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.BoolVar;
import org.chocosolver.solver.variables.IntVar;
import sample.workoutDetails.WorkoutType;

import java.util.ArrayList;

/**
 * Class that uses Integer Linear Programming to select songs that have been played the least times + are within duration limits
 */

public class IntegerLinearProgramming {
    private int sumOfSongsDuration; // Eg. 25*60 for a 25min just songs workout
    private final int MAXOVERTIME = 60; // maximum number of seconds a song might be overtime (in seconds)
    private SongStats[] songStatsArray;
    private int[] songDurations;
    private int[] timesSongsPlayed;
    private WorkoutType workoutType;


    public IntegerLinearProgramming(SongStats[] songStatsArray, int sumOfSongsDuration, WorkoutType workoutType) {
        this.songStatsArray = songStatsArray;
        this.sumOfSongsDuration = sumOfSongsDuration;
        this.workoutType = workoutType;
        setSongDurations();
        setTimesSongsWerePlayed();
    }

    private void setSongDurations() {
        int totalSongsDurations = 0;
        this.songDurations = new int[this.songStatsArray.length];
        for (int i = 0; i < this.songStatsArray.length; i++) {
            this.songDurations[i] = this.songStatsArray[i].getDuration();
            totalSongsDurations += this.songDurations[i];
        }
        if (sumOfSongsDuration<0){
            sumOfSongsDuration = totalSongsDurations;
        }
    }

    private void setTimesSongsWerePlayed() {
        this.timesSongsPlayed = new int[this.songStatsArray.length];
        for (int i = 0; i < this.songStatsArray.length; i++) {
            this.timesSongsPlayed[i] = this.songStatsArray[i].getNumberPlayed();
        }
    }


    /**
     * Method that computes the ILP solution for the optimization problem
     *
     * @return int[] of indices of SongStats[] songStatsArray that are included in a found solution/ playlist
     */
    public int[] ILPModel() {
        ArrayList<Integer> indicesOfChosenSongs = new ArrayList<>();
        Model playlistModel = new Model();
        BoolVar[] isSongIncluded = playlistModel.boolVarArray("isSongIncluded", this.songStatsArray.length);
        IntVar[] partialSumOfPlays = new IntVar[isSongIncluded.length];
        for (int i = 0; i < isSongIncluded.length; i++) {
            IntVar partialSumOfPlay = playlistModel.intScaleView(isSongIncluded[i], this.timesSongsPlayed[i]);
            partialSumOfPlays[i] = partialSumOfPlay;
        }
        IntVar sumOfTimesSongsWerePlayed = playlistModel.sum("sumOfTimesSongsWerePlayed", partialSumOfPlays);

        int upperBound = this.sumOfSongsDuration + this.MAXOVERTIME;
        int lowerBound = this.sumOfSongsDuration;
        if (this.workoutType == WorkoutType.JUST_SONGS) {
            playlistModel.scalar(isSongIncluded, this.songDurations, ">=", lowerBound).post();
            playlistModel.scalar(isSongIncluded, this.songDurations, "<=", upperBound).post();
        }
        else{
            /*
            The logic to simplify the formula:
            1. Assume that each workout video 1-3 lasts approx 90 seconds, and the intros last approx 120 seconds
            2. The lower bound constraint is therefore: intro + songdur_1 + songdur_2 +..._songdur_n + (n-1)*90 >= lowerBoundDur (eg 25*60)
            3. Let's transfer the lower bound:
                LB: songdur_1 + songdur_2 +..._songdur_n >= lowerBoundDur - 120 + 90 - n*90
                LB: songdur_1 + songdur_2 +..._songdur_n >= lowerBoundDur - 30 - n*90
            4. Right hand side can be transformed to a single IntVar, by first getting n (numberOfSongsIncluded),
                applying intScaleView (-90), and offset(lowerBoundDur-30)
            5. Same way we can transform the upper bound
             */
            IntVar numberOfSongsIncluded = playlistModel.sum("numberOfSongsIncluded", isSongIncluded);
            IntVar adjustedBoundDraft = playlistModel.intScaleView(numberOfSongsIncluded, -90);
            IntVar adjustedLowerBound = playlistModel.intOffsetView(adjustedBoundDraft, lowerBound-30);
            IntVar adjustedUpperBound = playlistModel.intOffsetView(adjustedBoundDraft, upperBound-30);
            playlistModel.scalar(isSongIncluded, this.songDurations, ">=", adjustedLowerBound).post();
            playlistModel.scalar(isSongIncluded, this.songDurations, "<=", adjustedUpperBound).post();
        }
        playlistModel.setObjective(Model.MINIMIZE, sumOfTimesSongsWerePlayed);
        Solver solver = playlistModel.getSolver();
        // Get just 1 solution, no need for others
        solver.solve();
        for (int i = 0; i < isSongIncluded.length; i++) {
            if (isSongIncluded[i].getValue() == 1) {
                indicesOfChosenSongs.add(i);
            }
        }
        return indicesOfChosenSongs.stream().mapToInt(i -> i).toArray();
    }

    public SongStats[] chosenSongs(int[] chosenSongsIndices) {
        SongStats[] chosenSongs = new SongStats[chosenSongsIndices.length];
        for (int i = 0; i < chosenSongsIndices.length; i++) {
            int indexOfChosenSong = chosenSongsIndices[i];
            chosenSongs[i] = this.songStatsArray[indexOfChosenSong];
            System.out.println(chosenSongs[i].getPathToSong());
        }
        if (!checkILP(chosenSongs)) {
            throw new Error("The chosen songs do not satisfy the duration constraints of the playlist! :c");
        }
        return chosenSongs;
    }

    private boolean checkILP(SongStats[] chosenSongs) {
        double totalDuration = 0;
        for (SongStats chosenSong : chosenSongs) {
            double duration = chosenSong.getDuration();
            totalDuration += duration;
        }
        if (workoutType != WorkoutType.JUST_SONGS) {
            for (int i = 0; i < chosenSongs.length - 1; i++) {
                totalDuration += 90;
            }
            totalDuration += 120;
        }
        int upperBound = this.sumOfSongsDuration + this.MAXOVERTIME;
        int lowerBound = this.sumOfSongsDuration;
        return totalDuration >= lowerBound && totalDuration <= upperBound;
    }


}

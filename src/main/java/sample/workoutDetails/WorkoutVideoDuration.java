package sample.workoutDetails;

/**
 * Class that stores HARDCODED information on the duration of the warmup and workout videos for all the workout types
 * Does not include info on the duration of the cooldown as cooldown is not included in the total duration of the workout
 * Info is hardcoded as it is not time consuming to gather this info, and it is not planned on adding any more workouts
 * (since all available workouts are included already)
 */
public class WorkoutVideoDuration {
    private WorkoutType workoutType;
    private WorkoutDuration workoutDuration;

    public WorkoutVideoDuration(WorkoutType workoutType, WorkoutDuration workoutDuration){
        this.workoutType = workoutType;
        this.workoutDuration = workoutDuration;
    }

    /**
     * Return duration of an aerobics workout videos (in seconds)
     * @param idxOfVideo index of video: 0-warmup, 1-3 videos
     * @return duration of video in seconds or -1 in case of error (should not occur due to previous assertion)
     */
    private int durationOfAero(int idxOfVideo){
        assert idxOfVideo >=0 && idxOfVideo <= 3;
        switch (idxOfVideo){
            case 0:
                return 119;
            case 1:
                return 101;
            case 2:
            case 3:
                return 102;
            default:
                return -1;
        }
    }

    /**
     * Return duration of a kickboxing workout videos (in seconds)
     * @param idxOfVideo index of video: 0-warmup, 1-3 videos
     * @return duration of video in seconds or -1 in case of error (should not occur due to previous assertion)
     */
    private int durationOfKickboxing(int idxOfVideo){
        assert idxOfVideo >=0 && idxOfVideo <= 3;
        switch (idxOfVideo){
            case 0:
                return 124;
            case 1:
            case 2:
            case 3:
                return 88;
            default:
                return -1;
        }
    }

    /**
     * Return duration of an latin workout videos (in seconds)
     * @param idxOfVideo index of video: 0-warmup, 1-3 videos
     * @return duration of video in seconds or -1 in case of error (should not occur due to previous assertion)
     */
    private int durationOfLatin(int idxOfVideo){
        assert idxOfVideo >=0 && idxOfVideo <= 3;
        switch (idxOfVideo){
            case 0:
                return 129;
            case 1:
            case 3:
                return 96;
            case 2:
                return 94;
            default:
                return -1;
        }
    }

    /**
     * Return duration of an sixties workout videos (in seconds)
     * @param idxOfVideo index of video: 0-warmup, 1-3 videos
     * @return duration of video in seconds or -1 in case of error (should not occur due to previous assertion)
     */
    private int durationOfSixties(int idxOfVideo){
        assert idxOfVideo >=0 && idxOfVideo <= 3;
        switch (idxOfVideo){
            case 0:
                return 126;
            case 1:
                return 97;
            case 2:
            case 3:
                return 93;
            default:
                return -1;
        }
    }

    public int getDurationOfWorkoutVideos(int idxOfVideo){
        switch (this.workoutType){
            case AEROBICS:
                return durationOfAero(idxOfVideo);
            case KICKBOXING:
                return durationOfKickboxing(idxOfVideo);
            case LATIN:
                return durationOfLatin(idxOfVideo);
            case SIXTIES:
                return durationOfSixties(idxOfVideo);
            default:
                return 0;
        }
    }

    public int[] indicesOfWorkoutVideosBasedOnDuration() {
        switch (workoutDuration) {
            case TEN:
                return new int[]{0, 1};
            case TWENTY_FIVE:
                return new int[]{0, 1, 2, 3};
            case FORTY_FIVE:
                return new int[]{0, 1, 2, 3, 1, 2, 3};
            default:
                return new int[]{};
        }
    }
}

package sample.playlistUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Object that represents the info on songs: path, number of times it was played, duration
 */
public class SongStats implements Comparable<SongStats>{
    private String pathToSong;
    private int numberPlayed;
    private int duration;

    public SongStats(){}

    public SongStats(String pathToSong, int numberPlayed, int duration){
        this.pathToSong=pathToSong;
        this.numberPlayed=numberPlayed;
        this.duration=duration;
    }

    /**
     *
     * @return String that allows for the object to be saved to a file in a readable way ( saving as an object would make it difficult to debug )
     */
    public String saveSongToString(){
        String songInfo = "(";
        songInfo += this.pathToSong;
        songInfo += ",";
        songInfo += this.numberPlayed;
        songInfo += ",";
        songInfo += this.duration;
        songInfo += ")";
        return songInfo;
    }

    /**
     *
     * @param songInfo of form (..a..,..b..,..c..) where a = pathToSong, b = numberPlayed, c = duration
     */
    void readSongFromString(String songInfo){
        String removeLeftBracket = songInfo.replace("(", "");
        String removeRightBracket = removeLeftBracket.replace(")", "");
        String[] songInfoArr = removeRightBracket.split(",");
        this.pathToSong = songInfoArr[0];
        this.numberPlayed = Integer.parseInt(songInfoArr[1]);
        this.duration = Integer.parseInt(songInfoArr[2]);
    }

    @Override
    public int compareTo(SongStats o) {
        return Integer.compare(this.numberPlayed, o.numberPlayed);

    }

    public void updateTextFileWithNewNumberPlayed(String oldSongInfo) throws IOException {
        String SONGSTATSINFOFOLDER = "src/main/java/sample/playlistUtils/SongStatsInfo.txt";
        List<String> fileContent = new ArrayList<>(Files.readAllLines(Paths.get(SONGSTATSINFOFOLDER), StandardCharsets.UTF_8));

        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(oldSongInfo)) {
                System.out.println(oldSongInfo + " SongStats old");
                System.out.println(this.saveSongToString() + " SongStats new");
                fileContent.set(i, this.saveSongToString());
                break;
            }
        }

        Files.write(Paths.get(SONGSTATSINFOFOLDER), fileContent, StandardCharsets.UTF_8);
    }

    int getNumberPlayed() {
        return numberPlayed;
    }

    public String getPathToSong(){
        return pathToSong;
    }

    int getDuration() {
        return duration;
    }

    public void increaseNumberPlayed(){
        this.numberPlayed = this.numberPlayed+1;
    }


}

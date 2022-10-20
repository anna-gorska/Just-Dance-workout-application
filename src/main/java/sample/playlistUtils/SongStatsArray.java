package sample.playlistUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Class that creates an array of all songs
 * It either reads them from the SongStatsInfo.txt file, or updates the file with new songs from folder
 */

public class SongStatsArray {
    private final String SONGSTATSINFOFOLDER = "src/main/java/sample/playlistUtils/SongStatsInfo.txt";
    private final String SONGFOLDER = "src/Songs";
    /**
     * Method to read SongStats objects saved in a text file SongStatsInfo.txt - saves time as reading the duration is omitted
     *
     * @return array of songs from SongStatsInfo.txt transformed into SongStats objects
     */
    private SongStats[] readOldSongs() {
        //removeEmptyLinesFromTXTFile();
        BufferedReader br;
        SongStats[] output = new SongStats[1];
        {
            try {
                // create buffered reader
                File file = new File(SONGSTATSINFOFOLDER);
                br = new BufferedReader(new FileReader(file));
                String inputLine = br.readLine();

                if (inputLine != null) {
                    //String[] wordArr = inputLine.split("");
                    List<String> linesList = Files.readAllLines(Paths.get(SONGSTATSINFOFOLDER));
                    String[] wordArr = linesList.toArray(new String[0]);

                    int numberOfSongs = 0;
                    for (String s : wordArr) {
                        if (!s.equals("")) {
                            numberOfSongs++;
                        }
                    }
                    output = new SongStats[numberOfSongs];
                    for (int i = 0; i < numberOfSongs; i++) {
                        SongStats songStatRead = new SongStats();
                        songStatRead.readSongFromString(wordArr[i]);
                        output[i] = songStatRead;
                        System.out.println(songStatRead.getPathToSong() + " <- Reading old songs in SongStatsArray");
                        }

                }
                else{
                    return null;
                }
                // catch exceptions if the files are not found
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    private String[] oldSongsPaths(SongStats[] oldSongs) {
        if (oldSongs != null) {
            String[] oldSongsPaths = new String[oldSongs.length];
            for (int i = 0; i < oldSongs.length; i++) {
                oldSongsPaths[i] = oldSongs[i].getPathToSong();
            }

            return oldSongsPaths;
        }
        return null;
    }

    private boolean areThereNewSongs(String[] oldSongsPaths){
        if (oldSongsPaths == null){
            return true;
        }
        File folder = new File(this.SONGFOLDER);
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File f : listOfFiles) {
            if (f.getName().substring(f.getName().length() - 3).equals("mp4")) {
                String songPath = f.getPath();
                // Check if there are any new songs to be added
                if (Arrays.stream(oldSongsPaths).noneMatch(songPath::equals)) {
                    return true;
                }
            }
        }
        return false;
    }

    private SongStats[] readNewSongs(String[] oldSongsPaths) throws IOException {
        if (areThereNewSongs(oldSongsPaths)) {
            SongsDurationGrabber songsDurationGrabber = new SongsDurationGrabber();
            File folder = new File(this.SONGFOLDER);
            File[] listOfFiles = folder.listFiles();
            assert listOfFiles != null;
            ArrayList<SongStats> songs = new ArrayList<>();
            for (File f : listOfFiles) {
                if (f.getName().substring(f.getName().length() - 3).equals("mp4")) {
                    String songPath = f.getPath();
                    // Check if song has already been read by the readOldSongs method to save time
                    if (oldSongsPaths == null || Arrays.stream(oldSongsPaths).noneMatch(songPath::equals)) {
                        int songDuration = (int) songsDurationGrabber.getSongDuration(f.getPath());
                        System.out.println(songPath + " <- Reading new songs in SongStatsArray");
                        int numberOfTimesPlayed = 0;
                        SongStats songStats = new SongStats(songPath, numberOfTimesPlayed, songDuration);
                        songs.add(songStats);
                    }

                }
            }
            SongStats[] songsArr = new SongStats[songs.size()];
            songsArr = songs.toArray(songsArr);
            return songsArr;
        }
        return null;
    }

    /**
     * The only method that needs to be public, read all songs by combining old and new ones
     * @return array of all songs
     * @throws IOException e
     */
    public ArrayList<SongStats> readAllSongs() throws IOException {
        SongStats[] oldSongs = readOldSongs();
        String[] oldSongsPaths = oldSongsPaths(oldSongs);
        SongStats[] newSongs;
        if (oldSongs != null) {
            System.out.println("old: " + oldSongs.length);
        }
        System.out.println("new: " + Objects.requireNonNull(new File(SONGFOLDER).list()).length);
        if (oldSongs == null || oldSongs.length != Objects.requireNonNull(new File(SONGFOLDER).list()).length - 1) {
            newSongs = readNewSongs(oldSongsPaths);
            System.out.println("New songs");
            addNewSongsToFile(newSongs);

            int oldSongsLength = 0;
            if (oldSongs != null){
                oldSongsLength = oldSongs.length;
            }
            int newSongsLength = 0;
            if (newSongs != null){
                newSongsLength = newSongs.length;
            }

            SongStats[] result;
            if (oldSongs != null) {
                result = Arrays.copyOf(oldSongs, oldSongsLength + newSongsLength);
            }
            else{
                result = newSongs;
            }
            if (newSongs != null) {
                System.arraycopy(newSongs, 0, result, oldSongsLength, newSongsLength);
            }
            sortTextFileAlphabetically();

            assert result != null;
            assert result.length == Objects.requireNonNull(new File(SONGFOLDER).list()).length;
            for (SongStats songStats : result){
                System.out.println("Checkup " + songStats.getPathToSong());
            }
            return new ArrayList<>(Arrays.asList(result));
        }
        assert oldSongs.length == Objects.requireNonNull(new File(SONGFOLDER).list()).length;
        return new ArrayList<>(Arrays.asList(oldSongs));

    }

    private void addNewSongsToFile(SongStats[] newSongs){
        if (newSongs != null) {
            if (newSongs.length > 0) {
                for (SongStats newSong : newSongs) {
                    String newSongString = newSong.saveSongToString();
                    try (FileWriter fw = new FileWriter(SONGSTATSINFOFOLDER, true);
                         BufferedWriter bw = new BufferedWriter(fw);
                         PrintWriter out = new PrintWriter(bw)) {
                        out.println(newSongString);
                    } catch (IOException e) {
                        System.out.println("Error while writing new songs to the songstatsinfo.txt file :<");
                    }
                }
            }
        }
    }

    private void sortTextFileAlphabetically() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(SONGSTATSINFOFOLDER));
        ArrayList<String> str = new ArrayList<>();
        String line;
        while((line=reader.readLine())!=null){
            str.add(line);
        }
        reader.close();
        str.sort(String::compareToIgnoreCase);
        FileWriter writer = new FileWriter(SONGSTATSINFOFOLDER);
        for(String s: str){
            writer.write(s);
            writer.write("\r\n");
        }
        writer.close();
    }
}

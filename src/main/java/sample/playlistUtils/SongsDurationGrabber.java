package sample.playlistUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

import java.io.IOException;

/**
 * Find durations of songs using FFmpeg
 */

public class SongsDurationGrabber {

    double getSongDuration(String songPath) throws IOException {
        FFprobe ffprobe = new FFprobe("src\\ffprobe.exe");
        System.out.println("SongsDurationGrabber " + songPath);
        FFmpegProbeResult probeResult = ffprobe.probe(songPath);
        FFmpegFormat format = probeResult.getFormat();
        return format.duration;

    }
}

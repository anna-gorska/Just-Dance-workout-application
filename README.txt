A project that creates a playlist of dance videos, accompanied with an interactive UI
Song files not included due to the size of the folder

--- The functionalities ---
1. Create a dance song playlist based on predefined filters (few types to choose, as well as few types of durations)
2. Compatibility with multiple machines so that given the same seed, the generated playlist will be the same among multiple computers
3. Does not require any internet connection
4. The songs are chosen in a way such that the total duration will not exceed significantly the chosen duration, as well as songs that were played a lot should be less likely to be played again to provide more diversity
5. Multiple interactive UI functions, as pausing the songs, going back and forth

Uses:
1. Gradle for management of libraries
2. JavaFX for UI and video player
3. Ffmpeg for reading the duration of an mp4 file
4. Choco-solver for integer linear programming
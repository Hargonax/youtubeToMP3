# youtubeToMP3
Get a bunch of Youtube vídeos as MP3.
The project works with Java 18.

We need to create an Excel file where we are indicating all the Youtube videos to download as MP3.

For downloading the Youtube video and convert it to MP3 we need two tools:
    - yt-dlp: https://github.com/yt-dlp/yt-dlp
    - ffmpeg: https://ffmpeg.org/
    
You need to install yt-dlp and ffmpeg in your system (you can use pip install yt-dlp and make sure to have ffmpeg in you PATH).
An executable version of ffmpeg for MacOs is included in the utils folder.

We also use mp3agic library. Documentation can be found in https://github.com/mpatric/mp3agic.


# MacOs installation tips
If you wish to install a Python application that isn't in Homebrew,
it may be easiest to use 'pipx install xyz', which will manage a
virtual environment for you. You can install pipx with
    
brew install pipx

# Windows installation tips
To use yt-dlp, we need to download the file yt-dlp.exe from this page:
https://github.com/yt-dlp/yt-dlp
and add it to ./utils/windows in the project.

To install FFMPEG, I followed the instructions of this page:
https://www.gyan.dev/ffmpeg/builds/
and executed in Powershell the following command:
winget install "FFmpeg (Shared)"
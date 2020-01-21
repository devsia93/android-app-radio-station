import android.media.MediaPlayer;

import java.io.IOException;

public class Player {

   private String mURLStream;
   private boolean isStart;
   private MediaPlayer mMediaPlayer;
    public Player (String URLStream){
        mURLStream = URLStream;
    }

    public void start(){
        isStart = true;
        if (isStart)
            mMediaPlayer.reset();
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(mURLStream);
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public void stop(){
        mMediaPlayer.stop();
        isStart = false;
    }
}

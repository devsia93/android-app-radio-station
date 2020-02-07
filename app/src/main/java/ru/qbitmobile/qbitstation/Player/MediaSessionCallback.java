package ru.qbitmobile.qbitstation.Player;

import android.content.Context;
import android.media.AudioManager;
import android.support.v4.media.session.MediaSessionCompat;

public class MediaSessionCallback extends MediaSessionCompat.Callback implements AudioManager.OnAudioFocusChangeListener {

    private Context mContext;
    private String mURLStream;
    private Player mPlayer;

    public MediaSessionCallback(Context context, String URLStream) {
        super();

        mContext = context;
        mURLStream = URLStream;
        mPlayer = new Player(mURLStream);
    }

    @Override
    public void onPlay() {
        super.onPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange){
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                mPlayer.stop();
                break;
            case AudioManager.AUDIOFOCUS_GAIN:
                mPlayer.start(mContext);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                mPlayer.stop();
                break;
        }
    }
}

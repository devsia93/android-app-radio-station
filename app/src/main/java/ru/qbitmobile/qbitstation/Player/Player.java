package ru.qbitmobile.qbitstation.Player;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class Player {

   private static String mURLStream;
   private static SimpleExoPlayer mExoPlayer;
   private static boolean isPlay;

    public Player (String URLStream){
        mURLStream = URLStream;
    }

    public static void start(Context context){

        if (mExoPlayer != null) {
            mExoPlayer.stop();
        }

                Uri URI = Uri.parse(mURLStream);

                DataSource.Factory factory = new DefaultHttpDataSourceFactory(Util.getUserAgent(context, context.getPackageName()));
                MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory)
                        .createMediaSource(URI);
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(context);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);
                isPlay = true;
    }

    public static void stop(){
        mExoPlayer.stop();
        isPlay = false;
    }

    public static String getCurrentUrlStream(){
        return mURLStream;
    }

    public static void setUrlStream(String url){
        mURLStream = url;
    }

    public static ExoPlayer getPlayer(){
        return mExoPlayer;
    }

    public static boolean isPlayed(){
        return isPlay;
    }
}

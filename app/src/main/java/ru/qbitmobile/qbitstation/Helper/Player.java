package ru.qbitmobile.qbitstation.Helper;

import android.content.Context;
import android.net.Uri;

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
   private static String preUrl;


    public Player (String URLStream){
        mURLStream = URLStream;
    }

    public void start(Context context){

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
    }

    public void stop(){
        mExoPlayer.stop();
    }

    public static String getCurrentUrlStream(){
        return mURLStream;
    }

    public void setUrlStream(String url){
        mURLStream = url;
    }
}

package ru.qbitmobile.qbitstation.Helper;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSourceFactory;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import okhttp3.OkHttpClient;

public class Player {

   private String mURLStream;
   private boolean isStart;
   private MediaPlayer mMediaPlayer;
   private static SimpleExoPlayer mExoPlayer;

   private Thread thread;

    public Player (String URLStream){
        mURLStream = URLStream;
    }

    public void start(Context context){


        /*if (isStart & mMediaPlayer!=null) {
            mMediaPlayer.reset();
            thread.interrupt();
            thread = null;
        }

        isStart = true;


            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        mMediaPlayer = new MediaPlayer();
                        mMediaPlayer.setDataSource(mURLStream);
                        mMediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mMediaPlayer.start();
                }
            });
           thread.start();*/

        if (mExoPlayer != null) {
            mExoPlayer.stop();
        }


                Uri URI = Uri.parse(mURLStream);
        DataSource.Factory httpDataSourceFactory = new OkHttpDataSourceFactory(new OkHttpClient().newBuilder().build(), Util.getUserAgent(context, context.getPackageName()), null);

                DataSource.Factory factory = new DefaultHttpDataSourceFactory(Util.getUserAgent(context, context.getPackageName()));
                MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory)
                        .createMediaSource(URI);
                mExoPlayer = ExoPlayerFactory.newSimpleInstance(context);
                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);
    }

    public void stop(){
        /*mMediaPlayer.stop();
        thread.interrupt();
        thread = null;*/
        mExoPlayer.stop();
        isStart = false;
    }
}

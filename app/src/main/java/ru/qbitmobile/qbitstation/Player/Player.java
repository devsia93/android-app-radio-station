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

import java.util.List;

import ru.qbitmobile.qbitstation.BaseObject.Station;
import ru.qbitmobile.qbitstation.Notification.CreateNotification;


public class Player {

    private static String mURLStream;
    private static SimpleExoPlayer mExoPlayer;
    private static boolean isPlay;
    private static List<Station> sStations;
    private static int mPosition;
    private static Station currentStation;

    public static void start(Context context, List<Station> stations, Station station) {

        if (mExoPlayer != null) {
            mExoPlayer.stop();
        }
        sStations = stations;
        mPosition = sStations.indexOf(station);
        currentStation = station;

        Uri URI = Uri.parse(station.getStream());

        DataSource.Factory factory = new DefaultHttpDataSourceFactory(Util.getUserAgent(context, context.getPackageName()));
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory)
                .createMediaSource(URI);
        mExoPlayer = ExoPlayerFactory.newSimpleInstance(context);
        mExoPlayer.prepare(mediaSource);
        mExoPlayer.setPlayWhenReady(true);
        isPlay = true;

    }

    public static void stop() {
        mExoPlayer.stop();
        isPlay = false;
    }

    public static void nextStation(Context context) {
        currentStation = sStations.get(sStations.indexOf(currentStation)+1);
        start(context, sStations, currentStation);
    }

    public static void previousStation(Context context){
        currentStation = sStations.get(sStations.indexOf(currentStation)-1);
        start(context, sStations, currentStation);
    }
    public static String getCurrentUrlStream() {
        return mURLStream;
    }

    public static void setUrlStream(String url) {
        mURLStream = url;
    }

    public static ExoPlayer getPlayer() {
        return mExoPlayer;
    }

    public static boolean isPlayed() {
        return isPlay;
    }
}

package ru.qbitmobile.qbitstation.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.baseObject.Radio;
import ru.qbitmobile.qbitstation.baseObject.Station;
import ru.qbitmobile.qbitstation.controller.RadioStationController;
import ru.qbitmobile.qbitstation.helper.AnimatorHelper;
import ru.qbitmobile.qbitstation.helper.KeyboardHelper;
import ru.qbitmobile.qbitstation.helper.MediaControllerHelper;
import ru.qbitmobile.qbitstation.helper.PreferenceHelper;
import ru.qbitmobile.qbitstation.helper.ReportHelper;
import ru.qbitmobile.qbitstation.service.PlayerService;

public class BaseStationAdapter extends RecyclerView.Adapter<BaseStationAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Station> mStations;
    private Context mContext;
    private Radio mRadio;

    private ArrayList<ViewHolder> viewHolders = new ArrayList<>();

    PreferenceHelper preferenceHelper;

    public BaseStationAdapter(Context context, Radio radio) {
        this.mStations = radio.getStations();
        this.mRadio = radio;
        this.mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        preferenceHelper = new PreferenceHelper(mContext);

        setHasStableIds(true);
    }

    public void setRadio(Radio radio){
        mRadio = radio;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.example_list_item_station, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Station station = mStations.get(position);

        holder.textView.setText(station.getName());

        Glide.with(mContext)
                .asBitmap()
                .load(station.getImage())
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        RadioStationController.setHashMap(station, resource);
                        holder.imageView.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFirebaseReport(position);
                //Toaster.Toast(mContext, "Adapter.holder.onClick: " + mStations.get(position).getName());

                ReportHelper.report(mStations.get(position));
                AnimatorHelper.viewHolders = viewHolders;

                RadioStationController.setSelectedRadio(mRadio);

                if (RadioStationController.getSelectedStation() != null && RadioStationController.getSelectedStation() == mStations.get(position) && PlayerService.isPlaying) {
                    MediaControllerHelper.mediaController.getTransportControls().pause();
                    AnimatorHelper.stopAnimation(holder.playViewAnimation);
                } else {

                    if (PlayerService.isPlaying) {
                        MediaControllerHelper.mediaController.getTransportControls().pause();
                    }
                    if (MediaControllerHelper.mediaController != null) {
                        MediaControllerHelper.mediaController.getTransportControls().play();
                    }
                    AnimatorHelper.startAnimation(holder.playViewAnimation);
                }
                RadioStationController.setSelectedStation(mStations.get(position));
                if ((holder.imageView.getDrawable()) != null)
                    RadioStationController.setImageSelectedStation(RadioStationController.getBitmapFromhashmap(station));
            }
        });
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                KeyboardHelper.closeKeyboard(mContext, holder.itemView);
                return false;
            }
        });

        viewHolders.add(holder);
        if (RadioStationController.getSelectedStation() != null && checkEqualsStation(station, RadioStationController.getSelectedStation()) && PlayerService.isPlaying)
            AnimatorHelper.startAnimation(holder.playViewAnimation);
    }

    private boolean checkEqualsStation(Station station, Station selectedStation) {
        return station.getName().equals(selectedStation.getName());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void createFirebaseReport(int position) {
        try {
            FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
            Bundle eventDetails = new Bundle();

            StringBuilder sb = new StringBuilder();
            sb.append(mStations.get(position).getName() + " : " + mStations.get(position).getStream());

            eventDetails.putString("station", sb.toString());
            firebaseAnalytics.logEvent("select_station", eventDetails);
        } catch (Exception e) {
            Log.d("glide", e.getMessage());
        }
    }
    public void notifyDataSetStart(){
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mStations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements Serializable {
        final ImageView imageView;
        final TextView textView;
        public final AVLoadingIndicatorView playViewAnimation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivStation);
            textView = itemView.findViewById(R.id.tvStation);
            playViewAnimation = itemView.findViewById(R.id.playing_anim);
        }
    }
}

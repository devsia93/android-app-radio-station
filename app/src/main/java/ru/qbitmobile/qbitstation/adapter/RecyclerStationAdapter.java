package ru.qbitmobile.qbitstation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.util.ArrayList;
import java.util.List;

import ru.qbitmobile.qbitstation.baseObject.Radio;
import ru.qbitmobile.qbitstation.helper.AnimatorHelper;
import ru.qbitmobile.qbitstation.baseObject.Station;
import ru.qbitmobile.qbitstation.helper.MediaControllerHelper;
import ru.qbitmobile.qbitstation.helper.ReportHelper;
import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.controller.RadioStationController;
import ru.qbitmobile.qbitstation.service.PlayerService;

public class RecyclerStationAdapter extends RecyclerView.Adapter<RecyclerStationAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Station> mStations;
    private Context mContext;

    private List<ViewHolder> viewHolders = new ArrayList<>();

    public RecyclerStationAdapter(Context context, List<Station> stations) {
        this.mStations = stations;
        this.mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.example_list_item_station, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Station station = mStations.get(position);
        holder.textView.setText(station.getName());
        Glide.with(mContext)
                .asBitmap()
                .load(mStations.get(position).getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
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

                for (Radio r : RadioStationController.getListRadios()) {
                    if (r.getStations().contains(mStations.get(position))) {
                        RadioStationController.setSelectedRadio(r);
                        break;
                    }
                }

                if (RadioStationController.getSelectedStation() != null && RadioStationController.getSelectedStation() == mStations.get(position)) {
                    MediaControllerHelper.mediaController.getTransportControls().pause();
                    AnimatorHelper.stopAnimation(holder.playViewAnimation);
                } else {

                    if (PlayerService.isPlaying)
                        MediaControllerHelper.mediaController.getTransportControls().stop();
                    if (MediaControllerHelper.mediaController != null) {
                        MediaControllerHelper.mediaController.getTransportControls().play();
                    }
                    AnimatorHelper.startAnimation(holder.playViewAnimation);
                }
                RadioStationController.setSelectedStation(mStations.get(position));
                if (((BitmapDrawable) holder.imageView.getDrawable()) != null)
                    RadioStationController.setImageSelectedStation(((BitmapDrawable) holder.imageView.getDrawable()).getBitmap());
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

    @Override
    public int getItemCount() {
        return mStations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

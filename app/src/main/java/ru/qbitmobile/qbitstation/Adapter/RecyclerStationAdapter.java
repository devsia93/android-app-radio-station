package ru.qbitmobile.qbitstation.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.Helper.AnimatorHelper;
import ru.qbitmobile.qbitstation.BaseObject.Station;
import ru.qbitmobile.qbitstation.Helper.Player;
import ru.qbitmobile.qbitstation.Helper.ReportHelper;
import ru.qbitmobile.qbitstation.Notification.NotificationService;
import ru.qbitmobile.qbitstation.R;

public class RecyclerStationAdapter extends RecyclerView.Adapter<RecyclerStationAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Station> mStations;
    private Context mContext;

    private static RecyclerView.ViewHolder preHolder;

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
//        viewHolder.setIsRecyclable(false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try {
        Station station = mStations.get(position);
        holder.textView.setText(station.getName());
//        holder.setIsRecyclable(false);

            Glide.with(mContext)
                    .load(mStations.get(position).getImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFirebaseReport(position);
                Log.d("debug", mStations.get(position).getName());

                ReportHelper.report(mStations.get(position));

                Player player = new Player(mStations.get(position).getStream());

                if (preHolder == holder) {
                    player.stop();
                    AnimatorHelper.stopAnimation(holder.playViewAnimation);
                    preHolder = null;
                } else {
                    player.start(mContext);
                    startPlayerService();
                    AnimatorHelper.startAnimation(holder.playViewAnimation);
                    preHolder = holder;
                }


            }

            private void startPlayerService() {
                Intent serviceIntent = new Intent(mContext, NotificationService.class);
                serviceIntent.setAction(Const.ACTION.STARTFOREGROUND_ACTION);
                mContext.startService(serviceIntent);
            }
        });
        } catch (Exception e){
            Log.d("glide", e.getMessage());
        }
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
        } catch (Exception e){
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
        final AVLoadingIndicatorView playViewAnimation;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivStation);
            textView = itemView.findViewById(R.id.tvStation);
            playViewAnimation = itemView.findViewById(R.id.playing_anim);
        }
    }
}

package ru.qbitmobile.qbitstation.Adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.BaseObject.Station;
import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.Controller.RadioStationController;
import ru.qbitmobile.qbitstation.Fragment.SearchFragment;
import ru.qbitmobile.qbitstation.Helper.AnimatorHelper;
import ru.qbitmobile.qbitstation.Helper.KeyboardHelper;
import ru.qbitmobile.qbitstation.Helper.MediaControllerHelper;
import ru.qbitmobile.qbitstation.Helper.ReportHelper;
import ru.qbitmobile.qbitstation.Helper.Toaster;
import ru.qbitmobile.qbitstation.Player.Player;
import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.Service.PlayerService;

public class FilterRecyclerStationAdapter extends RecyclerView.Adapter<FilterRecyclerStationAdapter.ViewHolder> implements Filterable {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<Station> mAllStations;
    private List<Station> filteredList;

    private Intent serviceIntent;

    private static RecyclerView.ViewHolder preHolder;
    private List<RecyclerStationAdapter.ViewHolder> viewHolders;

    public FilterRecyclerStationAdapter(LayoutInflater layoutInflater, List<Station> stations, Context context) {
        this.mLayoutInflater = layoutInflater;
        this.mContext = context;
        mAllStations = new ArrayList<>(stations);
        filteredList = mAllStations;
        serviceIntent = new Intent(mContext, PlayerService.class);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                List<Station> tempFilteredList = new ArrayList<>();

                if (constraint.toString().isEmpty()) {
                    filteredList = mAllStations;
                } else {
                    for (Station station : mAllStations) {
                        if (station.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            tempFilteredList.add(station);
                        }
                    }
                    filteredList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList =(ArrayList<Station>) results.values;
                notifyDataSetChanged();
            }
        };

        return filter;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.example_list_item_station, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(filteredList.get(position).getName());

        Glide.with(mContext)
                .load(filteredList.get(position).getImage())
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportHelper.report(filteredList.get(position));
                AnimatorHelper.viewHolders = viewHolders;

                for (Radio r : RadioStationController.getListRadios()) {
                    if (r.getStations().contains(filteredList.get(position))) {
                        RadioStationController.setSelectedRadio(r);
                        break;
                    }
                }

                if (RadioStationController.getSelectedStation() != null && RadioStationController.getSelectedStation() == filteredList.get(position)) {
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
                RadioStationController.setSelectedStation(filteredList.get(position));
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

    }

    private void createFirebaseReport(int position) {
        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
        Bundle eventDetails = new Bundle();

        StringBuilder sb = new StringBuilder();
        sb.append(filteredList.get(position).getName())
                .append(" : ").append(filteredList.get(position).getStream());

        eventDetails.putString("station", sb.toString());
        firebaseAnalytics.logEvent("select_station", eventDetails);
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
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

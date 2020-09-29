package ru.qbitmobile.qbitstation.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ru.qbitmobile.qbitstation.baseObject.Radio;
import ru.qbitmobile.qbitstation.baseObject.Station;
import ru.qbitmobile.qbitstation.controller.RadioStationController;
import ru.qbitmobile.qbitstation.helper.AnimatorHelper;
import ru.qbitmobile.qbitstation.helper.Toaster;

public class RecyclerStationAdapter extends BaseStationAdapter {

    private Radio mRadio;
    private Context mContext;
    private ArrayList<Station> mStations;
    private FavoriteStationAdapter mFavoriteStationAdapter;

    public RecyclerStationAdapter(Context context, Radio radio, FavoriteStationAdapter adapter) {
        super(context, radio);

        mRadio = radio;
        mContext = context;
        mStations = mRadio.getStations();
        mFavoriteStationAdapter = adapter;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        Station station = mStations.get(position);

        if (RadioStationController.getSelectedStation() != null && station != null
                && station.getName().equals(RadioStationController.getSelectedStation().getName())) {
            AnimatorHelper.startAnimation(holder.playViewAnimation);
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (preferenceHelper.checkContainsStation(station)) {
                    preferenceHelper.deleteFavoriteStation(station);
                    Toaster.Toast(mContext, "RecyclerStationAdapter.OnLongClickListener.DELETE_FROM_"+mRadio.getGenre().toUpperCase());

                } else {
                    preferenceHelper.addFavoriteStation(station);
                    Toaster.Toast(mContext, "RecyclerStationAdapter.OnLongClickListener.ADD_TO_"+mRadio.getGenre().toUpperCase());
                }
                mFavoriteStationAdapter.setData(preferenceHelper.getRadio());

                return false;
            }
        });
    }
}

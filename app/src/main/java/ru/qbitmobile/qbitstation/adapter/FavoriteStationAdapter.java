package ru.qbitmobile.qbitstation.adapter;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import ru.qbitmobile.qbitstation.baseObject.Radio;
import ru.qbitmobile.qbitstation.baseObject.Station;
import ru.qbitmobile.qbitstation.helper.Toaster;

public class FavoriteStationAdapter extends BaseStationAdapter {

    private Radio mRadio;
    private Context mContext;

    public FavoriteStationAdapter(Context context, Radio radio) {
        super(context, radio);

        mRadio = radio;
        mContext = context;
    }

    public void setData(Radio radio) {
        mRadio.getStations().clear();
        mRadio.getStations().addAll(radio.getStations());
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        Station station = mRadio.getStations().get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (preferenceHelper.checkContainsStation(station)) {
                    preferenceHelper.deleteFavoriteStation(station);
                }

                Toaster.Toast(mContext, "FavoriteStationAdapter.OnLongClickListener.DELETE_FROM_"+mRadio.getGenre().toUpperCase());

                setData(preferenceHelper.getRadio());

                return false;
            }
        });
    }
}

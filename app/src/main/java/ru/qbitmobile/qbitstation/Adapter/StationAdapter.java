package ru.qbitmobile.qbitstation.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.BaseObject.Station;
import ru.qbitmobile.qbitstation.Helper.ImageHelper;
import ru.qbitmobile.qbitstation.R;

public class StationAdapter extends BaseAdapter {

    private Context mContext;
    private Radio mRadio;
    private LayoutInflater mLayoutInflater;
    private ImageView mImageViewStation;

    public StationAdapter (Context context, Radio radio){
        mRadio = radio;
        mContext = context;
        mLayoutInflater = (LayoutInflater) LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mRadio.getStations().size();
    }

    @Override
    public Object getItem(int position) {
        return mRadio.getStations().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null){
            view = mLayoutInflater.inflate(R.layout.example_list_item_station, parent, false);
        }

        Station station= mRadio.getStations().get(position);
        mImageViewStation = view.findViewById(R.id.ivStation);

        Bitmap bitmap = null;
        ((TextView) view.findViewById(R.id.tvStation)).setText(station.getName());
         Glide.with(view.getContext()).asBitmap()
                .load(mRadio.getStations().get(position).getImage())
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        mImageViewStation.setImageBitmap(ImageHelper.getRoundedCornerBitmap(resource, 50));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });

        return view;
    }
}

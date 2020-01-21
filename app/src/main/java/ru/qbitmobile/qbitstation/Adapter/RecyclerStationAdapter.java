package ru.qbitmobile.qbitstation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ru.qbitmobile.qbitstation.BaseObject.Station;
import ru.qbitmobile.qbitstation.R;

public class RecyclerStationAdapter extends RecyclerView.Adapter<RecyclerStationAdapter.ViewHolder>{

    private LayoutInflater mLayoutInflater;
    private List<Station> mStations;
    private Context mContext;

    public RecyclerStationAdapter(Context context, List<Station> stations) {
        this.mStations = stations;
        this.mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.example_list_item_station, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Station station = mStations.get(position);
        holder.textView.setText(station.getName());
        Glide.with(mContext)
                .load(mStations.get(position).getImage())
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mStations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageView;
        final TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivStation);
            textView = itemView.findViewById(R.id.tvStation);
        }
    }
}

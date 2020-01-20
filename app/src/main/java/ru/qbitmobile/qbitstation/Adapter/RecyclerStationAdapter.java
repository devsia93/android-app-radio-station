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

import java.util.ArrayList;
import java.util.Collection;

import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.BaseObject.Station;
import ru.qbitmobile.qbitstation.R;

public class RecyclerStationAdapter extends RecyclerView.Adapter<RecyclerStationAdapter.StationViewHolder> {

    private ArrayList<Station> stations = new ArrayList<>();
    private ArrayList<Station> mStations;
    private Context mContext;

    public RecyclerStationAdapter(ArrayList<Station> stations, Context context){
        mStations = stations;
        mContext = context;
    }

    private void setItems(Collection<Station> stations){
        this.stations.addAll(stations);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.example_list_item_station, parent, false);
        return new StationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StationViewHolder holder, int position) {
        Station station = mStations.get(position);

        TextView textView = holder.tvStation;
        textView.setText(station.getName());
        Glide.with(mContext)
                .load(station.getImage())
                .error(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.ivStation);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    // Предоставляет прямую ссылку на каждый View-компонент
    // Используется для кэширования View-компонентов и последующего быстрого доступа к ним
    class StationViewHolder extends RecyclerView.ViewHolder {
        // Ваш ViewHolder должен содержать переменные для всех
        // View-компонентов, которым вы хотите задавать какие-либо свойства
        // в процессе работы пользователя со списком

        // Мы также создали конструктор, который принимает на вход View-компонент строкИ
        // и ищет все дочерние компоненты

        public TextView tvStation;
        public ImageView ivStation;

        public StationViewHolder(View itemView) {
            super(itemView);
            tvStation = (TextView) itemView.findViewById(R.id.tvSatation);
            ivStation = (ImageView) itemView.findViewById(R.id.ivStation);
        }

    }
}

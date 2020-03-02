package ru.qbitmobile.qbitstation.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.adapter.FavoriteStationAdapter;
import ru.qbitmobile.qbitstation.adapter.RecyclerStationAdapter;
import ru.qbitmobile.qbitstation.baseObject.Radio;

/**
 * A simple {@link Fragment} subclass.
 */
public class StationsFragment extends Fragment {

    private Radio mRadio;

    private Context mContext;

    public RecyclerView mRecyclerView;
    private FavoriteStationAdapter mRecyclerStationAdapter;

    public StationsFragment(Context context, Radio radio) {
        mRadio = radio;
        mContext = context;
    }

    public StationsFragment(Context context, Radio radio, FavoriteStationAdapter adapter) {
        mRadio = radio;
        mContext = context;
        mRecyclerStationAdapter = adapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stations, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setNestedScrollingEnabled(false);

        if (mRadio.getGenre().equals("Избранное")) {
            mRecyclerView.setAdapter(mRecyclerStationAdapter);
        } else {
            mRecyclerView.setAdapter(new RecyclerStationAdapter(mContext, mRadio, mRecyclerStationAdapter));
        }

        return view;
    }
}


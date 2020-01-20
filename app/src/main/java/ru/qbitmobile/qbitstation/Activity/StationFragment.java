package ru.qbitmobile.qbitstation.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;

import ru.qbitmobile.qbitstation.Adapter.StationAdapter;
import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StationFragment extends ListFragment {

    private Radio mRadio;

    private TextView mTextViewGenre;

    private StationAdapter stationAdapter;

    public StationFragment(Radio radio) {
        this.mRadio = radio;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_station, container, false);
        // Inflate the layout for this fragment
        String stationName[] = (mRadio.getStationNames());


        stationAdapter = new StationAdapter(getContext(), mRadio);
        setListAdapter(stationAdapter);


/*        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stationName);
        setListAdapter(adapter);*/

        mTextViewGenre = view.findViewById(R.id.fragment_station_tvGenre);
        mTextViewGenre.setText(mRadio.getGenre());

        return view;
    }
}

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

import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StationFragment extends ListFragment {

    private Radio mRadio;

    private ListView listView;

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
        // Inflate the layout for this fragment
        ListAdapter adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_2, mRadio.getStations());
        setListAdapter(adapter);

        return inflater.inflate(R.layout.fragment_station, container, false);
    }
}

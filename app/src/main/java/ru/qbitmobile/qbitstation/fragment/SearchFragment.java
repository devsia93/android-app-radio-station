package ru.qbitmobile.qbitstation.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.adapter.FavoriteStationAdapter;
import ru.qbitmobile.qbitstation.adapter.FilterRecyclerStationAdapter;
import ru.qbitmobile.qbitstation.adapter.RecyclerStationAdapter;
import ru.qbitmobile.qbitstation.baseObject.Radio;
import ru.qbitmobile.qbitstation.baseObject.Station;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private List<Radio> mRadios;
    private List<Station> mStations;
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private FilterRecyclerStationAdapter mAdapter;

    private LinearLayout mLinearLayout;

    private String preSearchText;

    private static boolean isSearchable;
    private ArrayList<RecyclerStationAdapter> mRecyclerStationAdapters;

    public SearchFragment(List<Radio> radios, LinearLayout container, ArrayList<RecyclerStationAdapter> favoriteStationAdapters) {
        mRadios = radios;
        mStations = new ArrayList<>();
        mLinearLayout = container;
        mRecyclerStationAdapters = favoriteStationAdapters;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        setHasOptionsMenu(true);

        for (Radio radio : mRadios){
            if (!radio.getGenre().equals("Избранное"))
            mStations.addAll(radio.getStations());
        }

        Collections.sort(mStations, new Comparator<Station>() {
            @Override
            public int compare(Station o1, Station o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        mRecyclerView = view.findViewById(R.id.recyclerViewSearch);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new FilterRecyclerStationAdapter(inflater, mStations, view.getContext(), mRecyclerStationAdapters);
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = view.findViewById(R.id.toolbar_main);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        mSearchView = (SearchView) item.getActionView();
        mSearchView.setBackgroundResource(R.drawable.bg_grey_searchview);
        mSearchView.setQueryHint("Поиск");
        View v = mSearchView.findViewById(R.id.search_plate);
        v.setBackgroundColor(Color.TRANSPARENT);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    setVisibilityContainers(View.VISIBLE, View.GONE);
                } else {
                        setVisibilityContainers(View.GONE, View.VISIBLE);
                }

                mAdapter.getFilter().filter(newText);
                mRecyclerView.setItemViewCacheSize(mAdapter.getItemCount());
                return true;
            }

            private void setVisibilityContainers(int mainContainerVisibility, int bodyContainerVisibility) {
                mLinearLayout.setVisibility(mainContainerVisibility);
                mRecyclerView.setVisibility(bodyContainerVisibility);
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

}

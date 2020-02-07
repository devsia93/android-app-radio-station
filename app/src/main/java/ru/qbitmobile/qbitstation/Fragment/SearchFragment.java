package ru.qbitmobile.qbitstation.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import ru.qbitmobile.qbitstation.Adapter.FilterRecyclerStationAdapter;
import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.BaseObject.Station;
import ru.qbitmobile.qbitstation.Helper.KeyboardHelper;
import ru.qbitmobile.qbitstation.R;

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

    public SearchFragment(List<Radio> radios, LinearLayout container) {
        mRadios = radios;
        mStations = new ArrayList<>();
        mLinearLayout = container;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        setHasOptionsMenu(true);

        for(Radio radio : mRadios)
            mStations.addAll(radio.getStations());

        mRecyclerView = view.findViewById(R.id.recyclerViewSearch);
        mAdapter = new FilterRecyclerStationAdapter(inflater, mStations, view.getContext());
        mRecyclerView.setAdapter(mAdapter);

        Toolbar toolbar = view.findViewById(R.id.toolbar_main);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);

        mSearchView = (SearchView) item.getActionView();
        mSearchView.setBackgroundResource(R.drawable.bg_grey_searchview);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                KeyboardHelper.closeKeyboard(getContext());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    setVisibilityContainers(View.VISIBLE, View.GONE);
                } else {
                    setVisibilityContainers(View.GONE, View.VISIBLE);
                }

                mAdapter.getFilter().filter(newText);
                return false;
            }

            private void setVisibilityContainers(int mainContainerVisibility, int bodyContainerVisibility) {
                mLinearLayout.setVisibility(mainContainerVisibility);
                mRecyclerView.setVisibility(bodyContainerVisibility);
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

}

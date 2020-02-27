//package ru.qbitmobile.qbitstation.fragment;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//import ru.qbitmobile.qbitstation.R;
//import ru.qbitmobile.qbitstation.adapter.RecyclerStationAdapter;
//import ru.qbitmobile.qbitstation.baseObject.Station;
//import ru.qbitmobile.qbitstation.helper.PreferenceHelper;
//
//public class FavoriteFragment extends Fragment {
//
//    public RecyclerView mRecyclerView;
//    public RecyclerStationAdapter mRecyclerStationAdapter;
//    PreferenceHelper preferenceHelper;
//
//    public FavoriteFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_stations, container, false);
//
//        preferenceHelper = new PreferenceHelper(getContext());
//
//        ArrayList<Station> stations = preferenceHelper.getFavoriteListStations();
//
//        mRecyclerView = view.findViewById(R.id.recyclerView);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        mRecyclerStationAdapter = new RecyclerStationAdapter(getContext(), stations);
//        mRecyclerView.setAdapter(mRecyclerStationAdapter);
//
//        return view;
//    }
//}

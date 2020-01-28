package ru.qbitmobile.qbitstation.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import ru.qbitmobile.qbitstation.Adapter.RecyclerStationAdapter;
import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RadiosFragment extends Fragment {

    private Radio mRadio;
    private TextView tvGenre;
    public ExpandableLayout expandableLayout1;


    public RadiosFragment(Radio radio) {
        // Required empty public constructor
        mRadio = radio;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_radios, container, false);

        tvGenre = view.findViewById(R.id.fragment_radios_textview_genre);
        tvGenre.setText(mRadio.getGenre());
        expandableLayout1 = (ExpandableLayout) view.findViewById(R.id.expandableLayout1);
        tvGenre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandableLayout1.isExpanded())
                    expandableLayout1.toggle(); // toggle expand and collapse
                else expandableLayout1.expand();
            }
        });



        FragmentManager mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

       // mFragmentTransaction.add(R.id.fragment_radios_container, stationsFragment, mRadio.getGenre()).commit();

        return view;
    }


}

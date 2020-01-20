package ru.qbitmobile.qbitstation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.Helper.JSONHelper;
import ru.qbitmobile.qbitstation.R;

public class MainActivity extends FragmentActivity {

    LinearLayout llFromFragment;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    StationFragment stationFragment;

    RecyclerStationFragment mRecyclerStationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Radio> radioArray = new ArrayList<>();
        radioArray = (ArrayList<Radio>) JSONHelper.importFromJSON(getApplicationContext());




        if (radioArray != null) {

            for (Radio r : radioArray) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();

//                stationFragment = new StationFragment(r);
                mRecyclerStationFragment = new RecyclerStationFragment(r);
                mFragmentTransaction.add(R.id.main_container, mRecyclerStationFragment);
                Log.d("debug", r.getStations().get(0).getName());
                mFragmentTransaction.commit();

            }
        }


    }
}

package ru.qbitmobile.qbitstation.Activity;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;

import ru.qbitmobile.qbitstation.Adapter.RecyclerStationAdapter;
import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.Helper.JSONHelper;
import ru.qbitmobile.qbitstation.R;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends FragmentActivity {

    LinearLayout llFromFragment;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    StationFragment stationFragment;

    Toolbar mToolBar;

//    RecyclerStationFragment mRecyclerStationFragment;

    private void vibrate() {
        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<Radio> radioArray = new ArrayList<>();
        radioArray = (ArrayList<Radio>) JSONHelper.importFromJSON(getApplicationContext());
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerStationAdapter recyclerStationAdapter;

        if (radioArray != null) {
            for (Radio r : radioArray) {
               /* mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
//                stationFragment = new StationFragment(r);
                mRecyclerStationFragment = new RecyclerStationFragment(r);
                mFragmentTransaction.add(R.id.main_container, mRecyclerStationFragment);
                Log.d("debug", r.getStations().get(0).getName());
                mFragmentTransaction.commit();*/
                recyclerStationAdapter =  new RecyclerStationAdapter(this, r.getStations());
                recyclerView.setAdapter(recyclerStationAdapter);
            }
        }
    }
}

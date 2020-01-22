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

import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;
import java.util.ArrayList;

import ru.qbitmobile.qbitstation.Adapter.RecyclerStationAdapter;
import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.Fragment.RadiosFragment;
import ru.qbitmobile.qbitstation.Helper.JSONHelper;
import ru.qbitmobile.qbitstation.R;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends FragmentActivity {

    LinearLayout llFromFragment;
//    FragmentManager mFragmentManager;
//    FragmentTransaction mFragmentTransaction;

    StationFragment stationFragment;

    Toolbar mToolBar;

    private FirebaseAnalytics mFirebaseAnalytics;

//    RecyclerStationFragment mRecyclerStationFragment;

    private void vibrate() {
        ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(5);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        ArrayList<Radio> radioArray = new ArrayList<>();
        radioArray = (ArrayList<Radio>) JSONHelper.importFromJSON(getApplicationContext());

        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        if (radioArray != null) {
            for (Radio r : radioArray) {

//                stationFragment = new StationFragment(r);
                RadiosFragment radiosFragment = new RadiosFragment(this, r);
                mFragmentTransaction.add(R.id.main_container, radiosFragment, r.getGenre());
                Log.d("debug", r.getGenre());

            }
        }
        mFragmentTransaction.commit();
    }
}

package ru.qbitmobile.qbitstation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.Fragment.RadiosFragment;
import ru.qbitmobile.qbitstation.Fragment.StationsFragment;
import ru.qbitmobile.qbitstation.Helper.JSONHelper;
import ru.qbitmobile.qbitstation.R;

public class MainActivity extends AppCompatActivity {

    LinearLayout llFromFragment;
//    FragmentManager mFragmentManager;
//    FragmentTransaction mFragmentTransaction;

    LinearLayout mLinearLayout;

    private FirebaseAnalytics mFirebaseAnalytics;

    //    RecyclerStationFragment mRecyclerStationFragment;
    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            // Set the local night mode to some value
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        }

        mLinearLayout = findViewById(R.id.main_container);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        ArrayList<Radio> radioArray = new ArrayList<>();
        radioArray = (ArrayList<Radio>) JSONHelper.importFromJSON(getApplicationContext());



//        StationsFragment stationsFragment = new StationsFragment(this, radioArray);
        int i = 0;
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        if (radioArray != null) {
            for (Radio r : radioArray) {
                FragmentManager mFragmentManager = getSupportFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
               /* LinearLayout linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setId(LinearLayout.generateViewId());*/
                RadiosFragment radiosFragment = new RadiosFragment(r);
//                stationFragment = new StationFragment(r);
                mFragmentTransaction.add(/*linearLayout.getId()*/mLinearLayout.getId(), radiosFragment, r.getGenre());
                Log.d("debug", r.getGenre());
                mFragmentTransaction.commit();
                /*mLinearLayout.addView(linearLayout, i);*/
                i++;
            }
        }

    }
}

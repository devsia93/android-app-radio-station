package ru.qbitmobile.qbitstation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.Helper.JSONHelper;
import ru.qbitmobile.qbitstation.R;

public class MainActivity extends AppCompatActivity {

    LinearLayout llFromFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Radio> radioArray = new ArrayList<>();
        radioArray = (ArrayList<Radio>) JSONHelper.importFromJSON(getApplicationContext());

        llFromFragment = findViewById(R.id.activity_main_container_from_fragment);



        if (radioArray != null) {
            for (Radio r : radioArray) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                StationFragment stationFragment;
                stationFragment = new StationFragment(r);
                ft.add(llFromFragment.getId(), stationFragment);
                ft.commit();
                Log.d("debug", r.getGenre());
            }
        }
        else Log.d("debug", "no");

    }
}

package ru.qbitmobile.qbitstation.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import ru.qbitmobile.qbitstation.Adapter.StationAdapter;
import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.Fragment.RadiosFragment;
import ru.qbitmobile.qbitstation.Fragment.StationsFragment;
import ru.qbitmobile.qbitstation.Helper.AnimationRotate;
import ru.qbitmobile.qbitstation.Helper.JSONHelper;
import ru.qbitmobile.qbitstation.Helper.ReportHelper;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
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

        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ArrayList<Radio> radioArray = new ArrayList<>();
        radioArray = (ArrayList<Radio>) JSONHelper.importFromJSON(getApplicationContext());

        createListStations(radioArray);

        initialyzeAppMetrica(radioArray);

    }

    private void initialyzeAppMetrica(ArrayList<Radio> radioArray) {
        ReportHelper.setRadioList(radioArray);
    }

    private void createListStations(ArrayList<Radio> radioArray) {
        if (radioArray != null) {
            for (Radio r : radioArray) {
                View childLayout = new View(this);

                LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
                childLayout = inflater.inflate(R.layout.layout_child_conteiner, mLinearLayout, false);

                ImageView imageView = childLayout.findViewById(R.id.child_container_imageview_arrow);

                FragmentManager mFragmentManager = getSupportFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

                StationsFragment stationsFragment = new StationsFragment(this, r);

                ExpandableLayout expandableLayout = childLayout.findViewById(R.id.child_container_expandableLayout);
                expandableLayout.setId(View.generateViewId());
                expandableLayout.setInterpolator(new LinearInterpolator());

                LinearLayout linearLayout = childLayout.findViewById(R.id.child_container_main_container);
                linearLayout.setId(LinearLayout.generateViewId());
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (expandableLayout.isExpanded()) {
                            AnimationRotate.RotateArrow(imageView, expandableLayout.isExpanded());
                            expandableLayout.toggle();
                        } else {
                            AnimationRotate.RotateArrow(imageView, expandableLayout.isExpanded());
                            expandableLayout.expand();

                        }
                    }
                });

                LinearLayout linearLayoutContainer = childLayout.findViewById(R.id.child_container_container);
                linearLayoutContainer.setId(LinearLayout.generateViewId());

                TextView textViewGenre = childLayout.findViewById(R.id.child_container_textview_genre);
                textViewGenre.setId(View.generateViewId());
                textViewGenre.setText(r.getGenre());

                TextView textViewCountStations = childLayout.findViewById(R.id.child_container_textview_count_stations);
                textViewCountStations.setId(View.generateViewId());
                textViewCountStations.setText(String.valueOf(r.getStations().size()));

                mFragmentTransaction.add(linearLayoutContainer.getId(), stationsFragment, r.getGenre()).commit();
                Log.d("debug", r.getGenre());
                mLinearLayout.addView(linearLayout);

                expandableLayout.toggle();
                if (!expandableLayout.isExpanded())
                    imageView.setRotation(Const.CURRENT_ROTATE_ARROW);
            }
        }
    }
}


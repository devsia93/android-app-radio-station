package ru.qbitmobile.qbitstation.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.analytics.FirebaseAnalytics;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ru.qbitmobile.qbitstation.Adapter.RecyclerStationAdapter;
import ru.qbitmobile.qbitstation.BaseObject.Radio;
import ru.qbitmobile.qbitstation.BaseObject.Station;
import ru.qbitmobile.qbitstation.Const;

import ru.qbitmobile.qbitstation.Fragment.SearchFragment;
import ru.qbitmobile.qbitstation.Fragment.StationsFragment;
import ru.qbitmobile.qbitstation.Helper.AnimationRotate;
import ru.qbitmobile.qbitstation.Helper.JSONHelper;
import ru.qbitmobile.qbitstation.Helper.KeyboardHelper;
import ru.qbitmobile.qbitstation.Helper.MediaControllerHelper;
import ru.qbitmobile.qbitstation.Player.Player;
import ru.qbitmobile.qbitstation.Helper.ReportHelper;
import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.Controller.RadioStationController;
import ru.qbitmobile.qbitstation.Service.PlayerService;

public class MainActivity extends AppCompatActivity {

    LinearLayout mLinearLayout;

    private FirebaseAnalytics mFirebaseAnalytics;

    static {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(MediaControllerHelper.serviceConnection);
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

        MediaControllerHelper.onCreate(this);

        bindService(new Intent(this, PlayerService.class), MediaControllerHelper.serviceConnection, BIND_AUTO_CREATE);

        mLinearLayout = findViewById(R.id.main_container);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if (savedInstanceState == null) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        ArrayList<Radio> radioArray = (ArrayList<Radio>) JSONHelper.importFromJSON(getApplicationContext());
        RadioStationController.setListRadios(radioArray);

        SearchFragment searchFragment = new SearchFragment(radioArray, mLinearLayout);
        FragmentTransaction mFragmentTransaction = getSupportFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.container_from_toolbar, searchFragment).commit();

        initialyzeAppMetrica(radioArray);

        createListStations(radioArray);
    }

    private void initialyzeAppMetrica(ArrayList<Radio> radioArray) {
        ReportHelper.setRadioList(radioArray);
    }

    private void createListStations(ArrayList<Radio> radioArray) {

        if (radioArray != null) {
            for (Radio r : radioArray) {
                for (Station station : r.getStations()){
                    Glide.with(this).asBitmap().load(station.getImage()).into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            RadioStationController.setHashMap(station, resource);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {

                        }
                    });
                }
                RadioStationController.getListStations().addAll(r.getStations());

                LayoutInflater inflater = (LayoutInflater) this.getSystemService(this.LAYOUT_INFLATER_SERVICE);
                View childLayout = inflater.inflate(R.layout.layout_child_conteiner, mLinearLayout, false);

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
                        KeyboardHelper.closeKeyboard(getApplicationContext(), v);
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


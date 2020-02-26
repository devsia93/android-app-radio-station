package ru.qbitmobile.qbitstation.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.analytics.FirebaseAnalytics;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import ru.qbitmobile.qbitstation.Const;
import ru.qbitmobile.qbitstation.R;
import ru.qbitmobile.qbitstation.adapter.RecyclerStationAdapter;
import ru.qbitmobile.qbitstation.baseObject.Radio;
import ru.qbitmobile.qbitstation.baseObject.Station;
import ru.qbitmobile.qbitstation.controller.RadioStationController;
import ru.qbitmobile.qbitstation.fragment.SearchFragment;
import ru.qbitmobile.qbitstation.fragment.StationsFragment;
import ru.qbitmobile.qbitstation.helper.AnimationRotate;
import ru.qbitmobile.qbitstation.helper.AnimatorHelper;
import ru.qbitmobile.qbitstation.helper.JSONHelper;
import ru.qbitmobile.qbitstation.helper.KeyboardHelper;
import ru.qbitmobile.qbitstation.helper.MediaControllerHelper;
import ru.qbitmobile.qbitstation.helper.ReportHelper;
import ru.qbitmobile.qbitstation.helper.TinyDB;
import ru.qbitmobile.qbitstation.notification.CreateNotificationChannel;
import ru.qbitmobile.qbitstation.service.PlayerService;

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

        CreateNotificationChannel.create(this);

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

    @Override
    protected void onPause() {

        TinyDB tinydb = new TinyDB(this);

        ArrayList<Object> holderObjects = tinydb.getListObject("HOLDER", RecyclerStationAdapter.ViewHolder.class);
        if (AnimatorHelper.viewHolders == null)
            AnimatorHelper.viewHolders = new ArrayList<>();

        for(Object o : holderObjects)
            AnimatorHelper.viewHolders.add((RecyclerStationAdapter.ViewHolder) o);


        tinydb.putInt("POSITION", RadioStationController.getPosition());

        super.onPause();
    }

    @Override
    protected void onResume() {

        TinyDB tinydb = new TinyDB(this);

        ArrayList<Object> holderObjects = new ArrayList<Object>();

        if (AnimatorHelper.viewHolders != null) {
            for (RecyclerStationAdapter.ViewHolder v : AnimatorHelper.viewHolders)
                holderObjects.add((Object) v);

            RadioStationController.setPosition(tinydb.getInt("POSITION"));
        }

        if (AnimatorHelper.viewHolders != null && AnimatorHelper.viewHolders.size() > 0 && PlayerService.isPlaying)
            AnimatorHelper.startAnimation(RadioStationController.getPosition());

        super.onResume();
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


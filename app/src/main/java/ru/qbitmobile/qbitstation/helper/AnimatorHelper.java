package ru.qbitmobile.qbitstation.helper;

import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import ru.qbitmobile.qbitstation.adapter.BaseStationAdapter;

public class AnimatorHelper {

    private static AVLoadingIndicatorView preIndicator;

    public static ArrayList<BaseStationAdapter.ViewHolder> viewHolders;

    public static void startAnimation(AVLoadingIndicatorView indicatorView){
        if (indicatorView != null){
            if(preIndicator!=null) {
                preIndicator.setVisibility(View.INVISIBLE);
            }
            indicatorView.setVisibility(View.VISIBLE);
            preIndicator = indicatorView;
        }

    }

    public static void stopAnimation(AVLoadingIndicatorView indicatorView){
        if (indicatorView != null)
        {
            indicatorView.setVisibility(View.INVISIBLE);
        }
    }

    public static void startAnimation(int position){
        if (viewHolders != null) {
            startAnimation(viewHolders.get(position).playViewAnimation);
        }
    }

    public static void stopAnimation(int position){
        if (viewHolders != null)
            stopAnimation(viewHolders.get(position).playViewAnimation);
    }

    public static boolean getStatusAnimation(AVLoadingIndicatorView indicatorView){
        return indicatorView.getVisibility() == View.VISIBLE;
    }
}

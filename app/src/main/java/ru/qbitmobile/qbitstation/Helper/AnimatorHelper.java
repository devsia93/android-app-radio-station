package ru.qbitmobile.qbitstation.Helper;

import android.util.Log;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

public class AnimatorHelper {

    private static AVLoadingIndicatorView preIndicator;

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
}

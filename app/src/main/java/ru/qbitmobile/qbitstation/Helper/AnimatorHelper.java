package ru.qbitmobile.qbitstation.Helper;

import android.util.Log;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;

public class AnimatorHelper {
    private static AVLoadingIndicatorView indicatorView;
    public AnimatorHelper(AVLoadingIndicatorView indicatorView){

        if (this.indicatorView != null) {
            this.indicatorView.setVisibility(View.INVISIBLE);
        }
        this.indicatorView = indicatorView;
    }

    public void startAnimation(){
        if (indicatorView != null){
            indicatorView.setVisibility(View.VISIBLE);
        }

    }

    public void stopAnimation(){
        if (indicatorView != null)
        {
            indicatorView.setVisibility(View.INVISIBLE);
        }
    }
}

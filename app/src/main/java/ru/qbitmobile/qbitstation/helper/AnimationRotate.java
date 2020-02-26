package ru.qbitmobile.qbitstation.helper;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class AnimationRotate {
    public static void RotateArrow(ImageView imageView, boolean isRotate) {
        RotateAnimation rotateAnimation;
        if (isRotate) {
            rotateAnimation = new RotateAnimation(90.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            rotateAnimation = new RotateAnimation(0.0f, 90.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setDuration(250);
        rotateAnimation.setFillAfter(true);
        imageView.startAnimation(rotateAnimation);
    }

}

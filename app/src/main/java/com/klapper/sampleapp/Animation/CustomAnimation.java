package com.klapper.sampleapp.Animation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

/**
 * Created by c1103304 on 2018/3/8.
 */

public class CustomAnimation {
    public static void singleClick(View v){
        AnimatorSet ans = new AnimatorSet();
        ObjectAnimator[] oan = new ObjectAnimator[3];
        oan[0] = ObjectAnimator.ofFloat(v, "translationX",-3,3,0);
        oan[1] = ObjectAnimator.ofFloat(v, "scaleX", 0.9f,1f);
        oan[2] = ObjectAnimator.ofFloat(v, "scaleY", 0.9f,1f);
        ans.setInterpolator(new AnticipateOvershootInterpolator());
        ans.playTogether(oan);
        ans.setDuration(200);
        ans.start();
    }
    public static void showLayout(View v){
        if(v.getVisibility()==View.GONE) v.setVisibility(View.VISIBLE);
        AnimatorSet ans = new AnimatorSet();
        ObjectAnimator[] oan = new ObjectAnimator[2];
        oan[0] = ObjectAnimator.ofFloat(v, "scaleX", 0,1);
        oan[1] = ObjectAnimator.ofFloat(v, "scaleY", 0,1);
        ans.setInterpolator(new FastOutSlowInInterpolator());
        ans.playTogether(oan);
        ans.setDuration(275);
        ans.start();
    }
}

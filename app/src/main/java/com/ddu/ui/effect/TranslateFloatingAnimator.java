package com.ddu.ui.effect;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.annotation.NonNull;

import com.ddu.ui.view.FloatingTextView;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;

/**
 * Author UFreedom
 * 
 */
public class TranslateFloatingAnimator extends ReboundFloatingAnimator {


    private float translateY;
    private long duration;

    public TranslateFloatingAnimator() {
        translateY = -200f;
        duration = 1500;
    }
    

    public TranslateFloatingAnimator(float translateY, long duration) {
        this.translateY = translateY;
        this.duration = duration;
    }

    @Override
    public void applyFloatingAnimation(@NonNull final FloatingTextView view) {

        view.setTranslationY(0);
        view.setAlpha(1f);
        view.setScaleX(0f);
        view.setScaleY(0f);
        Spring scaleAnim = createSpringByBouncinessAndSpeed(10, 15)
                .addListener(new SimpleSpringListener() {
                    @Override
                    public void onSpringUpdate(@NonNull Spring spring) {
                        float transition = transition((float) spring.getCurrentValue(), 0.0f, 1.0f);
                        view.setScaleX(transition);
                        view.setScaleY(transition);
                    }
                });


        ValueAnimator translateAnimator = ObjectAnimator.ofFloat(0, translateY);
        translateAnimator.setDuration(duration);
        translateAnimator.setStartDelay(50);
        translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                view.setTranslationY((Float) valueAnimator.getAnimatedValue());
            }
        });
        translateAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setTranslationY(0);
                view.setAlpha(0f);

            }
        });


        ValueAnimator alphaAnimator = ObjectAnimator.ofFloat(1.0f, 0.0f);
        alphaAnimator.setDuration(duration);
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator valueAnimator) {
                view.setAlpha((Float) valueAnimator.getAnimatedValue());
            }
        });


        scaleAnim.setEndValue(1f);
        alphaAnimator.start();
        translateAnimator.start();
    }
}
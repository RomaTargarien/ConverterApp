package com.example.converterapp.util.ext

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Button

fun View.animateColorTransition(
    startColor: Int,
    endColor: Int,
    animationDuration: Long = 500L
) {
    ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor).apply {
        duration = animationDuration
        interpolator = DecelerateInterpolator()
        addUpdateListener {
            val color = it.animatedValue as Int
            when (this@animateColorTransition) {
                is ViewGroup -> {
                    setBackgroundColor(color)
                }
                is Button -> {
                    background.setTint(color)
                }
                else -> {
                }
            }
        }
    }.start()
}
package com.impulsed.moviespec.presentation.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo

import com.impulsed.moviespec.BuildConfig

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@SuppressLint("SourceLockedOrientationActivity")
fun Context.setPortrait() {
    val activity = this.findActivity()
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

fun String.formatUrlForGlide() : String {
    return BuildConfig.BASE_IMAGE_URL + "w154" + this
}

fun String.formatUrlForPosterGlide() : String {
    return BuildConfig.BASE_IMAGE_URL + "original" + this
}

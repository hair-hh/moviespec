package com.impulsed.moviespec.presentation.utility

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo

import com.impulsed.moviespec.BuildConfig
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

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

fun String.trimDateToYear() : String {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    val date = LocalDate.parse(this)
    return date.year.toString()
}

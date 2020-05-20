package com.appspell.onboarding

import android.os.Parcelable
import android.view.Gravity
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.appspell.onboardinglibrary.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OnboardingConfig(
    @ColorRes val backgroundColorRes: Int? = null,

    // indicators
    @DrawableRes val indicatorDrawableRes: Int? = null,
    @DrawableRes val indicatorInactiveDrawableRes: Int? = null,
    val indicatorGravity: Int = Gravity.START or Gravity.CENTER_VERTICAL,

    // next button
    val nextButtonText: String? = null,
    @StringRes val nextButtonTextResId: Int? = R.string.onboarding_next_button,
    @ColorRes val nextButtonTextColor: Int? = null,

    // Get Started button
    val nextButtonGetStartedText: String? = null,
    @StringRes val nextButtonGetStartedTextResId: Int? = R.string.onboarding_next_button_last,
    @ColorRes val nextButtonLastItemTextColor: Int? = null,

    val animation: AnimationType = AnimationType.SLIDE,

    val pages: List<Page>
) : Parcelable {

    @Parcelize
    data class Page(
        @ColorRes val backgroundColorRes: Int? = null,

        val header: String? = null,
        val headerRes: Int? = null,
        @ColorRes val headerTextColorRes: Int? = null,

        @DrawableRes val imageRes: Int? = null,

        val title: String? = null,
        val titleRes: Int? = null,
        @ColorRes val titleTextColorRes: Int? = null,

        val description: String? = null,
        val descriptionRes: Int? = null,
        @ColorRes val descriptionTextColorRes: Int? = null
    ) : Parcelable

    enum class AnimationType {
        ZOOM, DEPTH, ALPHA, FLIP, POP, SLIDE
    }
}
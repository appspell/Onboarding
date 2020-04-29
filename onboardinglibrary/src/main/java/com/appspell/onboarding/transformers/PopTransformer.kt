package com.appspell.onboarding.transformers

import android.view.View
import androidx.annotation.RequiresApi
import androidx.viewpager2.widget.ViewPager2

private const val OFFSET = 0.05f

@RequiresApi(21)
class PopTransformer : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        view.apply {
            val pageWidth = width
            val pageHeight = height
            when {
                position < -1 -> { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    alpha = 0f
                }
                position <= 0 -> { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    alpha = 1f + position

                    val offset = OFFSET * Math.abs(position)
                    translationX = (pageWidth * -position)
                    translationY = pageHeight * -offset
                    translationZ = 0f

                    scaleX = 1f
                    scaleY = 1f
                }
                position <= 1 -> { // (0,1]
                    // Fade the page out.
                    alpha = 1 - position

                    // Counteract the default slide transition
                    val offset = OFFSET * Math.abs(position)
                    translationX = pageWidth * -position
                    translationY = pageHeight * offset
                    // Move it behind the left page
                    translationZ = -1f

                    // Scale the page down (between MIN_SCALE and 1)
                    scaleX = 1f
                    scaleY = 1f
                }
                else -> { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    alpha = 0f
                }
            }
        }
    }
}
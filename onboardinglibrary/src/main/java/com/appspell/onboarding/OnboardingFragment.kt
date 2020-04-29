package com.appspell.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.appspell.onboarding.transformers.*
import com.appspell.onboardinglibrary.R
import kotlinx.android.synthetic.main.fragment_onboarding.*
import me.relex.circleindicator.Config

private const val EXTRA_CONFIG = "com.appspell.onboarding.EXTRA_CONFIG"

class OnboardingFragment : Fragment() {

    companion object {
        fun getInstance(config: OnboardingConfig) = OnboardingFragment()
            .apply {
                arguments = Bundle().apply { putParcelable(EXTRA_CONFIG, config) }
            }
    }

    var onNavigationListener: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applyConfig(view)

        initViews()
    }

    private fun applyConfig(view: View) {
        val config = arguments?.getParcelable(EXTRA_CONFIG) as? OnboardingConfig
        if (config?.backgroundColorRes != null) {
            view.setBackgroundResource(config.backgroundColorRes)
        }

        updateNextButtonText()

        // indicator
        val indicatorConfig = Config.Builder()
        if (config?.indicatorDrawableRes != null) {
            indicatorConfig.drawable(config.indicatorDrawableRes)
        }
        if (config?.indicatorInactiveDrawableRes != null) {
            indicatorConfig.drawableUnselected(config.indicatorInactiveDrawableRes)
        }
        if (config?.indicatorGravity != null) {
            indicatorConfig.gravity(config.indicatorGravity)
        }
        onboardingIndicator.initialize(indicatorConfig.build())
    }

    private fun updateNextButtonText() {
        val config = arguments?.getParcelable(EXTRA_CONFIG) as? OnboardingConfig

        if (config?.nextButtonGetStartedTextResId != null) {
            onboardingGetStartedBtn.setText(config.nextButtonGetStartedTextResId)
        }
        if (config?.nextButtonGetStartedText != null) {
            onboardingGetStartedBtn.text = config.nextButtonGetStartedText
        }
        if (config?.nextButtonLastItemTextColor != null) {
            onboardingGetStartedBtn.setTextColor(
                ContextCompat.getColor(
                    onboardingGetStartedBtn.context,
                    config.nextButtonLastItemTextColor
                )
            )
        }

        if (config?.nextButtonTextResId != null) {
            onboardingNextBtn.setText(config.nextButtonTextResId)
        }
        if (config?.nextButtonText != null) {
            onboardingNextBtn.text = config.nextButtonText
        }
        if (config?.nextButtonTextColor != null) {
            onboardingNextBtn.setTextColor(
                ContextCompat.getColor(
                    onboardingNextBtn.context,
                    config.nextButtonTextColor
                )
            )
        }
    }

    private fun initViews() {
        val config = arguments?.getParcelable(EXTRA_CONFIG) as? OnboardingConfig

        if (activity == null) {
            throw Exception("Onboarding fragment should be inside FragmentActivity")
        }

        val pagerAdapter = ScreenSlidePagerAdapter(activity!!, config?.pages ?: emptyList())
        onboardingPager.adapter = pagerAdapter

        when (config?.animation) {
            OnboardingConfig.AnimationType.ZOOM -> onboardingPager.setPageTransformer(
                ZoomTransformer()
            )
            OnboardingConfig.AnimationType.DEPTH -> onboardingPager.setPageTransformer(
                DepthPageTransformer()
            )
            OnboardingConfig.AnimationType.ALPHA -> onboardingPager.setPageTransformer(
                AlphaTransformer()
            )
            OnboardingConfig.AnimationType.POP -> onboardingPager.setPageTransformer(
                PopTransformer()
            )
            OnboardingConfig.AnimationType.FLIP -> onboardingPager.setPageTransformer(
                FlipTransformer()
            )
            else -> {
            }
        }

        onboardingIndicator.setViewPager(onboardingPager)
        pagerAdapter.registerAdapterDataObserver(onboardingIndicator.adapterDataObserver)

        onboardingNextBtn.setOnClickListener { onNextClick() }
        onboardingGetStartedBtn.setOnClickListener { onNavigationListener?.invoke() }

        onboardingPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                onScroll(position)
            }
        })
        onScroll(0)
    }

    private fun onScroll(position: Int) {
        val max = onboardingPager.adapter?.itemCount ?: 0

        if (position == max - 1) {
            ViewCompat.animate(onboardingNextBtn).alpha(0f)
            ViewCompat.animate(onboardingGetStartedBtn).alpha(1f)
            onboardingNextBtn.visibility = View.GONE
            onboardingGetStartedBtn.visibility = View.VISIBLE
        } else {
            ViewCompat.animate(onboardingNextBtn).alpha(1f)
            ViewCompat.animate(onboardingGetStartedBtn).alpha(0f)
            onboardingNextBtn.visibility = View.VISIBLE
            onboardingGetStartedBtn.visibility = View.GONE
        }
    }

    private fun onNextClick() {
        val current = onboardingPager.currentItem
        onboardingPager.currentItem = current + 1
    }

    private inner class ScreenSlidePagerAdapter(
        activity: FragmentActivity,
        val items: List<OnboardingConfig.Page>
    ) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = items.size

        override fun createFragment(position: Int): Fragment =
            OnboardingFragmentPage.getInstance(items[position])
    }
}
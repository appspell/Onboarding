package com.appspell.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.appspell.onboardinglibrary.R
import kotlinx.android.synthetic.main.fragment_onboarding_page.view.*

private const val EXTRA_CONFIG = "com.appspell.onboarding.EXTRA_PAGE_CONFIG"

class OnboardingFragmentPage : Fragment() {

    companion object {
        fun getInstance(config: OnboardingConfig.Page) = OnboardingFragmentPage()
            .apply {
                arguments = Bundle().apply { putParcelable(EXTRA_CONFIG, config) }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val config = arguments?.getParcelable(EXTRA_CONFIG) as? OnboardingConfig.Page

        if (config?.backgroundColorRes != null) {
            view.setBackgroundResource(config.backgroundColorRes)
        }

        // header
        with(view.onboardingPageHeader) {
            visibility = View.GONE
            if (!config?.header.isNullOrEmpty()) {
                text = config?.header
                visibility = View.VISIBLE
            }
            if (config?.headerRes != null) {
                setText(config.headerRes)
                visibility = View.VISIBLE
            }
            if (config?.headerTextColorRes != null) {
                val color =
                    ResourcesCompat.getColor(resources, config.headerTextColorRes, context.theme)
                setTextColor(color)
            }
        }

        // image
        with(view.onboardingPageImage) {
            visibility = View.INVISIBLE
            if (config?.imageRes != null) {
                setImageResource(config.imageRes)
                visibility = View.VISIBLE
            }
        }

        // title
        with(view.onboardingPageTitle) {
            visibility = View.GONE
            if (!config?.title.isNullOrEmpty()) {
                text = config?.title
                visibility = View.VISIBLE
            }
            if (config?.titleRes != null) {
                setText(config.titleRes)
                visibility = View.VISIBLE
            }
            if (config?.titleTextColorRes != null) {
                val color =
                    ResourcesCompat.getColor(resources, config.titleTextColorRes, context.theme)
                setTextColor(color)
            }
        }

        // description
        with(view.onboardingPageDescription) {
            visibility = View.GONE
            if (!config?.description.isNullOrEmpty()) {
                text = config?.description
                visibility = View.VISIBLE
            }
            if (config?.descriptionRes != null) {
                setText(config.descriptionRes)
                visibility = View.VISIBLE
            }
            if (config?.descriptionTextColorRes != null) {
                val color =
                    ResourcesCompat.getColor(
                        resources,
                        config.descriptionTextColorRes,
                        context.theme
                    )
                setTextColor(color)
            }
        }
    }
}
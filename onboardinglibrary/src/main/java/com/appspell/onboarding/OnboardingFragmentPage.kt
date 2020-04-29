package com.appspell.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.appspell.onboardinglibrary.R

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
    }
}
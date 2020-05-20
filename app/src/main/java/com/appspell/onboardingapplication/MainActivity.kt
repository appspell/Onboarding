package com.appspell.onboardingapplication

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.appspell.onboarding.OnboardingConfig
import com.appspell.onboarding.OnboardingFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val config = OnboardingConfig(
            backgroundColorRes = R.color.colorAccent,
            indicatorDrawableRes = R.drawable.custom_indicator,
            animation = OnboardingConfig.AnimationType.SLIDE,
            pages = listOf(
                OnboardingConfig.Page(
                    backgroundColorRes = R.color.colorPrimary,
                    header = "string header",
                    imageRes = R.drawable.ic_timeline_black_24dp,
                    title = "string title",
                    description = "string description"
                ),
                OnboardingConfig.Page(
                    backgroundColorRes = R.color.colorPrimaryDark,
                    headerRes = R.string.header,
                    imageRes = R.drawable.ic_timeline_black_24dp,
                    titleRes = R.string.title,
                    descriptionRes = R.string.description
                ),
                OnboardingConfig.Page(
                    backgroundColorRes = R.color.colorAccent,
                    header = "short header",
                    description = "Long text is here. Long text is here. Long text is here. Long text is here. Long text is here. Long text is here. Long text is here. "
                ),
                OnboardingConfig.Page(
                    backgroundColorRes = R.color.colorPrimaryDark,
                    title = "title",
                    titleTextColorRes = R.color.colorAccent,
                    description = "Long text is here. Long text is here. Long text is here. Long text is here. Long text is here. Long text is here. Long text is here. ",
                    descriptionTextColorRes = R.color.colorAccent
                )
            )
        )

        val onboardingFragment = OnboardingFragment.getInstance(config)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, onboardingFragment)
            .commit()

        onboardingFragment.onNavigationListener = {
            // Open page
            Toast.makeText(this, "OPEN", Toast.LENGTH_SHORT).show()
        }
    }
}

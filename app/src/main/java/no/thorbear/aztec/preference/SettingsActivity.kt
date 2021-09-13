package no.thorbear.aztec.preference

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import no.thorbear.aztec.R

/**
 * Hosts the preference fragment to configure settings.
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.setTitle(R.string.pref_screen_title_live_preview)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.settings_container,
                LivePreviewPreferenceFragment()
            )
            .commit()
    }
}

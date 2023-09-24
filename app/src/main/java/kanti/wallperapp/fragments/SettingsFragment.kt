package kanti.wallperapp.fragments

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import kanti.wallperapp.R
import kanti.wallperapp.view.changeDarkMode

class SettingsFragment : PreferenceFragmentCompat() {

	override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
		setPreferencesFromResource(R.xml.preference_main, rootKey)
		findPreference<ListPreference>(getString(R.string.preference_theme_key))?.apply {
			onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
				val value: String = newValue as String
				val listPreference = preference as ListPreference

				if (listPreference.value == value)
					return@OnPreferenceChangeListener false

				context.changeDarkMode(value)
				return@OnPreferenceChangeListener true
			}
		}
	}

}
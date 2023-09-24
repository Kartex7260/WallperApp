package kanti.wallperapp.view

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import kanti.wallperapp.R

fun Context.changeDarkMode(value: String) {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
		changeDarkModeApi31(value)
		true
	}
	else {
		changeDarkModeApi30(value)
		true
	}
}

fun Context.changeDarkModeApi30(newValue: String) {
	when (newValue) {
		getString(R.string.preference_theme_value_light) -> {
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
		}
		getString(R.string.preference_theme_value_dark) -> {
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
		}
		getString(R.string.preference_theme_value_as_system) -> {
			AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
		}
	}
}

@RequiresApi(Build.VERSION_CODES.S)
fun Context.changeDarkModeApi31(newValue: String) {
	val uiManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
	when (newValue) {
		getString(R.string.preference_theme_value_light) -> {
			uiManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_NO)
		}
		getString(R.string.preference_theme_value_dark) -> {
			uiManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_YES)
		}
		getString(R.string.preference_theme_value_as_system) -> {
			uiManager.setApplicationNightMode(UiModeManager.MODE_NIGHT_AUTO)
		}
	}
}
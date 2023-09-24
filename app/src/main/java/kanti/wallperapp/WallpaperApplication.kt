package kanti.wallperapp

import android.app.Application
import android.os.Build
import androidx.preference.PreferenceManager
import coil.Coil
import coil.ImageLoader
import dagger.hilt.android.HiltAndroidApp
import kanti.wallperapp.view.changeDarkModeApi30
import javax.inject.Inject

@HiltAndroidApp
class WallpaperApplication : Application() {

	@Inject lateinit var imageLoader: ImageLoader

	override fun onCreate() {
		super.onCreate()
		Coil.setImageLoader(imageLoader)

		if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
			val preferences = PreferenceManager.getDefaultSharedPreferences(this)
			val darkMode = preferences.getString(
				getString(R.string.preference_theme_key),
				getString(R.string.preference_theme_value_as_system)
			) as String
			changeDarkModeApi30(darkMode)
		}
	}

}
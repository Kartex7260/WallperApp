package kanti.wallperapp.viewmodel

import android.app.WallpaperManager
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kanti.wallperapp.viewmodel.uistate.WallpaperInstallingUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class WallpaperInstallingViewModel : ViewModel() {

	private lateinit var wallpaperBitmap: Bitmap

	private val _installationStatusLiveData = MutableLiveData<WallpaperInstallingUiState>()
	val installingStatusLiveData: LiveData<WallpaperInstallingUiState> = _installationStatusLiveData

	fun setDrawable(drawable: Drawable) {
		wallpaperBitmap = drawable.toBitmap()
	}

	fun installWallpaperTo(context: Context, which: Int) {
		viewModelScope.launch(Dispatchers.Default + SupervisorJob()) {
			try {
				installWallpaper(context, which)
				_installationStatusLiveData.postValue(WallpaperInstallingUiState(true))
			} catch (ex: Exception) {
				_installationStatusLiveData.postValue(WallpaperInstallingUiState(false))
			}
		}
	}

	private fun installWallpaper(context: Context, which: Int) {
		WallpaperManager.getInstance(context)
			.setBitmap(
				wallpaperBitmap,
				getMidVisibleCorp(wallpaperBitmap),
				true,
				when (which) {
					0 -> WallpaperManager.FLAG_LOCK
					1 -> WallpaperManager.FLAG_SYSTEM
					else -> WallpaperManager.FLAG_LOCK + WallpaperManager.FLAG_SYSTEM
				}
			)
	}

	private fun getMidVisibleCorp(bitmap: Bitmap): Rect {
		val displayMetrics = Resources.getSystem().displayMetrics
		val left = if (bitmap.width > displayMetrics.widthPixels)
			(bitmap.width - displayMetrics.widthPixels) / 2
		else
			bitmap.width
		return Rect(
			left,
			0,
			left + displayMetrics.widthPixels,
			bitmap.height
		)
	}

}
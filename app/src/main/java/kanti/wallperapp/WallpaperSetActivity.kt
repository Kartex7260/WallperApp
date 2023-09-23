package kanti.wallperapp

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.databinding.ActivityWallpaperSetBinding
import kanti.wallperapp.net.largeImageRequest

class WallpaperSetActivity : AppCompatActivity() {

	private lateinit var view: ActivityWallpaperSetBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		view = ActivityWallpaperSetBinding.inflate(layoutInflater)
		setContentView(view.root)

		val imageData = getImageData()
		title = imageData.title
		val imageRequest = largeImageRequest(this, imageData, view.imageViewWallpaper)
		imageLoader.enqueue(imageRequest)

		view.buttonSetWallpaper.setOnClickListener {
			val wallpaperBitmap = view.imageViewWallpaper.drawable.toBitmap()
			WallpaperManager.getInstance(applicationContext)
				.setBitmap(
					wallpaperBitmap,
					getMidVisibleCorp(wallpaperBitmap),
					true
				)
		}
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

	private fun getImageData(): ImageData {
		val imageTitle = intent.getStringExtra(EXTRA_IMAGE_TITLE)
		val imageLink = intent.getStringExtra(EXTRA_IMAGE_LINK)

		if (imageLink == null || imageTitle == null) {
			finish()
			return ImageData("", "")
		}
		return ImageData(imageTitle, imageLink)
	}

	companion object {

		private const val EXTRA_IMAGE_TITLE = "imageTitle"
		private const val EXTRA_IMAGE_LINK = "imageLink"

		fun startActivity(context: Context, imageData: ImageData) {
			val intent = Intent(context, WallpaperSetActivity::class.java).apply {
				putExtra(EXTRA_IMAGE_TITLE, imageData.title)
				putExtra(EXTRA_IMAGE_LINK, imageData.link)
			}
			context.startActivity(intent)
		}

	}

}
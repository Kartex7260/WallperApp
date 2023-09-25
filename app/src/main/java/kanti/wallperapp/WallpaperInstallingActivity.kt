package kanti.wallperapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.imageLoader
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.databinding.ActivityWallpaperSetBinding
import kanti.wallperapp.fragments.WallpaperInstallingDialogFragment
import kanti.wallperapp.net.largeImageRequest
import kanti.wallperapp.viewmodel.WallpaperInstallingViewModel

class WallpaperInstallingActivity : AppCompatActivity() {

	private lateinit var view: ActivityWallpaperSetBinding
	private val viewModel by viewModels<WallpaperInstallingViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		view = ActivityWallpaperSetBinding.inflate(layoutInflater)
		setContentView(view.root)

		val imageData = getImageData()
		title = imageData.title
		val imageRequest = largeImageRequest(this, imageData, view.imageViewWallpaper)
		imageLoader.enqueue(imageRequest)

		view.buttonSetWallpaper.setOnClickListener {
			viewModel.setDrawable(view.imageViewWallpaper.drawable)
			val dialogFragment = WallpaperInstallingDialogFragment()
			dialogFragment.show(supportFragmentManager, null)
		}

		viewModel.installingStatusLiveData.observe(this) {
			Toast.makeText(this,
				if (it.installingSuccess) {
					getString(R.string.wallpaper_install_success)
				} else {
					getString(R.string.wallpaper_install_fail)
				},
				Toast.LENGTH_SHORT
			).show()
		}
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
			val intent = Intent(context, WallpaperInstallingActivity::class.java).apply {
				putExtra(EXTRA_IMAGE_TITLE, imageData.title)
				putExtra(EXTRA_IMAGE_LINK, imageData.link)
			}
			context.startActivity(intent)
		}

	}

}
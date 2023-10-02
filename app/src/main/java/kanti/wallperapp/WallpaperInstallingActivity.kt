package kanti.wallperapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.imageLoader
import dagger.hilt.android.AndroidEntryPoint
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.databinding.ActivityWallpaperSetBinding
import kanti.wallperapp.domain.OnFavourite
import kanti.wallperapp.fragments.WallpaperInstallingDialogFragment
import kanti.wallperapp.net.largeImageRequest
import kanti.wallperapp.viewmodel.WallpaperInstallingViewModel

@AndroidEntryPoint
class WallpaperInstallingActivity : AppCompatActivity() {

	private lateinit var view: ActivityWallpaperSetBinding
	private val viewModel by viewModels<WallpaperInstallingViewModel>()

	private lateinit var imageHolder: ImageHolder

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		view = ActivityWallpaperSetBinding.inflate(layoutInflater)
		setContentView(view.root)

		val imageData = getExtraImageData()
		if (imageData == null) {
			finish()
			return
		}

		imageHolder = ImageHolder(imageData, viewModel)

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

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.menu_wallpaper_installing_option, menu)
		imageHolder.menuItem = menu.findItem(R.id.menu_wallpaper_installing_option_favourite)
		imageHolder.updateButton()
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		return when (item.itemId) {
			R.id.menu_wallpaper_installing_option_favourite -> {
				imageHolder.switchFavourite()
				true
			}
			else -> super.onOptionsItemSelected(item)
		}
	}

	private fun getExtraImageData(): ImageData? {
		val title = intent.getStringExtra(EXTRA_IMAGE_TITLE)
		val link = intent.getStringExtra(EXTRA_IMAGE_LINK)
		val favourite = intent.getBooleanExtra(EXTRA_IMAGE_FAVOURITE, false)
		val position = intent.getIntExtra(EXTRA_IMAGE_POSITION, -1)

		if (link == null || title == null) {
			return null
		}
		return ImageData(title, link, favourite, position)
	}

	companion object {

		private const val EXTRA_IMAGE_TITLE = "imageTitle"
		private const val EXTRA_IMAGE_LINK = "imageLink"
		private const val EXTRA_IMAGE_FAVOURITE = "imageFavourite"
		private const val EXTRA_IMAGE_POSITION = "imagePosition"

		fun startActivity(context: Context, imageData: ImageData) {
			val intent = Intent(context, WallpaperInstallingActivity::class.java).apply {
				putExtra(EXTRA_IMAGE_TITLE, imageData.title)
				putExtra(EXTRA_IMAGE_LINK, imageData.link)
				putExtra(EXTRA_IMAGE_FAVOURITE, imageData.favourite)
				putExtra(EXTRA_IMAGE_POSITION, imageData.position)
			}
			context.startActivity(intent)
		}

	}

}

private class ImageHolder(
	val image: ImageData,
	private val onFavourite: OnFavourite<ImageData>,
	var menuItem: MenuItem? = null
) {

	init {
		updateButton()
	}

	fun switchFavourite() {
		image.favourite = !image.favourite
		onFavourite.onFavourite(image)
		updateButton()
	}

	fun updateButton() {
		menuItem?.apply {
			if (image.favourite) {
				setIcon(R.drawable.baseline_star_24)
			} else {
				setIcon(R.drawable.baseline_star_outline_24)
			}
		}
	}

}
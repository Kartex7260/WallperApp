package kanti.wallperapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import coil.imageLoader
import dagger.hilt.android.AndroidEntryPoint
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.RepositoryResultType
import kanti.wallperapp.databinding.ActivityImagesBinding
import kanti.wallperapp.net.smallImageRequest
import kanti.wallperapp.view.ImagesRecyclerAdapter
import kanti.wallperapp.viewmodel.FavouriteTagsViewModel
import kanti.wallperapp.viewmodel.FavouriteViewModel
import kanti.wallperapp.viewmodel.ImagesViewModel

@AndroidEntryPoint
class ImagesActivity : AppCompatActivity() {

	private val columnCount = 3

	private lateinit var view: ActivityImagesBinding
	private val viewModel: ImagesViewModel by viewModels()
	private val favouriteViewModel: FavouriteTagsViewModel by viewModels()

	private lateinit var tagHolder: TagHolder

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		view = ActivityImagesBinding.inflate(layoutInflater)
		setContentView(view.root)

		val tag = getTag()
		if (tag == null) {
			finish()
			return
		}
		tagHolder = TagHolder(tag, favouriteViewModel)

		view.recyclerViewImages.layoutManager = GridLayoutManager(this, columnCount)

		viewModel.imagesLinksLiveData.observe(this) { imagesUiState ->
			if (imagesUiState.process || imagesUiState.images == null) {
				view.recyclerViewImages.adapter = null
				view.progressBarImages.visibility = View.VISIBLE
				return@observe
			}

			view.progressBarImages.visibility = View.INVISIBLE
			view.recyclerViewImages.adapter = ImagesRecyclerAdapter(
				imagesUiState.images.data ?: listOf(),
				::onClickRecyclerImage,
				::onBindImage
			)

			when (imagesUiState.images.resultType) {
				RepositoryResultType.NotConnection -> {
					Toast.makeText(
						this@ImagesActivity,
						getString(R.string.net_error_connection),
						Toast.LENGTH_SHORT
					).show()
				}
				RepositoryResultType.Fail -> {
					Toast.makeText(
						this@ImagesActivity,
						getString(R.string.net_unexpected_error),
						Toast.LENGTH_SHORT
					).show()
				}
				else -> {}
			}
		}

		title = tagHolder.tag.displayName
		updateImages()
	}

	private fun onClickRecyclerImage(imageData: ImageData) {
		WallpaperInstallingActivity.startActivity(this, imageData)
	}

	private fun onBindImage(imageView: ImageView, imageData: ImageData) {
		val request = smallImageRequest(this, imageData, imageView)
		imageLoader.enqueue(request)
	}


	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.menu_images_option, menu)
		tagHolder.menuItem = menu.findItem(R.id.menu_images_option_star)
		tagHolder.updateButton()
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
		R.id.menu_images_option_refresh -> {
			updateImages()
			true
		}
		R.id.menu_images_option_star -> {
			tagHolder.switchFavourite()
			true
		}
		else -> { super.onOptionsItemSelected(item) }
	}

	private fun updateImages() {
		viewModel.getImagesLinks(tagHolder.tag.name)
	}

	private fun getTag(): Tag? {
		intent.run {
			val name = getStringExtra(EXTRA_TAG_NAME) ?: return null
			val displayName = getStringExtra(EXTRA_TAG_DISPLAY_NAME) ?: return null
			val favourite = getBooleanExtra(EXTRA_TAG_FAVOURITE, false)
			val position = getIntExtra(EXTRA_TAG_NAME, -1)
			return Tag(name, displayName, favourite, position)
		}
	}


	companion object {

		private const val EXTRA_TAG_NAME = "tagName"
		private const val EXTRA_TAG_DISPLAY_NAME = "tagDisplayName"
		private const val EXTRA_TAG_FAVOURITE = "tagFavourite"
		private const val EXTRA_TAG_POSITION = "tagPosition"

		fun startActivity(context: Context, tag: Tag) {
			val intent = Intent(context, ImagesActivity::class.java)
			intent.putExtra(EXTRA_TAG_NAME, tag.name)
			intent.putExtra(EXTRA_TAG_DISPLAY_NAME, tag.displayName)
			intent.putExtra(EXTRA_TAG_FAVOURITE, tag.favourite)
			intent.putExtra(EXTRA_TAG_POSITION, tag.position)
			context.startActivity(intent)
		}

	}
}

class TagHolder(
	val tag: Tag,
	private val favouriteViewModel: FavouriteViewModel<Tag>,
	var menuItem: MenuItem? = null
) {

	init {
		updateButton()
	}

	fun switchFavourite() {
		tag.favourite = !tag.favourite
		favouriteViewModel.onFavourite(tag)
		updateButton()
	}

	fun updateButton() {
		if (tag.favourite) {
			menuItem?.setIcon(R.drawable.baseline_star_24)
		} else {
			menuItem?.setIcon(R.drawable.baseline_star_outline_24)
		}
	}

}

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
import kanti.wallperapp.viewmodel.ImagesViewModel

@AndroidEntryPoint
class ImagesActivity : AppCompatActivity() {

	private val columnCount = 3

	private lateinit var view: ActivityImagesBinding
	private val viewModel: ImagesViewModel by viewModels()

	private lateinit var tagName: String

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		view = ActivityImagesBinding.inflate(layoutInflater)
		setContentView(view.root)

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

		title = intent.getStringExtra(EXTRA_TAG_DISPLAY_NAME)
		intent.getStringExtra(EXTRA_TAG_NAME)?.also { tagName ->
			this.tagName = tagName
			viewModel.getImagesLinks(tagName)
		}
	}

	private fun onClickRecyclerImage(imageData: ImageData) {
		WallpaperInstallingActivity.startActivity(this, imageData)
	}

	private fun onBindImage(imageView: ImageView, imageData: ImageData) {
		val request = smallImageRequest(this, imageData, imageView)
		imageLoader.enqueue(request)
	}


	override fun onCreateOptionsMenu(menu: Menu?): Boolean {
		menuInflater.inflate(R.menu.menu_images_option, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
		R.id.menu_images_option_refresh -> {
			viewModel.getImagesLinks(tagName)
			true
		}
		else -> { super.onOptionsItemSelected(item) }
	}


	companion object {

		private const val EXTRA_TAG_NAME = "tagName"
		private const val EXTRA_TAG_DISPLAY_NAME = "tagDisplayName"

		fun startActivity(context: Context, tag: Tag) {
			val intent = Intent(context, ImagesActivity::class.java)
			intent.putExtra(EXTRA_TAG_NAME, tag.name)
			intent.putExtra(EXTRA_TAG_DISPLAY_NAME, tag.displayName)
			context.startActivity(intent)
		}

	}
}
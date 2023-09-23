package kanti.wallperapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import coil.imageLoader
import dagger.hilt.android.AndroidEntryPoint
import kanti.wallperapp.data.model.ImageLink
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

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		view = ActivityImagesBinding.inflate(layoutInflater)
		setContentView(view.root)

		view.recyclerViewImages.layoutManager = GridLayoutManager(this, columnCount)

		viewModel.imagesLinksLiveData.observe(this) { imagesUiState ->
			view.recyclerViewImages.removeAllViews()
			imagesUiState.images.apply {
				when (resultType) {
					RepositoryResultType.Success -> {
						view.recyclerViewImages.adapter = ImagesRecyclerAdapter(
							data ?: listOf(),
							::onClickRecyclerImage,
							::onBindImage
						)
					}
					RepositoryResultType.NotConnection -> {
						Toast.makeText(
							this@ImagesActivity,
							getString(R.string.net_error_connection),
							Toast.LENGTH_SHORT
						).show()
					}
					else -> {
						Toast.makeText(
							this@ImagesActivity,
							getString(R.string.net_unexpected_error),
							Toast.LENGTH_SHORT
						).show()
					}
				}
			}
		}

		title = intent.getStringExtra(EXTRA_TAG_DISPLAY_NAME)
		intent.getStringExtra(EXTRA_TAG_NAME)?.also { tagName ->
			viewModel.getImagesLinks(tagName)
		}
	}

	private fun onClickRecyclerImage(imageLink: ImageLink) {
	}

	private fun onBindImage(imageView: ImageView, imageLink: ImageLink) {
		val request = smallImageRequest(this, imageLink, imageView)
		imageLoader.enqueue(request)
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
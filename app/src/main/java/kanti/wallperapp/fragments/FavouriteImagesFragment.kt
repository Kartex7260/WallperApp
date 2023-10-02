package kanti.wallperapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.imageLoader
import dagger.hilt.android.AndroidEntryPoint
import kanti.wallperapp.WallpaperInstallingActivity
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.databinding.FragmentFavouriteImagesBinding
import kanti.wallperapp.net.smallImageRequest
import kanti.wallperapp.view.ImagesRecyclerAdapter
import kanti.wallperapp.viewmodel.FavouriteImagesViewModel

@AndroidEntryPoint
class FavouriteImagesFragment : Fragment() {

	private val columnCount = 3

	private lateinit var view: FragmentFavouriteImagesBinding
	private val viewModel: FavouriteImagesViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.getImages()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		view = FragmentFavouriteImagesBinding.inflate(inflater, container, false).apply {
			recyclerViewFavouriteImages.layoutManager = GridLayoutManager(requireContext(), columnCount)
		}
		return view.root
	}

	override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
		super.onViewCreated(v, savedInstanceState)
		viewModel.favouriteImagesLiveData.observe(viewLifecycleOwner) { uiState ->
			if (uiState.process) {
				view.recyclerViewFavouriteImages.adapter = null
				view.progressBarFavouriteImages.visibility = View.VISIBLE
				return@observe
			}

			view.progressBarFavouriteImages.visibility = View.INVISIBLE
			view.recyclerViewFavouriteImages.adapter = ImagesRecyclerAdapter(
				uiState.images ?: listOf(),
				::onImageClick,
				::onLoadImage,
				viewModel
			)
		}
	}

	override fun onResume() {
		super.onResume()
		updateFavouriteData()
	}

	private fun onImageClick(image: ImageData) {
		WallpaperInstallingActivity.startActivity(requireContext(), image)
	}

	private fun onLoadImage(imageView: ImageView, imageData: ImageData) {
		requireContext().apply {
			val request = smallImageRequest(viewLifecycleOwner, imageData, imageView)
			imageLoader.enqueue(request)
		}
	}

	private fun updateFavouriteData() {
		val adapter = view.recyclerViewFavouriteImages.adapter as ImagesRecyclerAdapter? ?: return
		val indexLiveData = viewModel.updateFavouriteImages(viewLifecycleOwner.lifecycleScope, adapter.imageData)
		indexLiveData.observe(viewLifecycleOwner) {
			adapter.notifyItemChanged(it)
		}
	}

}
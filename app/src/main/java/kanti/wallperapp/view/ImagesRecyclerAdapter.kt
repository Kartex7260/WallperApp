package kanti.wallperapp.view

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kanti.wallperapp.R
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.viewmodel.FavouriteViewModel

class ImagesRecyclerAdapter(
	val imageData: MutableList<ImageData>,
	private val lifecycleOwner: LifecycleOwner,
	private val onClick: (ImageData) -> Unit,
	private val onLoadImage: (ImageView, ImageData) -> Unit,
	private val favouriteImageViewModel: FavouriteViewModel<ImageData>
) : RecyclerView.Adapter<ImagesRecyclerAdapter.ImageViewHolder>(), DefaultLifecycleObserver {

	inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		val imageView: ImageView = (view.findViewById(R.id.imageViewWallpaperMini) as ImageView).apply {
			scaleType = ImageView.ScaleType.CENTER_CROP
		}
		private val starButton: StarButton = view.findViewById(R.id.starButtonWallpaper)

		fun setImage(image: ImageData, index: Int) {
			onLoadImage(imageView, image)
			imageView.setOnClickListener { onClick(image) }

			starButton.checked = image.favourite
			starButton.setOnClickListener {
				val liveData = favouriteImageViewModel.onFavourite(image, starButton.checked)
				liveData.observe(lifecycleOwner) { image ->
					imageData[index] = image
					setImageLazy(image, index)
					liveData.removeObservers(lifecycleOwner)
				}
			}
		}

		private fun setImageLazy(image: ImageData, index: Int) {
			imageView.setOnClickListener { onClick(image) }
			starButton.checked = image.favourite
			starButton.setOnClickListener {
				val liveData = favouriteImageViewModel.onFavourite(image, starButton.checked)
				liveData.observe(lifecycleOwner) { image ->
					imageData[index] = image
					setImageLazy(image, index)
					liveData.removeObservers(lifecycleOwner)
				}
			}
		}

	}

	init {
		lifecycleOwner.lifecycle.addObserver(this)
	}

	override fun onResume(owner: LifecycleOwner) {
		val liveData = favouriteImageViewModel.syncFavouriteData(imageData)
		liveData.observe(lifecycleOwner) { favouriteUiState ->
			favouriteUiState.apply {
				imageData[index] = value
				notifyItemChanged(index)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
		val imageView = LayoutInflater.from(parent.context).inflate(
			R.layout.view_image,
			parent,
			false
		)
		return ImageViewHolder(imageView)
	}

	override fun onViewAttachedToWindow(holder: ImageViewHolder) {
		super.onViewAttachedToWindow(holder)

		val oneThirdScreen = Resources.getSystem().displayMetrics.widthPixels / 3
		holder.imageView.apply {
			layoutParams.apply {
				width = oneThirdScreen
				height = oneThirdScreen
			}
		}
	}

	override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
		val image = imageData[position]
		holder.setImage(image, position)
	}

	override fun getItemCount(): Int = imageData.size

}
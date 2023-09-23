package kanti.wallperapp.view

import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kanti.wallperapp.data.model.ImageLink

class ImagesRecyclerAdapter(
	private val imageLinks: List<ImageLink>,
	private val onClick: (ImageLink) -> Unit,
	private val onBindImage: (ImageView, ImageLink) -> Unit
) : RecyclerView.Adapter<ImagesRecyclerAdapter.ImageViewHolder>() {

	class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		val imageView: ImageView = view as ImageView

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
		val imageView = ImageView(parent.context).apply {
			scaleType = ImageView.ScaleType.CENTER_CROP
		}
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
		val image = imageLinks[position]
		onBindImage(holder.imageView, image)
		holder.imageView.setOnClickListener { onClick(image) }
	}

	override fun getItemCount(): Int = imageLinks.size

}
package kanti.wallperapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import kanti.wallperapp.R
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.viewmodel.FavouriteViewModel

class TagsRecyclerAdapter(
	val tags: MutableList<Tag>,
	private val lifecycleOwner: LifecycleOwner,
	private val onClick: (Tag) -> Unit,
	private val favouriteViewModel: FavouriteViewModel<Tag>
) : RecyclerView.Adapter<TagsRecyclerAdapter.TagViewHolder>(), DefaultLifecycleObserver {

	inner class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		private val textViewTagDisplayName: TextView = view.findViewById(R.id.textViewTagDisplayName)
		private val starButtonTag: StarButton = view.findViewById(R.id.starButtonTag)

		fun setTag(tag: Tag, index: Int) {
			textViewTagDisplayName.text = tag.displayName

			starButtonTag.checked = tag.favourite
			starButtonTag.setOnClickListener {
				val liveData = favouriteViewModel.onFavourite(tag, starButtonTag.checked)
				liveData.observe(lifecycleOwner) { tag ->
					tags[index] = tag
					notifyItemChanged(index)
					liveData.removeObservers(lifecycleOwner)
				}
			}

			itemView.setOnClickListener { onClick(tag) }
		}

	}

	init {
		lifecycleOwner.lifecycle.addObserver(this)
	}

	override fun onResume(owner: LifecycleOwner) {
		super.onResume(owner)
		val favouriteLiveData = favouriteViewModel.syncFavouriteData(tags)
		favouriteLiveData.observe(lifecycleOwner) { favouriteUiState ->
			favouriteUiState.apply {
				tags[index] = value
				notifyItemChanged(index)
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val view = layoutInflater.inflate(R.layout.view_tag_card, parent, false)
		return TagViewHolder(view)
	}

	override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
		val tag = tags[position]
		holder.setTag(tag, position)
	}

	override fun getItemCount(): Int = tags.size

}
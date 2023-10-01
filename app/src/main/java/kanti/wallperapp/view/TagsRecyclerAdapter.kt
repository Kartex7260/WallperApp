package kanti.wallperapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kanti.wallperapp.R
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.domain.OnFavourite

class TagsRecyclerAdapter(
	val tags: List<Tag>,
	private val onClick: (Tag) -> Unit,
	private val favouriteViewModel: OnFavourite<Tag>
) : RecyclerView.Adapter<TagsRecyclerAdapter.TagViewHolder>() {

	inner class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		private val textViewTagDisplayName: TextView = view.findViewById(R.id.textViewTagDisplayName)
		private val starButtonTag: StarButton = view.findViewById(R.id.starButtonTag)

		fun setTag(tag: Tag) {
			textViewTagDisplayName.text = tag.displayName

			starButtonTag.checked = tag.favourite
			starButtonTag.setOnClickListener {
				tag.favourite = starButtonTag.checked
				favouriteViewModel.onFavourite(tag)
			}

			itemView.setOnClickListener { onClick(tag) }
		}

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val view = layoutInflater.inflate(R.layout.view_tag_card, parent, false)
		return TagViewHolder(view)
	}

	override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
		val tag = tags[position]
		holder.setTag(tag)
	}

	override fun getItemCount(): Int = tags.size

}
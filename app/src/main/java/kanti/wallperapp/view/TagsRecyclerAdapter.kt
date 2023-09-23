package kanti.wallperapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kanti.wallperapp.R
import kanti.wallperapp.data.model.Tag

class TagsRecyclerAdapter(
	private val tags: List<Tag>,
	private val onClick: (Tag) -> Unit
) : RecyclerView.Adapter<TagsRecyclerAdapter.TagViewHolder>() {

	class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		val textViewTagDisplayName: TextView = view.findViewById(R.id.textViewTagDisplayName)

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val view = layoutInflater.inflate(R.layout.view_tag_card, parent, false)
		return TagViewHolder(view)
	}

	override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
		val tag = tags[position]
		holder.textViewTagDisplayName.text = tag.displayName
		holder.itemView.setOnClickListener { onClick(tag) }
	}

	override fun getItemCount(): Int = tags.size

}
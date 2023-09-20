package kanti.wallperapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kanti.wallperapp.R
import kanti.wallperapp.data.repositories.Tag

class TagListRecyclerAdapter(
	private val tags: List<Tag>,
	private val onClick: (String) -> Unit
) : RecyclerView.Adapter<TagListRecyclerAdapter.TagViewHolder>() {

	class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		val textViewTagDisplayName: TextView = view.findViewById(R.id.textViewTagDisplayName)

	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
		val layoutInflater = LayoutInflater.from(parent.context)
		val view = layoutInflater.inflate(R.layout.tag_card_view, parent, false)
		return TagViewHolder(view)
	}

	override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
		val tag = tags[position]
		holder.textViewTagDisplayName.text = tag.displayName
		holder.itemView.setOnClickListener { onClick(tag.name) }
	}

	override fun getItemCount(): Int = tags.size

}
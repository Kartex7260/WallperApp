package kanti.wallperapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kanti.wallperapp.ImagesActivity
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.databinding.FragmentFavouriteTagsBinding
import kanti.wallperapp.view.TagsRecyclerAdapter
import kanti.wallperapp.viewmodel.FavouriteTagsViewModel

@AndroidEntryPoint
class FavouriteTagsFragment : Fragment() {

	private lateinit var viewBind: FragmentFavouriteTagsBinding
	private val viewModel: FavouriteTagsViewModel by viewModels()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.getTags()
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		viewBind = FragmentFavouriteTagsBinding.inflate(inflater)
		return viewBind.root
	}

	override fun onStart() {
		super.onStart()
		viewBind.recyclerViewFavouriteTags.layoutManager = LinearLayoutManager(requireContext())

		viewModel.tagsLiveData.observe(viewLifecycleOwner) {
			if (it.process) {
				viewBind.recyclerViewFavouriteTags.adapter = null
				viewBind.progressBarFavouriteTags.visibility = View.VISIBLE
				return@observe
			}

			viewBind.progressBarFavouriteTags.visibility = View.INVISIBLE
			viewBind.recyclerViewFavouriteTags.adapter = TagsRecyclerAdapter(
				it.tags ?: mutableListOf(),
				viewLifecycleOwner,
				::onClickTag,
				viewModel.favouriteTagViewModel
			)
		}
	}

	private fun onClickTag(tag: Tag) {
		ImagesActivity.startActivity(requireContext(), tag)
	}

}
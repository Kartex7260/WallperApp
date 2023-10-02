package kanti.wallperapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import kanti.wallperapp.R
import kanti.wallperapp.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {

	private lateinit var viewBind: FragmentFavouritesBinding
	private val backStack = "favourite"

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		viewBind = FragmentFavouritesBinding.inflate(layoutInflater)
		return viewBind.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		viewBind.apply {
			cardViewFavouriteTags.setOnClickListener {
				parentFragmentManager.commit {
					setReorderingAllowed(true)
					replace<FavouriteTagsFragment>(R.id.fragment_container_favourites)
					addToBackStack(backStack)
				}
			}
			cardViewFavouriteImages.setOnClickListener {
				parentFragmentManager.commit {
					setReorderingAllowed(true)
					replace<FavouriteImagesFragment>(R.id.fragment_container_favourites)
					addToBackStack(backStack)
				}
			}
		}
	}

}
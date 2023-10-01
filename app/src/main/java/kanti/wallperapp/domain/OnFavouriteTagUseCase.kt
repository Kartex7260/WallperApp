package kanti.wallperapp.domain

import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.FavouriteTagsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnFavouriteTagUseCase @Inject constructor(
	private val favouriteTag: FavouriteTagsRepository
) {

	operator fun invoke(coroutineScope: CoroutineScope, tag: Tag) {
		coroutineScope.launch {
			if (tag.favourite) {
				favouriteTag.add(tag)
			} else {
				favouriteTag.delete(tag)
			}
		}
	}

}
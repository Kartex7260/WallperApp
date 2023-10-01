package kanti.wallperapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.FavouriteTagsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateFavouriteTagsUseCase @Inject constructor(
	private val favouriteTags: FavouriteTagsRepository
) {

	operator fun invoke(coroutineScope: CoroutineScope, tags: List<Tag>): LiveData<Int> {
		val liveData = MutableLiveData<Int>()
		coroutineScope.launch {
			val tagsFlow = tags.asFlow()
			tagsFlow.collectIndexed { index, tag ->
				val isFavourite = favouriteTags.isFavourite(tag)
				if (isFavourite != tag.favourite) {
					tag.favourite = isFavourite
					liveData.postValue(index)
				}
			}
		}
		return liveData
	}

}
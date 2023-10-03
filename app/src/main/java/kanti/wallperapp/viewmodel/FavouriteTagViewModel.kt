package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.FavouriteTagsRepository
import kanti.wallperapp.viewmodel.uistate.FavouriteUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

class FavouriteTagViewModel(
	private val coroutineScope: CoroutineScope,
	private val favouriteTagsRepository: FavouriteTagsRepository
) : FavouriteViewModel<Tag> {

	override fun onFavourite(value: Tag, isFavourite: Boolean): LiveData<Tag> {
		val liveData = MutableLiveData<Tag>()
		coroutineScope.launch {
			val newTag = favouriteTagsRepository.onFavourite(value, isFavourite)
			liveData.postValue(newTag)
		}
		return liveData
	}

	override fun syncFavouriteData(values: List<Tag>): LiveData<FavouriteUiState<Tag>> {
		val liveData = MutableLiveData<FavouriteUiState<Tag>>()
		coroutineScope.launch {
			values.asFlow().collectIndexed { index, tag ->
				val syncedTag = favouriteTagsRepository.syncFavourite(tag)
				if (syncedTag != null) {
					val uiState = FavouriteUiState(syncedTag, index)
					liveData.postValue(uiState)
				}
			}
		}
		return liveData
	}

}
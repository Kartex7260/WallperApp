package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.FavouriteTagsRepository
import kanti.wallperapp.viewmodel.uistate.FavouriteTagsUiState
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteTagsViewModel @Inject constructor(
	private val favouriteTags: FavouriteTagsRepository
) : ViewModel(), FavouriteViewModel<Tag> {

	private val _tagsLiveData = MutableLiveData<FavouriteTagsUiState>()
	val tagsLiveData: LiveData<FavouriteTagsUiState> = _tagsLiveData

	fun getTags() {
		_tagsLiveData.value = FavouriteTagsUiState(process = true)
		viewModelScope.launch {
			val tags = favouriteTags.getAll()
			val uiState = FavouriteTagsUiState(tags)
			_tagsLiveData.postValue(uiState)
		}
	}

	override fun onFavourite(value: Tag) {
		viewModelScope.launch {
			if (value.favourite) {
				favouriteTags.add(value)
			} else {
				favouriteTags.delete(value)
			}
		}
	}

	fun updateFavouriteTag(tags: List<Tag>): LiveData<Int> {
		val liveData = MutableLiveData<Int>()
		viewModelScope.launch {
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
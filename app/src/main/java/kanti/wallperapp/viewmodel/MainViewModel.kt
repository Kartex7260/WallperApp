package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.FavouriteTagsRepository
import kanti.wallperapp.data.repositories.TagsRepository
import kanti.wallperapp.viewmodel.uistate.TagsUiState
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val tagsRepository: TagsRepository,
	private val favouritesTags: FavouriteTagsRepository
) : ViewModel() {

	val favouriteTagViewModel: FavouriteViewModel<Tag> by lazy {
		FavouriteTagViewModel(viewModelScope, favouritesTags)
	}

	private val _tagsLiveData = MutableLiveData<TagsUiState>()
	val tagsLiveData: LiveData<TagsUiState> = _tagsLiveData

	fun getTags() {
		_tagsLiveData.value = TagsUiState(process = true)
		viewModelScope.launch {
			val tags = tagsRepository.getTags()
			syncFavouriteData(tags.data ?: mutableListOf())
			_tagsLiveData.postValue(TagsUiState(tags))
		}
	}

	private suspend fun syncFavouriteData(list: MutableList<Tag>) {
		list.asFlow().collectIndexed { index, tag ->
			val syncedTag = favouritesTags.syncFavourite(tag)
			if (syncedTag != null) {
				list[index] = syncedTag
			}
		}
	}

}
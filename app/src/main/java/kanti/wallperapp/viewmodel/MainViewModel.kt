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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val tagsRepository: TagsRepository,
	private val favouritesTags: FavouriteTagsRepository
) : ViewModel(), FavouriteViewModel<Tag> {

	private val _tagsLiveData = MutableLiveData<TagsUiState>()
	val tagsLiveData: LiveData<TagsUiState> = _tagsLiveData

	fun getTags() {
		_tagsLiveData.value = TagsUiState(process = true)
		viewModelScope.launch {
			val tags = tagsRepository.getTags()
			getFavourites(tags.data ?: listOf())
			_tagsLiveData.postValue(TagsUiState(tags))
		}
	}

	fun updateFavouriteTag(tags: List<Tag>): LiveData<Int> {
		val liveData = MutableLiveData<Int>()
		viewModelScope.launch {
			val tagsFlow = tags.asFlow()
			tagsFlow.collectIndexed { index, tag ->
				val isFavourite = favouritesTags.isFavourite(tag)
				if (isFavourite != tag.favourite) {
					tag.favourite = isFavourite
					liveData.postValue(index)
				}
			}
		}
		return liveData
	}

	override fun onFavourite(value: Tag) {
		viewModelScope.launch {
			if (value.favourite) {
				favouritesTags.add(value)
			} else {
				favouritesTags.delete(value)
			}
		}
	}

	private suspend fun getFavourites(list: List<Tag>) {
		val tagsFlow = list.asFlow()
		tagsFlow.onEach { tag ->
			val isFavourite = favouritesTags.isFavourite(tag)
			tag.favourite = isFavourite
		}.collect()
	}

}
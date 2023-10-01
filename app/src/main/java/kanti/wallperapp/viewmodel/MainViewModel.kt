package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.FavouriteTagsRepository
import kanti.wallperapp.data.repositories.TagsRepository
import kanti.wallperapp.domain.OnFavourite
import kanti.wallperapp.domain.OnFavouriteTagUseCase
import kanti.wallperapp.domain.UpdateFavouriteTagsUseCase
import kanti.wallperapp.viewmodel.uistate.TagsUiState
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val tagsRepository: TagsRepository,
	private val favouritesTags: FavouriteTagsRepository,
	private val onFavouriteTag: OnFavouriteTagUseCase,
	private val updateFavouriteTags: UpdateFavouriteTagsUseCase
) : ViewModel(), OnFavourite<Tag> {

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

	override fun onFavourite(value: Tag) {
		onFavouriteTag(viewModelScope, value)
	}

	fun updateFavouriteTags(tags: List<Tag>): LiveData<Int> {
		return updateFavouriteTags(viewModelScope, tags)
	}

	private suspend fun getFavourites(list: List<Tag>) {
		val tagsFlow = list.asFlow()
		tagsFlow.onEach { tag ->
			val isFavourite = favouritesTags.isFavourite(tag)
			tag.favourite = isFavourite
		}.collect()
	}

}
package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.FavouriteTagsRepository
import kanti.wallperapp.viewmodel.uistate.FavouriteTagsUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteTagsViewModel @Inject constructor(
	private val favouriteTags: FavouriteTagsRepository
) : ViewModel() {

	private val _tagsLiveData = MutableLiveData<FavouriteTagsUiState>()
	val tagsLiveData: LiveData<FavouriteTagsUiState> = _tagsLiveData

	val favouriteTagViewModel: FavouriteViewModel<Tag> by lazy {
		FavouriteTagViewModel(viewModelScope, favouriteTags)
	}

	fun getTags() {
		_tagsLiveData.value = FavouriteTagsUiState(process = true)
		viewModelScope.launch {
			val tags = favouriteTags.getAll()
			val uiState = FavouriteTagsUiState(tags)
			_tagsLiveData.postValue(uiState)
		}
	}

}
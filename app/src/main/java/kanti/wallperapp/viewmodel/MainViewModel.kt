package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.wallperapp.data.repositories.TagsRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
	private val tagsRepository: TagsRepository
) : ViewModel() {

	private val _tagsLiveData = MutableLiveData<TagsUiState>()
	val tagsLiveData: LiveData<TagsUiState> = _tagsLiveData

	fun getTags() {
		viewModelScope.launch {
			val tags = tagsRepository.getTags()
			_tagsLiveData.postValue(TagsUiState(tags))
		}
	}

}
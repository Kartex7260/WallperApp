package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.wallperapp.data.repositories.ImageLinksRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
	private val imagesLinkRepository: ImageLinksRepository
) : ViewModel() {

	private val _imagesLinksLiveData: MutableLiveData<ImagesUiState> = MutableLiveData()
	val imagesLinksLiveData: LiveData<ImagesUiState> = _imagesLinksLiveData

	fun getImagesLinks(tagName: String) {
		viewModelScope.launch {
			val imagesLinks = imagesLinkRepository.getImageLinks(tagName)
			_imagesLinksLiveData.postValue(ImagesUiState(imagesLinks))
		}
	}

}
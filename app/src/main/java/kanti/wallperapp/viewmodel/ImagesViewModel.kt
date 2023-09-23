package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.wallperapp.data.repositories.ImageDatasRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
	private val imagesLinkRepository: ImageDatasRepository
) : ViewModel() {

	private val _imagesLinksLiveData: MutableLiveData<ImagesUiState> = MutableLiveData()
	val imagesLinksLiveData: LiveData<ImagesUiState> = _imagesLinksLiveData

	fun getImagesLinks(tagName: String) {
		_imagesLinksLiveData.value = ImagesUiState(process = true)

		viewModelScope.launch {
			val imagesLinks = imagesLinkRepository.getImageLinks(tagName)
			_imagesLinksLiveData.postValue(ImagesUiState(imagesLinks))
		}
	}

}
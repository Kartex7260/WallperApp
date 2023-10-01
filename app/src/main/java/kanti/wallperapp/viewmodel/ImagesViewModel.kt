package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.ImageDataRepository
import kanti.wallperapp.domain.OnFavourite
import kanti.wallperapp.domain.OnFavouriteTagUseCase
import kanti.wallperapp.viewmodel.uistate.ImagesUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
	private val imagesLinkRepository: ImageDataRepository,
	private val onFavouriteTag: OnFavouriteTagUseCase
) : ViewModel() {

	private var onFavouriteTagHolder: OnFavourite<Tag>? = null

	private val _imagesLinksLiveData: MutableLiveData<ImagesUiState> = MutableLiveData()
	val imagesLinksLiveData: LiveData<ImagesUiState> = _imagesLinksLiveData

	fun getImagesLinks(tagName: String) {
		_imagesLinksLiveData.value = ImagesUiState(process = true)

		viewModelScope.launch {
			val imagesLinks = imagesLinkRepository.getImageLinks(tagName)
			_imagesLinksLiveData.postValue(ImagesUiState(imagesLinks))
		}
	}

	fun getOnFavouriteTag(): OnFavourite<Tag> {
		if (onFavouriteTagHolder == null) {
			onFavouriteTagHolder = object : OnFavourite<Tag> {
				override fun onFavourite(value: Tag) {
					onFavouriteTag(viewModelScope, value)
				}
			}
		}
		return onFavouriteTagHolder!!
	}

}
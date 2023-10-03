package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.repositories.FavouriteImagesRepository
import kanti.wallperapp.data.repositories.FavouriteTagsRepository
import kanti.wallperapp.data.repositories.ImageDataRepository
import kanti.wallperapp.viewmodel.uistate.ImagesUiState
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
	private val imageDataRepository: ImageDataRepository,
	private val favouriteImagesRepository: FavouriteImagesRepository,
	private val favouriteTagsRepository: FavouriteTagsRepository
) : ViewModel() {

	val favouriteTagViewModel: FavouriteViewModel<Tag> by lazy {
		FavouriteTagViewModel(viewModelScope, favouriteTagsRepository)
	}
	val favouriteImageViewModel: FavouriteViewModel<ImageData> by lazy {
		FavouriteImageViewModel(viewModelScope, favouriteImagesRepository)
	}

	private val _imagesLinksLiveData: MutableLiveData<ImagesUiState> = MutableLiveData()
	val imagesLinksLiveData: LiveData<ImagesUiState> = _imagesLinksLiveData

	fun getImagesLinks(tagName: String) {
		_imagesLinksLiveData.value = ImagesUiState(process = true)

		viewModelScope.launch {
			val imagesLinks = imageDataRepository.getImageLinks(tagName)
			syncFavourite(imagesLinks.data)
			_imagesLinksLiveData.postValue(ImagesUiState(imagesLinks))
		}
	}

	private suspend fun syncFavourite(images: MutableList<ImageData>?) {
		if (images == null) return
		images.asFlow().collectIndexed { index, image ->
			val syncedImage = favouriteImagesRepository.syncFavourite(image)
			if (syncedImage != null) {
				images[index] = syncedImage
			}
		}
	}

}
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
import kanti.wallperapp.domain.OnFavourite
import kanti.wallperapp.domain.UpdateFavouriteImagesUseCase
import kanti.wallperapp.viewmodel.uistate.ImagesUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
	private val imageDataRepository: ImageDataRepository,
	private val favouriteImagesRepository: FavouriteImagesRepository,
	private val favouriteTagsRepository: FavouriteTagsRepository,
	private val updateFavouriteImagesUseCase: UpdateFavouriteImagesUseCase
) : ViewModel(), OnFavourite<ImageData> {

	private var onFavouriteTagHolder: OnFavourite<Tag>? = null

	private val _imagesLinksLiveData: MutableLiveData<ImagesUiState> = MutableLiveData()
	val imagesLinksLiveData: LiveData<ImagesUiState> = _imagesLinksLiveData

	fun getImagesLinks(tagName: String) {
		_imagesLinksLiveData.value = ImagesUiState(process = true)

		viewModelScope.launch {
			val imagesLinks = imageDataRepository.getImageLinks(tagName)
			imagesLinks.data?.forEach { image ->
				image.favourite = favouriteImagesRepository.isFavourite(image)
			}
			_imagesLinksLiveData.postValue(ImagesUiState(imagesLinks))
		}
	}

	fun getOnFavouriteTag(): OnFavourite<Tag> {
		if (onFavouriteTagHolder == null) {
			onFavouriteTagHolder = object : OnFavourite<Tag> {
				override fun onFavourite(value: Tag) {
					viewModelScope.launch {
						favouriteTagsRepository.onFavourite(value)
					}
				}
			}
		}
		return onFavouriteTagHolder!!
	}

	fun updateFavouriteImages(coroutineScope: CoroutineScope, images: List<ImageData>): LiveData<Int> {
		return updateFavouriteImagesUseCase(coroutineScope, images)
	}

	override fun onFavourite(value: ImageData) {
		viewModelScope.launch {
			favouriteImagesRepository.onFavourite(value)
		}
	}

}
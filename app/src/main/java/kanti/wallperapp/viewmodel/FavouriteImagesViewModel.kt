package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.data.repositories.FavouriteImagesRepository
import kanti.wallperapp.viewmodel.uistate.FavouriteImagesUiState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteImagesViewModel @Inject constructor(
	private val favouriteImages: FavouriteImagesRepository
) : ViewModel() {

	val favouriteImageViewModel: FavouriteViewModel<ImageData> by lazy {
		FavouriteImageViewModel(viewModelScope, favouriteImages)
	}

	private val _favouriteImagesLiveData = MutableLiveData<FavouriteImagesUiState>()
	val favouriteImagesLiveData: LiveData<FavouriteImagesUiState> = _favouriteImagesLiveData

	fun getImages() {
		_favouriteImagesLiveData.value = FavouriteImagesUiState(process = true)
		viewModelScope.launch {
			val images = favouriteImages.getAll()
			_favouriteImagesLiveData.postValue(FavouriteImagesUiState(images))
		}
	}

}
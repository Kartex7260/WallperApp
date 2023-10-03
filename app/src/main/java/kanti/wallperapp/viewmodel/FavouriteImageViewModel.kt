package kanti.wallperapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.data.repositories.FavouriteImagesRepository
import kanti.wallperapp.viewmodel.uistate.FavouriteUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

class FavouriteImageViewModel(
	private val coroutineScope: CoroutineScope,
	private val favouriteImages: FavouriteImagesRepository
) : FavouriteViewModel<ImageData> {

	override fun onFavourite(value: ImageData, isFavourite: Boolean): LiveData<ImageData> {
		val liveData = MutableLiveData<ImageData>()
		coroutineScope.launch {
			val newImage = favouriteImages.onFavourite(value, isFavourite)
			liveData.postValue(newImage)
		}
		return liveData
	}

	override fun syncFavouriteData(values: List<ImageData>): LiveData<FavouriteUiState<ImageData>> {
		val liveData = MutableLiveData<FavouriteUiState<ImageData>>()
		coroutineScope.launch {
			values.asFlow().collectIndexed { index, imageData ->
				val syncedImage = favouriteImages.syncFavourite(imageData)
				if (syncedImage != null) {
					val uiState = FavouriteUiState(syncedImage, index)
					liveData.postValue(uiState)
				}
			}
		}
		return liveData
	}

}
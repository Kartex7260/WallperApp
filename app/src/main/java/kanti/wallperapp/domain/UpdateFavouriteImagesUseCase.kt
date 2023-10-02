package kanti.wallperapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.data.repositories.FavouriteImagesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateFavouriteImagesUseCase @Inject constructor(
	private val favouriteImages: FavouriteImagesRepository
) {

	operator fun invoke(coroutineScope: CoroutineScope, list: List<ImageData>): LiveData<Int> {
		val liveData = MutableLiveData<Int>()
		coroutineScope.launch {
			val imagesFlow = list.asFlow()
			imagesFlow.collectIndexed { index, value ->
				val isFavourite = favouriteImages.isFavourite(value)
				if (value.favourite != isFavourite) {
					value.favourite = isFavourite
					liveData.postValue(index)
				}
			}
		}
		return liveData
	}

}
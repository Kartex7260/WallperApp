package kanti.wallperapp.data.repositories

import kanti.wallperapp.data.datasource.FavouriteImagesLocalDataSource
import kanti.wallperapp.data.model.ImageData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteImagesRepository @Inject constructor(
	private val favouriteImages: FavouriteImagesLocalDataSource
) {

	suspend fun getAll(): List<ImageData> = favouriteImages.getAll()

	suspend fun onFavourite(image: ImageData) {
		if (image.favourite) {
			favouriteImages.add(image)
		} else {
			favouriteImages.delete(image)
		}
	}

	suspend fun isFavourite(image: ImageData) = favouriteImages.isFavourite(image)

}
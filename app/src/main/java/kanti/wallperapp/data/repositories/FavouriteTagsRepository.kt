package kanti.wallperapp.data.repositories

import kanti.wallperapp.data.datasource.FavoriteTagsLocalDataSource
import kanti.wallperapp.data.model.Tag
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteTagsRepository @Inject constructor(
	private val favouriteTagsLocal: FavoriteTagsLocalDataSource
) {

	suspend fun getAll(): List<Tag> = favouriteTagsLocal.getAll()

	suspend fun onFavourite(tag: Tag) {
		if (tag.favourite) {
			favouriteTagsLocal.add(tag)
		} else {
			favouriteTagsLocal.delete(tag)
		}
	}

	suspend fun isFavourite(tag: Tag) = favouriteTagsLocal.isFavourite(tag)

}
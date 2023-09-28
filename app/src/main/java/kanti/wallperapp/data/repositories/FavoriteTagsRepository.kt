package kanti.wallperapp.data.repositories

import kanti.wallperapp.data.datasource.FavoriteTagsLocalDataSource
import kanti.wallperapp.data.model.Tag
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteTagsRepository @Inject constructor(
	private val localDataSource: FavoriteTagsLocalDataSource
) {

	suspend fun getAll(): List<Tag> = localDataSource.getAll()

	suspend fun add(tag: Tag) = localDataSource.add(tag)

	suspend fun delete(tag: Tag) = localDataSource.delete(tag)

}
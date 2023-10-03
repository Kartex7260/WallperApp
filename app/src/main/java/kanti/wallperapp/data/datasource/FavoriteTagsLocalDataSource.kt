package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.model.Tag

interface FavoriteTagsLocalDataSource {

	suspend fun getAll(): MutableList<Tag>

	suspend fun get(tag: Tag): Tag

	suspend fun insert(tag: Tag)

	suspend fun delete(tag: Tag)

	suspend fun isFavourite(tag: Tag): Boolean

}
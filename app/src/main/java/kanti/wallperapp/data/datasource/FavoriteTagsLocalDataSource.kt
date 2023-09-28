package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.model.Tag

interface FavoriteTagsLocalDataSource {

	suspend fun getAll(): List<Tag>

	suspend fun add(tag: Tag)

	suspend fun delete(tag: Tag)

}
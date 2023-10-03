package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.model.Tag

interface TagsRemoteDataSource {

	suspend fun getTags(): RemoteDataResult<MutableList<Tag>>

}
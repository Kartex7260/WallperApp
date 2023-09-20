package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.repositories.Tag

interface TagsRemoteDataSource {

	suspend fun getTags(): RemoteDataResult<List<Tag>>

}
package kanti.wallperapp.data.repositories

import kanti.wallperapp.data.Tag
import kanti.wallperapp.data.TagsRemoteDataSource
import kanti.wallperapp.data.retrofit.DataResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsRepository @Inject constructor(
	private val tagsRemote: TagsRemoteDataSource
) {

	suspend fun getTags(): DataResponse<List<Tag>> {
		return tagsRemote.getTags()
	}

}
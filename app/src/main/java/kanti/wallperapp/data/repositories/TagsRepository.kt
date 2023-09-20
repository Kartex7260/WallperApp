package kanti.wallperapp.data.repositories

import kanti.wallperapp.data.datasource.TagsRemoteDataSource
import kanti.wallperapp.data.datasource.toRepositoryResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagsRepository @Inject constructor(
	private val tagsRemote: TagsRemoteDataSource
) {

	suspend fun getTags(): RepositoryResult<List<Tag>> {
		val tags = tagsRemote.getTags()
		return tags.toRepositoryResult()
	}

}
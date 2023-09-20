package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.repositories.Tag
import kanti.wallperapp.data.retrofit.ImageService
import kanti.wallperapp.data.retrofit.MetaData
import kanti.wallperapp.data.retrofit.TagsDTO
import kanti.wallperapp.data.retrofit.toDataSourceResult
import kanti.wallperapp.di.DispatcherIO
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TagsRetrofitDataSource @Inject constructor(
	private val imageService: ImageService,
	@DispatcherIO private val coroutineContext: CoroutineContext
) : TagsRemoteDataSource {

	override suspend fun getTags(): RemoteDataResult<List<Tag>> {
		val tagsCall = imageService.getAllTags()
		val tagsResponse = withContext(coroutineContext) { tagsCall.awaitResponse() }
		return tagsResponse.toDataSourceResult(::tagsDtoToTagList)
	}

	private fun tagsDtoToTagList(metaData: MetaData<TagsDTO>?): List<Tag>? {
		if (metaData?.data == null)
			return null
		return metaData.data.tags.map { tagDto ->
			Tag(tagDto.name, tagDto.displayName)
		}
	}

}
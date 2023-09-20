package kanti.wallperapp.data

import kanti.wallperapp.data.retrofit.DataResponse
import kanti.wallperapp.data.retrofit.ImageService
import kanti.wallperapp.data.retrofit.TagsDTO
import kanti.wallperapp.di.DispatcherIO
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class TagsRetrofitDataSource @Inject constructor(
	private val imageService: ImageService,
	@DispatcherIO private val coroutineContext: CoroutineContext
) : TagsRemoteDataSource {

	override suspend fun getTags(): DataResponse<List<Tag>> {
		val tagsCall = imageService.getAllTags()
		val tagsResponse = withContext(coroutineContext) { tagsCall.awaitResponse() }
		return DataResponse(
			tagsResponse.isSuccessful,
			tagsResponse.code(),
			tagsResponse.body().convertToTagList()
		)
	}

	private fun DataResponse<TagsDTO>?.convertToTagList(): List<Tag>? {
		if (this == null || data == null)
			return null
		return data.tags.map { tagDTO ->
			Tag(tagDTO.name, tagDTO.displayName)
		}
	}

}
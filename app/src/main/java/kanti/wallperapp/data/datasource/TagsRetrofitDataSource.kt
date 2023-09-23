package kanti.wallperapp.data.datasource

import android.util.Log
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.retrofit.ImageService
import kanti.wallperapp.data.retrofit.model.MetaData
import kanti.wallperapp.data.retrofit.model.TagsDTO
import kanti.wallperapp.data.retrofit.toDataSourceResult
import kanti.wallperapp.net.NoConnectivityException
import kotlinx.coroutines.CancellationException
import retrofit2.awaitResponse
import javax.inject.Inject

class TagsRetrofitDataSource @Inject constructor(
	private val imageService: ImageService
) : TagsRemoteDataSource {

	private val logTag = "TagsRetrofitService"

	override suspend fun getTags(): RemoteDataResult<List<Tag>> {
		Log.d(logTag, "getTags()")
		val tagsCall = imageService.getAllTags()
		return try {
			val tagsResponse = tagsCall.awaitResponse()
			tagsResponse.toDataSourceResult(::tagsDtoToTagList)
		} catch (ex: NoConnectivityException) {
			Log.i(logTag, ex.message, ex)
			RemoteDataResult(resultType = RemoteDataResultType.NotConnection)
		} catch (ex: Exception) {
			Log.i(logTag, ex.message, ex)
			RemoteDataResult(resultType = RemoteDataResultType.Fail)
		} catch (ex: CancellationException) {
			throw ex
		}
	}

	private fun tagsDtoToTagList(metaData: MetaData<TagsDTO>?): List<Tag>? {
		if (metaData?.data == null)
			return null
		return metaData.data.tags.map { tagDto ->
			Tag(tagDto.name, tagDto.displayName)
		}
	}

}
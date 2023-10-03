package kanti.wallperapp.data.datasource

import android.util.Log
import kanti.wallperapp.data.model.Tag
import kanti.wallperapp.data.retrofit.ImageService
import kanti.wallperapp.data.retrofit.model.MetaData
import kanti.wallperapp.data.retrofit.model.TagsDTO
import kanti.wallperapp.data.retrofit.toDataSourceResult
import kanti.wallperapp.net.ConnectionChecker
import kotlinx.coroutines.CancellationException
import retrofit2.awaitResponse
import javax.inject.Inject

class TagsRetrofitDataSource @Inject constructor(
	private val imageService: ImageService,
	private val connectionChecker: ConnectionChecker
) : TagsRemoteDataSource {

	private val logTag = "TagsRetrofitService"

	override suspend fun getTags(): RemoteDataResult<MutableList<Tag>> {
		Log.d(logTag, "getTags()")

		if (!connectionChecker.isConnection()) {
			Log.i(logTag, "No connection!")
			return RemoteDataResult(resultType = RemoteDataResultType.NotConnection)
		}

		val tagsCall = imageService.getAllTags()
		return try {
			val tagsResponse = tagsCall.awaitResponse()
			tagsResponse.toDataSourceResult(::tagsDtoToTagList)
		} catch (ex: Exception) {
			Log.i(logTag, ex.message, ex)
			RemoteDataResult(resultType = RemoteDataResultType.Fail)
		} catch (ex: CancellationException) {
			throw ex
		}
	}

	private fun tagsDtoToTagList(metaData: MetaData<TagsDTO>?): MutableList<Tag>? {
		if (metaData?.data == null)
			return null
		return metaData.data.tags.mapIndexed { index, tagDto ->
			Tag(tagDto.name, tagDto.displayName, position = index)
		}.toMutableList()
	}

}
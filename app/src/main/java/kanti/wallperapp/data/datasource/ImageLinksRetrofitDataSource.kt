package kanti.wallperapp.data.datasource

import android.util.Log
import kanti.wallperapp.data.model.ImageData
import kanti.wallperapp.data.retrofit.ImageService
import kanti.wallperapp.data.retrofit.model.MetaData
import kanti.wallperapp.data.retrofit.model.TagItemsDTO
import kanti.wallperapp.data.retrofit.toDataSourceResult
import kanti.wallperapp.net.NoConnectivityException
import kotlinx.coroutines.CancellationException
import retrofit2.awaitResponse
import javax.inject.Inject

class ImageLinksRetrofitDataSource @Inject constructor(
	private val imageService: ImageService
) : ImageLinksRemoteDataSource {

	private val logTag = "ImageRetrofitService"

	override suspend fun getImages(tagName: String): RemoteDataResult<List<ImageData>> {
		Log.d(logTag, "getImages(\"$tagName\")")
		val tagImagesIdCall = imageService.getTagImagesId(tagName)
		return try {
			val response = tagImagesIdCall.awaitResponse()
			response.toDataSourceResult(::tagItemsDtoToTagList)
		} catch (ex: NoConnectivityException) {
			Log.i(logTag, ex.toString(), ex)
			RemoteDataResult(resultType = RemoteDataResultType.NotConnection)
		} catch (ex: Exception) {
			Log.i(logTag, ex.toString(), ex)
			RemoteDataResult(resultType = RemoteDataResultType.Fail)
		} catch (ex: CancellationException) {
			throw ex
		}
	}

	private fun tagItemsDtoToTagList(metaData: MetaData<TagItemsDTO>?): List<ImageData>? {
		if (metaData?.data == null)
			return null
		return metaData.data.items.flatMap { tagItem ->
			tagItem.images?.map { itemImage ->
				ImageData(tagItem.title, itemImage.link)
			} ?: listOf()
		}
	}

}
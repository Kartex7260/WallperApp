package kanti.wallperapp.data.datasource

import android.util.Log
import kanti.wallperapp.net.NoConnectivityException
import kanti.wallperapp.data.repositories.Image
import kanti.wallperapp.data.retrofit.ImageService
import kanti.wallperapp.data.retrofit.MetaData
import kanti.wallperapp.data.retrofit.TagItemsDTO
import kanti.wallperapp.data.retrofit.toDataSourceResult
import kotlinx.coroutines.CancellationException
import retrofit2.awaitResponse
import javax.inject.Inject

class ImageRetrofitDataSource @Inject constructor(
	private val imageService: ImageService
) : ImageRemoteDataSource {

	private val logTag = "ImageRetrofitService"

	override suspend fun getImages(tagName: String): RemoteDataResult<List<Image>> {
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

	private fun tagItemsDtoToTagList(metaData: MetaData<TagItemsDTO>?): List<Image>? {
		if (metaData?.data == null)
			return null
		return metaData.data.items.flatMap { tagItem ->
			tagItem.images.map { itemImage ->
				Image(itemImage.link)
			}
		}
	}

}
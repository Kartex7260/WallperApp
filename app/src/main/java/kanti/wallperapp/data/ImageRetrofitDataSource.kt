package kanti.wallperapp.data

import kanti.wallperapp.data.retrofit.DataResponse
import kanti.wallperapp.data.retrofit.ImageService
import kanti.wallperapp.data.retrofit.TagItemsDTO
import kanti.wallperapp.di.DispatcherIO
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ImageRetrofitDataSource @Inject constructor(
	private val imageService: ImageService,
	@DispatcherIO private val coroutineContext: CoroutineContext
) : ImageRemoteDataSource {

	override suspend fun getImages(tagName: String): DataResponse<List<Image>> {
		val tagImagesIdCall = imageService.getTagImagesId(tagName)
		val response = withContext(coroutineContext) { tagImagesIdCall.awaitResponse() }
		return DataResponse(
			response.isSuccessful,
			response.code(),
			response.body().convertTagItemsToImageList()
		)
	}

	private fun DataResponse<TagItemsDTO>?.convertTagItemsToImageList(): List<Image>? {
		if (this == null || data == null)
			return null
		return data.items.flatMap { tagItem ->
			tagItem.images.map { itemImage ->
				Image(itemImage.link)
			}
		}
	}

}
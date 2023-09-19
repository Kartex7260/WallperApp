package kanti.wallperapp.data

import kanti.wallperapp.data.retrofit.DataResponse
import kanti.wallperapp.data.retrofit.ImageService
import kanti.wallperapp.data.retrofit.TagItemsDTO
import retrofit2.awaitResponse
import javax.inject.Inject

class ImageRetrofitDataSource @Inject constructor(
	private val imageService: ImageService
) : ImageRemoteDataSource {

	override suspend fun getImages(tagName: String): DataResponse<List<Image>> {
		val tagImagesIdCall = imageService.getTagImagesId(tagName)
		val response = tagImagesIdCall.awaitResponse()
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
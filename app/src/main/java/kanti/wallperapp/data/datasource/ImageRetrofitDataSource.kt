package kanti.wallperapp.data.datasource

import kanti.wallperapp.data.repositories.Image
import kanti.wallperapp.data.retrofit.ImageService
import kanti.wallperapp.data.retrofit.MetaData
import kanti.wallperapp.data.retrofit.TagItemsDTO
import kanti.wallperapp.data.retrofit.toDataSourceResult
import kanti.wallperapp.di.DispatcherIO
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ImageRetrofitDataSource @Inject constructor(
	private val imageService: ImageService,
	@DispatcherIO private val coroutineContext: CoroutineContext
) : ImageRemoteDataSource {

	override suspend fun getImages(tagName: String): RemoteDataResult<List<Image>> {
		val tagImagesIdCall = imageService.getTagImagesId(tagName)
		val response = withContext(coroutineContext) { tagImagesIdCall.awaitResponse() }
		return response.toDataSourceResult(::tagItemsDtoToTagList)
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
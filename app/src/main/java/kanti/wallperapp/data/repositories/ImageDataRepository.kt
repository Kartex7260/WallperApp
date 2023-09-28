package kanti.wallperapp.data.repositories

import kanti.wallperapp.data.datasource.ImageLinksRemoteDataSource
import kanti.wallperapp.data.datasource.toRepositoryResult
import kanti.wallperapp.data.model.ImageData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageDataRepository @Inject constructor(
	private val imageRemote: ImageLinksRemoteDataSource
) {

	suspend fun getImageLinks(tagName: String): RepositoryResult<List<ImageData>> {
		val images = imageRemote.getImages(tagName)
		return images.toRepositoryResult()
	}

}
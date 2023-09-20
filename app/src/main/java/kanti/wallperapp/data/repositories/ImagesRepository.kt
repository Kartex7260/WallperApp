package kanti.wallperapp.data.repositories

import kanti.wallperapp.data.datasource.ImageRemoteDataSource
import kanti.wallperapp.data.datasource.toRepositoryResult
import javax.inject.Inject

class ImagesRepository @Inject constructor(
	private val imageRemote: ImageRemoteDataSource
) {

	suspend fun getImages(tagName: String): RepositoryResult<List<Image>> {
		val images = imageRemote.getImages(tagName)
		return images.toRepositoryResult()
	}

}
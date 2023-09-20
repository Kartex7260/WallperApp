package kanti.wallperapp.data.repositories

import kanti.wallperapp.data.Image
import kanti.wallperapp.data.ImageRemoteDataSource
import kanti.wallperapp.data.retrofit.DataResponse
import javax.inject.Inject

class ImageRepository @Inject constructor(
	private val imageRemote: ImageRemoteDataSource
) {

	suspend fun getImages(tagName: String): DataResponse<List<Image>> {
		return imageRemote.getImages(tagName)
	}

}